package com.NaTicket.n.flights;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.NaTicket.n.utils.BackActivity;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.flights.adpaters.Flights_Cities_Adpaters;
import com.NaTicket.n.flights.pojo.Flight_City_DTO;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nagarjuna on 12/29/2017.
 */

public class Flights_City_Search_Activity extends BackActivity {

    RecyclerView Cities_view;
    private SearchView searchCityView;
    Flights_Cities_Adpaters flightsCitiesAdpaters;
    private ProgressDialog mProgressDialog;
    private LinearLayout search_layout;
    String FlightType,City_Type;
    ArrayList<Flight_City_DTO> cityDtoArrayList=new ArrayList<>();
    ArrayList<Flight_City_DTO> TopcityArrayList=new ArrayList<>();
    boolean IsLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_city_search);
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        inittoolbar();
        toolbartitle.setText("Search City");
        init();
        Intent ip=getIntent();
        FlightType=ip.getStringExtra("FlightType");
        City_Type=ip.getStringExtra("CityType");
        if (FlightType.equals("1")){
            Load_Domestic_Top_Cities();
        }else {
            Load_International_Top_Cities();
        }
        isConnected();
    }

    private void init() {
        searchCityView = (SearchView)findViewById(R.id.searchCityView);
        search_layout= (LinearLayout) findViewById(R.id.search_layout);
        Cities_view = (RecyclerView)findViewById(R.id.cities_view);
        Cities_view.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        Cities_view.setLayoutManager(layoutManager);
        int id = searchCityView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final EditText editText = (EditText) searchCityView.findViewById(id);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IsLoaded){
                    InputMethodManager im = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
                    im.showSoftInput(editText, 0);
                    setcitydata(cityDtoArrayList);
                    IsLoaded=true;
                }
            }
        });
    }


    public void Load_Domestic_Top_Cities(){
            String response = loadJSONFromAsset("domestic_flights.json");
            if(response!=null) {
                InputStream stream = new ByteArrayInputStream(response.getBytes());
                Gson gson = new Gson();
                Reader reader = new InputStreamReader(stream);
                Flight_City_DTO[] citiesList = gson.fromJson(reader, Flight_City_DTO[].class);
                ArrayList<Flight_City_DTO> arrayList = new ArrayList<Flight_City_DTO>(Arrays.asList(citiesList));
                TopcityArrayList=arrayList;
                setTopCityData(arrayList);
            }
    }

    public void Load_International_Top_Cities(){
        String response = loadJSONFromAsset("international_flights.json");
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Flight_City_DTO[] citiesList = gson.fromJson(reader, Flight_City_DTO[].class);
            ArrayList<Flight_City_DTO> arrayList = new ArrayList<Flight_City_DTO>(Arrays.asList(citiesList));
            TopcityArrayList=arrayList;
            setTopCityData(arrayList);
        }
    }

    public String loadJSONFromAsset(String File) {
        String json = null;
        try {
            InputStream is = getAssets().open(File);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void isConnected(){
        if (Util.isNetworkAvailable(getApplicationContext())){
            callFlightCities();
        }else {
            Util.alertDialogShow(this,"Please Check Your Internet Connection");
        }
    }

    private void callFlightCities(){
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Getting Cities",this);
            ServiceClasses.getFlightCities(Flights_City_Search_Activity.this, Constants.GETAIRPORTS+"flightType="+FlightType);
        }else{
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void errorresponse (String response){
        hideProgressDialog();
        showAlertDialogforError("Timed out! \nTry again");
    }

    private void showAlertDialogforError(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                ServiceClasses.getFlightCities(Flights_City_Search_Activity.this, Constants.GETAIRPORTS+"flightType="+FlightType);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    public void getFlightCities(String response){
        if(response!=null) {
            hideProgressDialog();
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Flight_City_DTO[] flight_cities_dto=gson.fromJson(reader,Flight_City_DTO[].class);
            if (flight_cities_dto!=null) {
                ArrayList<Flight_City_DTO> arrayList = new ArrayList<Flight_City_DTO>(Arrays.asList(flight_cities_dto));
                cityDtoArrayList=arrayList;
            }
        }
    }

    public void setTopCityData(ArrayList<Flight_City_DTO> arrayList){
        flightsCitiesAdpaters = new Flights_Cities_Adpaters(arrayList,this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Flights_City_Search_Activity.this);
        Cities_view.setLayoutManager(mLayoutManager);
        Cities_view.setAdapter(flightsCitiesAdpaters);
        search(searchCityView);
    }


    public void setcitydata(ArrayList<Flight_City_DTO> arrayList){
        flightsCitiesAdpaters = new Flights_Cities_Adpaters(arrayList,this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Flights_City_Search_Activity.this);
        Cities_view.setLayoutManager(mLayoutManager);
        Cities_view.setAdapter(flightsCitiesAdpaters);
        search(searchCityView);
    }



    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new  SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                flightsCitiesAdpaters.getFilter().filter(newText);
                return true;
            }
        });
    }


    public void getCityData(Flight_City_DTO cityCTO) {
        // Util.showMessage(this,cityCTO.getCityId()+"");
        Intent intent=new Intent();
        intent.putExtra("CITY_NAME",cityCTO.getCity());
        intent.putExtra("CITY_ID",cityCTO.getAirportCode());
        intent.putExtra("COUNTRY_NAME",cityCTO.getCountry());
        intent.putExtra("AIRPORT_NAME",cityCTO.getAirportDesc());
        if (City_Type.equals("1")){
            setResult(2,intent);
        }else {
            setResult(3,intent);
        }

        finish();//finishing activity
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
}

