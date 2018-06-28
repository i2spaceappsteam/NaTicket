package com.NaTicket.n.paymnet_gate_ways;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.R;
import com.NaTicket.n.buses.Bus_Booking_Activity;
import com.NaTicket.n.buses.Buses_MainActivity;
import com.NaTicket.n.common.pojo.GetClientDetails;
import com.NaTicket.n.flights.Flight_Booking_Activity;
import com.NaTicket.n.flights.Flights_Search_Activity;
import com.NaTicket.n.holidays.HolidaySearchActivity;
import com.NaTicket.n.holidays.Holiday_Booking_Activity;
import com.NaTicket.n.hotels.HotelSearchActivity;
import com.NaTicket.n.hotels.Hotel_Booking_Activity;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.recharges.RechargeResponseActivity;
import com.NaTicket.n.recharges.Recharge_MainActivity;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.serviceclasses.Service_SavingPayments;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ankit on 9/5/2017.
 */

public class Payment_Gateway_Main extends AppCompatActivity implements View.OnClickListener {

    String amt;
    private String mfirstname; // From Previous Activity
    private String memail; // From Previous Activity
    private String mphone; // From Previous Activity
    String mtxnid; // This will create below randomly

    String msg,hash;
    int PGIdFromClientDetails=0;
    String Responsee;
    LinearLayout payment_gatway_view, loadingPg_progress_lyt;
    String OnwardReferenceNumber, ReturnTicketReferencenumber;
    String data, refno,Booking_Type,OperatorName="";
    double Confee=0;
    InputStream inputStream;
    ArrayList<HashMap<String,String>> ResponseString=new ArrayList<>();
    Boolean PayMentstatus,NB=false,showSpinner=false;

    TextView PayUproceed,PayULogOut,total_amt;
    Boolean PGPayU;
    Login_utils login_utils;
    ResultModel resultModel;
    TransactionResponse transactionResponse;
    String PGResponseHash = null;
    String C_Response;

    GetClientDetails ClientDetailsDTO;

    String RequestParams;
    String txnId, phone, productName, firstName, email,ResponseStatus;

    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    Double amount=0.0,FinalAmount=0.0;

    public static final String TAG = "PaymentGateway : ";
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("In On Resume");
        setContentView(R.layout.payment_details_screen);

        TextView ToolbarTitle= (TextView) findViewById(R.id.toolbartitle);
        ToolbarTitle.setText("Payment");

        ImageView BackPress= (ImageView) findViewById(R.id.backBtn);

        BackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogforError("Do you wish to cancel this Transaction ?");
            }
        });

        isConnected();

        InintView();


    }


    public void InintView(){

        payment_gatway_view = (LinearLayout) findViewById(R.id.payment_gatway_view);
        loadingPg_progress_lyt = (LinearLayout) findViewById(R.id.loadingPg_progress_lyt);

        PGPayU = false;

        PayUproceed= (TextView) findViewById(R.id.payU_Proceed);
        PayULogOut= (TextView) findViewById(R.id.payU_Logout);
        total_amt= (TextView) findViewById(R.id.total_pay_amt);

        if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
            PayULogOut.setVisibility(View.VISIBLE);
        } else {
            PayULogOut.setVisibility(View.GONE);
        }



        PayUproceed.setOnClickListener(this);
        PayULogOut.setOnClickListener(this);

        PayUproceed.setEnabled(true);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            mfirstname = bundle.getString("name");
            memail = bundle.getString("email");
            amt = bundle.getString("amount");
            mphone = bundle.getString("phone");
            refno = bundle.getString("referencenumber");
            Booking_Type = bundle.getString("BookingType");
            ReturnTicketReferencenumber = bundle.getString("ReturnTicketrReferenceNumber");
            OperatorName = bundle.getString("OperatorName");
            String con = bundle.getString("convFee");

            if (con != null) {
                Confee = Integer.parseInt(con);
            }


            amount = Double.parseDouble(amt);

            FinalAmount = Util.getprice(amount);
            amt = FinalAmount.toString();


            total_amt.setText(Constants.AGENT_CURRENCY_SYMBOL + amt + "");

            login_utils = new Login_utils(this);

            ///Flight_Booking///Bus_Booking
            Log.i(amt, "" + mfirstname + " : " + memail + " : " + mphone + " : " + refno);

            if (Booking_Type.equals("Hotel_Booking")) {
                Double Amount = (double) Math.round(Float.parseFloat(amt));
                amt = String.valueOf(Amount);
                System.out.println("Amount : " + amt);
            }
        }




    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.payU_Proceed:

                launchPayUMoneyFlow();


                break;

            case R.id.payU_Logout:
                PayUmoneyFlowManager.logoutUser(getApplicationContext());
                PayULogOut.setVisibility(View.GONE);

                break;


        }

    }



    /**
     * This function prepares the data for payment and launches payumoney plug n play sdk
     */
    private void launchPayUMoneyFlow() {


        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Payment_Details");

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("NaTicket");

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();



        txnId = System.currentTimeMillis() + "";
        phone = mphone;
        productName = "Booking Ticket";
        firstName = mfirstname.replace(" ", "");
        email = memail;
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";

        builder.setAmount(amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setIsDebug(Constants.PayUmoneyMerchantIsDebug)
                .setKey(Constants.PayUmoneyMerchantKey)
                .setMerchantId(Constants.PayUmoneyMerchantID);


        try {
            mPaymentParams = builder.build();

            RequestParams = CalculateRequestParams(mPaymentParams);


            callSavePaymentDetails();


        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            //payNowButton.setEnabled(true);
        }
    }



    private String CalculateRequestParams(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        stringBuilder.append(Constants.PayUmoneyMerchantSALT);



        return stringBuilder.toString();
    }





    private void callSavePaymentDetails() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            Service_SavingPayments.SavePaymentDetails(Payment_Gateway_Main.this, Constants.SAVEPAYMENTDETAILS, preparePaymentdetailss());
        } else {
            Util.showMessage(Payment_Gateway_Main.this, Constants.NO_INT_MSG);
        }
    }


    private String preparePaymentdetailss() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("PGResponse", null);
            jsonBody.put("PaymentId", 0);
            jsonBody.put("PaymentGatewayId", PGIdFromClientDetails);
            jsonBody.put("UserId", 0);
            jsonBody.put("ReferenceNumber", refno);
            jsonBody.put("Amount", amt);
            jsonBody.put("TransactionCharges", 0);
            jsonBody.put("ResponseString", "Request sent to payment gateway");
            jsonBody.put("PaymentStatus", 3);
            jsonBody.put("IPAddress", "::1");
            jsonBody.put("Request", "null");
            jsonBody.put("ClientId", 0);
            jsonBody.put("PGRequest", RequestParams);
            jsonBody.put("PaymentGatewayName", "PayU Money");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }

    public void postSaveDetailsParams(String response) {
        if (response != null) {
            msg = Util.getResponseMessage(response);


            if (msg != null) {

                callGenerateHash();
            } else {

                Toast.makeText(this, "Failed !", Toast.LENGTH_SHORT).show();
            }
        } else {
            callBackActivity(Booking_Type);
        }

    }







    ///////Method to generate has from server before payment
    private void callGenerateHash() {
        if (Util.isNetworkAvailable(getApplicationContext())) {

            String mUrl = Constants.GenerateHash + "AccountId=" + Constants.PayUmoneyMerchantKey +
                    "&ReferenceNo=" + txnId + "&Amount=" + amount + "&Name=" + firstName + "&Email=" + email +
                    "&SecretKey=" + Constants.PayUmoneyMerchantSALT;

            Log.d("Web HASH URL",mUrl);


            Service_SavingPayments.GenerateHash(Payment_Gateway_Main.this, mUrl);
        } else {
            Util.showMessage(Payment_Gateway_Main.this, Constants.NO_INT_MSG);
        }
    }


    public void postGenerateHash(String response) {

        if (response != null) {
            hash = Util.getResponseMessage(response);



            if (hash.isEmpty() || hash.equals("")) {
                Toast.makeText(Payment_Gateway_Main.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
            } else {
                mPaymentParams.setMerchantHash(hash);

                // Invoke the following function to open the checkout page.
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,
                        Payment_Gateway_Main.this,
                        R.style.AppTheme_default,
                        true);
            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed here it is 2
        System.out.println("RESULTCODE--->" + resultCode);
        System.out.println("REQUESTCODE--->" + requestCode);
        System.out.println("RESULT_OK--->" + RESULT_OK);


        // Result Code is -1 send from Payumoney activity
        Log.d("PayMainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Response from Payumoney
            Responsee = transactionResponse.getPayuResponse();

            // Response from SURl and FURL
            String merchantResponse = transactionResponse.getTransactionDetails();

            JSONObject jsonObject = null;
            JSONObject PaymentResult = null;

            try {
                jsonObject = new JSONObject(Responsee);
                PaymentResult = (JSONObject) jsonObject.get("result");
                PGResponseHash = PaymentResult.getString("hash");
                txnId = PaymentResult.getString("txnid");
                amount = PaymentResult.getDouble("amount");
                firstName = PaymentResult.getString("firstname");
                email = PaymentResult.getString("email");
                ResponseStatus = PaymentResult.getString("status");


            } catch (JSONException e) {
                e.printStackTrace();
            }



            callReGenerateHash();


        }

    }


    ///////Method to Regenerate has from server after payment response received
    private void callReGenerateHash() {
        if (Util.isNetworkAvailable(getApplicationContext())) {

            String mUrl = Constants.ReGenerateHash + "AccountId=" + Constants.PayUmoneyMerchantKey +
                    "&ReferenceNo=" + txnId + "&Amount=" + amount + "&Name=" + firstName + "&Email=" + email +
                    "&SecretKey=" + Constants.PayUmoneyMerchantSALT+"&Status="+ResponseStatus;

            Log.d("Web HASH URL",mUrl);


            Service_SavingPayments.ReGenerateHash(Payment_Gateway_Main.this, mUrl);
        } else {
            Util.showMessage(Payment_Gateway_Main.this, Constants.NO_INT_MSG);
        }
    }


    public void postReGenerateHash(String response) {

        if (response != null) {
            hash = Util.getResponseMessage(response);

            if (hash.isEmpty() || hash.equals("")) {
                Toast.makeText(Payment_Gateway_Main.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
            } else {
                if (hash.equals(PGResponseHash)) {
                    // Check which object is non-null
                    if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                        if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                            //Success Transaction
                            //PayUmoney.success();
                            message = "Transaction Successful!";
                            Util.showMessage(Payment_Gateway_Main.this, message);
                            SendResponse();
                        } else {
                            //Failure Transaction
                            message = "Transaction Failed!";
                            Util.showMessage(Payment_Gateway_Main.this, message);
                            SendResponse();
                        }


                    } else if (resultModel != null && resultModel.getError() != null) {
                        Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
                    } else {
                        Log.d(TAG, "Both objects are null!");
                    }
                }else{
                    Util.showMessage(Payment_Gateway_Main.this,"Couldn't verify payment");
                    Util.startHomeActivity(Payment_Gateway_Main.this);

                }
            }

        }

    }



    public void isConnected() {
        if (Util.isNetworkAvailable(getApplicationContext())) {


            callClientDetails();

        } else {
            Util.alertDialogShow(this, "Please Check Your Internet Connection");
        }
    }


    private void callClientDetails() {
        ServiceClasses.getClientDetails(Payment_Gateway_Main.this, Constants.CLIENTDETAILS);

    }


    public void getClientDetailsResponse(String response) {
        GetClientDetails getClientDetailsDTO = null;
        int PG_Status = 0;
        if (response != null) {

            C_Response = response;


            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            getClientDetailsDTO = gson.fromJson(reader, GetClientDetails.class);
            if (getClientDetailsDTO != null && getClientDetailsDTO.getServices() != null && getClientDetailsDTO.getServices().size() > 0) {
                try {

                    ClientDetailsDTO = getClientDetailsDTO;
                    if (getClientDetailsDTO.getPaymentGateways().size() != 0) {
                        for (int i = 0; i < getClientDetailsDTO.getPaymentGateways().size(); i++) {

                            if (getClientDetailsDTO.getPaymentGateways().get(i).getPaymentGatewayName().equals("PayU Money")) {
                                PG_Status = getClientDetailsDTO.getPaymentGateways().get(i).getStatus();
                                PGIdFromClientDetails=getClientDetailsDTO.getPaymentGateways().get(i).getPaymentGatewayId();
                                if (PG_Status == 1) {
                                    loadingPg_progress_lyt.setVisibility(View.GONE);
                                    payment_gatway_view.setVisibility(View.VISIBLE);
                                    break;

                                } else {
                                    Util.showMessage(this, "Payment Gateway is disabled");
                                    Util.startHomeActivity(this);
                                    finish();
                                    break;
                                }
                            }
                        }
                    } else {
                        Util.showMessage(this, "No Active Payment Gateway ");
                        Util.startHomeActivity(this);
                        finish();
                    }

                } catch (Exception e) {

                }
            } else {
                Util.showMessage(this, "Something went wrong!");
            }
        } else {
            Util.showMessage(this, "Something went wrong!");
        }

    }


    public void errorresponse(String response) {

        Snackbar snackbar = Snackbar
                .make(loadingPg_progress_lyt, "Couldn't create connection", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(getResources().getColor(R.color.colorWhite))
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callClientDetails();
                    }
                });

        snackbar.show();
    }






    private void showAlertDialogforError(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setTitle("Alert !");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                callBackActivity(Booking_Type);
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








    public void  SendResponse() {


            if (message.equalsIgnoreCase("Transaction Successful!")) {
                PayMentstatus = true;
                    /*new Payment().execute();*/

                if (Booking_Type.equals("Bus_Booking")){
                    Intent ip=new Intent(Payment_Gateway_Main.this,Bus_Booking_Activity.class);

                    ip.putExtra("Status",PayMentstatus);
                    ip.putExtra("Request",RequestParams);
                    ip.putExtra("Response",Responsee);
                    ip.putExtra("PaymentGatewayId", PGIdFromClientDetails);
                    ip.putExtra("Amount",amt);
                    ip.putExtra("ReferenceNumber",refno);
                    ip.putExtra("ReturnTicketrReferenceNumber",ReturnTicketReferencenumber);
                    ip.putExtra("name",mfirstname);
                    ip.putExtra("email",memail);
                    ip.putExtra("phone",mphone);
                    ip.putExtra("IsAgent","No");
                    ip.putExtra("PaymentId",msg);
                    startActivity(ip);
                }else if (Booking_Type.equals("Flight_Booking")){
                    Intent ip=new Intent(Payment_Gateway_Main.this,Flight_Booking_Activity.class);

                    ip.putExtra("Status",PayMentstatus);
                    ip.putExtra("Request",RequestParams);
                    ip.putExtra("Response",Responsee);
                    ip.putExtra("PaymentGatewayId", PGIdFromClientDetails);
                    ip.putExtra("Amount",amt);
                    ip.putExtra("referencenumber",refno);
                    ip.putExtra("name",mfirstname);
                    ip.putExtra("email",memail);
                    ip.putExtra("phone",mphone);
                    ip.putExtra("IsAgent","No");
                    ip.putExtra("PaymentId",msg);
                    startActivity(ip);
                }else if (Booking_Type.equals("Hotel_Booking")){
                    Intent ip=new Intent(Payment_Gateway_Main.this,Hotel_Booking_Activity.class);
                    ip.putExtra("Status",PayMentstatus);
                    ip.putExtra("Request",RequestParams);
                    ip.putExtra("Response",Responsee);
                    ip.putExtra("PaymentGatewayId", PGIdFromClientDetails);
                    ip.putExtra("Amount",amt);
                    ip.putExtra("referencenumber",refno);
                    ip.putExtra("name",mfirstname);
                    ip.putExtra("email",memail);
                    ip.putExtra("phone",mphone);
                    ip.putExtra("IsAgent","No");
                    ip.putExtra("PaymentId",msg);
                    startActivity(ip);
                }else if (Booking_Type.equals("Holidays")){
                    Intent ip=new Intent(Payment_Gateway_Main.this,Holiday_Booking_Activity.class);
                    ip.putExtra("Status",PayMentstatus);
                    ip.putExtra("Request",RequestParams);
                    ip.putExtra("Response",Responsee);
                    ip.putExtra("PaymentGatewayId", PGIdFromClientDetails);
                    ip.putExtra("Amount",amt);
                    ip.putExtra("referencenumber",refno);
                    ip.putExtra("name",mfirstname);
                    ip.putExtra("email",memail);
                    ip.putExtra("phone",mphone);
                    ip.putExtra("IsAgent","No");
                    ip.putExtra("PaymentId",msg);
                    startActivity(ip);
                }
                else if (Booking_Type.equals("Recharge")){
                    Intent ip=new Intent(Payment_Gateway_Main.this,RechargeResponseActivity.class);
                    ip.putExtra("Status",PayMentstatus);
                    ip.putExtra("Request",RequestParams);
                    ip.putExtra("Response",Responsee);
                    ip.putExtra("PaymentGatewayId", PGIdFromClientDetails);
                    ip.putExtra("Amount",amt);
                    ip.putExtra("referencenumber",refno);
                    ip.putExtra("name",mfirstname);
                    ip.putExtra("email",memail);
                    ip.putExtra("phone",mphone);
                    ip.putExtra("IsAgent","No");
                    ip.putExtra("convFee",Confee);
                    ip.putExtra("operatorName",OperatorName);
                    ip.putExtra("PaymentId",msg);
                    startActivity(ip);
                }


            } else {
                PayMentstatus = false;
                //new PaymentFailed().execute();
                SaveFailedPaymentDetails();




        }
    }



    private void SaveFailedPaymentDetails() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            //showProgressDialog("Please wait",this);
            Service_SavingPayments.SaveFailedPaymentDetails(Payment_Gateway_Main.this, Constants.SAVEPAYMENTDETAILS,preparePaymentdetails());
        }else{
            Util.showMessage(Payment_Gateway_Main.this, Constants.NO_INT_MSG);
        }
    }


    private String preparePaymentdetails() {
        JSONObject jsonBody=new JSONObject();
        try {
            jsonBody.put("PGResponse", Responsee);
            jsonBody.put("PaymentId",0);
            jsonBody.put("PaymentGatewayId","93");
            jsonBody.put("UserId",0);
            jsonBody.put("ReferenceNumber",refno);
            jsonBody.put("Amount",amt);
            jsonBody.put("TransactionCharges",0);
            jsonBody.put("ResponseString",Responsee);
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

    public void postSaveDetailsParamss(String response){
        if (response!=null){
            msg=Util.getResponseMessage(response);
            callBackActivity(Booking_Type);

        }else{

        }

    }


    public void callBackActivity(String Booking_Type){
        switch (Booking_Type) {
            case "Bus_Booking": {
                Intent ip = new Intent(Payment_Gateway_Main.this, Buses_MainActivity.class);
                ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ip);
                finish();
                break;
            }
            case "Flight_Booking": {
                Intent ip = new Intent(Payment_Gateway_Main.this, Flights_Search_Activity.class);
                ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ip);
                finish();
                break;
            }
            case "Hotel_Booking": {
                Intent ip = new Intent(Payment_Gateway_Main.this, HotelSearchActivity.class);
                ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ip);
                finish();
                break;
            }
            case "Holidays": {
                Intent ip = new Intent(Payment_Gateway_Main.this,HolidaySearchActivity.class);
                ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ip);
                finish();
                break;
            }
            case "Recharge": {
                Intent ip = new Intent(Payment_Gateway_Main.this, Recharge_MainActivity.class);
                ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ip);
                finish();
                break;
            }
        }
    }












    @Override
    public void onBackPressed() {
        showAlertDialogforError("Do you wish to cancel this Transaction ?");
    }

    public static String intentToString(Intent intent) {
        if (intent == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder("action: ")
                .append(intent.getAction())
                .append(" data: ")
                .append(intent.getDataString())
                .append(" extras: ")
                ;
        for (String key : intent.getExtras().keySet())
            stringBuilder.append(key).append("=").append(intent.getExtras().get(key)).append(" ");

        return stringBuilder.toString();

    }






}
