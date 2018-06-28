package com.NaTicket.n.buses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.NaTicket.n.buses.busbeanclass.BoadingPoint;
import com.NaTicket.n.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class BoadingPointActivity extends AppCompatActivity {

    private ListView listView;
    String BoardingTimes;
    BoadingPoint Boardingpoint;
    private BoardingAdapter myAppAdapter;

    String ReturnDate;


    String Operator, BusType, departureTime, BusTypeid, NetFare;
    String CancellationPolicy, PartialCancellationAllowed, Provider, ObiboAPIProvidrer;
    String JourneyDate, SelectedSeats, NumberOfSeats, BusTypeID;
    String ServiceTaxes, OperatorserviceCharge, Fares, SeatCodes, ConvienceFee, TotalConvienceFee;


    String PassengerIsReturnselected = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listView);
        SharedPreferences preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
        BoardingTimes = preference.getString("BoardingTimes", null);
        ReturnDate = preference.getString("PassengerRETURN_DATE", null);
        PassengerIsReturnselected = preference.getString("PassengerIsReturnselected", null);

        System.out.println("BoardingTimes" + BoardingTimes);

        //////***** Onward Bus Select Details in shared Preference *****////
        SharedPreferences preference1 = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
        Operator = preference1.getString("Operator", null);
        BusType = preference1.getString("BusType", null);
        BusTypeid = preference1.getString("BusTypeID", null);
        departureTime = preference1.getString("departureTime", null);
        CancellationPolicy = preference1.getString("CancellationPolicy", null);
        PartialCancellationAllowed = preference1.getString("PartialCancellationAllowed", null);
        Provider = preference1.getString("Provider", null);
        ObiboAPIProvidrer = preference1.getString("ObiboAPIProvider", null);
        JourneyDate = preference1.getString("JourneyDate", null);
        SelectedSeats = preference1.getString("SelectedSeats", null);
        NumberOfSeats = preference1.getString("NumberOfSeats", null);
        BusTypeID = preference1.getString("BusTypeID", null);
        NetFare = preference1.getString("NetFare", null);
        ServiceTaxes = preference1.getString("ServiceTaxes", null);
        OperatorserviceCharge = preference1.getString("OperatorserviceCharge", null);
        Fares = preference1.getString("Fares", null);
        SeatCodes = preference1.getString("SeatCodes", null);
        ConvienceFee = preference1.getString("ConvienceFee", null);
        TotalConvienceFee = preference1.getString("TotalConvienceFee", null);


        ArrayList<BoadingPoint> boadingPointArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(BoardingTimes);
            System.out.println("Array****" + jsonArray);

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject boardtime = jsonArray.getJSONObject(j);
                Boardingpoint = new BoadingPoint();
                Boardingpoint.setBoardingAddress(boardtime.getString("Address"));
                Boardingpoint.setBoardingContactNumbers(boardtime.getString("ContactNumbers"));
                Boardingpoint.setBoardingContactPersons(boardtime.getString("ContactPersons"));
                Boardingpoint.setBoardingPointId(boardtime.getString("PointId"));
                Boardingpoint.setBoardingLandmark(boardtime.getString("Landmark"));
                Boardingpoint.setBoardingLocation(boardtime.getString("Location"));
                Boardingpoint.setBoardingName(boardtime.getString("Name"));

                int time = Integer.parseInt(boardtime.getString("Time"));
                double time2 = time / 60;
                DecimalFormat df = new DecimalFormat("##.#");
                int minuts = time % 60;
                String timestring = df.format(time2) + ":" + minuts;
                final String Btime = timestring;

                try {
                    String _24HourTime = Btime;
                    SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                    Date _24HourDt = _24HourSDF.parse(_24HourTime);
                    System.out.println(_24HourDt);
                    String Time = _12HourSDF.format(_24HourDt);
                    Boardingpoint.setBoardingTime(Time);
                    System.out.println(Time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boadingPointArrayList.add(Boardingpoint);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myAppAdapter = new BoardingAdapter(boadingPointArrayList, BoadingPointActivity.this);
        listView.setAdapter(myAppAdapter);


    }

    public class BoardingAdapter extends BaseAdapter {

        public class ViewHolder {
            TextView txtTitle, txtSubTitle;



        }

        public List<BoadingPoint> boadinglist;

        public Context context;
        ArrayList<BoadingPoint> arraylist;

        private BoardingAdapter(List<BoadingPoint> apps, Context context) {
            this.boadinglist = apps;
            this.context = context;
            arraylist = new ArrayList<>();
            arraylist.addAll(boadinglist);

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
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            final ViewHolder viewHolder;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.boadingpoint_list_item, null);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.boading_point);
                viewHolder.txtSubTitle = (TextView) rowView.findViewById(R.id.boading_time);

                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtTitle.setText("" + boadinglist.get(position).getBoardingLocation() + ": ");
            viewHolder.txtSubTitle.setText("@ " + boadinglist.get(position).getBoardingTime() + "");

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    switch (PassengerIsReturnselected) {
                        case "Yes": {

                            Intent i = new Intent(context, SearchActivity.class);

                            SharedPreferences preference = context.getSharedPreferences("OnwardJourneyDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preference.edit();
                            editor.putString("OnwardOperator", Operator);
                            editor.putString("OnwardBusType", BusType);
                            editor.putString("OnwardBusTypeID", BusTypeid);
                            editor.putString("OnwarddepartureTime", departureTime);
                            editor.putString("OnwardCancellationPolicy", CancellationPolicy);
                            editor.putString("OnwardPartialCancellationAllowed", PartialCancellationAllowed);
                            editor.putString("OnwardProvider", Provider);
                            editor.putString("OnwardObiboAPIProvider", ObiboAPIProvidrer);
                            editor.putString("OnwardJourneyDate", JourneyDate);
                            editor.putString("OnwardSelectedSeats", SelectedSeats);
                            editor.putString("OnwardNumberOfSeats", NumberOfSeats);
                            editor.putString("OnwardNetFare", NetFare);
                            editor.putString("OnwardServiceTaxes", ServiceTaxes);
                            editor.putString("OnwardOperatorserviceCharge", OperatorserviceCharge);
                            editor.putString("OnwardFares", Fares);
                            editor.putString("OnwardBoadingId", boadinglist.get(position).getBoardingPointId());
                            editor.putString("OnwardBoadingName", boadinglist.get(position).getBoardingLocation());
                            editor.putString("OnwardSeatCode", SeatCodes);
                            editor.putString("OnwardConvienceFee", ConvienceFee);
                            editor.putString("OnwardTotalConvienceFee", TotalConvienceFee);

                            editor.apply();


                            SharedPreferences preference2 = context.getSharedPreferences("JourneyDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = preference2.edit();

                            editor2.putString("IsReturnYes", "Yes");
                            editor2.putString("IsReturnSelected", "Yes");
                            editor2.putString("ReturnNumberOfSeats", NumberOfSeats);

                            editor2.apply();


                            context.startActivity(i);
                            break;
                        }
                        case "No": {

                            Intent i = new Intent(context, PassengerDetails.class);

                            SharedPreferences preference = context.getSharedPreferences("OnwardJourneyDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preference.edit();
                            editor.putString("OnwardOperator", Operator);
                            editor.putString("OnwardBusType", BusType);
                            editor.putString("OnwardBusTypeID", BusTypeid);
                            editor.putString("OnwarddepartureTime", departureTime);
                            editor.putString("OnwardCancellationPolicy", CancellationPolicy);
                            editor.putString("OnwardPartialCancellationAllowed", PartialCancellationAllowed);
                            editor.putString("OnwardProvider", Provider);
                            editor.putString("OnwardObiboAPIProvider", ObiboAPIProvidrer);
                            editor.putString("OnwardJourneyDate", JourneyDate);
                            editor.putString("OnwardSelectedSeats", SelectedSeats);
                            editor.putString("OnwardNumberOfSeats", NumberOfSeats);
                            editor.putString("OnwardNetFare", NetFare);
                            editor.putString("OnwardServiceTaxes", ServiceTaxes);
                            editor.putString("OnwardOperatorserviceCharge", OperatorserviceCharge);
                            editor.putString("OnwardFares", Fares);
                            editor.putString("OnwardBoadingId", boadinglist.get(position).getBoardingPointId());
                            editor.putString("OnwardBoadingName", boadinglist.get(position).getBoardingLocation());
                            editor.putString("OnwardSeatCode", SeatCodes);
                            editor.putString("OnwardConvienceFee", ConvienceFee);
                            editor.putString("OnwardTotalConvienceFee", TotalConvienceFee);
                            editor.apply();

                            context.startActivity(i);

                            SharedPreferences preference2 = context.getSharedPreferences("JourneyDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = preference2.edit();

                            editor2.putString("IsReturnYes", "No");
                            editor2.putString("IsReturnSelected", "No");
                            editor2.apply();
                            break;
                        }
                        default: {
                            Intent i = new Intent(context, PassengerDetails.class);


                            SharedPreferences preference1 = context.getSharedPreferences("BoardingPoint", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = preference1.edit();
                            editor1.putString("BoadingId", boadinglist.get(position).getBoardingPointId());
                            editor1.putString("BoadingName", boadinglist.get(position).getBoardingLocation());

                            editor1.apply();


                            context.startActivity(i);
                            break;
                        }
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

                   /* else if (charText.length() != 0 && postDetail.getPostSubTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                        parkingList.add(postDetail);
                    }*/
                }
            }
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
