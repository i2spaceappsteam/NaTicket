package com.NaTicket.n.common;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.GetClientDetails;
import com.NaTicket.n.common.pojo.Version_DTO;
import com.NaTicket.n.loginpackage.Login_Activity;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.PrefManager;
import com.NaTicket.n.utils.Util;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Nagarjuna on 8/14/2017.
 */

public class Splash_Activity extends AppCompatActivity {

    RelativeLayout parentLinear;
    Boolean first=false;
    String Services;
    String data;
    GetClientDetails ClientDetailsDTO;
    private PrefManager prefManager;
    String C_Response;
    String currentVersion;
    String online_version = null;
    Boolean NewUpdateAvailable=false;
    Login_utils login_utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.splash_screen);
        parentLinear = (RelativeLayout) findViewById(R.id.parentLinear);
        login_utils=new Login_utils(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);


        prefManager = new PrefManager(this);

        callVersionCodeCheck();
        callcountrycodes();
        callClientDetails();


    }

    private void callcountrycodes() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getDailingcodes(Splash_Activity.this, Constants.COUNTRY_DAILING_CODES);
        }else{
            Util.showMessage(this,Constants.NO_INT_MSG);
        }
    }

    public void getdialingcodesresponse(String response) {
        if (response!=null){
            login_utils.setDailindcodesresponse(response);
        }
    }

    private void callClientDetails() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getClientDetails(Splash_Activity.this, Constants.CLIENTDETAILS);
        }else{
            Snackbar snackbar = Snackbar
                    .make(parentLinear, "No internet connection", Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(getResources().getColor(R.color.colorWhite))
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callClientDetails();
                        }
                    });

            snackbar.show();
        }
    }


    public void getClientDetailsResponse(String response) {
        GetClientDetails getClientDetailsDTO =null;
        if(response!=null){

            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            getClientDetailsDTO = gson.fromJson(reader, GetClientDetails.class);
            if(getClientDetailsDTO!=null && getClientDetailsDTO.getServices()!=null && getClientDetailsDTO.getServices().size()>0){
                ClientDetailsDTO = getClientDetailsDTO;
                callParentlist();
            }else{
                Util.showMessage(this,"Something went wrong!");
            }
        }else {
            Util.showMessage(this, "Something went wrong!");
        }

    }

    private void callParentlist() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getParentlist(Splash_Activity.this,Constants.AGENTLIST);
        }else{
            Snackbar snackbar = Snackbar
                    .make(parentLinear, "No internet connection", Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(getResources().getColor(R.color.colorWhite))
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callParentlist();
                        }
                    });

            snackbar.show();
        }
    }

    public void getParentlistResponse(String response) {
        String parentid = null;
        if (response!=null){
            JSONArray jarray = null;
            try {
                jarray = new JSONArray(response);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jsonObject = jarray.getJSONObject(i);
                    parentid = jsonObject.getString("UserId");
                }
                final Login_utils login_utils=new Login_utils(this);
                final String finalParentid = parentid;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(NewUpdateAvailable){
                            Intent i=new Intent(Splash_Activity.this,Version_updater_Activity.class);
                            i.putExtra("NewVesionCode",online_version);
                            //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            finish();
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }else {
                            login_utils.setClientDetails(ClientDetailsDTO.getClientId(), finalParentid, C_Response);
                            if (prefManager.isFirstTimeLaunch()) {
                                Intent i = new Intent(Splash_Activity.this, WelcomeActivity.class);
                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            } else {
                                if (login_utils.getUserDetails(Constants.USERPHONE) == null || login_utils.getUserDetails(Constants.USERPHONE).equals("")) {
                                    Intent i = new Intent(Splash_Activity.this, Login_Activity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                }else{
                                    Intent i = new Intent(Splash_Activity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                }
                            }
                        }
                    }
                }, 3000);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    private void callVersionCodeCheck() {

        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (Util.isNetworkAvailable(this)) {
            ServiceClasses.GETVERSIONCODE(Splash_Activity.this, Constants.VERSIONCODE+"119");
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getversioncoderesponse(String response) {
        if (response != null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Version_DTO version_dto = gson.fromJson(reader, Version_DTO.class);

            if (version_dto.getVersionDetails().size() != 0) {
                for (int p = 0; p < version_dto.getVersionDetails().size(); p++) {
                    if (version_dto.getVersionDetails().get(p).getClientID() == 119) {
                        online_version = version_dto.getVersionDetails().get(p).getAndroidVersion();
                    }
                }
                String currentversion = currentVersion;
                if (currentversion != null && online_version != null) {
                    if (Float.valueOf(currentversion) < Float.valueOf(online_version)) {
                        NewUpdateAvailable=true;
                    }
                }
            }

        }

    }


    public void errorresponse (String response){

        Snackbar snackbar = Snackbar
                .make(parentLinear, "Couldn't create connection", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.colorWhite))
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callClientDetails();
                    }
                });

        snackbar.show();
    }
}
