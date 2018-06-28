package com.NaTicket.n.holidays;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.holidays.adapters.HolidaysAdapter;
import com.NaTicket.n.holidays.pojo.AvailableHolidayDTO;
import com.NaTicket.n.holidays.pojo.AvailableHolidayPackagesDTO;
import com.NaTicket.n.holidays.pojo.Holiday_Filters_DTO;
import com.NaTicket.n.holidays.pojo.SelectedHolidayDetailsDTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class AvailableHolidaysActivity extends BackActivity {
    private RecyclerView hotelsView;
    HolidaysAdapter mAdapter;
    SearchView searchHotelView;
    private ProgressDialog mProgressDialog;
    private SelectedHolidayDetailsDTO selDetails;
    TextView  sort, filter;
    TextView toolbartitle;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String userLogin, agentLogin;
    String UserId="5", User_agentid="",UserType;
    TextView Title,Duration,Fare;
    AvailableHolidayDTO holidaysMainDTO;
    Boolean FilterTrue = false;
    Currency_Utils currency_utils;
    Holiday_Filters_DTO filter_details;
    String Tags;
    Login_utils login_utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_holidays);
        currency_utils=new Currency_Utils(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        toolbartitle.setText("Available Holidays");
        setSupportActionBar(toolbar);
        inittoolbar();
        selDetails = (SelectedHolidayDetailsDTO) getIntent().getSerializableExtra("selHolidayDetails");

        login_utils=new Login_utils(this);
        intiViews();

        if(login_utils.getUserDetails(Constants.USERTYPE)!=null&&!login_utils.getUserDetails(Constants.USERTYPE).equals("")) {
            UserId = login_utils.getUserDetails(Constants.USERTYPE);
            User_agentid = login_utils.getUserDetails(Constants.USERID);
        }
        callHotelsData();
        sortfilter();
    }

    private void setHotelsData(ArrayList<AvailableHolidayPackagesDTO> availableHolidays ) {
        mAdapter = new HolidaysAdapter(this,availableHolidays);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        hotelsView.setLayoutManager(mLayoutManager);
        hotelsView.setItemAnimator(new DefaultItemAnimator());
        hotelsView.setAdapter(mAdapter);
        search(searchHotelView);
    }

    private void callHotelsData() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Fetching Holidays...",this);
            //Util.showMessage(AvailableHotelsActivity.this,selDetails.getCheckIn());
            String url = Constants.GETHOLIDAYPACKAGES+"destinationId="+selDetails.getDestinationId()+
                    "&journeyDate="+selDetails.getJourneyDate()
                    +"&duration=1&holidayPackageName=&holidayPackageId=0"+"&categoryId="+selDetails.getCategory_type()+"&categoryName="+"ALL"+
                    "&subCategoryId="+selDetails.getCategoryId()+
                    "&userType="+UserId+"&user="+User_agentid;
             Log.v("url==",url);
            ServiceClasses.getHolidays(AvailableHolidaysActivity.this,url );
           // ServiceCalls.getHolidays(AvailableHolidaysActivity.this, AppConstants.GETHOLIDAYPACKAGES);
        }else{
            Util.showMessage(this,Constants.NO_INT_MSG);
        }
    }

    private void showAlertDialog(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }


    private void intiViews() {
        hotelsView =(RecyclerView)findViewById(R.id.hotelsView);
        searchHotelView=(SearchView)findViewById(R.id.searchHotelView);
        searchHotelView.setQueryHint("Search Holiday Packages");

        sort = (TextView) findViewById(R.id.sort);
        filter = (TextView) findViewById(R.id.filter);
    }



    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void sortfilter() {
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog dialog = new BottomSheetDialog(AvailableHolidaysActivity.this);
                dialog.setContentView(R.layout.hotel_search_sorting);

                Title = (TextView) dialog.findViewById(R.id.hotels);
                Duration = (TextView) dialog.findViewById(R.id.star_rating);
                Fare = (TextView) dialog.findViewById(R.id.price);

                Title.setText("Title");
                Duration.setText("Duration");
                Fare.setText("Price");


                Title.setTag("Asc");
                Duration.setTag("Asc");
                Fare.setTag("Asc");

                if (Tags!=null){
                    if (Tags.equals("TitleASC")){
                        sortTitleColor();
                        Title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    }else if (Tags.equals("TitleDSC")){
                        sortTitleColor();
                        Title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    }

                    if (Tags.equals("DURASC")){
                        sortTimeColor();
                        Duration.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    }else if (Tags.equals("DURDSC")){
                        Duration.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    }

                    if (Tags.equals("FareASC")){
                        sortPriceColor();
                        Fare.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    }else if (Tags.equals("FareDSC")){
                        sortPriceColor();
                        Fare.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    }
                }

                Title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sortTitleColor();
                        Title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        Duration.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        Fare.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        if (Title.getTag().toString().equals("Asc")) {
                            Title.setTag("Desc");
                            Tags="TitleASC";
                            ArrayList<AvailableHolidayPackagesDTO> H = mAdapter.sortByNameAsc();
                            mAdapter.refreshList(H);
                            Title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                        } else {
                            Title.setTag("Asc");
                            Tags="TitleDSC";
                            ArrayList<AvailableHolidayPackagesDTO> h = mAdapter.sortByNameDesc();
                            mAdapter.refreshList(h);
                            Title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                        }
                    }
                });
                Duration.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sortTimeColor();
                        Title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        Duration.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        Fare.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        if (Duration.getTag().equals("Asc")) {
                            Duration.setTag("Desc");
                            Tags="DURASC";
                            ArrayList<AvailableHolidayPackagesDTO> h = mAdapter.sortByStarAsc();
                            mAdapter.refreshList(h);
                            Duration.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                        } else {
                            Duration.setTag("Asc");
                            Tags="DURDSC";
                            ArrayList<AvailableHolidayPackagesDTO> h = mAdapter.sortByStarDesc();
                            mAdapter.refreshList(h);
                            Duration.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                        }
                    }
                });
                Fare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sortPriceColor();
                        Title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        Duration.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        Fare.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        if (Fare.getTag().toString().equals("Asc")) {
                            Fare.setTag("Desc");
                            Tags="FareASC";
                            ArrayList<AvailableHolidayPackagesDTO> h = mAdapter.sortByPriceAsc();
                            mAdapter.refreshList(h);
                            Fare.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                        } else {
                            Fare.setTag("Asc");
                            Tags="FareDSC";
                            ArrayList<AvailableHolidayPackagesDTO> h = mAdapter.sortByPriceDesc();
                            mAdapter.refreshList(h);
                            Fare.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                        }


                    }
                });
                dialog.show();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(AvailableHolidaysActivity.this, Holiday_Filters_Activity.class);
                if (!FilterTrue) {
                    nextActivity.putExtra("Holidayslist", holidaysMainDTO.getAvailableHolidayPackages());
                    nextActivity.putExtra("Filteredlist",filter_details);
                    Tags=null;
                }
                startActivityForResult(nextActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    public void sortTitleColor(){
        Title.setTextColor(getResources().getColor(R.color.colorAccent));
        Duration.setTextColor(getResources().getColor(R.color.white));
        Fare.setTextColor(getResources().getColor(R.color.white));
    }
    public void sortTimeColor(){
        Title.setTextColor(getResources().getColor(R.color.white));
        Duration.setTextColor(getResources().getColor(R.color.colorAccent));
        Fare.setTextColor(getResources().getColor(R.color.white));
    }
    public void sortPriceColor(){
        Title.setTextColor(getResources().getColor(R.color.white));
        Duration.setTextColor(getResources().getColor(R.color.white));
        Fare.setTextColor(getResources().getColor(R.color.colorAccent));
    }



    public void getHotelData(AvailableHolidayPackagesDTO availableHotelsDTO) {
        Intent i = new Intent(AvailableHolidaysActivity.this, HolidayDetailActivity.class);
        i.putExtra("SelHoliday",availableHotelsDTO);
        i.putExtra("selDetails",selDetails);
       // i.putExtra("roomList",roomList);
        startActivity(i);

    }

    private  void showProgressDialog(String msg,Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void getHolidaysResponse(String response) {
        hideProgressDialog();
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            holidaysMainDTO = gson.fromJson(reader, AvailableHolidayDTO.class);
            //  Util.showMessage(this,hotelsMainDTO.getAvailableHotels().get(0).getHotelName());
            if(holidaysMainDTO!=null && holidaysMainDTO.getAvailableHolidayPackages()!=null){
                if(holidaysMainDTO.getAvailableHolidayPackages().size()>0){
                    setHotelsData(holidaysMainDTO.getAvailableHolidayPackages());
                }else {
                    showAlertDialog("No Holiday Packages Available");
                }
            }
        }

    }


    private void FilterMethod(){
        ArrayList<AvailableHolidayPackagesDTO> availableHolidays=new ArrayList<>();
        availableHolidays=holidaysMainDTO.getAvailableHolidayPackages();
        ArrayList<String>  array=new ArrayList<>();
        ArrayList<AvailableHolidayPackagesDTO> Filteredlist=new ArrayList<>();
        String Curr_Value=currency_utils.getCurrencyValue("Currency_Value");
        for (int p=0;p<availableHolidays.size();p++){
            int Stars=availableHolidays.get(p).getHotelRating();
            if (Stars==51){
               Stars=0;
            }else if (Stars==52){
               Stars=1;
            }else if (Stars==53){
                Stars=2;
            }else if (Stars==54){
                Stars=3;
            }else if (Stars==55){
                Stars=4;
            }else if (Stars==56){
                Stars=5;
            }else{
                Stars=5;
            }
            int finalAmount=Util.roundup(Double.parseDouble(availableHolidays.get(p).getFareDetails().get(0).getAdultFare())*Double.parseDouble(Curr_Value));
            if (finalAmount >= filter_details.getMin_Rate() && finalAmount <= filter_details.getMax_Rate()) {
                if (filter_details.getSelected_Sub() != null&&filter_details.getSelected_Sub().size()!=0) {
                    String Subcategory=availableHolidays.get(p).getSubCategory();
                         array=filter_details.getSelected_Sub();
                        if (array.contains(Subcategory)) {
                            if (Stars == filter_details.getzero_star()) {
                                Filteredlist.add(availableHolidays.get(p));
                            }else if (Stars == filter_details.getone_star()) {
                                Filteredlist.add(availableHolidays.get(p));
                            } else if (Stars == filter_details.gettwo_star()) {
                                Filteredlist.add(availableHolidays.get(p));
                            } else if (Stars == filter_details.getthree_star()) {
                                Filteredlist.add(availableHolidays.get(p));
                            } else if (Stars == filter_details.getfour_star()) {
                                Filteredlist.add(availableHolidays.get(p));
                            } else if (Stars == filter_details.getfive_star()) {
                                Filteredlist.add(availableHolidays.get(p));
                            } else if (filter_details.getzero_star() == 6 &&filter_details.getone_star() == 6 && filter_details.gettwo_star() == 6 && filter_details.getthree_star() == 6 && filter_details.getfour_star() == 6 && filter_details.getfive_star() == 6) {
                                Filteredlist.add(availableHolidays.get(p));
                            }
                        }
                } else {
                    if (Stars == filter_details.getzero_star()) {
                        Filteredlist.add(availableHolidays.get(p));
                    }else if (Stars == filter_details.getone_star()) {
                        Filteredlist.add(availableHolidays.get(p));
                    } else if (Stars == filter_details.gettwo_star()) {
                        Filteredlist.add(availableHolidays.get(p));
                    } else if (Stars == filter_details.getthree_star()) {
                        Filteredlist.add(availableHolidays.get(p));
                    } else if (Stars == filter_details.getfour_star()) {
                        Filteredlist.add(availableHolidays.get(p));
                    } else if (Stars == filter_details.getfive_star()) {
                        Filteredlist.add(availableHolidays.get(p));
                    } else if (filter_details.getzero_star() == 6 &&filter_details.getone_star() == 6 && filter_details.gettwo_star() == 6 && filter_details.getthree_star() == 6 && filter_details.getfour_star() == 6 && filter_details.getfive_star() == 6) {
                        Filteredlist.add(availableHolidays.get(p));
                    }
                }
            }
        }
        setHotelsData(Filteredlist);
        if (Filteredlist.size()==0){
            showAlertDialog2("No Holiday Packages Available for Selected Filter");
        }
    }

    private void showAlertDialog2(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent nextActivity = new Intent(AvailableHolidaysActivity.this, Holiday_Filters_Activity.class);
                nextActivity.putExtra("Holidayslist", holidaysMainDTO.getAvailableHolidayPackages());
                startActivityForResult(nextActivity,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        alertDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (data!=null){
                Boolean Filter=data.getBooleanExtra("Filter",false);
                if (Filter){
                    Tags=null;
                    filter_details= (Holiday_Filters_DTO) data.getSerializableExtra("Filteredlist");
                    FilterMethod();
                }else {
                    callHotelsData();
                    filter_details=null;
                }

            }
        }
    }
}
