package com.NaTicket.n.recharges;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.R;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.serviceclasses.Service_SavingPayments;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by Administrator on 08-03-2016.
 */
public class RechargeResponseActivity extends AppCompatActivity {

    TextView Recharge_status,TXN_ID,Rec_amount,Mobile,Operator;
    String refno,MobNum,Response,Paymentgatewayid,amt,RequestParams,OperatorName;
    RelativeLayout recharge_PlaceHolder,Recharge_success_lyt;
    Boolean PaymentStatus=false;
    Boolean IsFromOrder=false;
    ProgressDialog mProgressDialog;
    ImageView BackPress;
    double ConvFee=0;
    AlertDialog.Builder alertDialog;
    double ShowAmount;
    String msg;
    Login_utils login_utils;


    Toolbar toolbar;
    Button clickhere;
    protected  void onCreate(Bundle savedInstanstate){
        super.onCreate(savedInstanstate);

        setContentView(R.layout.recharge_status);

        TextView ToolbarTitle= (TextView) findViewById(R.id.toolbartitle);
        ToolbarTitle.setText("Recharge Status");

        BackPress= (ImageView) findViewById(R.id.backBtn);




            Bundle extras = getIntent().getExtras();
            if(extras != null) {

                PaymentStatus= extras.getBoolean("Status");
                Paymentgatewayid=extras.getString("PaymentGatewayId");
                refno=extras.getString("referencenumber");
                amt=extras.getString("Amount");
                MobNum=extras.getString("phone");
                Response=extras.getString("Response");
                OperatorName=extras.getString("operatorName");
                RequestParams=extras.getString("Request");
                IsFromOrder=extras.getBoolean("isFromOrder");
                ConvFee=extras.getDouble("convFee");
                msg=extras.getString("PaymentId");


                //TotalAmount=Double.parseDouble(amt)+ConvFee;


            }



        InitializeView();



    }

