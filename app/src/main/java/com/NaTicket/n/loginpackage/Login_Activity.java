package com.NaTicket.n.loginpackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.R;
import com.NaTicket.n.common.MainActivity;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.loginpackage.pojo.User_Details_DTO;
import com.NaTicket.n.serviceclasses.Service_Login_Package;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


public class Login_Activity extends BackActivity implements View.OnClickListener{
    EditText Email_et,Password_et;
    TextView Login_tv,Signup_tv,Forgot_pw_tv;
    User_Details_DTO getUserDetailsDTO;
    ProgressDialog mProgressDialog;
    String Response,sRole="6";
    RadioButton User_Radio,Agent_Radio;
    Boolean doubleBackToExitPressedOnce=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initviews();
        /*inittoolbar();
        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Login"+"");*/

    }

    public void initviews(){
        Email_et= (EditText) findViewById(R.id.etEmail);
        Password_et= (EditText) findViewById(R.id.etPass);
        Login_tv= (TextView) findViewById(R.id.loginTv);
        Signup_tv= (TextView) findViewById(R.id.sign_up);
        Forgot_pw_tv= (TextView) findViewById(R.id.forgot);
        User_Radio= (RadioButton) findViewById(R.id.user_radio);
        Agent_Radio= (RadioButton) findViewById(R.id.Agent_radio);
        User_Radio.setChecked(true);
        Login_tv.setOnClickListener(this);
        Forgot_pw_tv.setOnClickListener(this);
        Signup_tv.setOnClickListener(this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_log);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.user_radio){
                    sRole="6";
                }else{
                    sRole="4";
                }
            }
        });

        Email_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG", "Enter pressed");
                    onClick(Login_tv);
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginTv:
                if (validatevalues()){
                        callUserLogin();
                }
                break;
            case R.id.sign_up:
                startActivity(new Intent(this, SignUp_Activity.class));
                break;
            case R.id.forgot:
                startActivity(new Intent(this, Forgot_Password_Activity.class));
                break;
        }
    }

    private boolean validatevalues(){
       if (!Util.checkField(Email_et)){
            Util.showMessage(this,"Enter Email");
            return false;
        }else if (!Util.checkField(Password_et)){
            Util.showMessage(this,"Enter Password");
            return false;
        }else if (!Util.validateEmail(Email_et.getText().toString())){
            Util.showMessage(this,"Enter Valid Email");
            return false;
        }
        return true;
    }




    public void callUserLogin(){
        showProgressDialog("Please Wait",this);
        if (Util.isNetworkAvailable(this)){
            if(sRole.equals("6")) {
                Service_Login_Package.UserLOGIN(this, Constants.USERLOGIN, preparePayLoad());
            }else{
                Service_Login_Package.UserLOGIN(this, Constants.AGENTLOGIN, preparePayLoad());
            }
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    private String preparePayLoad(){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("Username", Email_et.getText().toString());
            jsonBody.put("Password", Password_et.getText().toString());
            jsonBody.put("Client", null);
            jsonBody.put("IsAgentfromPSALogin", false);
            jsonBody.put("IsWebApiAgentLogin", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }

    public void postUserLOGINResponse(String response) {
        hideProgressDialog();
        if (response != null) {
            if (Util.getResponseCode(response) == 200) {
                if (Util.getResponseMessage(response) != null) {
                    callUserDetails();
                } else {
                   Util.showMessage(this,Util.getResponseMessage(response)+"");
                }
            } else {
                Util.showMessage(this, Util.getResponseMessage(response)+"");
            }
        }
    }

    public void callUserDetails(){
        if (Util.isNetworkAvailable(this)){
            if(sRole.equals("6")) {
                Service_Login_Package.getUserDetails(this, Constants.USERDETAILS + Email_et.getText().toString());
            }else{
                Service_Login_Package.getUserDetails(this, Constants.AGENTDETAILS + Email_et.getText().toString());
            }
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getUserDetailsResponses(String response) {
        InputStream stream = new ByteArrayInputStream(response.getBytes());
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(stream);
        getUserDetailsDTO = gson.fromJson(reader, User_Details_DTO.class);
        if(getUserDetailsDTO!=null){
           callDecrpytmethod(getUserDetailsDTO.getUserId());
            Response=response;
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
        hideProgressDialog();
        if (Util.getResponseCode(response)==200){
            String Decrpted=Util.getResponseMessage(response);
            Login_utils loginutils=new Login_utils(this);
            if(sRole.equals("6")) {
                loginutils.setUserDetails(getUserDetailsDTO.getFirstName() + " " + getUserDetailsDTO.getLastName(),
                        getUserDetailsDTO.getEmail(),
                        Password_et.getText().toString(),
                        getUserDetailsDTO.getMobile(),
                        getUserDetailsDTO.getDOB(),
                        getUserDetailsDTO.getGender(),
                        getUserDetailsDTO.getUserId(),
                        Decrpted,
                        "6",
                        Response,
                        getUserDetailsDTO.getAddress(),
                        getUserDetailsDTO.getBalance());
            }else{
                loginutils.setUserDetails(getUserDetailsDTO.getFirstName() + " " + getUserDetailsDTO.getLastName(),
                        getUserDetailsDTO.getEmail(),
                        Password_et.getText().toString(),
                        getUserDetailsDTO.getMobile(),
                        getUserDetailsDTO.getDOB(),
                        getUserDetailsDTO.getGender(),
                        getUserDetailsDTO.getUserId(),
                        Decrpted,
                        "4",
                        Response,
                        getUserDetailsDTO.getAddress(),
                        getUserDetailsDTO.getBalance());
            }
            Intent ip=new Intent(Login_Activity.this, MainActivity.class);
            Util.showMessage(this,"Logged in Successfully");
            startActivity(ip);
        }else {
            Util.showMessage(this,"Some thing went Wrong");
        }
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
