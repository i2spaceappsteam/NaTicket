package com.NaTicket.n.loginpackage;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.loginpackage.pojo.User_Details_DTO;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.serviceclasses.Service_Login_Package;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.TextDrawable;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.UUID;

/**
 * Created by Nagarjuna on 15-01-2018.
 */

public class Forgot_Password_Activity extends BackActivity implements View.OnClickListener {

    EditText email_et;
    TextView enter_tv;
    ProgressDialog mProgressDialog;
    User_Details_DTO getUserDetailsDTO;
    String Mobile="",FirstNAme="",LastName="",Email="";

    RadioButton User_Radio,Agent_Radio;
    String sRole="6";
    String MethodState="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        inittoolbar();
        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Forgot Password"+"");
        initviews();

    }

    public void initviews(){
        email_et=  findViewById(R.id.email_et);
        enter_tv=  findViewById(R.id.submitTv);
        enter_tv.setOnClickListener(this);


        User_Radio=  findViewById(R.id.user_radio);
        Agent_Radio=  findViewById(R.id.Agent_radio);
        User_Radio.setChecked(true);

        RadioGroup radioGroup =  findViewById(R.id.radio_group_for);
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





        email_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("TAG", "Enter pressed");
                    onClick(enter_tv);
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitTv:
                if (validatevalues()){
                    //callChangePassword();
                    callUserDetails();
                }
                break;
        }

    }

    private boolean validatevalues(){
       if (!Util.checkField(email_et)){
           Util.showMessage(this, "Enter Registered Email");
           return false;
       }else if (!Util.validateEmail(email_et.getText().toString())){
           Util.showMessage(this,"Enter Valid Email");
           return false;
       }

        return true;
    }


    public void callUserDetails(){
        if (Util.isNetworkAvailable(this)){

            if(sRole.equals("6")) {
                Service_Login_Package.getUserDetails(this, Constants.USERDETAILS + email_et.getText().toString());
            }else{
                Service_Login_Package.getUserDetails(this, Constants.AGENTDETAILS + email_et.getText().toString());
            }
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getUserDetailsResponses(String response) {

        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            getUserDetailsDTO = gson.fromJson(reader, User_Details_DTO.class);
            if (getUserDetailsDTO != null) {
                if (getUserDetailsDTO.getDailingCode()!=null){
                    Mobile =getUserDetailsDTO.getDailingCode()+getUserDetailsDTO.getMobile();
                }else {
                    Mobile = "91"+getUserDetailsDTO.getMobile();
                }
                ShowOtpAlert();

            } else {
                Util.showMessage(this, "Email is not registered");
            }

        }else{
            Util.showMessage(this, "Email is not registered");
        }
    }


    public void errorresponse(String response) {

        Util.showMessage(this, "Email is not registered");
    }









    private void ShowOtpAlert(){

        final Dialog OTPAlert = new Dialog(Forgot_Password_Activity.this);
        OTPAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        OTPAlert.setContentView(R.layout.logout_permisson_dialog);
        OTPAlert.setCancelable(false);

        TextView Msg=  OTPAlert.findViewById(R.id.Msg_alert);
        TextView Positive =  OTPAlert.findViewById(R.id.logout);
        TextView Negative =  OTPAlert.findViewById(R.id.dialog_cancel);

        Positive.setText("YES");

        Msg.setText("Send OTP to "+Mobile+ " for verification?");


        Positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OTPAlert.dismiss();
                callGETOTP();


            }
        });

        Negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OTPAlert.dismiss();

            }
        });

        OTPAlert.show();

    }







    /////////////////////////////GET OTP/////////////////////////////////////////////////////////////////////////////////////////////

    public void callGETOTP() {

        showProgressDialog("Please wait...", this);
        if (Util.isNetworkAvailable(this)) {
            ServiceClasses.GETOTP(this, Constants.BASEURL + Constants.OTP + Mobile);
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getOTPResponse(String response) {
        hideProgressDialog();
        String key = "";
        if (response != null) {

            if(Util.getResponseMessage(response).equals("Success")){

                try {
                    JSONObject myObject = new JSONObject(response);
                    key = myObject.getString("Key");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i=new Intent(this, Otp_Waiting_Activity.class);


                i.putExtra("FirstName",FirstNAme);
                i.putExtra("LastName",LastName);
                i.putExtra("Email",email_et.getText().toString());
                i.putExtra("Mobile",Mobile);
                i.putExtra("VerificationKey",key);

                i.putExtra("Role",sRole);
                i.putExtra("MethodState","Forgot");






                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }else{
                Util.showMessage(this,"Couldn't reach Phone");
            }



        }
    }



    public void callChangePassword(){
        showProgressDialog("Please Wait",this);
        if (Util.isNetworkAvailable(this)){
            Service_Login_Package.FORGOTPASSWORD(this, Constants.FORGOTPASSWORD,preparePayLoad());
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }


    private String preparePayLoad() {
        JSONObject jsonBody = new JSONObject();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String password=uuid.substring(0,6);
        Log.e("NewPassword",password);
        try {
            jsonBody.put("UserType", "User");
            jsonBody.put("Email", email_et.getText().toString());
            jsonBody.put("NewPassword", password);
            jsonBody.put("Mobile", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonBody.toString();
    }

    public void postforgotresponse(String response) {
        hideProgressDialog();
        if (response!=null){
            if (Util.getResponseCode(response)==200){
                if (Util.getResponseMessage(response).equals("Success")){
                 newpassword();
                }else {
                    if(Util.getResponseMessage(response).matches("Failed")) {
                        Util.showMessage(this, "Invalid emailId,\nPlease enter registered emailID");
                    }
                }
            }else {
                Util.showMessage(this,Util.getResponseMessage(response));
            }
    }
    }


    public void  newpassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("We have sent your new password to your registered Email Thank You :)")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent ip=new Intent(Forgot_Password_Activity.this,Login_Activity.class);
                        startActivity(ip);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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
