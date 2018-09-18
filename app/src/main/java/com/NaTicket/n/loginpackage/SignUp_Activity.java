package com.NaTicket.n.loginpackage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.loginpackage.pojo.Country_Codes_DTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.ServiceClasses;
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
import java.util.ArrayList;

/**
 * Created by Nagarjuna on 15-01-2018.
 */

public class SignUp_Activity extends BackActivity implements View.OnClickListener {

    EditText email_et, phone_et, first_et, last_et;
    TextView sign_up_tv, dialing_code;
    String DeviceToken;
    Login_utils login_utils;
    ProgressDialog mProgressDialog;
    String sRole = "6";
    RadioButton User_Radio, Agent_Radio;

    Country_Codes_DTO[] country_dail_code;
    private PopupWindow mPopupWindow;
    RelativeLayout parentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        login_utils = new Login_utils(this);
        initviews();
        inittoolbar();
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView ToolBar_Title = findViewById(R.id.toolbartitle);
        ToolBar_Title.setText("" + "Sign Up" + "");
        DeviceToken = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

    }


    public void initviews() {
        email_et = findViewById(R.id.et_email);
        phone_et = findViewById(R.id.et_mobile);
        first_et = findViewById(R.id.et_first_name);
        last_et = findViewById(R.id.et_last_name);
        dialing_code = findViewById(R.id.dail_code);
        User_Radio = findViewById(R.id.user_radio_signUp);
        Agent_Radio = findViewById(R.id.Agent_radio_signUp);
        sign_up_tv = findViewById(R.id.signupTv);
        parentLayout = findViewById(R.id.content_layout);
        sign_up_tv.setOnClickListener(this);
        dialing_code.setOnClickListener(this);
        RadioGroup radioGroup = findViewById(R.id.radio_group_signUp);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.user_radio_signUp) {
                    sRole = "6";
                } else {
                    sRole = "4";
                }
            }
        });


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

        country_dail_code = Util.getdailingcodes_array(login_utils.getDailindcodesresponse(Constants.DAILING_CODES));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signupTv:
                if (validatevalues()) {
                    //callUserRegistration();
                    callVerifyUser();
                }
                break;
            case R.id.dail_code:
                showBottomSheet(Util.getdialingcodesresponse(login_utils.getDailindcodesresponse(Constants.DAILING_CODES)), "Dialing code", dialing_code);
                break;
        }
    }


    ////////////////////////////////////Verify User/////////////////////////////////////////////////////////////////////////

    public void callVerifyUser() {

        showProgressDialog("Please wait...", this);
        if (Util.isNetworkAvailable(this)) {
            ServiceClasses.GETVERIFYUSER(this, Constants.BASEURL + Constants.VERIFYUSER + email_et.getText().toString() + "&mobileNumber=" + phone_et.getText().toString() +
                    "&UserType=" + sRole);
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getVerifyUserResponse(String response) {
        hideProgressDialog();
        if (response != null) {
            if (Util.getResponseMessage(response).toLowerCase().equals("success")) {
                callGETOTP();
            } else if (Util.getResponseMessage(response).equals("Duplicate")) {
                Util.showMessage(this, "Email/Mobile already registered");
            } else {
                Util.showMessage(this, "Couldn't reach Phone");
            }
        }
    }


    /////////////////////////////GET OTP/////////////////////////////////////////////////////////////////////////////////////////////

    public void callGETOTP() {

        showProgressDialog("Please wait...", this);
        if (Util.isNetworkAvailable(this)) {
            ServiceClasses.GETOTP(this, Constants.BASEURL + Constants.OTP + dialing_code.getText().toString() + phone_et.getText().toString());
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getOTPResponse(String response) {
        hideProgressDialog();
        String key = null;
        if (response != null) {

            if (Util.getResponseMessage(response).equals("Success")) {

                try {
                    JSONObject myObject = new JSONObject(response);
                    key = myObject.getString("Key");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Intent i = new Intent(this, Otp_Waiting_Activity.class);
                i.putExtra("FirstName", first_et.getText().toString());
                i.putExtra("LastName", last_et.getText().toString());
                i.putExtra("Email", email_et.getText().toString());
                i.putExtra("Mobile", phone_et.getText().toString());
                i.putExtra("DailingCode", dialing_code.getText().toString());
                i.putExtra("Role", sRole);
                i.putExtra("VerificationKey", key);
                i.putExtra("MethodState", "Register");


                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            } else {
                Util.showMessage(this, "Couldn't reach Phone");
            }


        }
    }


    private boolean validatevalues() {
        String PhoneNumber = phone_et.getText().toString();
        if (!Util.checkField(first_et)) {
            Util.showMessage(this, "Enter First Name");
            return false;
        } else if (!Util.checkField(last_et)) {
            Util.showMessage(this, "Enter Last Name");
            return false;
        } else if (!Util.checkField(email_et)) {
            Util.showMessage(this, "Enter Email");
            return false;
        } else if (!Util.validateEmail(email_et.getText().toString())) {
            Util.showMessage(this, "Enter Valid Email");
            return false;
        } else if (!Util.checkField(phone_et)) {
            Util.showMessage(this, "Enter Mobile Number");
            return false;
        } else if (PhoneNumber.length() != 10) {
            Util.showMessage(this, "Enter 10 digit Mobile Number");
            return false;
        } else if (!Util.validateMobilenumber(PhoneNumber)) {
            Util.showMessage(this, "Enter Valid Mobile Number");
            return false;
        }
        return true;
    }

    private void showProgressDialog(String msg, Context context) {
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
        startActivity(new Intent(this, Login_Activity.class));
    }

    @SuppressLint("SetTextI18n")
    public void showBottomSheet(ArrayList<String> content, String Title, final TextView value_toset) {
        View parentView = getLayoutInflater().inflate(R.layout.countries_list, null);
        TextView BSDTittle = parentView.findViewById(R.id.BSDTittle);
        final SearchView searchView = parentView.findViewById(R.id.searchcountryView);
        ImageView close_window = parentView.findViewById(R.id.closePopup);
        BSDTittle.setText("Select " + Title);
        ListView select_city_list = parentView.findViewById(R.id.select_city_pilg_bs);
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(SignUp_Activity.this, R.layout.spinner_item, content);
        if (select_city_list != null) {
            select_city_list.setAdapter(cityAdapter);
        }
        assert select_city_list != null;

        select_city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String visatype = (String) ((TextView) view).getText();
                value_toset.setText(Util.getcountryIndex(country_dail_code, visatype));
                mPopupWindow.dismiss();
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });
        searchView.setQueryHint("Search " + Title);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String txt) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String txt) {
                // TODO Auto-generated method stub
                cityAdapter.getFilter().filter(txt);
                return false;
            }
        });

        close_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow = new PopupWindow(
                parentView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();
        if (!isFinishing()) {
            mPopupWindow.showAtLocation(parentLayout, Gravity.CENTER, 0, 0);
        }
    }


    public void errorresponse(String message) {
    }
}
