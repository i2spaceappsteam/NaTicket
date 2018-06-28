package com.NaTicket.n.common.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.R;
import com.NaTicket.n.recharges.Recharge_MainActivity;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Nagarjuna on 1/18/2018.
 */

public class Reports_Recharge_Details extends BackActivity {

    TextView Recharge_status,TXN_ID,Rec_amount,Mobile,Operator;
    String refno,MobNum,Response,Paymentgatewayid,RequestParams,OperatorName;
    RelativeLayout recharge_PlaceHolder,Recharge_success_lyt;
    Boolean PaymentStatus=false;
    Boolean IsFromOrder=false;
    int amt;



    protected  void onCreate(Bundle savedInstanstate){
        super.onCreate(savedInstanstate);

        setContentView(R.layout.recharge_status);


        inittoolbar();
        TextView ToolBar_Title= (TextView) findViewById(R.id.toolbartitle);
        ToolBar_Title.setText(""+"Recharge Status"+"");
        Bundle extras = getIntent().getExtras();
        if(extras != null) {


            refno=extras.getString("referencenumber");
            amt=extras.getInt("Amount");
            MobNum=extras.getString("phone");
            OperatorName=extras.getString("operatorName");


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

        recharge_PlaceHolder.setVisibility(View.GONE);
        Recharge_success_lyt.setVisibility(View.VISIBLE);

        new RechargeSatus().execute();






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

                    jsonObject = new JSONObject(res);

                    RechargeStatus = jsonObject.getString("Status");
                    msg = jsonObject.getString("Message");



                    if (RechargeStatus.equals("1")) {


                        TXN_ID.setText("TXN_ID:\n"+refno);
                        Rec_amount.setText(""+amt);
                        Mobile.setText(""+MobNum+"");
                        Operator.setText(""+OperatorName);
                        //Toast.makeText(Reports_Recharge_Details.this, ""+msg, Toast.LENGTH_SHORT).show();




                    } else {
                        final Context context = Reports_Recharge_Details.this;
                        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

                        alertDialogBuilder.setTitle("Response");
                        alertDialogBuilder
                                .setMessage(msg)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        Intent i = new Intent(Reports_Recharge_Details.this, Recharge_MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);



                                    }

                                });
                        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                }

            } catch (Exception e) {
                Toast.makeText(Reports_Recharge_Details.this,"Some Problem Occured with Provider\n Please Try later",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }
}
