package com.NaTicket.n.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.BuildConfig;
import com.NaTicket.n.R;
import com.NaTicket.n.buses.Buses_MainActivity;
import com.NaTicket.n.common.adapter.FeatureGridAdapter;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.common.pojo.Version_DTO;
import com.NaTicket.n.flights.Flights_Search_Activity;
import com.NaTicket.n.holidays.HolidaySearchActivity;
import com.NaTicket.n.hotels.HotelSearchActivity;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.recharges.Recharge_MainActivity;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Ankit on 08-01-2018.
 */

public class MainActivity extends BaseActivity {

    private GridView featuresgridView;
    private ArrayList<Integer> featuresList=new ArrayList<>();
    Login_utils login_utils;
    String User_Role;
    Boolean doubleBackToExitPressedOnce=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_utils=new Login_utils(this);
        getLoginPreefernces();
        setcurrency();
        getDynamicPageContent();




        featuresgridView= (GridView) findViewById(R.id.featuresgridView);
        featuresList.add(1);
        featuresList.add(2);
        featuresList.add(3);
        featuresList.add(4);
        featuresList.add(5);


        setDataAdapters();








    }
    public void getLoginPreefernces() {
    if(login_utils.getUserDetails(Constants.USERTYPE).equals("6")){
        User_Role=login_utils.getUserDetails(Constants.USERTYPE);
    }else {
        User_Role="5";
    }
    }



    private void setDataAdapters() {
        FeatureGridAdapter featureGridAdapter = new FeatureGridAdapter(this, featuresList);
        featuresgridView.setAdapter(featureGridAdapter);
        //setDynamicHeight(featuresgridView);



        featuresgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String feature = ((TextView) v.findViewById( R.id.featureName)).getText().toString();
                if (feature.matches("Flights"))
                    startActivity(new Intent(MainActivity.this, Flights_Search_Activity.class));
                if(feature.matches("Hotels"))
                    startActivity(new Intent(MainActivity.this, HotelSearchActivity.class));
                if(feature.matches("Buses"))
                    startActivity(new Intent(MainActivity.this, Buses_MainActivity.class));
                if(feature.matches("Recharges"))
                    startActivity(new Intent(MainActivity.this, Recharge_MainActivity.class));
                if(feature.matches("Holidays"))
                    startActivity(new Intent(MainActivity.this, HolidaySearchActivity.class));


            }
        });
    }

    public void setcurrency(){
        Currency_Utils currency_utils=new Currency_Utils(MainActivity.this);
        currency_utils.addCurrency("INR","1","₹ ");
    }


    public void getDynamicPageContent(){
        if (Util.isNetworkAvailable(this)){
            ServiceClasses.DYNAMICCONTENT(this, Constants.DYNAMICPAGECONTENT);
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getDynamicresponse(String response) {
        if (response!=null){
            login_utils.setDynamicData(response);
        }

    }






    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
            System.exit(0);
            return;

        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to close "+getResources().getString(R.string.app_name), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 5000);
    }
}
