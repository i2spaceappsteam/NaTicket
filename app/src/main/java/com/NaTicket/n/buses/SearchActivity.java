package com.NaTicket.n.buses;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.buses.busbeanclass.AvailableBusDetailsBean;
import com.NaTicket.n.R;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.ServiceHandler;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class SearchActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog;

    LinearLayout DateLinearlayout;
    private ListView AvailableListView;
    private MyAppAdapter AvailableBusAdapter;
    private ArrayList<AvailableBusDetailsBean> postArrayList;

    TextView operator, time, price;
    TextView no_buses_found, Date_of_Journey;
    Boolean Operator = true, Time = true, Price = true;

    AvailableBusDetailsBean BusDetailsBean;
    JSONArray results;
    String Arrival_id, Destination_id, Journey_Date, DATE_OF_JOURNEY;
    String ProvidersCount, ResponceStatus, Message;

    ImageButton Increse_Date, Decrese_Date;
    String str;
    String Title_name, ReturnDateisTrue, IsReturnYes, ReturnTitle_name;

    //OPERATOR ASCENDING?///////////
    private boolean mAscendingOrder[] = {true, true, true};
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String userLogin, agentLogin, SuperAdmin;
    String UserId="5", User_agentid="";
    LinearLayout DatePickr;

    //////Filter//////
    String AC_slected, NonACSELECTED, SleeperSELECTED, SemiSleeperSELECTED;
    String OperaterId;
    private ArrayList<String> BoardingArray = new ArrayList<>();
    ArrayList<String> DropingArray = new ArrayList<>();
    ArrayList<String> Operater = new ArrayList<>();
    String OriginalDate, Filtered_date, Filter_day_ofJourney;
    String DayofJourney;
    List<String> Traveler;
    List<String> FilterBoadingId;
    List<String> FilterDropingId;

    static LoadingDialog dialogFragment;

    String Filter="False";
    String FROMNAME, TONAME;

    String Fboading, Fdroping, oper;
    Toolbar toolbar;

    TextView toolbartitle, sort, filter;
    Boolean SortingApplied = false, ArrowUP = false;
    String Sorting = "";
    Typeface font;

    Login_utils login_utils;
    String User_Role;
    //CountDownTimer Timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_serach_page);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbartitle = (TextView) findViewById(R.id.toolbar_title);
        sort = (TextView) findViewById(R.id.sort);
        filter = (TextView) findViewById(R.id.filter);



        no_buses_found = (TextView) findViewById(R.id.No_buses_found);
        Date_of_Journey = (TextView) findViewById(R.id.date_of_journey);
        DateLinearlayout = (LinearLayout) findViewById(R.id.date_layout);
        DatePickr = (LinearLayout) findViewById(R.id.DateCalendar);
        AvailableListView = (ListView) findViewById(R.id.search_list);
        postArrayList = new ArrayList<>();


        font = Typeface.createFromAsset(
                this.getAssets(),"fonts/OpenSans_Regular.ttf");

       /* pref = getSharedPreferences("LoginPreference", MODE_PRIVATE);
        editor = pref.edit();
        userLogin = pref.getString("UserLOGIN", null);
        agentLogin = pref.getString("AgentLOGIN", null);


        if (userLogin != null && userLogin.equals("Yes")) {
            UserId = "6";
            User_agentid = pref.getString("User_agentid", null);
        } else if (agentLogin != null && agentLogin.equals("Yes")) {
            UserId = "4";
            User_agentid = pref.getString("User_agentid", null);
        } else {
            UserId = "5";
            User_agentid = pref.getString("SuperAdmin", null);
        }
*/
        login_utils=new Login_utils(this);

        if(login_utils.getUserDetails(Constants.USERTYPE)!=null&&!login_utils.getUserDetails(Constants.USERTYPE).equals("")) {
            UserId = login_utils.getUserDetails(Constants.USERTYPE);
            User_agentid = login_utils.getUserDetails(Constants.USERID);
        }


        SharedPreferences preference = getApplicationContext().getSharedPreferences("JourneyDetails", MODE_PRIVATE);
        Arrival_id = preference.getString("ARRIVAL_ID", null);
        Destination_id = preference.getString("DESTINATION_ID", null);
        OriginalDate = preference.getString("JOURNEY_DATE", null);
        DATE_OF_JOURNEY = preference.getString("DAY_OF_JOURNEY", null);
        Title_name = preference.getString("TITLE_BAR", null);
        ReturnTitle_name = preference.getString("ReturnTripTITLE_BAR", null);
        ReturnDateisTrue = preference.getString("PassengerRETURN_DATE", null);
        IsReturnYes = preference.getString("IsReturnYes", null);
        FROMNAME = preference.getString("FROMNAME", null);
        TONAME = preference.getString("TONAME", null);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
        Filter = pref.getString("FILTER", null);
        AC_slected = pref.getString("ACSELECTED", null);
        NonACSELECTED = pref.getString("NonACSELECTED", null);
        SleeperSELECTED = pref.getString("SleeperSELECTED", null);
        SemiSleeperSELECTED = pref.getString("SemiSleeperSELECTED", null);
        OperaterId = pref.getString("OperaterId", null);
        Fboading = pref.getString("FilterBoadingId", null);
        Fdroping = pref.getString("FilterDropingId", null);
        oper = pref.getString("Filter_OperaterId", null);
        System.out.println("Boading" + Fboading + FilterBoadingId + FilterDropingId);
        if (Filter != null) {
            Filtered_date = pref.getString("JourneyDate", null);
            Filter_day_ofJourney = pref.getString("DayofJourney", null);
            System.out.println(Filtered_date + "-----DAte");
            System.out.println(Filter_day_ofJourney + "-----Day_of_journey");
        }
        if (oper != null || Fboading != null || Fdroping != null) {
            if (oper != null) {
                Traveler = Arrays.asList(oper.split("~"));
            }
            if (Fboading != null) {
                FilterBoadingId = Arrays.asList(Fboading.split("~"));
            }
            if (Fdroping != null) {
                FilterDropingId = Arrays.asList(Fdroping.split("~"));
            }
            System.out.println("RESULT*****" + Traveler + FilterBoadingId + FilterDropingId);
        }

        if (IsReturnYes.equals("Yes")) {
            toolbartitle.setText(TONAME + " > " + FROMNAME);
            DateLinearlayout.setVisibility(View.INVISIBLE);
        } else {
            toolbartitle.setText(FROMNAME + " > " + TONAME);
        }


        if (Filter != null) {
            Journey_Date = Filtered_date;
            Date_of_Journey.setText(Filter_day_ofJourney);
            DayofJourney = Date_of_Journey.getText().toString();
        } else {
            Journey_Date = OriginalDate;
            Date_of_Journey.setText(DATE_OF_JOURNEY);
            DayofJourney = Date_of_Journey.getText().toString();
        }

        DatePickr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!no_buses_found.isShown()||Filter.equals("True")) {
                    Intent nextActivity = new Intent(SearchActivity.this, FilterActivity.class);
                    nextActivity.putExtra("JourneyDAte", Journey_Date);
                    nextActivity.putExtra("DayofJourney", DayofJourney);
                    nextActivity.putExtra("OnResume", "True");
                    startActivity(nextActivity);
                    //slide from right to left
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else{

                    Toast.makeText(SearchActivity.this, "Buses not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!no_buses_found.isShown()){
                BottomSheetDialog dialog = new BottomSheetDialog(SearchActivity.this);
                dialog.setContentView(R.layout.bus_search_sorting);

                operator = (TextView) dialog.findViewById(R.id.Operator);
                time = (TextView) dialog.findViewById(R.id.Time);
                price = (TextView) dialog.findViewById(R.id.Price);


                if (Sorting.equals("")) {

                } else {
                    if (Sorting.equals("Operator")) {
                        operator.setTypeface(font,Typeface.BOLD);
                        operator.setTextColor(getResources().getColor(R.color.colorAccent));
                        time.setTextColor(getResources().getColor(R.color.white));
                        price.setTextColor(getResources().getColor(R.color.white));

                        if (ArrowUP) {
                            operator.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                        } else {
                            operator.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                        }
                    } else if (Sorting.equals("Time")) {
                        time.setTypeface(font,Typeface.BOLD);
                        operator.setTextColor(getResources().getColor(R.color.white));
                        time.setTextColor(getResources().getColor(R.color.colorAccent));
                        price.setTextColor(getResources().getColor(R.color.white));

                        if (ArrowUP) {
                            time.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                        } else {
                            time.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                        }
                    } else if (Sorting.equals("Price")) {
                        price.setTypeface(font,Typeface.BOLD);
                        operator.setTextColor(getResources().getColor(R.color.white));
                        time.setTextColor(getResources().getColor(R.color.white));
                        price.setTextColor(getResources().getColor(R.color.colorAccent));

                        if (ArrowUP) {
                            price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                        } else {
                            price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                        }
                    }
                }


                operator.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        operator.setTypeface(font,Typeface.BOLD);
                        operator.setTextColor(getResources().getColor(R.color.colorAccent));
                        time.setTextColor(getResources().getColor(R.color.white));
                        price.setTextColor(getResources().getColor(R.color.white));


                        time.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        // Change the order of items based on name
                        if (mAscendingOrder[0]) {
                            // Show items descending
                            mAscendingOrder[0] = false;
                            AvailableBusAdapter.sortByNameAsc();
                            operator.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                            ArrowUP = true;
                        } else {
                            // Show items ascending
                            mAscendingOrder[0] = true;
                            AvailableBusAdapter.sortByNameDesc();
                            operator.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                            ArrowUP = false;
                        }

                        mAscendingOrder[1] = true;
                        mAscendingOrder[2] = true;

                        Sorting = "Operator";


                    }
                });

                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        time.setTypeface(font,Typeface.BOLD);
                        operator.setTextColor(getResources().getColor(R.color.white));
                        time.setTextColor(getResources().getColor(R.color.colorAccent));
                        price.setTextColor(getResources().getColor(R.color.white));
                        operator.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                        // Change the order of items based on quantity
                        if (mAscendingOrder[2]) {
                            // Show items descending
                            mAscendingOrder[2] = false;
                            AvailableBusAdapter.sortByTimeDesc();
                            time.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                            ArrowUP = true;
                        } else {
                            // Show items ascending
                            mAscendingOrder[2] = true;
                            AvailableBusAdapter.sortByTimeAsc();
                            time.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                            ArrowUP = false;
                        }

                        mAscendingOrder[0] = false;
                        mAscendingOrder[1] = true;

                        Sorting = "Time";
                    }
                });

                price.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        price.setTypeface(font,Typeface.BOLD);

                        operator.setTextColor(getResources().getColor(R.color.white));
                        time.setTextColor(getResources().getColor(R.color.white));
                        price.setTextColor(getResources().getColor(R.color.colorAccent));

                        time.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        operator.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        if (mAscendingOrder[1]) {
                            // Show items descending
                            mAscendingOrder[1] = false;
                            AvailableBusAdapter.sortByPriceDesc();
                            price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                            ArrowUP = true;

                        } else {
                            // Show items ascending
                            mAscendingOrder[1] = true;
                            AvailableBusAdapter.sortByPriceAsc();
                            price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                            ArrowUP = false;
                        }

                        mAscendingOrder[0] = false;
                        mAscendingOrder[2] = true;

                        Sorting = "Price";
                    }
                });

                dialog.show();
            }else{

                    Toast.makeText(SearchActivity.this, "Buses not available", Toast.LENGTH_SHORT).show();
                }
        }
        });

        isConnected();

    }



    @Override
    public void onStart() {
        super.onStart();


    }


    public class MyAppAdapter extends BaseAdapter {

        private LayoutInflater vi;
        private ArrayList<AvailableBusDetailsBean> parkingList = new ArrayList<>();
        public Context context;

        /**
         * Add white line
         */
        public void addItem(AvailableBusDetailsBean item) {
            parkingList.add(item);
            //   notifyDataSetChanged();
        }

        /**
         * Sort Available Bus Details list by name ascending
         */
        public void sortByNameAsc() {
            Comparator<AvailableBusDetailsBean> comparator = new Comparator<AvailableBusDetailsBean>() {

                @Override
                public int compare(AvailableBusDetailsBean object1, AvailableBusDetailsBean object2) {
                    return object1.getDisplayName().compareToIgnoreCase(object2.getDisplayName());
                }
            };
            Collections.sort(parkingList, comparator);
            notifyDataSetChanged();
        }

        /**
         * Sort Available Bus Details list by name descending
         */
        public void sortByNameDesc() {
            Comparator<AvailableBusDetailsBean> comparator = new Comparator<AvailableBusDetailsBean>() {

                @Override
                public int compare(AvailableBusDetailsBean object1, AvailableBusDetailsBean object2) {
                    return object2.getDisplayName().compareToIgnoreCase(object1.getDisplayName());
                }
            };
            Collections.sort(parkingList, comparator);
            notifyDataSetChanged();
        }

        /**
         * Sort Available Bus Details list by Time ascending
         */

        public void sortByTimeAsc() {
            Comparator<AvailableBusDetailsBean> comparator = new Comparator<AvailableBusDetailsBean>() {

                @Override
                public int compare(AvailableBusDetailsBean object1, AvailableBusDetailsBean object2) {
                    try {
                        return new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).parse(object1.getDepartureTime()).compareTo(new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).parse(object2.getDepartureTime()));
                    } catch (ParseException e) {
                        return 0;
                    }
                }
            };
            Collections.sort(parkingList, comparator);
            notifyDataSetChanged();


        }

        /**
         * Sort Available Bus Details list by Time descending
         */
        public void sortByTimeDesc() {
            Comparator<AvailableBusDetailsBean> comparator = new Comparator<AvailableBusDetailsBean>() {

                @Override
                public int compare(AvailableBusDetailsBean object1, AvailableBusDetailsBean object2) {
                    try {
                        return new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).parse(object2.getDepartureTime()).compareTo(new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).parse(object1.getDepartureTime()));
                    } catch (ParseException e) {
                        return 0;
                    }
                }
            };
            Collections.sort(parkingList, comparator);
            notifyDataSetChanged();
        }

        /**
         * Sort Available Bus Details list by price ascending
         */
        public void sortByPriceAsc() {
            Comparator<AvailableBusDetailsBean> comparator = new Comparator<AvailableBusDetailsBean>() {
                int roundValue, roundValue1;

                @Override
                public int compare(AvailableBusDetailsBean object1, AvailableBusDetailsBean object2) {
                    String[] parts = object1.getFares().split("/");
                    String[] strings = object2.getFares().split("/");
                    for (int i = 0; i < parts.length; i++) {
                        Double d = Double.parseDouble(parts[i]);
                        roundValue = (int) Math.round(d);
                    }
                    for (int i = 0; i < strings.length; i++) {
                        Double d = Double.parseDouble(strings[i]);
                        roundValue1 = (int) Math.round(d);
                    }
                    Integer first = roundValue;
                    Integer second = roundValue1;


                    return ((Integer) first).compareTo((Integer) (second));
                }
            };
            Collections.sort(parkingList, comparator);
            notifyDataSetChanged();
        }

        /**
         * Sort Available Bus Details list by price descending
         */
        public void sortByPriceDesc() {
            Comparator<AvailableBusDetailsBean> comparator = new Comparator<AvailableBusDetailsBean>() {
                int roundValue, roundValue1;

                @Override
                public int compare(AvailableBusDetailsBean object1, AvailableBusDetailsBean object2) {
                    String[] parts = object1.getFares().split("/");
                    String[] strings = object2.getFares().split("/");
                    for (int i = 0; i < parts.length; i++) {
                        Double d = Double.parseDouble(parts[i]);
                        roundValue = (int) Math.round(d);
                    }
                    for (int i = 0; i < strings.length; i++) {
                        Double d = Double.parseDouble(strings[i]);
                        roundValue1 = (int) Math.round(d);
                    }
                    Integer first = roundValue;
                    Integer second = roundValue1;


                    return ((Integer) second).compareTo((Integer) (first));
                }
            };
            Collections.sort(parkingList, comparator);
            notifyDataSetChanged();
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            ViewHolder viewHolder;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.listview_item, null);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.travels = (TextView) rowView.findViewById(R.id.Travels);
                viewHolder.busType = (TextView) rowView.findViewById(R.id.BusType);
                viewHolder.departureTime = (TextView) rowView.findViewById(R.id.DepartureTime);
                viewHolder.arrivalTime = (TextView) rowView.findViewById(R.id.ArrivalTime);
                viewHolder.available = (TextView) rowView.findViewById(R.id.BusSeats);
                viewHolder.netFares = (TextView) rowView.findViewById(R.id.NetFares);
                viewHolder.MticketImage = (ImageView) rowView.findViewById(R.id.M_ticket_Icon);
                viewHolder.Duration = (TextView) rowView.findViewById(R.id.Duration);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String fare = parkingList.get(position).getFares();
            String[] parts = fare.split("/");
            ArrayList<String> Fair = new ArrayList<>();
            try {
                for (int i = 0; i < parts.length; i++) {
                    Double d = Double.parseDouble(parts[i]);
                    int roundValue = (int) Math.round(d);
                    Fair.add(String.valueOf(roundValue));
                }
                String listString = TextUtils.join("/", Fair);
                if (parkingList.get(position).getDisplayName().equalsIgnoreCase("null")) {
                    viewHolder.travels.setText("" + parkingList.get(position).getTravels());
                } else {
                    viewHolder.travels.setText("" + parkingList.get(position).getDisplayName());
                }

                viewHolder.busType.setText("" + parkingList.get(position).getBusType());
                viewHolder.departureTime.setText("" + parkingList.get(position).getDepartureTime());
                viewHolder.arrivalTime.setText("" + parkingList.get(position).getArrivalTime());
                if (parkingList.get(position).getAvailableSeats().equals("0")) {
                    viewHolder.available.setText(" SOLD OUT");
                    viewHolder.available.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                } else {
                    viewHolder.available.setText("" + parkingList.get(position).getAvailableSeats() + " seats ");
                    viewHolder.available.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                }

                viewHolder.netFares.setText("₹ " + listString);
                if (parkingList.get(position).getMticket().equals("true")) {
                    viewHolder.MticketImage.setImageResource(R.drawable.mobile_icon);
                } else {
                    viewHolder.MticketImage.setImageResource(R.drawable.mticket);
                }
                viewHolder.Duration.setText("" + parkingList.get(position).getDuration() + "");
               /* if(parkingList.get(position).getRating().equals("0")){
                    viewHolder.Rating.setText( "  No");
                    viewHolder.Rating.setTextColor(getResources().getColor(R.color.Colorgray));
                    viewHolder.available.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
                    viewHolder.Rating.setBackgroundColor(Color.WHITE);
                    viewHolder.Rating.setVisibility(View.INVISIBLE);
                    viewHolder.TextRating.setVisibility(View.INVISIBLE);
                    viewHolder.NoRatings.setVisibility(View.VISIBLE);
                }else{
                    viewHolder.NoRatings.setVisibility(View.INVISIBLE);
                    viewHolder.Rating.setVisibility(View.VISIBLE);
                    viewHolder.TextRating.setVisibility(View.VISIBLE);
                    viewHolder.Rating.setText( " "+parkingList.get(position).getRating()+"/5 ");
                    viewHolder.Rating.setTextColor(getResources().getColor(R.color.colorWhite));
                    viewHolder.Rating.setBackgroundColor(Color.parseColor("#00998b"));
                }*/


            } catch (Exception e) {
                e.printStackTrace();

            }


            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //    int postin =position;
                    JSONArray Boardingtimes = parkingList.get(position).getBoardingTimes();
                    JSONArray Dropingtimes = parkingList.get(position).getDropingTimes();
                    //JSONArray BusImage = parkingList.get(position).getBusImages();
                    String BoardTimes = Boardingtimes.toString();
                    String DropingTimes = Dropingtimes.toString();
                    //String Images =BusImage.toString();
                    String Operator = parkingList.get(position).getDisplayName();
                    String operater = Operator.replaceAll("\\s+", "");
                    String BusType = parkingList.get(position).getBusType();
                    String BusTypeId = parkingList.get(position).getId();
                    String departureTime = parkingList.get(position).getDepartureTime();
                    Intent i = new Intent(context, SelectSeatActivity.class);

                    SharedPreferences preference = context.getSharedPreferences("JourneyDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putString("BoardingTimes", BoardTimes);
                    editor.putString("DroppingTimes", DropingTimes);
                    editor.putString("ConvenienceFee", parkingList.get(position).getConvenienceFee());
                    editor.putString("ConvenienceFeeType", parkingList.get(position).getConvenienceFeeType());
                    editor.putString("Operator", operater);
                    editor.putString("BusType", BusType);
                    editor.putString("BusTypeID", BusTypeId);
                    editor.putString("departureTime", departureTime);
                    editor.putString("CancellationPolicy", parkingList.get(position).getCancellationPolicy());
                    editor.putString("PartialCancellationAllowed", parkingList.get(position).getPartialCancellationAllowed());
                    editor.putString("Provider", parkingList.get(position).getProvider());
                    editor.putString("JourneyDate", Journey_Date);
                    editor.putString("ObiboAPIProvider", parkingList.get(position).getObiboAPIProvider());
                    editor.putString("Amenities", String.valueOf(parkingList.get(position).getAmenities()));


                    editor.commit();
                    i.putExtra("BoardingTimes", BoardTimes);
                    i.putExtra("DropingTimes", DropingTimes);
                    System.out.println("Date :" + Journey_Date + "JourneyDate" + "JourneyDetails");
                    System.out.println("Boarding" + parkingList.get(position).getObiboAPIProvider());


                    context.startActivity(i);
                }
            });
            return rowView;


        }

        private MyAppAdapter(ArrayList<AvailableBusDetailsBean> apps, Context context) {
            this.parkingList = apps;
            this.context = context;

        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return parkingList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


    }

    public class ViewHolder {
        TextView travels, busType, departureTime, arrivalTime, available, netFares, Duration;
        ImageView MticketImage;

    }


    public class Availablebuses extends AsyncTask<String, String, String> {

        String jsonstr;

        @Override
        protected void onPreExecute() {


            mProgressDialog = ProgressDialog.show(SearchActivity.this, "","Loading buses..." );
            mProgressDialog.setCancelable(false);



            /*FragmentManager fm = getFragmentManager();
            dialogFragment = new LoadingDialog();
            dialogFragment.setCancelable(false);
            dialogFragment.show(fm, "Sample Fragment");*/

        }
        /*Timer=new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mProgressDialog.dismiss();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SearchActivity.
                        this);
                alertDialogBuilder.setTitle("No Bus Operators found!");

                // set dialog message
                alertDialogBuilder
                        .setMessage("This could have happened due to:\n" +
                                "1. Server Timed Out \n" +
                                "2. No Operators are available at the movement\n" +
                                "Here is what you can do to get back on track...")
                        .setCancelable(false)
                        .setPositiveButton("Search Again", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent i = new Intent(SearchActivity.this, Buses_MainActivity.class);
                                startActivity(i);

                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }
        }.start();*/

        @Override
        protected String doInBackground(String... params) {
            String url;
            if (IsReturnYes.equals("Yes")) {
                url = Constants.BASEURL + Constants.AvailableBuses + "sourceId=" +  Arrival_id+ "&destinationId=" + Destination_id + "&journeyDate=" + Journey_Date + "&tripType=2&userType=" + UserId + "&user=" + User_agentid + "&returnDate=" + ReturnDateisTrue;
            } else {
                url = Constants.BASEURL + Constants.AvailableBuses + "sourceId=" + Arrival_id + "&destinationId=" + Destination_id + "&journeyDate=" + Journey_Date + "&tripType=1&userType=" + UserId + "&user=" + User_agentid;

            }

            System.out.println("url" + url);
            ServiceHandler sh = new ServiceHandler();
            jsonstr = sh.makeServiceCall(url, ServiceHandler.GET);
            System.out.println("JSON LENGTH" + jsonstr);

            if (jsonstr.length() != 0) {
                //Timer.cancel();
                mProgressDialog.dismiss();
                postArrayList.clear();

                try {


                    JSONObject jsonObject = new JSONObject(jsonstr);

                    results = jsonObject.getJSONArray("AvailableTrips");
                    if (results != null) {


                         final int numberOfItemsInResp = results.length();
                        for (int i = 0; i < numberOfItemsInResp; i++) {
                            BusDetailsBean = new AvailableBusDetailsBean();

                            JSONObject jobj = results.getJSONObject(i);
                            BusDetailsBean.setBoardingTimes(jobj.getJSONArray("BoardingTimes"));
                            JSONArray jarry = jobj.getJSONArray("BoardingTimes");

                            System.out.println("BoardingTimes" + jarry);
                            for (int j = 0; j < jarry.length(); j++) {
                                JSONObject boardtime = jarry.getJSONObject(j);

                                BusDetailsBean.setBoardingAddress(boardtime.getString("Address"));
                                BusDetailsBean.setBoardingContactNumbers(boardtime.getString("ContactNumbers"));
                                BusDetailsBean.setBoardingContactPersons(boardtime.getString("ContactPersons"));
                                BusDetailsBean.setBoardingPointId(boardtime.getString("PointId"));
                                BusDetailsBean.setBoardingLandmark(boardtime.getString("Landmark"));
                                BusDetailsBean.setBoardingLocation(boardtime.getString("Location"));
                                BusDetailsBean.setBoardingName(boardtime.getString("Name"));
                                BusDetailsBean.setBoardingTime(boardtime.getString("Time"));

                                BoardingArray.add(boardtime.getString("PointId"));

                            }

                            BusDetailsBean.setDropingTimes(jobj.getJSONArray("DroppingTimes"));
                            JSONArray dispatch = jobj.getJSONArray("DroppingTimes");
                            System.out.println("DroppingTimes" + dispatch);
                            for (int k = 0; k < dispatch.length(); k++) {
                                JSONObject disptime = dispatch.getJSONObject(k);

                                BusDetailsBean.setDroppingAddress(disptime.getString("Address"));
                                BusDetailsBean.setDroppingContactNumbers(disptime.getString("ContactNumbers"));
                                BusDetailsBean.setDroppingContactPersons(disptime.getString("ContactPersons"));
                                BusDetailsBean.setDroppingPointId(disptime.getString("PointId"));
                                BusDetailsBean.setDroppingLandmark(disptime.getString("Landmark"));
                                BusDetailsBean.setDroppingLocation(disptime.getString("Location"));
                                BusDetailsBean.setDroppingName(disptime.getString("Name"));
                                BusDetailsBean.setDroppingTime(disptime.getString("Time"));

                                DropingArray.add(disptime.getString("PointId"));
                            }
                            BusDetailsBean.setAvailableSeats(jobj.getString("AvailableSeats"));
                            BusDetailsBean.setId(jobj.getString("Id"));
                            Operater.add(jobj.getString("Id"));
                            BusDetailsBean.setTravels(jobj.getString("Travels"));
                            BusDetailsBean.setDisplayName(jobj.getString("DisplayName"));
                            BusDetailsBean.setBusType(jobj.getString("BusType"));
                            //  BusDetailsBean.setDepartureTime(jobj.getString("DepartureTime"));
                            String Dtime = jobj.getString("DepartureTime");
                            BusDetailsBean.setDepartureTime(Dtime);

                            String time = jobj.getString("ArrivalTime");
                            BusDetailsBean.setArrivalTime(time);

                            BusDetailsBean.setDuration(jobj.getString("Duration"));
                            BusDetailsBean.setCancellationPolicy(jobj.getString("CancellationPolicy"));
                            BusDetailsBean.setSourceId(jobj.getString("SourceId"));
                            BusDetailsBean.setDestinationId(jobj.getString("DestinationId"));
                            BusDetailsBean.setJourneydate(jobj.getString("Journeydate"));
                            BusDetailsBean.setFares(jobj.getString("Fares"));
                            BusDetailsBean.setServiceTax(jobj.getString("ServiceTax"));
                            BusDetailsBean.setOperatorServiceCharge(jobj.getString("OperatorServiceCharge"));
                            BusDetailsBean.setConvenienceFee(jobj.getString("ConvenienceFee"));
                            BusDetailsBean.setConvenienceFeeType(jobj.getString("ConvenienceFeeType"));
                            BusDetailsBean.setNetFares(jobj.getString("NetFares"));
                            BusDetailsBean.setProvider(jobj.getString("Provider"));
                            //   BusDetailsBean.setObiboAPIProvider(jobj.getString("ObiboAPIProvider"));
                            BusDetailsBean.setPartialCancellationAllowed(jobj.getString("PartialCancellationAllowed"));
                            BusDetailsBean.setSeatType(jobj.getString("SeatType"));
                            BusDetailsBean.setMticket(jobj.getString("Mticket"));
                            //

                            System.out.println("ConvenienceFeeType: " + BusDetailsBean.getConvenienceFeeType());
                            //  BusDetailsBean.setAffiliateId(jobj.getString("AffiliateId"));
                            //here Filter is true
                            if (Filter != null && Filter.equals("True")) {

                                //here Traveller not equal to null
                                if (Traveler != null && Fboading != null) {
                                    if (Traveler.contains(jobj.getString("Travels"))) {
                                        //here Boading point not equal to null
                                        if (FilterBoadingId != null) {
                                            for (int j = 0; j < jarry.length(); j++) {
                                                JSONObject boardtime = jarry.getJSONObject(j);
                                                if (FilterBoadingId.contains(boardtime.getString("Location"))) {
                                                    if (AC_slected != null && AC_slected.equals("true")) {
                                                        if (jobj.getString("SeatType").equals("1") || jobj.getString("SeatType").equals("2")) {
                                                            postArrayList.add(BusDetailsBean);
                                                        }
                                                    } else if (NonACSELECTED != null && NonACSELECTED.equals("true")) {
                                                        if (jobj.getString("SeatType").equals("3") || jobj.getString("SeatType").equals("4")) {
                                                            postArrayList.add(BusDetailsBean);
                                                        }
                                                    } else if (SleeperSELECTED != null && SleeperSELECTED.equals("true")) {
                                                        if (jobj.getString("SeatType").equals("2") || jobj.getString("SeatType").equals("4")) {
                                                            postArrayList.add(BusDetailsBean);
                                                        }
                                                    } else if (SemiSleeperSELECTED != null && SemiSleeperSELECTED.equals("true")) {
                                                        if (jobj.getString("SeatType").equals("1") || jobj.getString("SeatType").equals("3")) {
                                                            postArrayList.add(BusDetailsBean);
                                                        }
                                                    } else {
                                                        postArrayList.add(BusDetailsBean);
                                                    }
                                                }
                                            }
                                        } else {
                                            postArrayList.add(BusDetailsBean);

                                        }
                                    }
                                } else if (FilterBoadingId != null) {
                                    for (int j = 0; j < jarry.length(); j++) {
                                        JSONObject boardtime = jarry.getJSONObject(j);

                                        //here you can write the conditina for droping points checking
                                        if (FilterBoadingId.contains(boardtime.getString("Location"))) {
                                            if (AC_slected != null && AC_slected.equals("true")) {
                                                if (jobj.getString("SeatType").equals("1") || jobj.getString("SeatType").equals("2")) {
                                                    postArrayList.add(BusDetailsBean);
                                                }
                                            } else if (NonACSELECTED != null && NonACSELECTED.equals("true")) {
                                                if (jobj.getString("SeatType").equals("3") || jobj.getString("SeatType").equals("4")) {
                                                    postArrayList.add(BusDetailsBean);
                                                }
                                            } else if (SleeperSELECTED != null && SleeperSELECTED.equals("true")) {
                                                if (jobj.getString("SeatType").equals("2") || jobj.getString("SeatType").equals("4")) {
                                                    postArrayList.add(BusDetailsBean);
                                                }
                                            } else if (SemiSleeperSELECTED != null && SemiSleeperSELECTED.equals("true")) {
                                                if (jobj.getString("SeatType").equals("1") || jobj.getString("SeatType").equals("3")) {
                                                    postArrayList.add(BusDetailsBean);
                                                }
                                            } else {
                                                postArrayList.add(BusDetailsBean);
                                            }
                                            // here droing point not equal to null
                                        }

                                    }

                                } else if (Traveler != null) {
                                    if (Traveler.contains(jobj.getString("Travels"))) {
                                        if (AC_slected != null && AC_slected.equals("true")) {
                                            if (jobj.getString("SeatType").equals("1") || jobj.getString("SeatType").equals("2")) {
                                                postArrayList.add(BusDetailsBean);
                                            }
                                        } else if (NonACSELECTED != null && NonACSELECTED.equals("true")) {
                                            if (jobj.getString("SeatType").equals("3") || jobj.getString("SeatType").equals("4")) {
                                                postArrayList.add(BusDetailsBean);
                                            }
                                        } else if (SleeperSELECTED != null && SleeperSELECTED.equals("true")) {
                                            if (jobj.getString("SeatType").equals("2") || jobj.getString("SeatType").equals("4")) {
                                                postArrayList.add(BusDetailsBean);
                                            }
                                        } else if (SemiSleeperSELECTED != null && SemiSleeperSELECTED.equals("true")) {
                                            if (jobj.getString("SeatType").equals("1") || jobj.getString("SeatType").equals("3")) {
                                                postArrayList.add(BusDetailsBean);
                                            }
                                        } else {
                                            postArrayList.add(BusDetailsBean);
                                        }
                                    }

                                } else {
                                    if (AC_slected != null && AC_slected.equals("true")) {
                                        if (jobj.getString("SeatType").equals("1") || jobj.getString("SeatType").equals("2")) {
                                            postArrayList.add(BusDetailsBean);
                                        }
                                    } else if (NonACSELECTED != null && NonACSELECTED.equals("true")) {
                                        if (jobj.getString("SeatType").equals("3") || jobj.getString("SeatType").equals("4")) {
                                            postArrayList.add(BusDetailsBean);
                                        }
                                    } else if (SleeperSELECTED != null && SleeperSELECTED.equals("true")) {
                                        if (jobj.getString("SeatType").equals("2") || jobj.getString("SeatType").equals("4")) {
                                            postArrayList.add(BusDetailsBean);
                                        }
                                    } else if (SemiSleeperSELECTED != null && SemiSleeperSELECTED.equals("true")) {
                                        if (jobj.getString("SeatType").equals("1") || jobj.getString("SeatType").equals("3")) {
                                            postArrayList.add(BusDetailsBean);
                                        }
                                    } else {
                                        postArrayList.add(BusDetailsBean);
                                    }
                                }


                            } else {
                                postArrayList.add(BusDetailsBean);
                            }


                            SharedPreferences preference = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preference.edit();
                            editor.putString("JsonResponce", jsonstr);
                            editor.apply();
                        }
                        ProvidersCount = jsonObject.getString("ProvidersCount");
                        ResponceStatus = jsonObject.getString("ResponseStatus");
                        Message = jsonObject.getString("Message");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }


            } else {
                //   no_buses_found.setVisibility(View.VISIBLE);
                mProgressDialog.dismiss();
                //dialogFragment.dismiss();


            }
            return jsonstr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            mProgressDialog.dismiss();
            try {
                if (results.length() == 0) {
                   // Timer.cancel();
                    //mProgressDialog.dismiss();
                    Toast.makeText(SearchActivity.this, "No Buses Found", Toast.LENGTH_SHORT).show();
                    no_buses_found.setVisibility(View.VISIBLE);

                    //dialogFragment.dismiss();
                    System.out.println("1111");

                } else if (Filter != null && Filter.equals("True")) {
                    //Timer.cancel();

                    if (postArrayList.size() == 0) {
                       // mProgressDialog.dismiss();
                        //dialogFragment.dismiss();
                        no_buses_found.setVisibility(View.VISIBLE);
                    } else {
                        if (postArrayList.size() == 1) {
                            Toast.makeText(SearchActivity.this, postArrayList.size() + " Bus Found", Toast.LENGTH_SHORT).show();
                        } else if (results.length() > 1) {
                            Toast.makeText(SearchActivity.this, postArrayList.size() + " Buses Found", Toast.LENGTH_SHORT).show();
                        }
                        AvailableBusAdapter = new MyAppAdapter(postArrayList, SearchActivity.this);
                        // setListAdapter(myAppAdapter);
                        AvailableListView.setAdapter(AvailableBusAdapter);
                        AvailableBusAdapter.sortByTimeAsc();
                       // mProgressDialog.dismiss();
                        //dialogFragment.dismiss();
                    }
                } else if (results.length() != 0) {

                    if(no_buses_found.isShown()){
                        no_buses_found.setVisibility(View.GONE);
                    }
                    if (results.length() == 1) {
                        Toast.makeText(SearchActivity.this, results.length() + " Bus Found", Toast.LENGTH_SHORT).show();
                    } else if (results.length() > 1) {
                        Toast.makeText(SearchActivity.this, results.length() + " Buses Found", Toast.LENGTH_SHORT).show();
                    }


                    AvailableBusAdapter = new MyAppAdapter(postArrayList, SearchActivity.this);
                    // setListAdapter(myAppAdapter);
                    AvailableListView.setAdapter(AvailableBusAdapter);
                    AvailableBusAdapter.sortByTimeAsc();
                    //mProgressDialog.dismiss();
                    //dialogFragment.dismiss();

                } else {

                    //Timer.cancel();
                    //dialogFragment.dismiss();
                   // mProgressDialog.dismiss();
                    postArrayList.clear();
                    AvailableListView.setAdapter(null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SearchActivity.
                            this);

                    // set title
                    alertDialogBuilder.setTitle("No Bus Operators found!");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("This could have happened due to:\n" +
                                    "1. No bus operating on this route or \n" +
                                    "2. All seats for this route being sold out.\n" +
                                    "Here is what you can do to get back on track...")
                            .setCancelable(false)
                            .setPositiveButton("Search Again", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent i = new Intent(SearchActivity.this, Buses_MainActivity.class);
                                    startActivity(i);

                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }

            }catch (Exception e){

            }
        }
    }


    ///Date picker Fragment with max and min date
    @SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @SuppressWarnings("ResourceType")
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);


            calendar.add(Calendar.DATE, 45);


            // Set the Calendar new date as maximum date of date picker
            dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());

            // Subtract 6 days from Calendar updated date
            calendar.add(Calendar.DATE, -45);

            // Set the Calendar new date as minimum date of date picker
            dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());

            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the chosen date


            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);

            int Month = month + 1;


            SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date date = new Date(year, month, day);


            String formattedDate = simpledateformat.format(date.getTime());
            String s = formattedDate;
            StringTokenizer st = new StringTokenizer(s, "-");
            String Date = st.nextToken();
            String Month1 = st.nextToken();


            int months = Integer.parseInt(String.valueOf(Month1));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMM");
            int minus = months - 1;
            cal.set(Calendar.MONTH, minus);
            String month_name = month_date.format(cal.getTime());


            Date_of_Journey.setText("" + Date + " " + month_name + " " + year);
            DayofJourney = Date_of_Journey.getText().toString();
            Journey_Date = day + "-" + Month + "-" + year;

            String finDate = Journey_Date;


            if (finDate.charAt(1) == '-') finDate = "0" + finDate;
            if (finDate.charAt(4) == '-')
                finDate = finDate.substring(0, 3) + "0" + finDate.substring(3);
            System.out.println(finDate);

            Journey_Date = finDate;

            isConnected();

        }
    }


    @SuppressLint("ValidFragment")
    public class LoadingDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            // Inflate the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            View view = inflater.inflate(R.layout.dialog_bus, null);


            // Set the dialog layout
            builder.setView(view);
           /* WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
            wmlp.gravity = Gravity.CENTER;
*/
            return builder.create();


        }

        @Override
        public void dismiss() {
            super.dismiss();
        }

    }

    public void onResume() {
        super.onResume();



       /* getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        window.setLayout(200, 200);
        window.setGravity(Gravity.CENTER);*/
        //TODO:
    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            Intent nextActivity = new Intent(this, FilterActivity.class);
            nextActivity.putExtra("JourneyDAte", Journey_Date);
            nextActivity.putExtra("DayofJourney", DayofJourney);
            nextActivity.putExtra("OnResume", "True");
            startActivity(nextActivity);
            //slide from right to left
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    *//*@Override
    public void onBackPressed() {
        SharedPreferences preference = getApplicationContext().getSharedPreferences("Filter", MODE_PRIVATE);
        preference.edit().remove("ACSELECTED").commit();
        preference.edit().remove("NonACSELECTED").commit();
        preference.edit().remove("SleeperSELECTED").commit();

    }*//*
    // Method to manually check connection status

    */

    /**
     * Callback will be triggered when there is change in
     * network connection
     *//*

    //  finish();*/
    public void isConnected() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            new Availablebuses().execute();
        } else {
            Util.alertDialogShow(this, "Please Check Your Internet Connection");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(SearchActivity.this,Buses_MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }
}