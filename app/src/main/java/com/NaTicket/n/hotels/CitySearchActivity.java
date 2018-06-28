package com.NaTicket.n.hotels;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.hotels.adapters.CitiesAdapter;
import com.NaTicket.n.hotels.pojo.CityCTO;
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


public class CitySearchActivity extends BackActivity {
    private RecyclerView cities_view;
    private SearchView searchCityView;
    private CitiesAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private LinearLayout  search_layout;
    String HotelType;
    Toolbar toolbar;
    Boolean IsLoaded=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        inittoolbar();
        toolbartitle.setText("Search City");
        initView();

        Intent ip=getIntent();
        HotelType=ip.getStringExtra("hoteltype");
        setDataToView_TopCities();

    }

    private void initView() {
        searchCityView = (SearchView)findViewById(R.id.searchCityView);
        search_layout= (LinearLayout) findViewById(R.id.search_layout);

        int id = searchCityView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final EditText editText = (EditText) searchCityView.findViewById(id);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length()==2){
                    populate_searched(s.toString());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
                    setDataToView_TopCities();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    setDataToView_TopCities();
                }
            }
        });

        searchCityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cities_view = (RecyclerView)findViewById(R.id.cities_view);
        cities_view.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        cities_view.setLayoutManager(layoutManager);

    }

    private void populate_searched(String s) {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please Wait...",this);
            ServiceClasses.getHotelCities(CitySearchActivity.this, Constants.GETHOTELCITIES+"hoteltype="+HotelType+"&parm="+s);
        }else{
            Util.showMessage(CitySearchActivity.this, Constants.NO_INT_MSG);
        }
    }

    public void getHotelCitiesResponse(String response) {
        hideProgressDialog();
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            CityCTO[] citiesList = gson.fromJson(reader, CityCTO[].class);
            ArrayList<CityCTO> arrayList = new ArrayList<CityCTO>(Arrays.asList(citiesList));
            mAdapter = new CitiesAdapter(arrayList,this,HotelType);
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
            CityCTO[] citiesList = gson.fromJson(reader, CityCTO[].class);
            ArrayList<CityCTO> arrayList = new ArrayList<CityCTO>(Arrays.asList(citiesList));
            mAdapter = new CitiesAdapter(arrayList,this,HotelType);
            cities_view.setAdapter(mAdapter);
            if (HotelType.equals("1")){
                search(searchCityView);
            }
            hideProgressDialog();
        }
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




    public String loadJSONFromAsset_TopCities() {
        String json = null;
        InputStream is = null;
        try {
            if (HotelType.equals("1")){
                is = getAssets().open("domestic_hotels.json");
            }else {
                is=getAssets().open("popularhotelcities.json");
            }
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



    public void getCityData(CityCTO cityCTO) {
        // Util.showMessage(this,cityCTO.getCityId()+"");
        Intent intent=new Intent();
        intent.putExtra("CITY_NAME",cityCTO.getCityName());
        intent.putExtra("CITY_ID",cityCTO.getCityId());
        setResult(2,intent);
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