    private void InitializeView() {

        recharge_PlaceHolder= (RelativeLayout) findViewById(R.id.RechargeStatusPlaceHolder);
        Recharge_success_lyt= (RelativeLayout) findViewById(R.id.rechargeSuccessLyt);
        Recharge_status= (TextView) findViewById(R.id.recharge_status);
        TXN_ID= (TextView) findViewById(R.id.refernce_num);
        Rec_amount= (TextView) findViewById(R.id.amount);
        Mobile= (TextView) findViewById(R.id.mobilenum);
        Operator= (TextView) findViewById(R.id.operator);

        recharge_PlaceHolder.setVisibility(View.VISIBLE);
        Recharge_success_lyt.setVisibility(View.GONE);


        if(IsFromOrder){
            //callRecharge(refno);
            new ProcessRecharge().execute();
        }else  if (PaymentStatus){
            callUpdatePaymentParams(msg);
            }



        BackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(recharge_PlaceHolder.isShown()) {

                    showAlertDialogforError("Do you wish to cancel this Transaction ?");
                }else{
                    Intent ip = new Intent(RechargeResponseActivity.this,Recharge_MainActivity.class);
                    ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(ip);
                    finish();
                }
            }
        });








    }



    /*private void callSavePaymentDetails() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            //showProgressDialog("Please wait",this);
            Service_SavingPayments.SaveRecPaymentDetails(RechargeResponseActivity.this, Constants.SAVEPAYMENTDETAILS,preparePaymentdetails());
        }else{
            Util.showMessage(RechargeResponseActivity.this, Constants.NO_INT_MSG);
        }
    }


    private String preparePaymentdetails() {
        JSONObject jsonBody=new JSONObject();
        try {
            jsonBody.put("PGResponse",null);
            jsonBody.put("PaymentId",0);
            jsonBody.put("PaymentGatewayId",Paymentgatewayid);
            jsonBody.put("UserId",0);
            jsonBody.put("ReferenceNumber",refno);
            jsonBody.put("Amount",amt);
            jsonBody.put("TransactionCharges",0);
            jsonBody.put("ResponseString","Request sent to payment gateway");
            jsonBody.put("PaymentStatus",3);
            jsonBody.put("IPAddress","::1");
            jsonBody.put("PaymentGatewayName","Atom");
            jsonBody.put("Request","null");
            jsonBody.put("ClientId",0);
            jsonBody.put("PGRequest",RequestParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }

    public void postSaveDetailsParams(String response){
        if (response!=null){
            msg=Util.getResponseMessage(response);


                if(msg!=null) {
                    callUpdatePaymentParams(msg);
                }else {
                    // hideProgressDialog();
                    //showAlertDialog("Failed !");
                    Toast.makeText(this, "Failed !", Toast.LENGTH_SHORT).show();
                }
            }else{
                Util.showMessage(this,msg);
                 Intent ip = new Intent(RechargeResponseActivity.this,Recharge_MainActivity.class);
                ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ip);
                finish();
            }

    }*/


    private void callUpdatePaymentParams(String Message) {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            Service_SavingPayments.UpdateRecPaymentDetails(RechargeResponseActivity.this, Constants.UPDATEPAYMENTDETAILS,prepareUpdatePaymentdetails(Message));
            //showProgressDialog("Please wait",this);
        }else{
            Util.showMessage(RechargeResponseActivity.this, Constants.NO_INT_MSG);
        }
    }

    private String prepareUpdatePaymentdetails(String Message) {
        JSONObject jsonBody=new JSONObject();
        try {
            jsonBody.put("PGResponse",Response);
            jsonBody.put("PaymentId",Message);
            jsonBody.put("PaymentGatewayId",Paymentgatewayid);
            jsonBody.put("UserId",0);
            jsonBody.put("ReferenceNumber",refno);
            jsonBody.put("Amount",amt);
            jsonBody.put("TransactionCharges",0);
            jsonBody.put("ResponseString",Response);
            jsonBody.put("PaymentStatus",1);
            jsonBody.put("IPAddress","::1");
            jsonBody.put("PaymentGatewayName","PayUmoney");
            jsonBody.put("Request","null");
            jsonBody.put("ClientId",0);
            jsonBody.put("PGRequest",null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }


    public void errorreponse (String response){
        hideProgressDialog();
        showAlertDialogforError(response+"\nTry again");
    }

    public void postUpdateDetailsParams(String response){
        if (response!=null){
            if(Util.getResponseCode(response)==200){
                if(Util.getResponseMessage(response)!=null) {
                    ShowAmount=Double.parseDouble(amt)-ConvFee;
                    new ProcessRecharge().execute();
                    //callRecharge(refno);
                }else {

                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }else{
                Util.showMessage(this,"Something Went Wrong!");
            }
        }
    }






    private void callRecharge(String referenceID) {
        //showProgressDialog("Please Wait...",this);
        if(Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.Recharge(RechargeResponseActivity.this, Constants.rechargeurl+referenceID);
        }else{
            Util.showMessage(RechargeResponseActivity.this, Constants.NO_INT_MSG);
        }
    }

    public void getBookresponse(String response) throws JSONException {

        JSONObject object = new JSONObject(response);

        String RechargeStatus,msg,ref,BookingStatus;


        String Message = object.getString("Message");
        String Booking_Status = object.getString("BookingStatus");
        RechargeStatus = object.getString("Status");
        ref = object.getString("ReferenceNo");
        msg = object.getString("Message");
        object.getString("BookingStatus");
        object.getString("ResponseStatus");
        object.getString("RefundResponse");
        object.getString("Provider");
        if (msg.equals("Transaction Successful")) {

            /*recharge_PlaceHolder.setVisibility(View.GONE);
            Recharge_success_lyt.setVisibility(View.VISIBLE);

            TXN_ID.setText("TXN_ID:\n"+refno);
            Rec_amount.setText(""+amt);
            Operator.setText(""+OperatorName);*/

            callRechargeStatus(refno);




        } else {
            final Context context = RechargeResponseActivity.this;
            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder
                    .setMessage(Message)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Intent i = new Intent(RechargeResponseActivity.this, Recharge_MainActivity.class);
                            startActivity(i);



                        }

                    });
            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }


    private void callRechargeStatus(String referenceID) {
        //showProgressDialog("Please Wait...",this);
        if(Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.RechargeStatus(RechargeResponseActivity.this, Constants.rechargestatusurl+referenceID);
        }else{
            Util.showMessage(RechargeResponseActivity.this, Constants.NO_INT_MSG);
        }
    }

    public void getStausresponse(String response) throws JSONException {

        JSONObject object = new JSONObject(response);

        String RechargeStatus,msg,ref,BookingStatus;



        String Booking_Status = object.getString("BookingStatus");
        RechargeStatus = object.getString("Status");
        ref = object.getString("ReferenceNo");
        msg = object.getString("Message");
        object.getString("BookingStatus");
        object.getString("ResponseStatus");
        object.getString("RefundResponse");
        object.getString("Provider");
        if (RechargeStatus.equals("1")) {

            recharge_PlaceHolder.setVisibility(View.GONE);
            Recharge_success_lyt.setVisibility(View.VISIBLE);

            TXN_ID.setText("TXN_ID:\n"+refno);
            Rec_amount.setText(""+ShowAmount);
            Mobile.setText(""+MobNum+"");
            Operator.setText(""+OperatorName);
            Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();




        } else {
            final Context context = RechargeResponseActivity.this;
            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            Intent i = new Intent(RechargeResponseActivity.this, Recharge_MainActivity.class);
                            startActivity(i);



                        }

                    });
            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }




    public  class ProcessRecharge extends AsyncTask<String,String,String> {

        ProgressDialog dialog;
        String res;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //dialog=ProgressDialog.show(RechargeResponseActivity.this,"","Please Wait..\nGood things take time :)");
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();


                HttpGet httpGet = new HttpGet(Constants.rechargeurl + refno);
                httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
                httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                try {
                    res = httpClient.execute(httpGet, responseHandler);
                } catch (IOException e) {

                    e.printStackTrace();
                }




            }catch(Exception e){

            }
            return res;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //dialog.dismiss();
            String RechargeStatus,msg,ref,BookingStatus;
            JSONObject jsonObject = null;
            try {



                if ((!res.equals("")) && (!res.equals(null)) ) {

                    jsonObject = new JSONObject(res);
                    // encryptclientid=jsonObject.getString("ClientId")
                    RechargeStatus = jsonObject.getString("Status");
                    BookingStatus=jsonObject.getString("BookingStatus");
                    ref = jsonObject.getString("ReferenceNo");
                    msg = jsonObject.getString("Message");
                    jsonObject.getString("BookingStatus");
                    jsonObject.getString("ResponseStatus");
                    jsonObject.getString("RefundResponse");
                    jsonObject.getString("Provider");
                    System.out.println("Message : "+msg);


                   // if (msg.equals("Transaction Successful")) {

                      /*  recharge_PlaceHolder.setVisibility(View.GONE);
                        Recharge_success_lyt.setVisibility(View.VISIBLE);

                        TXN_ID.setText("TXN_ID:\n"+refno+"");
                        Rec_amount.setText(""+amt+"");
                        Mobile.setText(""+MobNum+"");
                        Operator.setText(""+OperatorName+"");*/

                      new RechargeSatus().execute();



                    /*} else {
                        final Context context = RechargeResponseActivity.this;
                        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

                        //alertDialogBuilder.setTitle("Response");
                        alertDialogBuilder
                                .setMessage(""+msg+"")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        Intent i = new Intent(RechargeResponseActivity.this, Recharge_MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);



                                    }

                                });
                        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }*/
                }

            } catch (Exception e) {
                Toast.makeText(RechargeResponseActivity.this,"Some Problem Occured with Provider\n Please Try later",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }



    public  class RechargeSatus extends AsyncTask<String,String,String> {

        ProgressDialog dialog;
        String res;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //dialog=ProgressDialog.show(RechargeResponseActivity.this,"","Please Wait..\nGood things take time :)");
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();


                HttpGet httpGet = new HttpGet(Constants.rechargestatusurl + refno);
                httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
                httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                try {
                    res = httpClient.execute(httpGet, responseHandler);
                } catch (IOException e) {

                    e.printStackTrace();
                }




            }catch(Exception e){

            }
            return res;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //dialog.dismiss();
            String RechargeStatus,msg,ref,BookingStatus;
            JSONObject jsonObject = null;
            try {



                if ((!res.equals("")) && (!res.equals(null)) ) {
                    //  Toast.makeText(getApplicationContext(),"hiii welcome to splash Screen",2000).show();
                    jsonObject = new JSONObject(res);
                    // encryptclientid=jsonObject.getString("ClientId")
                    RechargeStatus = jsonObject.getString("Status");
                    BookingStatus=jsonObject.getString("BookingStatus");
                    ref = jsonObject.getString("ReferenceNo");
                    msg = jsonObject.getString("Message");
                    jsonObject.getString("BookingStatus");
                    jsonObject.getString("ResponseStatus");
                    jsonObject.getString("RefundResponse");
                    jsonObject.getString("Provider");


                    if (RechargeStatus.equals("1")) {


                        recharge_PlaceHolder.setVisibility(View.GONE);
                        Recharge_success_lyt.setVisibility(View.VISIBLE);

                        TXN_ID.setText("TXN_ID:\n"+refno);
                        Rec_amount.setText(""+ShowAmount);
                        Mobile.setText(""+MobNum+"");
                        Operator.setText(""+OperatorName);
                        Toast.makeText(RechargeResponseActivity.this, ""+msg, Toast.LENGTH_SHORT).show();




                    } else {
                        final Context context = RechargeResponseActivity.this;
                        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

                        //alertDialogBuilder.setTitle("Response");
                        alertDialogBuilder
                                .setMessage(msg)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        Intent i = new Intent(RechargeResponseActivity.this, Recharge_MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);



                                    }

                                });
                        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                }

            } catch (Exception e) {
                Toast.makeText(RechargeResponseActivity.this,"Some Problem Occured with Provider\n Please Try later",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }


    private void showAlertDialogforError(String s) {
        alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setTitle("Alert !");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent ip = new Intent(RechargeResponseActivity.this,Recharge_MainActivity.class);
                ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ip);
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alertDialog.show();
    }



    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    @Override
    public void onBackPressed() {

        if(recharge_PlaceHolder.isShown()) {

            showAlertDialogforError("Do you wish to cancel this Transaction ?");
        }else{
            Intent ip = new Intent(RechargeResponseActivity.this,Recharge_MainActivity.class);
            ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ip);
            finish();
        }

    }
   /* @Override
    public void onClick(View v) {
        if(v==clickhere){
            if(newString.equals("1")||newString.equals("2")||newString.equals("3")){
                Intent i=new Intent(RechargeResponseActivity.this,Recharge_MainActivity.class);
                startActivity(i);
            }
        }
    }*/
}
