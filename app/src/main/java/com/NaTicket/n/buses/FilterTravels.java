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

import com.NaTicket.n.buses.busbeanclass.AvailableBusDetailsBean;
import com.NaTicket.n.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * Created by Ankit on 8/4/2016.
 */
public class FilterTravels extends AppCompatActivity {

    private ListView listView;
    Button Done;
    String Travels;
    JSONArray results;
    private ArrayList<AvailableBusDetailsBean> BoardingArray;
    AvailableBusDetailsBean BusDetailsBean;
    ArrayList<String> List = new ArrayList<>();
    ArrayList<String> FilterOperaters = new ArrayList<>();
    private TravelerAdapter myAppAdapter;
    String JourneyDate, DayofJourney, OnResume;

    String Operator;


    java.util.List<String> Array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_operaters_list);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
        String data = pref.getString("Filter_OperaterId", null);
        JourneyDate=pref.getString("JourneyDate",null);
        DayofJourney=pref.getString(("DayofJourney"),null);

        if (data != null) {
            Array = Arrays.asList(data.split("~"));
        }


        listView = (ListView) findViewById(R.id.Filter_listView);
        Done = (Button) findViewById(R.id.OK);
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String listString = TextUtils.join("~", FilterOperaters);


                System.out.println(listString);
                Intent i = new Intent(FilterTravels.this, FilterActivity.class);
                i.putExtra("OnResume", "false");
                i.putExtra("JourneyDAte",JourneyDate);
                i.putExtra("DayofJourney",DayofJourney);

                SharedPreferences preference = getApplication().getSharedPreferences("Filter", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
                Set<String> set = new HashSet<>();
                set.addAll(FilterOperaters);
                if(!listString.equals("")){
                    editor.putString("Filter_OperaterId", listString);
                    editor.putString("Filter_OperaterName", Operator);
                }else{
                    editor.putString("Filter_OperaterId", null);
                    editor.putString("Filter_OperaterName", null);
                }
                editor.apply();
                startActivity(i);
            }
        });

        SharedPreferences prefr = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
        Travels = prefr.getString("JsonResponce", null);
        BoardingArray = new ArrayList<>();
        JSONObject jsonObject;
        if (Travels != null) {
            try {
                jsonObject = new JSONObject(Travels);
                results = jsonObject.getJSONArray("AvailableTrips");

                for (int i = 0; i < results.length(); i++) {
                    BusDetailsBean = new AvailableBusDetailsBean();

                    JSONObject jobj = results.getJSONObject(i);
                    BusDetailsBean.setId(jobj.getString("Id"));
                    BusDetailsBean.setTravels(jobj.getString("Travels"));
                    String Travels = jobj.getString("Travels");
                    BusDetailsBean.setDisplayName(jobj.getString("DisplayName"));
                    BusDetailsBean.setBusType(jobj.getString("BusType"));

                    if (!Travels.equalsIgnoreCase("null")) {
                        if (!(List.contains(Travels))) {
                            List.add(Travels);
                            BoardingArray.add(BusDetailsBean);

                        }
                    } else {
                        if (!(List.contains(jobj.getString("Travels")))) {
                            List.add(jobj.getString("Travels"));
                            BoardingArray.add(BusDetailsBean);

                        }
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            myAppAdapter = new TravelerAdapter(BoardingArray, FilterTravels.this);
            myAppAdapter.sortByNameAsc();
            listView.setAdapter(myAppAdapter);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle("No Travels found!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("This could have happened due to:\n" +
                            "1. No bus operating on this route or \n" +
                            "2. All seats for this route being sold out.\n" +
                            "Here is what you can do to get back on track...")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity
                            dialog.cancel();
                            Intent i = new Intent(FilterTravels.this, FilterActivity.class);
                            startActivity(i);

                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }


    }


    public class TravelerAdapter extends BaseAdapter {

        public class ViewHolder {
            TextView txtTitle;
            CheckBox check;

        }

        java.util.List<AvailableBusDetailsBean> boadinglist;

        public Context context;
        ArrayList<AvailableBusDetailsBean> arraylist;

        private TravelerAdapter(List<AvailableBusDetailsBean> apps, Context context) {
            this.boadinglist = apps;
            this.context = context;
            arraylist = new ArrayList<>();
            arraylist.addAll(boadinglist);

        }

        /**
         * Sort Available Bus Details list by name ascending
         */
        void sortByNameAsc() {
            Comparator<AvailableBusDetailsBean> comparator = new Comparator<AvailableBusDetailsBean>() {

                @Override
                public int compare(AvailableBusDetailsBean object1, AvailableBusDetailsBean object2) {
                    return object1.getTravels().compareToIgnoreCase(object2.getTravels());
                }
            };
            Collections.sort(BoardingArray, comparator);
            notifyDataSetChanged();
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
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.Filter_Operater);
                viewHolder.check = (CheckBox) rowView.findViewById(R.id.Filter_CheckBox);
                rowView.setTag(viewHolder);
                rowView.setTag(R.id.Filter_CheckBox, viewHolder.check);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtTitle.setText(boadinglist.get(position).getDisplayName() + "");
            if (Array != null) {
                for (int i = 0; i < Array.size(); i++) {
                    if (!boadinglist.get(position).getDisplayName().equalsIgnoreCase("null")) {
                        if (Array.contains(boadinglist.get(position).getDisplayName())) {
                            viewHolder.check.setChecked(true);
                            viewHolder.txtTitle.setTextColor(getResources().getColor(R.color.colorAccent));
                            if (!FilterOperaters.contains(boadinglist.get(position).getDisplayName())) {
                                FilterOperaters.addAll(Collections.singletonList(boadinglist.get(position).getDisplayName()));
                            }
                        }
                    } else {
                        if (Array.contains(boadinglist.get(position).getTravels())) {
                            viewHolder.check.setChecked(true);
                            viewHolder.txtTitle.setTextColor(getResources().getColor(R.color.colorAccent));
                            if (!FilterOperaters.contains(boadinglist.get(position).getTravels())) {
                                FilterOperaters.addAll(Collections.singletonList(boadinglist.get(position).getTravels()));
                            }
                        }
                    }

                }

            }

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Operator = boadinglist.get(position).getDisplayName();
                    CheckBox chk = (CheckBox) v
                            .findViewById(R.id.Filter_CheckBox);
                    TextView operatorName=v.findViewById(R.id.Filter_Operater);

                    if (FilterOperaters.size() != 0) {
                        if (FilterOperaters.contains(boadinglist.get(position).getTravels())) {
                            System.out.println("row remove" + FilterOperaters);
                            FilterOperaters.remove(boadinglist.get(position).getTravels());
                            chk.setSelected(false);
                            chk.setChecked(false);
                            operatorName.setTextColor(getResources().getColor(R.color.colorBlock));
                            System.out.println("Operators Array remove" + FilterOperaters);
                        } else {

                            FilterOperaters.addAll(Collections.singletonList(boadinglist.get(position).getTravels()));
                            chk.setSelected(true);
                            chk.setChecked(true);
                            operatorName.setTextColor(getResources().getColor(R.color.colorAccent));
                            System.out.println("row add" + FilterOperaters + boadinglist.get(position).getTravels());
                            System.out.println("Operators Array add :" + FilterOperaters);
                        }
                    } else {
                        FilterOperaters.addAll(Collections.singletonList(boadinglist.get(position).getTravels()));
                        System.out.println("row add" + FilterOperaters + boadinglist.get(position).getTravels());
                        chk.setSelected(true);
                        chk.setChecked(true);
                        operatorName.setTextColor(getResources().getColor(R.color.colorAccent));
                        System.out.println("Operators Array  Add:" + FilterOperaters);
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
                for (AvailableBusDetailsBean postDetail : arraylist) {
                    if (charText.length() != 0 && postDetail.getDisplayName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        boadinglist.add(postDetail);
                    }
                }
            }
            myAppAdapter.sortByNameAsc();
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
        searchView.setQueryHint("operators");
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
