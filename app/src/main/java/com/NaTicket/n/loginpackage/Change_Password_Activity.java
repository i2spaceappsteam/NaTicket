package com.NaTicket.n.loginpackage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.Service_Login_Package;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nagarjuna on 15-01-2018.
 */

public class Change_Password_Activity extends BackActivity implements View.OnClickListener{

    EditText old_et,new_et,con_et;
    TextView change_tv;
    Login_utils login_utils;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);
        login_utils=new Login_utils(this);
        inittoolbar();
        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Change Password"+"");
        initviews();
    }

    public void initviews(){
        change_tv= (TextView) findViewById(R.id.changeTv);
        old_et= (EditText) findViewById(R.id.old_password);
        new_et= (EditText) findViewById(R.id.new_password);
        con_et= (EditText) findViewById(R.id.confirm_password);

        change_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changeTv:
                if (validatevalues()){
                    callChangePassword();
                }
                break;
        }
    }


    public void callChangePassword(){
        showProgressDialog("Please Wait",this);
        if (Util.isNetworkAvailable(this)){
            Service_Login_Package.CHANGEPASSWORD(this, Constants.CHANGEPASSWORD,preparePayLoad());
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }


    private String preparePayLoad() {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("Email", login_utils.getUserDetails(Constants.USEREMAIL));
            jsonBody.put("OldPassword", old_et.getText().toString());
            jsonBody.put("NewPassword", new_et.getText().toString());
            if(login_utils.getUserDetails(Constants.USERTYPE).equals("4")) {
                jsonBody.put("UserType", "Agent");
            }else{
                jsonBody.put("UserType", "User");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonBody.toString();
    }


    public void postchangepassresponse(String response) {
        hideProgressDialog();
        if (response!=null){
         if (Util.getResponseCode(response)==200){
             if (Util.getResponseMessage(response).equals("Password changed successfully")){
                showAlertDialog("Password changed successfully");
             }else {
                 Util.showMessage(this,"Enter Correct old password");
             }
         }else {
             Util.showMessage(this,"Enter Correct old password");
         }
        }
    }

    private void showAlertDialog(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setTitle("Congrats !");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               Util.startHomeActivity(Change_Password_Activity.this);



            }
        });
        alertDialog.show();
    }


    private boolean validatevalues(){
        if (!Util.checkField(old_et)){
            Util.showMessage(this,"Enter Old Password");
            return false;
        }else if (!Util.checkField(new_et)){
            Util.showMessage(this,"Enter New Password");
            return false;
        }else if (!Util.checkField(con_et)){
            Util.showMessage(this,"Enter Confirm Password");
            return false;
        }else if (!new_et.getText().toString().equals(con_et.getText().toString())){
            Util.showMessage(this,"New Password and Confirm Password should be same");
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


}
