package com.NaTicket.n.loginpackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.serviceclasses.ServiceHandler;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.TextDrawable;
import com.NaTicket.n.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Nagarjuna on 15-01-2018.
 */

public class SignUp_Activity extends BackActivity implements View.OnClickListener {


    EditText email_et,phone_et,first_et,last_et;
    TextView sign_up_tv;
    String DeviceToken;
    Login_utils login_utils;
    ProgressDialog mProgressDialog;
    String Response,sRole="6";
    RadioButton User_Radio,Agent_Radio;
    String parentid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        login_utils=new Login_utils(this);
        initviews();
        inittoolbar();
        ImageView backBtn= (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Sign Up"+"");
        DeviceToken= Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public void initviews(){
        email_et= (EditText) findViewById(R.id.et_email);
        phone_et= (EditText) findViewById(R.id.et_mobile);
        first_et= (EditText) findViewById(R.id.et_first_name);
        last_et= (EditText) findViewById(R.id.et_last_name);
        User_Radio= (RadioButton) findViewById(R.id.user_radio_signUp);
        Agent_Radio= (RadioButton) findViewById(R.id.Agent_radio_signUp);
        sign_up_tv= (TextView) findViewById(R.id.signupTv);
        sign_up_tv.setOnClickListener(this);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_signUp);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.user_radio_signUp){
                    sRole="6";
                }else{
                    sRole="4";
                }
            }
        });


        String code = "+91 ";
        phone_et.setCompoundDrawablesWithIntrinsicBounds(new TextDrawable(code), null, null, null);
        phone_et.setCompoundDrawablePadding(code.length() * 20);

            //////On board soft key, handling its done (Tick)
            phone_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                        Log.i("Activity", "Enter pressed");
                        onClick(sign_up_tv);
                    }
                    return false;
                }
            });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signupTv:
                if (validatevalues()){
                   //callUserRegistration();
                    callVerifyUser();
                }
                break;
        }
    }



    ////////////////////////////////////Verify User/////////////////////////////////////////////////////////////////////////

    public void callVerifyUser() {

        showProgressDialog("Please wait...", this);
        if (Util.isNetworkAvailable(this)) {
            ServiceClasses.GETVERIFYUSER(this, Constants.BASEURL + Constants.VERIFYUSER + email_et.getText().toString()+"&mobileNumber=" +phone_et.getText().toString());
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getVerifyUserResponse(String response) {
        hideProgressDialog();

        if (response != null) {

            if(Util.getResponseMessage(response).equals("Success")){
                callGETOTP();

            }else if(Util.getResponseMessage(response).equals("Duplicate")){

                Util.showMessage(this,"Email/Mobile already registered");
            }else{
                Util.showMessage(this,"Couldn't reach Phone");
            }



        }
    }


    /////////////////////////////GET OTP/////////////////////////////////////////////////////////////////////////////////////////////

    public void callGETOTP() {

        showProgressDialog("Please wait...", this);
        if (Util.isNetworkAvailable(this)) {
            ServiceClasses.GETOTP(this, Constants.BASEURL + Constants.OTP + phone_et.getText().toString());
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getOTPResponse(String response) {
        hideProgressDialog();
        String key = null;
        if (response != null) {

            if(Util.getResponseMessage(response).equals("Success")){

                try {
                    JSONObject myObject = new JSONObject(response);
                    key = myObject.getString("Key");
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                Intent i=new Intent(this, Otp_Waiting_Activity.class);
                i.putExtra("FirstName",first_et.getText().toString());
                i.putExtra("LastName",last_et.getText().toString());
                i.putExtra("Email",email_et.getText().toString());
                i.putExtra("Mobile",phone_et.getText().toString());
                i.putExtra("Role",sRole);
                i.putExtra("VerificationKey",key);
                i.putExtra("MethodState","Register");


                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }else{
                Util.showMessage(this,"Couldn't reach Phone");
            }



        }
    }






    private boolean validatevalues(){
        String PhoneNumber=phone_et.getText().toString();
        if (!Util.checkField(first_et)){
            Util.showMessage(this,"Enter First Name");
            return false;
        }else if (!Util.checkField(last_et)){
            Util.showMessage(this,"Enter Last Name");
            return false;
        }else if (!Util.checkField(email_et)){
            Util.showMessage(this,"Enter Email");
            return false;
        }else if (!Util.validateEmail(email_et.getText().toString())){
            Util.showMessage(this,"Enter Valid Email");
            return false;
        }else if(!Util.checkField(phone_et)){
            Util.showMessage(this,"Enter Mobile Number");
            return false;
        }else if(PhoneNumber.length()!=10){
            Util.showMessage(this,"Enter 10 digit Mobile Number");
            return false;
        }else if (!Util.validateMobilenumber(PhoneNumber)){
            Util.showMessage(this, "Enter Valid Mobile Number");
            return false;
        }
        return true;
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
        super.onBackPressed();
        startActivity(new Intent(this,Login_Activity.class));
    }



}
