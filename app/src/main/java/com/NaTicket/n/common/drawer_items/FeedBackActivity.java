package com.NaTicket.n.common.drawer_items;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.NaTicket.n.R;
import com.NaTicket.n.serviceclasses.ServiceClasses;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Nagarjuna on 15-01-2018.
 */

public class FeedBackActivity extends BackActivity implements View.OnClickListener {

    EditText et_Email,et_Name,et_phone,et_feed_back;
    TextView submit_tv;
    ProgressDialog mProgressDialog;
    String refid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back_activity);
        initviews();
        inittoolbar();
        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Feedback"+"");

    }

    private void initviews(){
        et_Email= (EditText) findViewById(R.id.et_Email);
        et_Name= (EditText) findViewById(R.id.et_name);
        et_phone= (EditText) findViewById(R.id.et_mobile);
        et_feed_back= (EditText) findViewById(R.id.et_feedback);
        submit_tv= (TextView) findViewById(R.id.submitTv);
        submit_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitTv:
                if (validatevalues()){
                    callSendFeedback();
                }
                break;
        }
    }

    public void callSendFeedback(){
        showProgressDialog("Please Wait",this);
        if (Util.isNetworkAvailable(this)){
            ServiceClasses.SendFeedBack(this, Constants.FEEDBACK,preparePayLoad());
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }


    public void postFeedBackResponse(String response) {
    hideProgressDialog();
        if (response!=null){
            if (Util.getResponseCode(response)==200){
                if (Util.getResponseMessage(response).equals(Constants.SUCCESS_RESPONSE)){
                    feedbacksubmit(refid);
                }
            }else {
                Util.showMessage(this,"Some thing went wrong");
            }
        }
    }




    private String preparePayLoad() {

        JSONObject object=new JSONObject();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        refid=uuid.substring(0,6);
        try {
            object.put("Catagory", "Feedback");
            object.put("Name", et_Name.getText().toString());
            object.put("EmailId", et_Email.getText().toString());
            object.put("Mobile", et_phone.getText().toString());
            object.put("Description", et_feed_back.getText().toString());
            object.put("ReferenceNumber",refid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }



    private boolean validatevalues(){
        if (!Util.checkField(et_Email)){
            Util.showMessage(this,"Enter Email");
            return false;
        }else if (!Util.checkField(et_Name)){
            Util.showMessage(this,"Enter Name");
            return false;
        }else if (!Util.checkField(et_phone)){
            Util.showMessage(this,"Enter Mobile No.");
            return false;
        }
        else if (!Util.validateMobileNumberIndia(et_phone)){
            Util.showMessage(this,"Enter 10 digit Mobile No.");
            return false;
        }else if (!Util.checkField(et_feed_back)){
            Util.showMessage(this,"Enter Feedback");
            return false;
        }else if (!Util.validateEmail(et_Email.getText().toString())){
            Util.showMessage(this,"Enter Valid Email");
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

    public void  feedbacksubmit(String refid){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(" Your Feedback Id is  "+refid+" \nThanks for your valuable feedback :)")
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
}
