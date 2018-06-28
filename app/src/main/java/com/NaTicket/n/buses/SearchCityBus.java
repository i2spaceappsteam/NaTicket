package com.NaTicket.n.buses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.buses.adapters.CityAdater_Bus;
import com.NaTicket.n.buses.adapters.FromtopcitiesAdapter;
import com.NaTicket.n.buses.pojo.Top_city_DTO;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ankit on 22-02-2018.
 */

public class SearchCityBus extends BackActivity {
    private RecyclerView cities_view;
    private SearchView searchCityView;
    private CityAdater_Bus mAdapter;
    private ProgressDialog mProgressDialog;
    private LinearLayout search_layout;
    Toolbar toolbar;
    Boolean IsLoaded=false;
    Top_city_DTO citiesList;
    SharedPreferences preference;
    String Res;
    GridView list;
    LinearLayout header_lyt;
    FromtopcitiesAdapter listadapter;
    ArrayList<Top_city_DTO> cityDtoArrayList = new ArrayList<>();
    ArrayList<Top_city_DTO> TopcityArrayList = new ArrayList<>();
    Boolean SourceCity;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        inittoolbar();
        toolbartitle.setText("Search City");
        Intent ip = getIntent();
        SourceCity=ip.getBooleanExtra("SourceCity",false);

        initView();

        setDataToView_TopCities();

        isConnected();

    }

    private void initView() {
        searchCityView = (SearchView)findViewById(R.id.searchCityView);
        search_layout= (LinearLayout) findViewById(R.id.search_layout);
        list=(GridView)findViewById(R.id.GridView);
        header_lyt= (LinearLayout) findViewById(R.id.header_lyt);
        cities_view = (RecyclerView)findViewById(R.id.cities_view);
        cities_view.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        cities_view.setLayoutManager(layoutManager);


        int id = searchCityView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        editText = (EditText) searchCityView.findViewById(id);

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

    private void callBusesCityData() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Loading Cities...", this);
            String strurl = Constants.BASEURL+ Constants.Sources;

            ServiceClasses.getBusesCity(SearchCityBus.this, strurl);

            Log.v("Url", strurl);

        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }


    public void getBusesResponse(String response) {

        if(response!=null) {
            hideProgressDialog();
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Top_city_DTO[] citiesList = gson.fromJson(reader, Top_city_DTO[].class);

            if (citiesList!=null) {
                ArrayList<Top_city_DTO> arrayList = new ArrayList<Top_city_DTO>(Arrays.asList(citiesList));
                cityDtoArrayList=arrayList;
            }

        }
    }

    public void errorreponse(String response) {
        hideProgressDialog();
        showAlertDialogforError("Timed out! \nTry again");
    }


    public void setDataSources(String response) {

        if(response!=null) {


            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Top_city_DTO[] citiesList = gson.fromJson(reader, Top_city_DTO[].class);
            cityDtoArrayList = new ArrayList<Top_city_DTO>(Arrays.asList(citiesList));
            mAdapter = new CityAdater_Bus(cityDtoArrayList,this);
            cities_view.setAdapter(mAdapter);
            search(searchCityView);


        }
    }


    private void setDataToView_TopCities() {
        String response = loadJSONFromAsset_TopCities();

        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Top_city_DTO[] citiesList = gson.fromJson(reader, Top_city_DTO[].class);

            cityDtoArrayList = new ArrayList<Top_city_DTO>(Arrays.asList(citiesList));
            listadapter = new FromtopcitiesAdapter(this, cityDtoArrayList);
            list.setAdapter(listadapter);
            list.setTextFilterEnabled(true);

//            ArrayList<Top_city_DTO> arrayList = new ArrayList<Top_city_DTO>(Arrays.asList(citiesList));
//            TopcityArrayList=arrayList;
//            setTopCityData(arrayList);
            search(searchCityView);

        }
    }


    public void setTopCityData(ArrayList<Top_city_DTO> arrayList){
        mAdapter = new CityAdater_Bus(arrayList,this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(SearchCityBus.this);
        cities_view.setLayoutManager(mLayoutManager);
        cities_view.setAdapter(mAdapter);



        search(searchCityView);
    }


    public void setcitydata(ArrayList<Top_city_DTO> arrayList){
        mAdapter = new CityAdater_Bus(arrayList,this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(SearchCityBus.this);
        cities_view.setLayoutManager(mLayoutManager);
        cities_view.setAdapter(mAdapter);
        cities_view.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
        header_lyt.setVisibility(View.GONE);
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
                if (!IsLoaded){
                    setcitydata(cityDtoArrayList);
                    IsLoaded=true;
                }
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }



    public void isConnected(){
        if (Util.isNetworkAvailable(getApplicationContext())){
            callBusesCityData();
        }else {
            Util.alertDialogShow(this,"Please Check Your Internet Connection");
        }
    }


    public String loadJSONFromAsset_TopCities() {
        String json = null;
        InputStream is = null;
        try {

                is=getAssets().open("bus_top_cities.json");

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



    public void getCityData(Top_city_DTO cityCTO) {
        // Util.showMessage(this,cityCTO.getCityId()+"");
        Intent intent=new Intent();
        intent.putExtra("CITY_NAME",cityCTO.getName());
        intent.putExtra("CITY_ID",cityCTO.getId());
        if(SourceCity) {
            setResult(2, intent);
        }else{
            setResult(3, intent);
        }
        finish();//finishing activity
    }

    private  void showProgressDialog(String msg, Context context) {
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

    private void showAlertDialogforError(String s) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                callBusesCityData();
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








}

