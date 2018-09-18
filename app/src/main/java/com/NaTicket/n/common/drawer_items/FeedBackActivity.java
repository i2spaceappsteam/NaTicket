package com.NaTicket.n.common.drawer_items;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import com.NaTicket.n.loginpackage.pojo.Country_Codes_DTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.NaTicket.n.R;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Nagarjuna on 15-01-2018.
 */

public class FeedBackActivity extends BackActivity implements View.OnClickListener {

    EditText et_Email, et_Name, et_phone, et_feed_back;
    TextView submit_tv;
    ProgressDialog mProgressDialog;
    String refid;
    Login_utils login_utils;

    Country_Codes_DTO[] country_dail_code;
    TextView dialing_code;
    private PopupWindow mPopupWindow;
    LinearLayout parentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back_activity);
        login_utils = new Login_utils(this);
        initviews();
        inittoolbar();
        TextView ToolBar_Title = findViewById(R.id.toolbartitle);
        ToolBar_Title.setText("" + "Feedback" + "");
        Util.getdialingcodesresponse(login_utils.getDailindcodesresponse(Constants.DAILING_CODES));
    }

    private void initviews() {
        et_Email = findViewById(R.id.et_Email);
        et_Name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_mobile);
        et_feed_back = findViewById(R.id.et_feedback);
        submit_tv = findViewById(R.id.submitTv);
        submit_tv.setOnClickListener(this);

        dialing_code = findViewById(R.id.dail_code);
        dialing_code.setOnClickListener(this);
        parentLayout = findViewById(R.id.parentLayout);

        country_dail_code = Util.getdailingcodes_array(login_utils.getDailindcodesresponse(Constants.DAILING_CODES));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitTv:
                if (validatevalues()) {
                    callSendFeedback();
                }
                break;
            case R.id.dail_code:
                showBottomSheet(Util.getdialingcodesresponse(login_utils.getDailindcodesresponse(Constants.DAILING_CODES)), "Dialing code", dialing_code);
                break;
        }
    }

    public void callSendFeedback() {
        showProgressDialog("Please Wait", this);
        if (Util.isNetworkAvailable(this)) {
            ServiceClasses.SendFeedBack(this, Constants.FEEDBACK, preparePayLoad());
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }


    public void postFeedBackResponse(String response) {
        hideProgressDialog();
        if (response != null) {
            if (Util.getResponseCode(response) == 200) {
                if (Util.getResponseMessage(response).equals(Constants.SUCCESS_RESPONSE)) {
                    feedbacksubmit(refid);
                }
            } else {
                Util.showMessage(this, "Some thing went wrong");
            }
        }
    }


    private String preparePayLoad() {

        JSONObject object = new JSONObject();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        refid = uuid.substring(0, 6);
        try {
            object.put("Catagory", "Feedback");
            object.put("Name", et_Name.getText().toString());
            object.put("EmailId", et_Email.getText().toString());
            object.put("Mobile", et_phone.getText().toString());
            object.put("Description", et_feed_back.getText().toString());
            object.put("ReferenceNumber", refid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }


    private boolean validatevalues() {
        if (!Util.checkField(et_Email)) {
            Util.showMessage(this, "Enter Email");
            return false;
        } else if (!Util.checkField(et_Name)) {
            Util.showMessage(this, "Enter Name");
            return false;
        } else if (!Util.checkField(et_phone)) {
            Util.showMessage(this, "Enter Mobile No.");
            return false;
        } else if (!Util.validateMobileNumberIndia(et_phone)) {
            Util.showMessage(this, "Enter 10 digit Mobile No.");
            return false;
        } else if (!Util.checkField(et_feed_back)) {
            Util.showMessage(this, "Enter Feedback");
            return false;
        } else if (!Util.validateEmail(et_Email.getText().toString())) {
            Util.showMessage(this, "Enter Valid Email");
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

    public void feedbacksubmit(String refid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(" Your Feedback Id is  " + refid + " \nThanks for your valuable feedback :)")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Util.startHomeActivity(FeedBackActivity.this);
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @SuppressLint("SetTextI18n")
    public void showBottomSheet(ArrayList<String> content, String Title, final TextView value_toset) {
        View parentView = getLayoutInflater().inflate(R.layout.countries_list, null);
        TextView BSDTittle = parentView.findViewById(R.id.BSDTittle);
        final SearchView searchView = parentView.findViewById(R.id.searchcountryView);
        ImageView close_window = parentView.findViewById(R.id.closePopup);
        BSDTittle.setText("Select " + Title);
        ListView select_city_list = (ListView) parentView.findViewById(R.id.select_city_pilg_bs);
        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(FeedBackActivity.this, R.layout.spinner_item, content);
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


}
