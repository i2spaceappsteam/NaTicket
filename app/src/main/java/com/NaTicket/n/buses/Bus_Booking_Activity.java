package com.NaTicket.n.buses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.NaTicket.n.serviceclasses.Service_SavingPayments;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ankit on 9/5/2017.
 */

public class Bus_Booking_Activity extends AppCompatActivity {


    String data, refno, ReturnTicketrefno,msg;
    Boolean PayMentstatus;
    InputStream inputStream;
    String mfirstname, memail, amt, mphone, Response, RequestParams, IsAgent, PaymentGatewayID;

    String OnwardReferenceNumber, ReturnTicketReferencenumber;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_booking_activity);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mfirstname = bundle.getString("name");
            memail = bundle.getString("email");
            amt = bundle.getString("Amount");
            mphone = bundle.getString("phone");
            refno = bundle.getString("ReferenceNumber");
            ReturnTicketReferencenumber = bundle.getString("ReturnTicketrReferenceNumber");
            PaymentGatewayID = bundle.getString("PaymentGatewayId");
            PayMentstatus = bundle.getBoolean("Status");
            Response = bundle.getString("Response");
            RequestParams = bundle.getString("Request");
            IsAgent = bundle.getString("IsAgent");
            msg=bundle.getString("PaymentId");

        }
        if (IsAgent.equals("Yes")) {
            new Busbooking().execute();
        } else {
            if (PayMentstatus) {
                callUpdatePaymentParams(msg);
            }
        }

    }


   /* @SuppressWarnings("deprecation")
    public class Payment extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Bus_Booking_Activity.this, "", "Please Wait Transaction is under Process");
        }

        @Override
        protected String doInBackground(String... params) {


            JSONObject bcparam = new JSONObject();
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Constants.BASEURL + Constants.SavePaymentDetails);
            httpPost.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpPost.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            try {
                bcparam.put("PaymentGatewayId", Response);
                bcparam.put("Amount", amt);
                if (PayMentstatus) {
                    bcparam.put("PaymentStatus", "Success");
                    bcparam.put("ResponseString", "Atom status");
                } else {
                    bcparam.put("PaymentStatus", "Failure");
                    bcparam.put("ResponseString", "Atom status");
                }
                bcparam.put("IPAddress", "::1");
                bcparam.put("ReferenceNumber", refno);
                bcparam.put("DeviceModel", "");
                bcparam.put("DeviceOS", "Android");
                bcparam.put("DeviceOSVersion", "");
                bcparam.put("DeviceToken", "");
                bcparam.put("ApplicationType", "Mobile");


                try {
                    StringEntity seEntity;
                *//*
                 * try { data1 = URLEncoder.encode(jsonObjectrows, "UTF-8"); }
				 * catch (UnsupportedEncodingException e2) { // TODO
				 * Auto-generated catch block e2.printStackTrace(); }
				 *//*
                    seEntity = new StringEntity(bcparam.toString(), "UTF_8");
                    seEntity.setContentType("application/json");
                    httpPost.setEntity(seEntity);
                    HttpResponse httpResponse;

                    httpResponse = httpClient.execute(httpPost);

                    inputStream = httpResponse.getEntity().getContent();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //json=bcparam.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String line;
            data = "";
            try {
                while ((line = bufferedReader.readLine()) != null)
                    data += line;
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.v("Json Object", "" + bcparam);
            Log.v("Save Data", "" + data);


            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if (!(data != null && data.equals(""))) {
                try {


                    JSONObject jsonObject = new JSONObject(data);


                    String strstatus = jsonObject.getString("StatusCode");
                    System.out.println(strstatus);
                    if (strstatus.contains("200")) {
                        new Busbooking().execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(Bus_Booking_Activity.this, "No Data Received", Toast.LENGTH_SHORT).show();

            }
        }
    }*/


    /*private void callSavePaymentDetails() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Please wait", this);
            Service_SavingPayments.SaveBusPaymentDetails(Bus_Booking_Activity.this, Constants.SAVEPAYMENTDETAILS, preparePaymentdetails());
        } else {
            Util.showMessage(Bus_Booking_Activity.this, Constants.NO_INT_MSG);
        }
    }


    private String preparePaymentdetails() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("PGResponse", null);
            jsonBody.put("PaymentId", 0);
            jsonBody.put("PaymentGatewayId", PaymentGatewayID);
            jsonBody.put("UserId", 0);
            jsonBody.put("ReferenceNumber", refno);
            jsonBody.put("Amount", amt);
            jsonBody.put("TransactionCharges", 0);
            jsonBody.put("ResponseString", "Request sent to payment gateway");
            jsonBody.put("PaymentStatus", 3);
            jsonBody.put("IPAddress", "::1");
            jsonBody.put("PaymentGatewayName", "Atom");
            jsonBody.put("Request", "null");
            jsonBody.put("ClientId", 0);
            jsonBody.put("PGRequest", RequestParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }

    public void postSaveDetailsParams(String response) {
        if (response != null) {

                if (Util.getResponseMessage(response) != null) {
                    callUpdatePaymentParams(Util.getResponseMessage(response));
                } else {
                    hideProgressDialog();
                    showAlertDialog("Booking Failed !\nCouldn't save payment details");
                    //Toast.makeText(this, "Booking Failed !", Toast.LENGTH_SHORT).show();
                }

        }
    }*/

    private void callUpdatePaymentParams(String Message) {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            Service_SavingPayments.UpdateBusPaymentDetails(Bus_Booking_Activity.this, Constants.UPDATEPAYMENTDETAILS, prepareUpdatePaymentdetails(Message));
            showProgressDialog("Please wait", this);
        } else {
            Util.showMessage(Bus_Booking_Activity.this, Constants.NO_INT_MSG);
        }
    }

    private String prepareUpdatePaymentdetails(String Message) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("PGResponse",Response);
            jsonBody.put("PaymentId", Message);
            jsonBody.put("PaymentGatewayId", PaymentGatewayID);
            jsonBody.put("UserId", 0);
            jsonBody.put("ReferenceNumber", refno);
            jsonBody.put("Amount", amt);
            jsonBody.put("TransactionCharges", 0);
            jsonBody.put("ResponseString", Response);
            jsonBody.put("PaymentStatus", 1);
            jsonBody.put("IPAddress", "::1");
            jsonBody.put("PaymentGatewayName", "Atom");
            jsonBody.put("Request", null);
            jsonBody.put("ClientId", 0);
            jsonBody.put("PGRequest", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }

    public void postUpdateDetailsParams(String response) {
        if (response != null) {
            if (Util.getResponseCode(response) == 200) {
                if (Util.getResponseMessage(response) != null) {
                    new Busbooking().execute();
                } else {
                    hideProgressDialog();
                    showAlertDialog("Booking Failed !");
                }
            } else {
                Util.showMessage(this, "Something Went Wrong!");
            }
        }
    }

    ///***ONWard Bus Ticket Booking Method******
    public class Busbooking extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        String res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(Bus_Booking_Activity.this, "", "Please wait...\nYour Booking is being processed...");
            dialog.setCancelable(false);
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient();


            HttpGet httpGet = new HttpGet(Constants.BASEURL + Constants.BookBusTicket + refno);
            httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                res = httpClient.execute(httpGet, responseHandler);
            } catch (IOException e) {

                e.printStackTrace();
            }

            Log.v("Busbooking", "" + res);
            return res;


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            String sts, msg, PNRNUMBER;
            JSONObject jsonObject;
            try {

                if ((!res.equals("")) && (!res.equals(null))) {
                    jsonObject = new JSONObject(res);
                    OnwardReferenceNumber = jsonObject.getString("ReferenceNo");
                    PNRNUMBER = jsonObject.getString("OperatorPNR");
                    jsonObject.getString("APIReferenceNo");
                    msg = jsonObject.getString("Message");
                    sts = jsonObject.getString("BookingStatus");
                    jsonObject.getString("ResponseStatus");
                    jsonObject.getString("RefundResponse");
                    jsonObject.getString("Provider");


                    if (ReturnTicketReferencenumber != null) {
                        new ReturnTicketBusbooking().execute();
                    } else if (sts.equals("1")) {
                        Intent i = new Intent(Bus_Booking_Activity.this, ResponseActivity.class);
                        i.putExtra("IsFromTrips","No");
                        i.putExtra("name", mfirstname);
                        i.putExtra("email", memail);
                        i.putExtra("phone", mphone);
                        i.putExtra("amount", amt);
                        i.putExtra("Pnrnumber", PNRNUMBER);
                        i.putExtra("msg", msg);
                        i.putExtra("Onwardrefno", OnwardReferenceNumber);
                        i.putExtra("status", sts);
                        i.putExtra("isFromOrder", true);
                        startActivity(i);
                    } else if (sts.equals("4")) {
                        Intent i = new Intent(Bus_Booking_Activity.this, ResponseActivity.class);
                        i.putExtra("IsFromTrips","No");
                        i.putExtra("name", mfirstname);
                        i.putExtra("email", memail);
                        i.putExtra("phone", mphone);
                        i.putExtra("amount", amt);
                        i.putExtra("Pnrnumber", PNRNUMBER);
                        i.putExtra("msg", msg);
                        i.putExtra("Onwardrefno", OnwardReferenceNumber);
                        i.putExtra("status", sts);
                        i.putExtra("isFromOrder", true);
                        startActivity(i);

                    } else if (sts.equals("3")) {
                        Intent i = new Intent(Bus_Booking_Activity.this, ResponseActivity.class);
                        i.putExtra("IsFromTrips","No");
                        i.putExtra("name", mfirstname);
                        i.putExtra("email", memail);
                        i.putExtra("phone", mphone);
                        i.putExtra("amount", amt);
                        i.putExtra("Pnrnumber", PNRNUMBER);
                        i.putExtra("msg", msg);
                        i.putExtra("Onwardrefno", OnwardReferenceNumber);
                        i.putExtra("status", sts);
                        i.putExtra("isFromOrder", true);
                        startActivity(i);


                    } else {

                        Toast.makeText(Bus_Booking_Activity.this, "Some Problem Occured.\n Please Check your credentials", Toast.LENGTH_LONG).show();
                    }
                } else {

                    Toast.makeText(Bus_Booking_Activity.this, "Some Problem Occured with Provider\n Please Try later", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {

                Toast.makeText(Bus_Booking_Activity.this, "Some Problem Occured with Provider\n Please Try later", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }


        }

    }

    /////******Return Ticket Booking *****/////
    public class ReturnTicketBusbooking extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        String res;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(Bus_Booking_Activity.this, "", "Please Wait...\nYour Booking is being processed...");
            dialog.setCancelable(false);
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient();


            HttpGet httpGet = new HttpGet(Constants.BASEURL + Constants.BookBusTicket + ReturnTicketReferencenumber);
            httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                res = httpClient.execute(httpGet, responseHandler);
            } catch (IOException e) {

                e.printStackTrace();
            }

            Log.v("Busbooking", "" + res);
            return res;


        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            String sts, ref, msg;
            JSONObject jsonObject;
            try {


                if ((!res.equals("")) && (!res.equals(null))) {
                    jsonObject = new JSONObject(res);
                    jsonObject.getString("OperatorPNR");
                    ref = jsonObject.getString("ReferenceNo");
                    jsonObject.getString("APIReferenceNo");
                    msg = jsonObject.getString("Message");
                    sts = jsonObject.getString("BookingStatus");
                    jsonObject.getString("ResponseStatus");
                    jsonObject.getString("RefundResponse");
                    jsonObject.getString("Provider");


                    if (sts.equals("1")) {


                        Intent i = new Intent(Bus_Booking_Activity.this, ResponseActivity.class);
                        i.putExtra("IsFromTrips","No");
                        i.putExtra("name", mfirstname);
                        i.putExtra("email", memail);
                        i.putExtra("phone", mphone);
                        i.putExtra("amount", amt);
                        i.putExtra("msg", msg);
                        i.putExtra("Onwardrefno", OnwardReferenceNumber);
                        i.putExtra("status", sts);
                        i.putExtra("Returnrefno", ref);
                        i.putExtra("isFromOrder", true);
                        startActivity(i);
                    } else if (sts.equals("4")) {
                        Intent i = new Intent(Bus_Booking_Activity.this, ResponseActivity.class);
                        i.putExtra("IsFromTrips","No");
                        i.putExtra("name", mfirstname);
                        i.putExtra("email", memail);
                        i.putExtra("phone", mphone);
                        i.putExtra("amount", amt);
                        i.putExtra("msg", msg);
                        i.putExtra("Returnrefno", ref);
                        i.putExtra("Onwardrefno", OnwardReferenceNumber);
                        i.putExtra("status", sts);
                        i.putExtra("isFromOrder", true);
                        startActivity(i);

                    } else if (sts.equals("3")) {
                        Intent i = new Intent(Bus_Booking_Activity.this, ResponseActivity.class);
                        i.putExtra("IsFromTrips","No");
                        i.putExtra("name", mfirstname);
                        i.putExtra("email", memail);
                        i.putExtra("phone", mphone);
                        i.putExtra("amount", amt);
                        i.putExtra("msg", msg);
                        i.putExtra("Onwardrefno", OnwardReferenceNumber);
                        i.putExtra("status", sts);
                        i.putExtra("Returnrefno", ref);
                        i.putExtra("isFromOrder", true);
                        startActivity(i);

                    } else {

                        Toast.makeText(Bus_Booking_Activity.this, "Some Problem Occured.\n Please Check your credentials", Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(Bus_Booking_Activity.this, "Some Problem Occured with Provider\n Please Try later", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {

                Toast.makeText(Bus_Booking_Activity.this, "Some Problem Occured with Provider\n Please Try later", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    private void showProgressDialog(String msg, Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();

       /* FragmentManager fm = getFragmentManager();
        dialogFragment = new LoadingDialog ();
        dialogFragment.show(fm, "Sample Fragment");*/
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
        //dialogFragment.dismiss();
    }

    private void showAlertDialog(String s) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder
                .setMessage(s)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent ip = new Intent(Bus_Booking_Activity.this,SearchActivity.class);
                        ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ip);
                        finish();
                    }
                });

        alertDialogBuilder.show();





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder
                .setMessage("Your Booking is under progress, pressing BACK may result in cancellation of this request.\n\nDo you still want to go back?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent ip = new Intent(Bus_Booking_Activity.this,SearchActivity.class);
                        ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(ip);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.show();
    }
}
