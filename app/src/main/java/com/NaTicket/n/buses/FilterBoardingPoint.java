package com.NaTicket.n.buses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.NaTicket.n.buses.busbeanclass.BoadingPoint;
import com.NaTicket.n.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ankit on 8/4/2016.
 */
public class FilterBoardingPoint extends AppCompatActivity {
    private ListView listView;
    Button Done;
    String BoardingTimes;
    JSONArray results;
    BoadingPoint Boardingpoint;
    private FilterBoardingAdapter myAppAdapter;

    private ArrayList<BoadingPoint> BoardingArray;
    ArrayList<String> List =new ArrayList<>();
    ArrayList<String> FilterOperaters =new ArrayList<>();
    String Boadinglocation;
    List<String> Array ;
    String JourneyDate, DayofJourney, OnResume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_operaters_list);

        SharedPreferences preff = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
        String data= preff.getString("FilterBoadingId", null);
        JourneyDate=preff.getString("JourneyDate",null);
        DayofJourney=preff.getString(("DayofJourney"),null);

        if(data!=null){
            Array  = Arrays.asList(data.split("~"));
        }

        Done=(Button)findViewById(R.id.OK);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String listString = TextUtils.join("~",FilterOperaters);
                System.out.println(listString);

                Intent i=new Intent(FilterBoardingPoint.this, FilterActivity.class);
                i.putExtra("OnResume","false");
                i.putExtra("JourneyDAte",JourneyDate);
                i.putExtra("DayofJourney",DayofJourney);

                SharedPreferences preference = getApplication().getSharedPreferences("Filter", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();

                if (!listString.equals("")) {
                    editor.putString("FilterBoadingId", listString);
                    editor.putString("FilterBoadingLocation", Boadinglocation);
                } else {
                    editor.putString("FilterBoadingId", null);
                    editor.putString("FilterBoadingLocation", null);
                }

                editor.apply();

                startActivity(i);
            }
        });

        listView = (ListView) findViewById(R.id.Filter_listView);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
        BoardingTimes = pref.getString("JsonResponce", null);
        BoardingArray = new ArrayList<>();
        System.out.println("Array****####" + BoardingTimes);
        JSONObject jsonObject;
        if(BoardingTimes!=null){
            BoardingArray.clear();
            try {
                jsonObject = new JSONObject(BoardingTimes);
                results = jsonObject.getJSONArray("AvailableTrips");

                for (int i = 0; i < results.length(); i++) {
                  //  Boardingpoint = new BoadingPoint();

                    JSONObject jobj = results.getJSONObject(i);


                    JSONArray jarry = jobj.getJSONArray("BoardingTimes");
                    System.out.println("Array****" + jarry);

                    for (int j = 0; j < jarry.length(); j++) {
                        JSONObject boardtime = jarry.getJSONObject(j);
                        Boardingpoint = new BoadingPoint();

                        Boardingpoint.setBoardingAddress(boardtime.getString("Address"));
                        Boardingpoint.setBoardingContactNumbers(boardtime.getString("ContactNumbers"));
                        Boardingpoint.setBoardingContactPersons(boardtime.getString("ContactPersons"));
                        Boardingpoint.setBoardingPointId(boardtime.getString("PointId"));
                        String Point=boardtime.getString("Location");
                        Boardingpoint.setBoardingLandmark(boardtime.getString("Landmark"));
                        Boardingpoint.setBoardingLocation(boardtime.getString("Location"));
                        Boardingpoint.setBoardingName(boardtime.getString("Name"));


                        if(List.contains(boardtime.getString("Location"))){
                            System.out.println("Locations : Repeted :" + boardtime.getString("Location"));
                        }else{
                            List.add(Point);
                            System.out.println("Locations :****" + boardtime.getString("Location"));
                            BoardingArray.add(Boardingpoint);
                        }



                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            myAppAdapter = new FilterBoardingAdapter(BoardingArray, FilterBoardingPoint.this);
            listView.setAdapter(myAppAdapter);
            myAppAdapter. sortByNameAsc();
        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            //  alertDialogBuilder.setTitle("Your Title");

            // set dialog message
            alertDialogBuilder
                    .setMessage(" No Boarding points available")
                    .setCancelable(false)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity

                            dialog.cancel();
                            Intent i=new Intent(FilterBoardingPoint.this, FilterActivity.class);
                            startActivity(i);
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }



    }


    public class FilterBoardingAdapter extends BaseAdapter {

        /**
         * Sort Available Bus Details list by name ascending
         */
        void sortByNameAsc() {
            Comparator<BoadingPoint> comparator = new Comparator<BoadingPoint>() {

                @Override
                public int compare(BoadingPoint object1, BoadingPoint object2) {
                    return object1.getBoardingLocation().compareToIgnoreCase(object2.getBoardingLocation());
                }
            };
            Collections.sort(BoardingArray, comparator);
            notifyDataSetChanged();
        }

        public class ViewHolder {
            TextView txtTitle;
            CheckBox check;

        }

        List<BoadingPoint> boadinglist;

        public Context context;
        ArrayList<BoadingPoint> arraylist;

        private FilterBoardingAdapter(List<BoadingPoint> apps, Context context) {
            this.boadinglist = apps;
            this.context = context;
            arraylist = new ArrayList<>();
            arraylist.addAll(boadinglist);

        }

        public int getViewTypeCount() {
            // TODO Auto-generated method stub
            return getCount();
        }

        @Override
        public int getItemViewType(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public int getCount() {
            return boadinglist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint({"InflateParams", "SetTextI18n"})
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            ViewHolder viewHolder;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.filter_operaters, null);
                viewHolder = new ViewHolder();
                viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.Filter_Operater);
                viewHolder.check = (CheckBox) rowView.findViewById(R.id.Filter_CheckBox);
                rowView.setTag(viewHolder);
                rowView.setTag(R.id.Filter_CheckBox, viewHolder.check);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            try{
                viewHolder.txtTitle.setText(boadinglist.get(position).getBoardingLocation() + "");

            }catch(Exception e){
                e.printStackTrace();
            }
            // viewHolder.check.setChecked(boadinglist.get(position).isSelected());
            if(Array!=null){
                for(int i=0; i<Array.size(); i++){
                    String Location =boadinglist.get(position).getBoardingLocation();
                    if(Array.contains(Location)){
                        viewHolder.check.setChecked(true);
                        viewHolder.txtTitle.setTextColor(getResources().getColor(R.color.colorAccent));
                        if(FilterOperaters.contains(boadinglist.get(position).getBoardingLocation())){

                        }else{
                            FilterOperaters.addAll(Arrays.<String>asList(boadinglist.get(position).getBoardingLocation()));
                        }

                    }
                }

            }
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boadinglocation = boadinglist.get(position).getBoardingLocation();
                    CheckBox chk = (CheckBox) v
                            .findViewById(R.id.Filter_CheckBox);
                    TextView BoardingPoint = v.findViewById(R.id.Filter_Operater);
                    if(FilterOperaters.size()!=0){
                        if (FilterOperaters.contains(boadinglist.get(position).getBoardingLocation())) {
                            System.out.println("row remove"+FilterOperaters);
                            FilterOperaters.remove(boadinglist.get(position).getBoardingLocation());
                            chk.setSelected(false);
                            chk.setChecked(false);
                            BoardingPoint.setTextColor(getResources().getColor(R.color.colorBlock));
                            System.out.println("row remove"+FilterOperaters);
                        } else {

                            FilterOperaters.add(boadinglist.get(position).getBoardingLocation());
                            chk.setSelected(true);
                            chk.setChecked(true);
                            BoardingPoint.setTextColor(getResources().getColor(R.color.colorAccent));
                            System.out.println("row add"+FilterOperaters+boadinglist.get(position).getBoardingLocation());

                        }
                    }else{
                        FilterOperaters.addAll(Arrays.<String>asList(boadinglist.get(position).getBoardingLocation()));
                        System.out.println("row add"+FilterOperaters+boadinglist.get(position).getBoardingLocation());
                        chk.setSelected(true);
                        chk.setChecked(true);
                        BoardingPoint.setTextColor(getResources().getColor(R.color.colorAccent));
                    }




                }
            });
            return rowView;


        }

        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());

            boadinglist.clear();
            if (charText.length() == 0) {
                boadinglist.addAll(arraylist);



            } else {
                for (BoadingPoint postDetail : arraylist) {
                    if (charText.length() != 0 && postDetail.getBoardingLocation().toLowerCase(Locale.getDefault()).contains(charText)) {
                        boadinglist.add(postDetail);


                    }


                }
            }
            myAppAdapter. sortByNameAsc();
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Boarding Point");
        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.curser); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }
        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                myAppAdapter.filter(searchQuery.trim());
                listView.invalidate();
                return true;

            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
