package com.NaTicket.n.hotels;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.NaTicket.n.common.activities.ResultIPC;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.hotels.adapters.HotelsAdapter;
import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.hotels.pojo.Hotel_Filters_DTO;
import com.NaTicket.n.hotels.pojo.HotelsMainDTO;
import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.hotels.pojo.SelectionDetailsDTO;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.Util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class AvailableHotelsActivity extends BackActivity {
    private RecyclerView hotelsView;
    HotelsAdapter mAdapter;
    SearchView searchHotelView;
    ArrayList<Room> roomList;
    private ProgressDialog mProgressDialog;
    private SelectionDetailsDTO selDetails;
    Toolbar toolbar;
    TextView title,sort,filter;
    private ArrayList<Integer> StarRating=new ArrayList<>();
    String Adults,Children,ChildAges;
    String Rooms;
    Hotel_utils hotel_utils;
    Login_utils login_utils;
    String UserId, User_agentid,UserType;
    Boolean FilterTrue = false;
    TextView hotels,price,star_rating;
    Currency_Utils currency_utils;
    HotelsMainDTO hotelsMainDTO;
    Hotel_Filters_DTO filter_details;
    TextView toolbartitle;
    String Tags;
    String Url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_hotels);
        login_utils=new Login_utils(this);
        currency_utils=new Currency_Utils(this);
        roomList =(ArrayList<Room>) getIntent().getSerializableExtra("roomsList");
        selDetails = (SelectionDetailsDTO) getIntent().getSerializableExtra("selDetails");
        intiViews();
        sortfilter();

        inittoolbar();
        TextView toolbartitle = (TextView) findViewById(R.id.toolbartitle);
        toolbartitle.setText(selDetails.getCityName());

        isConnected();

    }

    private void getLoginPreferences(){
        if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
            UserId = "6";
            UserType="User";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
            callHotelsData();

        } else if (login_utils.getUserDetails(Constants.USERTYPE).equals("4")) {
            UserId = "4";
            UserType="Agent";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
            callHotelsData();
        } else {
            UserId = "5";
            UserType="Guest";
            User_agentid =null;
            callHotelsData();
        }
    }



    private void setHotelsData(ArrayList<AvailableHotelsDTO> availableHotels) {
        for (AvailableHotelsDTO availableHotelsDTO :availableHotels){
            StarRating.add(availableHotelsDTO.getStarRating());
        }
        mAdapter   = new HotelsAdapter(this,availableHotels,selDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        hotelsView.setLayoutManager(mLayoutManager);
        hotelsView.setItemAnimator(new DefaultItemAnimator());
        hotelsView.setAdapter(mAdapter);
        search(searchHotelView);
    }



    private void callHotelsData() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Fetching Hotels...",this);
            Url=Constants.GETHOTELS+selDetails.getCityId()
                    +"&arrivalDate="+selDetails.getCheckIn()+"&departureDate="+selDetails.getCheckOut()+
                    "&rooms="+Rooms+"&adults="+Adults+"&children="+Children+
                    "&childrenAges="+ChildAges+"&NoOfDays="+selDetails.getNoofDays()+
                    "&userType="+UserId+"&hoteltype="+selDetails.getHoteltype()+"&user="+User_agentid+"&nationality="+selDetails.getNationalityid();
            ServiceClasses.getHotels(AvailableHotelsActivity.this,Url);

            Log.v("Url",Url);

        }else{
            Util.showMessage(this,Constants.NO_INT_MSG);
        }
    }


    private void intiViews() {
        hotelsView =(RecyclerView)findViewById(R.id.hotelsView);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        title= (TextView) findViewById(R.id.toolbar_title);
        sort= (TextView) findViewById(R.id.sort);
        filter= (TextView) findViewById(R.id.filter);
        searchHotelView=(SearchView)findViewById(R.id.searchHotelView);
        searchHotelView.setQueryHint("Hotel Name / Address");

        hotel_utils=new Hotel_utils(this);

        Adults= hotel_utils.getPaxDetails("Adults");
        Children=hotel_utils.getPaxDetails("Child");
        Rooms=hotel_utils.getPaxDetails("Rooms");
        ChildAges=hotel_utils.getPaxDetails("ChildAges");

        System.out.println("Adults:"+Adults+"  "+"Children:"+Children+"ChildAges:: "+ChildAges);

    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new  SearchView.OnQueryTextListener() {
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

        public void getHotelsResponse(String response) {
            hideProgressDialog();
            if(response!=null) {
                InputStream stream = new ByteArrayInputStream(response.getBytes());
                Gson gson = new Gson();
                Reader reader = new InputStreamReader(stream);
                hotelsMainDTO = gson.fromJson(reader, HotelsMainDTO.class);
                //  Util.showMessage(this,hotelsMainDTO.getAvailableHotels().get(0).getHotelName());
                if(hotelsMainDTO!=null && hotelsMainDTO.getAvailableHotels()!=null){
                    if(hotelsMainDTO.getAvailableHotels().size()>0){
                        setHotelsData(hotelsMainDTO.getAvailableHotels());
                    }
                    else {
                        //Util.showMessage(this,"No Hotels Available for Selected City");
                        showAlertDialog("No Hotels Available for Selected City");
                    }
                }
            }

        }


    public void errorreponse (String response){
        hideProgressDialog();
        showAlertDialogforError("Timed out! \nTry again");
    }


    private void sortfilter(){
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog dialog=new BottomSheetDialog(AvailableHotelsActivity.this);
                dialog.setContentView(R.layout.hotel_search_sorting);

                hotels= (TextView) dialog.findViewById(R.id.hotels);
                star_rating= (TextView) dialog.findViewById(R.id.star_rating);
                price= (TextView) dialog.findViewById(R.id.price);

                hotels.setTag("Asc");
                star_rating.setTag("Asc");
                price.setTag("Asc");

                if (Tags!=null){
                    if (Tags.equals("TitleASC")){
                        sortHotelColor();
                        hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    }else if (Tags.equals("TitleDSC")){
                        sortHotelColor();
                        hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    }

                    if (Tags.equals("DURASC")){
                        sortTimeColor();
                        star_rating.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    }else if (Tags.equals("DURDSC")){
                        sortTimeColor();
                        star_rating.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    }

                    if (Tags.equals("FareASC")){
                        sortPriceColor();
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    }else if (Tags.equals("FareDSC")){
                        sortPriceColor();
                        price.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    }
                }
                hotels.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sortHotelColor();
                        hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        star_rating.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        if (hotels.getTag().toString().equals("Asc")){
                            hotels.setTag("Desc");
                            Tags="TitleASC";
                            ArrayList<AvailableHotelsDTO> H=mAdapter.sortByNameAsc();
                            mAdapter.refreshList(H);
                            hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                        }else {
                            hotels.setTag("Asc");
                            Tags="TitleDSC";
                            ArrayList<AvailableHotelsDTO> h=mAdapter.sortByNameDesc();
                            mAdapter.refreshList(h);
                            hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                        }
                    }
                });

                star_rating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sortTimeColor();
                        hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        star_rating.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        if (star_rating.getTag().equals("Asc")){
                            star_rating.setTag("Desc");
                            Tags="DURASC";
                            ArrayList<AvailableHotelsDTO> h=mAdapter.sortByStarAsc();
                            mAdapter.refreshList(h);
                            star_rating.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_down,0);
                        }else{
                            star_rating.setTag("Asc");
                            Tags="DURDSC";
                            ArrayList<AvailableHotelsDTO> h=mAdapter.sortByStarDesc();
                            mAdapter.refreshList(h);
                            star_rating.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_up,0);
                        }
                    }
                });


                price.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sortPriceColor();
                        hotels.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        star_rating.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        price.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        if (price.getTag().toString().equals("Asc")){
                            price.setTag("Desc");
                            Tags="FareASC";
                            ArrayList<AvailableHotelsDTO> h=mAdapter.sortByPriceAsc();
                            mAdapter.refreshList(h);
                            price.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_down,0);
                        }else {
                            price.setTag("Asc");
                            Tags="FareDSC";
                            ArrayList<AvailableHotelsDTO> h=mAdapter.sortByPriceDesc();
                            mAdapter.refreshList(h);
                            price.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow_up,0);
                        }


                    }
                });



                dialog.show();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(AvailableHotelsActivity.this, Hotel_Filters_Activity.class);
                Bundle bundle = new Bundle();
                if (!FilterTrue) {
                    Tags=null;


                   // nextActivity.putExtra("URL",Url);
                    nextActivity.putExtra("Filteredlist",filter_details);

                    int sync = ResultIPC.get().setLargeData(hotelsMainDTO.getAvailableHotels());
                    nextActivity.putExtra("bigdata:synccode", sync);
                    //nextActivity.putExtra("Hotelslist", hotelsMainDTO.getAvailableHotels());


                }


                    startActivityForResult(nextActivity, 1);

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


    public void sortHotelColor(){
        hotels.setTextColor(getResources().getColor(R.color.colorAccent));
        star_rating.setTextColor(getResources().getColor(R.color.white));
        price.setTextColor(getResources().getColor(R.color.white));
    }
    public void sortTimeColor(){
        hotels.setTextColor(getResources().getColor(R.color.white));
        star_rating.setTextColor(getResources().getColor(R.color.colorAccent));
        price.setTextColor(getResources().getColor(R.color.white));
    }
    public void sortPriceColor(){
        hotels.setTextColor(getResources().getColor(R.color.white));
        star_rating.setTextColor(getResources().getColor(R.color.white));
        price.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void showAlertDialog(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }

    public void getHotelData(AvailableHotelsDTO availableHotelsDTO) {
        Intent i = new Intent(AvailableHotelsActivity.this, HotelDetailActivity.class);
        i.putExtra("SelHotel",availableHotelsDTO);
        i.putExtra("selDetails",selDetails);
        i.putExtra("roomList",roomList);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (data!=null){
                Boolean Filter=data.getBooleanExtra("Filter",false);
                if (Filter){
                    filter_details= (Hotel_Filters_DTO) data.getSerializableExtra("Filteredlist");
                    FilterMethod();
                }else {
                    callHotelsData();
                    filter_details=null;
                }

            }
        }
    }


    private void FilterMethod(){

        ArrayList<AvailableHotelsDTO> availableHotels=new ArrayList<>();
        availableHotels=hotelsMainDTO.getAvailableHotels();
        String Curr_Value=currency_utils.getCurrencyValue("Currency_Value");
        ArrayList<AvailableHotelsDTO> Filteredlist=new ArrayList<>();
        for (int p=0;p<availableHotels.size();p++) {
            int finalAmount = roundup((Double.parseDouble(availableHotels.get(p).getRoomDetails().get(0).getRoomTotal())+
                    Double.parseDouble(availableHotels.get(p).getRoomDetails().get(0).getServicetaxTotal())) * Double.parseDouble(Curr_Value));
            if (finalAmount >= filter_details.getMin_Rate() && finalAmount <= filter_details.getMax_Rate()) {
                if (availableHotels.get(p).getStarRating()==filter_details.getone_star()){
                    Filteredlist.add(availableHotels.get(p));
                }else if (availableHotels.get(p).getStarRating()==filter_details.gettwo_star()){
                    Filteredlist.add(availableHotels.get(p));
                }else if (availableHotels.get(p).getStarRating()==filter_details.getthree_star()){
                    Filteredlist.add(availableHotels.get(p));
                }else if (availableHotels.get(p).getStarRating()==filter_details.getfour_star()){
                    Filteredlist.add(availableHotels.get(p));
                }else if (availableHotels.get(p).getStarRating()==filter_details.getfive_star()){
                    Filteredlist.add(availableHotels.get(p));
                }else if (filter_details.getone_star()==6&&filter_details.gettwo_star()==6&&filter_details.getthree_star()==6&&filter_details.getfour_star()==6&&filter_details.getfive_star()==6){
                    Filteredlist.add(availableHotels.get(p));
                }
            }
        }
        setHotelsData(Filteredlist);
        if (Filteredlist.size()==0){
            showAlertDialog2("No Hotels Available for Selected Filter");
        }

    }

    private int roundup(double value){
        double dAbs = Math.abs(value);
        int amt2 = (int) dAbs;
        double result = dAbs - (double) amt2;
        int finalrate;
        if (result<0.5){
            finalrate= (int) Math.floor(value);
        }else {
            finalrate= (int) Math.ceil(value);
        }
        return finalrate;
    }

    public void isConnected(){
        if (Util.isNetworkAvailable(getApplicationContext())){
            getLoginPreferences();
        }else {
            Util.alertDialogShow(this,"Please Check Your Internet Connection");
        }
    }

    @Override
    public void onBackPressed() {
        Intent ip=new Intent(AvailableHotelsActivity.this,HotelSearchActivity.class);
        ip.putExtra("selDetails",selDetails);
        ip.putExtra("roomsList",roomList);
        ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ip);
        finish();
    }

    private void showAlertDialogforError(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                callHotelsData();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alertDialog.show();
    }

    private void showAlertDialog2(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
       // //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent nextActivity = new Intent(AvailableHotelsActivity.this, Hotel_Filters_Activity.class);
                //nextActivity.putExtra("Hotelslist", hotelsMainDTO.getAvailableHotels());
                //nextActivity.putExtra("Filteredlist",filter_details);

                nextActivity.putExtra("URL",Url);
                Tags=null;
                try {
                    startActivityForResult(nextActivity, 1);
                }catch (Exception e){

                }
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        alertDialog.show();
    }





}
