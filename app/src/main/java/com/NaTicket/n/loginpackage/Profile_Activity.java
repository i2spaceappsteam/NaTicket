package com.NaTicket.n.loginpackage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.loginpackage.pojo.User_Details_DTO;
import com.NaTicket.n.serviceclasses.ServiceHandler;
import com.NaTicket.n.serviceclasses.Service_Login_Package;
import com.NaTicket.n.utils.BackActivity;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Nagarjuna on 1/12/2018.
 */

public class Profile_Activity extends BackActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener{
    Login_utils login_utils;
    public EditText profile_email, profile_Mobile, profile_FirstName, profile_LastName;
    RadioGroup Gendergrp;
    RadioButton male, female;
    ImageView mEditPro;
    String DeviceToken;
    TextView Balance,saveT,age;
    LinearLayout age_layout;
    DatePickerDialog datePickerDialog;
    int cur = 0;
    static final int DATE_DIALOG_ID = 1;
    int Year,Month,Day;
    User_Details_DTO getUserDetailsDTO;
    String Response;
    String parentid;
    ProgressDialog mProgressDialog;
    String password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        //inittoolbar();
        TextView toolbar_title= (TextView) findViewById(R.id.toolbartitle);
        toolbar_title.setText(""+"Profile"+"");

        ImageView back_button= (ImageView) findViewById(R.id.backBtn);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.startHomeActivity(Profile_Activity.this);
            }
        });

        login_utils=new Login_utils(this);
        password=login_utils.getUserDetails(Constants.USERPASSWORD);
        initviews();
    }

    public void initviews(){
        profile_email = (EditText) findViewById(R.id.profile_email);
        profile_Mobile = (EditText) findViewById(R.id.profile_Mobile);
        profile_FirstName = (EditText) findViewById(R.id.profile_FirstName);
        profile_LastName= (EditText) findViewById(R.id.profile_Lastname);
        Gendergrp = (RadioGroup) findViewById(R.id.radioSex);
        Balance= (TextView) findViewById(R.id.user_bal);
        age = (TextView) findViewById(R.id.etAge);
        age_layout= (LinearLayout) findViewById(R.id.age_layout);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);
        saveT = (TextView) findViewById(R.id.save_text);
        mEditPro = (ImageView) findViewById(R.id.edit_profile);
        DeviceToken= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        profile_FirstName.setEnabled(false);
        profile_LastName.setEnabled(false);
        profile_Mobile.setEnabled(false);
        profile_email.setEnabled(false);
        age.setEnabled(false);
        age_layout.setEnabled(false);
        male.setEnabled(false);
        female.setEnabled(false);


        age_layout.setOnClickListener(this);
        saveT.setOnClickListener(this);
        mEditPro.setOnClickListener(this);
        getUserDetailsResponse(login_utils.getUserDetails(Constants.RESPONSE));
    }



    public void getUserDetailsResponse(String response) {
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            User_Details_DTO getUserDetailsDTO = gson.fromJson(reader, User_Details_DTO.class);
            if(getUserDetailsDTO!=null)
                getDetails(getUserDetailsDTO);
        }
    }

    public void getDetails(User_Details_DTO agent_user_detailsDTO) {
        profile_FirstName.setText(agent_user_detailsDTO.getFirstName());
        profile_LastName.setText(agent_user_detailsDTO.getLastName());
        profile_email.setText(agent_user_detailsDTO.getEmail());
        profile_Mobile.setText(agent_user_detailsDTO.getMobile());
        if (login_utils.getUserDetails(Constants.USERTYPE).equals("4")) {
            Balance.setVisibility(View.VISIBLE);
            Balance.setText("Bal : " + Constants.AGENT_CURRENCY_SYMBOL + agent_user_detailsDTO.getBalance() + "");
            new Getparentid().execute();
        }
        if (agent_user_detailsDTO.getGender().equals("Male")) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }
        String dob = agent_user_detailsDTO.getDOB();
        String[] separated = dob.split(" ");

        if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
            age.setText(separated[0]);
        } else {
              age.setText(getdate(separated[0]));
        }
    }






    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
        if(cur == DATE_DIALOG_ID){
                age.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);
        }
    }


    @Override
    public void onClick(View v) {
      switch(v.getId()){
          case R.id.age_layout:
              cur = DATE_DIALOG_ID;
              datePickerDialog = DatePickerDialog.newInstance(Profile_Activity.this, Year, Month-1, Day);
              datePickerDialog.setThemeDark(false);
              datePickerDialog.showYearPickerFirst(false);
              datePickerDialog.setAccentColor(getResources().getColor(R.color.colorAccent));
              datePickerDialog.setTitle("Select Date of Birth");
              datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
              break;
          case R.id.edit_profile:
              saveT.setVisibility(View.VISIBLE);
              mEditPro.setVisibility(View.GONE);
              age.setTextColor(getResources().getColor(R.color.colorBlack));
              profile_FirstName.setTextColor(getResources().getColor(R.color.colorBlack));
              profile_LastName.setTextColor(getResources().getColor(R.color.colorBlack));
              profile_FirstName.setEnabled(true);
              profile_LastName.setEnabled(true);
              age.setEnabled(true);
              male.setEnabled(true);
              female.setEnabled(true);
              age_layout.setEnabled(true);
              break;
          case R.id.save_text:
              if (validateparams()){
                  saveT.setVisibility(View.GONE);
                  mEditPro.setVisibility(View.VISIBLE);
                  age.setTextColor(getResources().getColor(R.color.colorGreyText));
                  profile_FirstName.setTextColor(getResources().getColor(R.color.colorGreyText));
                  profile_LastName.setTextColor(getResources().getColor(R.color.colorGreyText));
                  profile_FirstName.setEnabled(false);
                  profile_LastName.setEnabled(false);
                  profile_Mobile.setEnabled(false);
                  profile_email.setEnabled(false);
                  age.setEnabled(false);
                  male.setEnabled(false);
                  female.setEnabled(false);


                      callUserRegistration();

              }
              break;
      }
    }


    private void callUserRegistration() {
        mProgressDialog = ProgressDialog.show(Profile_Activity.this, "","Updating profile..." );
        mProgressDialog.setCancelable(false);
        if(Util.isNetworkAvailable(this)) {
            if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
                ServiceClasses.postUserRegistration(Profile_Activity.this, Constants.USERREGISTRATION, preparePayload());
            }else{
                ServiceClasses.postUserRegistration(Profile_Activity.this, Constants.AGENTREGISTRATION, preparePayload());
            }
        }else{
            Util.showMessage(this,Constants.NO_INT_MSG);
        }
    }


    public String getgender(){
        String Gender;
        if (male.isChecked()){
            Gender="1";
        }else {
            Gender="0";
        }
        return Gender;
    }


    private String preparePayload() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("IsSIteAdminAgent", "0");
            jsonBody.put("FirstName", profile_FirstName.getText().toString());
            jsonBody.put("LastName", profile_LastName.getText().toString());
            jsonBody.put("Password",password);
            jsonBody.put("Gender", getgender());
            jsonBody.put("DOB", age.getText().toString());
            jsonBody.put("Email", profile_email.getText().toString());
            jsonBody.put("AlternateEmail", "");
            jsonBody.put("Mobile", profile_Mobile.getText().toString());
            jsonBody.put("AlternateMobile", "");
            jsonBody.put("Telephone", "");
            jsonBody.put("Address", "hyd");
            jsonBody.put("City", "hyd");
            jsonBody.put("State", "2");
            jsonBody.put("Pincode", "500000");
            jsonBody.put("Status", "1");
            jsonBody.put("ActivationCode", "f75b021a-ca18-4812-a475-4f937054a52e");
            jsonBody.put("DeviceModel", Constants.DeviceModel);
            jsonBody.put("DeviceOS", Constants.DeviceOs);
            jsonBody.put("DeviceOSVersion",Constants.DeviceOsversion);
            jsonBody.put("DeviceToken",DeviceToken);
            jsonBody.put("UserId",login_utils.getUserDetails(Constants.DEPRCYT_USER_ID));
            if(login_utils.getUserDetails(Constants.USERTYPE).equals("4")){

                jsonBody.put("IsAgent", "1");
                jsonBody.put("ParentId", parentid);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Request:; "+jsonBody.toString());
        return  jsonBody.toString();
    }



    public void postUserRegistrationResponse(String response) {
        mProgressDialog.dismiss();
        if(Util.getResponseCode(response)==200&&Util.getResponseMessage(response).matches(Constants.SUCCESS_RESPONSE)){

                Util.showMessage(this,"Profile Updated Sucessfully");
                System.out.println("Response:; "+response);
                callGetUserDetails(profile_email.getText().toString());

        }else{
            System.out.println("Response:; "+response);
            Util.showMessage(this,"Something Went Wrong!");
        }
    }

    private void callGetUserDetails(String email) {
        if(Util.isNetworkAvailable(this)) {
            if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
                Service_Login_Package.getUserDetails(Profile_Activity.this, Constants.USERDETAILS + email);
            }else{
                Service_Login_Package.getUserDetails(Profile_Activity.this, Constants.AGENTDETAILS + email);
            }
        }else{
            Util.showMessage(this,Constants.NO_INT_MSG);
        }
    }

    public void getUserDetailsResponses(String response) {
        if(response!=null) {
            Response=response;
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            getUserDetailsDTO = gson.fromJson(reader, User_Details_DTO.class);
            if(getUserDetailsDTO!=null)
                callDecrpytmethod(getUserDetailsDTO.getUserId());
        }
    }


    public void callDecrpytmethod(String UserId){
        if (Util.isNetworkAvailable(this)){
            Service_Login_Package.getDecrpted_User_Id(this,Constants.DECRIPTCODE+UserId);
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getDecrptedUserResponses(String response) {
        if (Util.getResponseCode(response)==200){
            String Decrpted=Util.getResponseMessage(response);
            Login_utils loginutils=new Login_utils(this);
            if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
                loginutils.setUserDetails(getUserDetailsDTO.getFirstName() + " " + getUserDetailsDTO.getLastName(),
                        getUserDetailsDTO.getEmail(),
                        password,
                        getUserDetailsDTO.getMobile(),
                        getUserDetailsDTO.getDOB(),
                        getUserDetailsDTO.getGender(),
                        getUserDetailsDTO.getUserId(),
                        Decrpted,
                        "6",
                        Response,
                        getUserDetailsDTO.getAddress(),
                        getUserDetailsDTO.getBalance());
                getDetails(getUserDetailsDTO);
            }else{
                loginutils.setUserDetails(getUserDetailsDTO.getFirstName() + " " + getUserDetailsDTO.getLastName(),
                        getUserDetailsDTO.getEmail(),
                        password,
                        getUserDetailsDTO.getMobile(),
                        getUserDetailsDTO.getDOB(),
                        getUserDetailsDTO.getGender(),
                        getUserDetailsDTO.getUserId(),
                        Decrpted,
                        "4",
                        Response,
                        getUserDetailsDTO.getAddress(),
                        getUserDetailsDTO.getBalance());
                getDetails(getUserDetailsDTO);
            }
        }else {
            Util.showMessage(this,"Some thing went Wrong");
        }
    }


    private boolean validateparams() {
        if (profile_FirstName.getText().toString().matches("")){
            Util.showMessage(this,"Enter First Name");
            return false;
        }else if (profile_LastName.getText().toString().matches("")){
            Util.showMessage(this,"Enter Last Name");
            return false;
        }else if (age.getText().toString().matches("")){
            Util.showMessage(this,"Enter Date of Birth");
            return false;
        }
        return  true;
    }


    public class Getparentid extends AsyncTask<String,String,String> {

        String Res="";
        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh=new ServiceHandler();
            String jsonstr = sh.makeServiceCall(Constants.AGENTLIST, ServiceHandler.GET);
            if(jsonstr !=""){
                try{

                    JSONArray jarray=new JSONArray(jsonstr);
                    for(int i=0;i<jarray.length();i++){
                        JSONObject jsonObject=jarray.getJSONObject(i);
                        Res=jsonObject.getString("UserId");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return Res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parentid=s;
            System.out.println("parentid:-"+parentid);

        }
    }


    public String getdate(String date){
        String[] items1 = date.split("/");
        String m1=items1[0];
        String d1=items1[1];
        String y1=items1[2];
        Day = Integer.parseInt(d1);
        Month = Integer.parseInt(m1);
        Year = Integer.parseInt(y1);
        return String.format("%02d", Day)+"-"+String.format("%02d", Month)+"-"+Year;
    }

    @Override
    public void onBackPressed() {
        Util.startHomeActivity(Profile_Activity.this);
    }
}
