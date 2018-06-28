package com.NaTicket.n.loginpackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.serviceclasses.ServiceHandler;
import com.NaTicket.n.serviceclasses.Service_Login_Package;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ankit on 17-05-2018.
 */

public class Otp_Waiting_Activity extends AppCompatActivity implements View.OnClickListener {


    ProgressDialog mProgressDialog;
    String FirstName,LastName,Email,Mobile,VerificationKey, OTP, MethodState;
    EditText otp_digitone;
    EditText otp_digittwo;
    EditText otp_digitthree;
    EditText otp_digitfour;
    EditText otp_digitfive;
    EditText otp_digitsix;


    String message;
    Character one, two, three, four, five, six;
    Login_utils login_utils;
    ImageView  Otp_next_arrow;

    LinearLayout Otp_waiting_layout, passwordSent_layout;
    TextView wrong_number, Resend_otp, otp_waiting_TV, sentMailOK, registeredEmail;
    SmsVerifyCatcher smsVerifyCatcher;


    Animation MoveRight;

    TextView textCounter;
    ProgressBar progressBar;

    MyCountDownTimer myCountDownTimer;
    String parentid,sRole="6";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_waiting_activity);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        login_utils=new Login_utils(this);



        MoveRight = AnimationUtils.loadAnimation(this, R.anim.move_right);
        Otp_waiting_layout = (LinearLayout) findViewById(R.id.Otp_waiting_layout);
        passwordSent_layout = (LinearLayout) findViewById(R.id.passwordSent_layout);
        sentMailOK = (TextView) findViewById(R.id.sentMailOK);
        registeredEmail = (TextView) findViewById(R.id.registeredEmail);
        MethodState = getIntent().getStringExtra("MethodState");

        if (MethodState.equals("Forgot")) {
            Email = getIntent().getStringExtra("Email");
            Mobile = getIntent().getStringExtra("Mobile");
            VerificationKey = getIntent().getStringExtra("VerificationKey");
            sRole=getIntent().getStringExtra("Role");
        } else if (MethodState.equals("Register")) {
            FirstName = getIntent().getStringExtra("FirstName");
            LastName = getIntent().getStringExtra("LastName");
            Email = getIntent().getStringExtra("Email");
            Mobile = getIntent().getStringExtra("Mobile");
            VerificationKey = getIntent().getStringExtra("VerificationKey");
            sRole=getIntent().getStringExtra("Role");
        }

        otp_waiting_TV = (TextView) findViewById(R.id.otp_waiting_TV);

        otp_digitone = (EditText) findViewById(R.id.otp_digitone);
        otp_digittwo = (EditText) findViewById(R.id.otp_digittwo);
        otp_digitthree = (EditText) findViewById(R.id.otp_digitthree);
        otp_digitfour = (EditText) findViewById(R.id.otp_digitfour);
        otp_digitfive = (EditText) findViewById(R.id.otp_digitfive);
        otp_digitsix = (EditText) findViewById(R.id.otp_digitsix);

        wrong_number = (TextView) findViewById(R.id.wrong_number);
        Resend_otp = (TextView) findViewById(R.id.Resend_otp);

        Otp_next_arrow = (ImageView) findViewById(R.id.otp_next_arrow);


        otp_waiting_TV.setText("OTP has been sent to " + Mobile);

        wrong_number.setVisibility(View.GONE);
        Otp_next_arrow.setOnClickListener(this);
        wrong_number.setOnClickListener(this);
        Resend_otp.setOnClickListener(this);
        sentMailOK.setOnClickListener(this);

        progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        textCounter= (TextView) findViewById(R.id.tv_counter);
        Resend_otp.setVisibility(View.GONE);
        myCountDownTimer = new MyCountDownTimer(30000, 1);
        myCountDownTimer.start();


        ////////////////Automatic OTP Verification/////////////////////////
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);//Parse verification code


                //Util.showMessage(Mobile_Otp_Activity.this,code);

                one = code.charAt(0);
                two = code.charAt(1);
                three = code.charAt(2);
                four = code.charAt(3);
                five = code.charAt(4);
                six = code.charAt(5);


                try {
                    otp_digitone.setText(one.toString());
                    otp_digittwo.setText(two.toString());
                    otp_digitthree.setText(three.toString());
                    otp_digitfour.setText(four.toString());
                    otp_digitfive.setText(five.toString());
                    otp_digitsix.setText(six.toString());
                } catch (Exception e) {
                    System.out.println(e);
                }


                //etCode.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });


        /////////set next for OTP fields
        setnext(otp_digitone, otp_digittwo);
        setnext(otp_digittwo, otp_digitthree);
        setnext(otp_digitthree, otp_digitfour);
        setnext(otp_digitfour, otp_digitfive);
        setnext(otp_digitfive, otp_digitsix);

        ////////set previous for OTP fields
        setprevious(otp_digitsix, otp_digitfive);
        setprevious(otp_digitfive, otp_digitfour);
        setprevious(otp_digitfour, otp_digitthree);
        setprevious(otp_digitthree, otp_digittwo);
        setprevious(otp_digittwo, otp_digitone);

        otp_digitsix.addTextChangedListener(Sixth_OTP_digit_T_Watcher);
        otp_digitone.requestFocus();

    }



    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    private String getOTPSTRING() {

        OTP = String.valueOf(
                otp_digitone.getText().toString() +
                        otp_digittwo.getText().toString() +
                        otp_digitthree.getText().toString() +
                        otp_digitfour.getText().toString() +
                        otp_digitfive.getText().toString() +
                        otp_digitsix.getText().toString());

        return OTP;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.wrong_number:
                finish();
                break;

            case R.id.Resend_otp:
                callResendOTP();

                break;
            case R.id.sentMailOK:

                Intent ip = new Intent(Otp_Waiting_Activity.this, Login_Activity.class);
                startActivity(ip);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.otp_next_arrow:

                getOTPSTRING();
                if (OTP.length() == 6) {
                    callVERIFYOTP();
                } else {
                    Util.showMessage(this, "Enter 6 digit OTP");
                }
                break;
        }
    }


    TextWatcher Sixth_OTP_digit_T_Watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

            if (otp_digitsix.getText().length() == 1) {
                callVERIFYOTP();
            }

        }
    };




    //////////////////////VERIFY OTP/////////////////////////////////////////////////////////////////////////

    public void callVERIFYOTP() {

        getOTPSTRING();

        showProgressDialog("Please wait...", this);
        if (Util.isNetworkAvailable(this)) {
            ServiceClasses.GEVERIFYTOTP(this, Constants.BASEURL +Constants.VerifyOtp + VerificationKey + "&otp=" + OTP + "&mobile=" + Mobile);
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getVERIFYOTPResponse(String response) {
        hideProgressDialog();
        if (response != null) {

            message = Util.getResponseMessage(response);





            if (message.equals("Success")) {
                otp_digitsix.removeTextChangedListener(Sixth_OTP_digit_T_Watcher);

                if (MethodState.equals("Forgot")) {
                    callChangePassword();
                } else if (MethodState.equals("Register")) {
                    callUserRegistration();
                }


            }else {

                Util.showMessage(Otp_Waiting_Activity.this, "Incorrect OTP");
            }



        }
    }





    private void callUserRegistration() {
        showProgressDialog("Please Wait",this);
        if(Util.isNetworkAvailable(this)) {
            if(sRole.equals("6")) {
                ServiceClasses.postUserRegistration(Otp_Waiting_Activity.this, Constants.USERREGISTRATION, preparePayload());
            }else{

                new Getparentid().execute();

            }
        }else{
            Util.showMessage(this,Constants.NO_INT_MSG);
        }
    }


    private String preparePayload() {
        JSONObject jsonBody = new JSONObject();

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String password=uuid.substring(0,6);
        Log.e("NewPassword",password);
        try {
            jsonBody.put("IsSIteAdminAgent", "0");
            jsonBody.put("FirstName", FirstName);
            jsonBody.put("LastName",LastName);
            jsonBody.put("Password", password);
            jsonBody.put("Gender", "1");
            jsonBody.put("DOB", "10-01-1995");
            jsonBody.put("Email", Email);
            jsonBody.put("AlternateEmail", "");
            jsonBody.put("Mobile",Mobile);
            jsonBody.put("AlternateMobile", "");
            jsonBody.put("Telephone", "");
            jsonBody.put("Address", "");
            jsonBody.put("City", "");
            jsonBody.put("State", "2");
            jsonBody.put("Pincode", "");
            jsonBody.put("Status", "1");
            jsonBody.put("ActivationCode", "");
            jsonBody.put("DeviceModel", Constants.DeviceModel);
            jsonBody.put("DeviceOS", Constants.DeviceOs);
            jsonBody.put("DeviceOSVersion",Constants.DeviceOsversion);
            jsonBody.put("DeviceToken",Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
            if(sRole.equals("6")) {
                jsonBody.put("ParentId", login_utils.getClientDetails(Constants.PARENTID));
            }else{
                jsonBody.put("ParentId", parentid);
                jsonBody.put("IsAgent", "1");
                jsonBody.put("CompanyName", "Unknown");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Request:; "+jsonBody.toString());
        return  jsonBody.toString();
    }








    public class Getparentid extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh=new ServiceHandler();
            String jsonstr = sh.makeServiceCall(Constants.AGENTLIST, ServiceHandler.GET);
            if(jsonstr !=""){
                try{

                    JSONArray jarray=new JSONArray(jsonstr);
                    for(int i=0;i<jarray.length();i++){
                        JSONObject jsonObject=jarray.getJSONObject(i);
                        parentid=jsonObject.getString("UserId");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("parentid"+parentid);

            ServiceClasses.postUserRegistration(Otp_Waiting_Activity.this, Constants.AGENTREGISTRATION, preparePayload());

        }
    }


    public void postUserRegistrationResponse(String response) {
        hideProgressDialog();
        if(Util.getResponseCode(response)==200&&Util.getResponseMessage(response).matches(Constants.SUCCESS_RESPONSE)){

            Util.showMessage(this,"Registration Successful");
            Intent i=new Intent(this,Login_Activity.class);
            // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        }else  if(Util.getResponseMessage(response).matches(Constants.DUPLICATE_RESPONSE)){
            Util.showMessage(this,"Already registered");
            Intent i=new Intent(this,SignUp_Activity.class);
            startActivity(i);
        }else {
            System.out.println("Response:; "+response);
            Util.showMessage(this,"Something went wrong\n");
            Intent i=new Intent(this,SignUp_Activity.class);
            startActivity(i);
        }
    }


    public void callChangePassword() {
        showProgressDialog("Please Wait", this);
        if (Util.isNetworkAvailable(this)) {
            Service_Login_Package.FORGOTPASSWORD(this, Constants.FORGOTPASSWORD, preparePayLoad());
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }


    private String preparePayLoad() {
        JSONObject jsonBody = new JSONObject();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String password = uuid.substring(0, 6);
        Log.e("NewPassword", password);
        try {

            if(sRole.equals("6")) {
                jsonBody.put("UserType", "User");
            }else{
                jsonBody.put("UserType", "Agent");
            }
            jsonBody.put("Email", Email);
            jsonBody.put("NewPassword", password);
            jsonBody.put("Mobile", Mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonBody.toString();
    }

    public void postforgotresponse(String response) {
        hideProgressDialog();
        if (response != null) {
            if (Util.getResponseCode(response) == 200) {
                if (Util.getResponseMessage(response).equals("Success")) {

                    newpassword();
                } else {
                    if (Util.getResponseMessage(response).matches("Failed")) {
                        Util.showMessage(this, "Failed! Please try again");
                        onClick(sentMailOK);
                    }
                }
            } else {
                Util.showMessage(this, Util.getResponseMessage(response));
            }
        }
    }


    public void newpassword() {
        Otp_waiting_layout.setVisibility(View.GONE);
        passwordSent_layout.setVisibility(View.VISIBLE);
        passwordSent_layout.startAnimation(MoveRight);
        registeredEmail.setText("Your new password has been sent to " + Email + "\n");
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("We have sent your new password to your registered Email Thank You :)")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent ip=new Intent(Otp_Waiting_Activity.this,Login_Activity.class);
                        startActivity(ip);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();*/
    }



    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textCounter.setText(String.valueOf(millisUntilFinished/1000));
            int progress = (int) (millisUntilFinished/1000);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            textCounter.setVisibility(View.GONE);
            Resend_otp.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.GONE);
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





    private void setnext(final EditText editText, final EditText edittext2) {
        editText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                int size = 1;
                if (editText.getText().toString().length() == size)     //size as per your requirement
                {
                    edittext2.requestFocus();
                    editText.setEnabled(false);
                    edittext2.setEnabled(true);
                }
                if (s.toString().equals(""))     //size as per your requirement
                {
                    edittext2.requestFocus();
                    editText.setEnabled(false);
                    edittext2.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
    }


    private void setprevious(final EditText editText, final EditText edittext2) {


        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    int size = 0;
                    if (editText.getText().toString().length() == size)     //size as per your requirement
                    {
                        edittext2.requestFocus();
                        editText.setEnabled(false);
                        edittext2.setEnabled(true);
                    }
                }
                return false;
            }
        });


    }

    /////////////////////////////GET OTP/////////////////////////////////////////////////////////////////////////////////////////////

    public void callResendOTP() {

        showProgressDialog("Please wait...", this);
        if (Util.isNetworkAvailable(this)) {
            ServiceClasses.GETOTP(Otp_Waiting_Activity.this, Constants.BASEURL + Constants.OTP + Mobile);
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



                Intent i = new Intent(this, Otp_Waiting_Activity.class);
                i.putExtra("FirstName", FirstName);
                i.putExtra("LastName", LastName);
                i.putExtra("Email", Email);
                i.putExtra("Mobile", Mobile);
                i.putExtra("VerificationKey", key);
                i.putExtra("Role",sRole);
                i.putExtra("MethodState", MethodState);


                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();


            }else{
                Util.showMessage(this,"Couldn't reach Phone");
            }



        }
    }



    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }


}
