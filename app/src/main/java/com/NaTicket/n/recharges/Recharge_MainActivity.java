package com.NaTicket.n.recharges;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.NaTicket.n.common.pojo.GetPromoCodesDTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.paymnet_gate_ways.Payment_Gateway_Main;
import com.NaTicket.n.recharges.Bean.PromocodeBean;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.common.MainActivity;
import com.NaTicket.n.recharges.Bean.AgentDetailsBean;
import com.NaTicket.n.recharges.Bean.ReportsBean;
import com.NaTicket.n.recharges.Enums.RechargeType;
import com.NaTicket.n.recharges.pojo.Operators_DTO;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.DialogInterface.OnClickListener;

//import com.ebs.android.sdk.PaymentRequest;

public class Recharge_MainActivity extends AppCompatActivity
        implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    Boolean isInternetPresent = false;


    // Connection detector class

    EditText regmail, regmob;
    Button fsubmit, forgotagent, forgotuser;
    Menu menu;
    String agentbal;
    ImageView clientlogo;
    View view1, header, logininc;
    RadioGroup rechargetype;
    Button logindialogagent, logindialoguser, logindialogguest, logindialogsignup;
    Dialog agentpaymentDialog, forgotpassDialog, logindialog;
    ImageView wallet, pglogo;
    LinearLayout pglayout;
    TextView procced, user, agent, loginbutton, newuser, forgotpwd;
    int rechatype = 5;
    EditText mobnum, amount, username, password, EmailID;
    Spinner operatr;
    LinearLayout OperaterSpinner;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView clientname, clientbal;
    String opname, srole = "", agentid = "", newpasswor = "";
    ImageView moblie, dth, datacard;
    List oplist, opids;
    int count = 2;
    Operators_DTO bean;
    String operatorId;
    Bitmap bmp;
    InputStream inputStream;
    String usernameString, passwordString, data, forgotemail, forgotmobile, parentid;
    Boolean user_agent = false, dialogwallet = false, paymentinfo = false;
    NavigationView navigationView;
    ArrayList<Operators_DTO> operatorsDTOs = new ArrayList<>();
    ArrayList<AgentDetailsBean> beanArrayList;
    AgentDetailsBean agent_userbean;
    ReportsBean rbean;
    String mob;
    boolean PromoShow = false;
    GetPromoCodesDTO[] promoCodesDTOs;
    ArrayList<GetPromoCodesDTO> ValidPromoCode = new ArrayList<>();

    String PromoCode, PromoAmount, TotalAmount;
    double ConvenienceFee = 0;

    final int PICK_CONTACT = 9;
    private Uri uriContact;
    private String contactID;
    private static final String TAG = Recharge_MainActivity.class.getSimpleName();


    TextView HavePromo, appliedPr, ApplyPromo, FareAfterDiscount, select_operator;
    ImageView CanPromo, Pick_Contact;
    RelativeLayout PromoLayout;
    LinearLayout ApplyPromoLyt;
    EditText EnteredPrET;


    int Total;
    RadioGroup rech_radio_group;

    String UserId = "5", User_agentid = "";
    String userLogin, agentLogin;
    String AgentId, EMAIL, Name="User", ClientId;

    RadioButton prepaid, postpaid;
    LinearLayout user_Email_lyt;

    Toolbar toolbar;
    PromocodeBean promobean;
    ArrayList<PromocodeBean> promobeanlist = new ArrayList<>();
    Boolean promoboolean = false;
    Double couponamt, discamt;
    int total;
    String refno;
    TextView AvailableBal, AmountToPay, Pay;
    ImageView can_Dialog;

    Login_utils login_utils;
    String User_Role;
    ImageView BackPress;

    public static ArrayList<ReportsBean> reportsBeanArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            setContentView(R.layout.recharges_main_page);

            /*toolbar= (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });*/

            TextView ToolbarTitle = (TextView) findViewById(R.id.toolbartitle);
            ToolbarTitle.setText("Recharges");

            BackPress = (ImageView) findViewById(R.id.backBtn);
            BackPress.setOnClickListener(this);


            login_utils = new Login_utils(this);



            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            select_operator = (TextView) findViewById(R.id.select_operator_rec);
            moblie = (ImageView) findViewById(R.id.mobile1);
            dth = (ImageView) findViewById(R.id.dth);
            datacard = (ImageView) findViewById(R.id.datacard);
            procced = (TextView) findViewById(R.id.procced);
            operatr = (Spinner) findViewById(R.id.operator);
            OperaterSpinner = (LinearLayout) findViewById(R.id.OperaterSpinner);
            rech_radio_group = (RadioGroup) findViewById(R.id.recharge_radio_group);
            rech_radio_group.setOnCheckedChangeListener(this);
            prepaid = (RadioButton) findViewById(R.id.prepaid);
            postpaid = (RadioButton) findViewById(R.id.postpaid);
            mobnum = (EditText) findViewById(R.id.mobilenum);
            amount = (EditText) findViewById(R.id.amount);
            EmailID = (EditText) findViewById(R.id.emailId);
            user_Email_lyt = (LinearLayout) findViewById(R.id.user_Email_lyt);
            //Pick_Contact= (ImageView) findViewById(R.id.pick_Contacts);

            HavePromo = (TextView) findViewById(R.id.HavePromo);
            appliedPr = (TextView) findViewById(R.id.promoTag);
            ApplyPromo = (TextView) findViewById(R.id.ApplyPromoBtn);
            CanPromo = (ImageView) findViewById(R.id.removePromo);
            PromoLayout = (RelativeLayout) findViewById(R.id.promoLyt);
            ApplyPromoLyt = (LinearLayout) findViewById(R.id.AddPromoCode);
            EnteredPrET = (EditText) findViewById(R.id.enteredPromo);
            FareAfterDiscount = (TextView) findViewById(R.id.discount_amount);

            HavePromo.setOnClickListener(this);
            appliedPr.setOnClickListener(this);
            ApplyPromo.setOnClickListener(this);
            CanPromo.setOnClickListener(this);
            PromoLayout.setOnClickListener(this);
            ApplyPromoLyt.setOnClickListener(this);
            EnteredPrET.setOnClickListener(this);
            //Pick_Contact.setOnClickListener(this);


            select_operator.setOnClickListener(this);
            procced.setOnClickListener(this);


            oplist = new ArrayList<>();
            opids = new ArrayList<>();

            if (login_utils.getUserDetails(Constants.USERTYPE) != null && !login_utils.getUserDetails(Constants.USERTYPE).equals("")) {
                user_Email_lyt.setVisibility(View.GONE);
                UserId = login_utils.getUserDetails(Constants.USERTYPE);
                User_agentid = login_utils.getUserDetails(Constants.USERID);
                Name = login_utils.getUserDetails(Constants.USERNAME);
                EMAIL = login_utils.getUserDetails(Constants.USEREMAIL);
                agentbal=login_utils.getUserDetails(Constants.BALANCE);
                ClientId = login_utils.getUserDetails(Constants.CLIENTID);
            }

            callConvenienceFee();
            hideKeyboard();

            callPromoCodes();
            final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, oplist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            oplist = new ArrayList();


            mobnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        hideKeyboard();
                    }
                }
            });


            moblie.setOnClickListener(this);
            dth.setOnClickListener(this);
            datacard.setOnClickListener(this);
            prepaid.setOnClickListener(this);
            postpaid.setOnClickListener(this);
            mobnum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

            if (rechatype == 5 || rechatype == 6) {
                moblie.setImageDrawable(getResources().getDrawable(R.drawable.ic_mobile_dark_96));
                dth.setImageDrawable(getResources().getDrawable(R.drawable.ic_dth_light_96));
                datacard.setImageDrawable(getResources().getDrawable(R.drawable.ic_datacard_light_96));
                postpaid.setVisibility(View.VISIBLE);
                prepaid.setVisibility(View.VISIBLE);
                postpaid.setTextColor(Color.BLACK);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) Recharge_MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mobnum.getWindowToken(), 0);
    }


    @Override
    public void onClick(View v) {
        oplist.clear();

        int opid = 0;
        if (v == select_operator) {
            //new Getoperators().execute();
            Intent ip = new Intent(Recharge_MainActivity.this, Operator_list_activity_rec.class);
            ip.putExtra("Category", rechatype);
            startActivityForResult(ip, 1);
        }

        if (v == BackPress) {
            onBackPressed();
        }

//        if(v==Pick_Contact){
//            Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
//
//            startActivityForResult(intent, PICK_CONTACT);
//        }


        if (v == HavePromo) {

            if (PromoShow) {
                ApplyPromoLyt.setVisibility(View.GONE);
                PromoShow = false;
            } else {
                ApplyPromoLyt.setVisibility(View.VISIBLE);
                PromoShow = true;
            }
        }
        if (v == appliedPr) {

        }
        if (v == ApplyPromo) {
            if (!validatePromoCode()) {

            } else {
                callValidatePromo();
            }

        }

        if (v == CanPromo) {
            EnteredPrET.setText("");
            EnteredPrET.setHint("Enter here");
            PromoLayout.setVisibility(View.GONE);
            HavePromo.setVisibility(View.VISIBLE);
            promoboolean = false;


        }
        if (v == PromoLayout) {

        }
        if (v == ApplyPromoLyt) {

        }
        if (v == EnteredPrET) {

        }

        if (v == moblie) {
            mobnum.getText().clear();
            amount.getText().clear();

            select_operator.setText("");
            select_operator.setHint("Select Operator");
            rechatype = RechargeType.PrepaidMobile.typenum();
            if (rechatype == 5 || rechatype == 6) {
                moblie.setImageDrawable(getResources().getDrawable(R.drawable.ic_mobile_dark_96));
                dth.setImageDrawable(getResources().getDrawable(R.drawable.ic_dth_light_96));
                datacard.setImageDrawable(getResources().getDrawable(R.drawable.ic_datacard_light_96));
                postpaid.setVisibility(View.VISIBLE);
                prepaid.setVisibility(View.VISIBLE);
                mobnum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            }
            oplist.clear();
            //new Getoperators().execute();

        }
        if (v == dth) {
            mobnum.getText().clear();
            amount.getText().clear();
            select_operator.setText("");
            select_operator.setHint("Select Operator");
            rechatype = RechargeType.PrepaidDTH.typenum();
            if (rechatype == 1) {
                dth.setImageDrawable(getResources().getDrawable(R.drawable.ic_dth_dark_96));
                moblie.setImageDrawable(getResources().getDrawable(R.drawable.ic_mobile_light_96));
                datacard.setImageDrawable(getResources().getDrawable(R.drawable.ic_datacard_light_96));
                postpaid.setVisibility(View.GONE);
                prepaid.setVisibility(View.GONE);
                mobnum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
            }

            //new Getoperators().execute();

        }
        if (v == datacard) {
            mobnum.getText().clear();
            amount.getText().clear();
            select_operator.setText("");
            select_operator.setHint("Select Operator");
            rechatype = RechargeType.PrepaidDatacard.typenum();
            if (rechatype == 3) {
                datacard.setImageDrawable(getResources().getDrawable(R.drawable.ic_datacard_dark_96));
                dth.setImageDrawable(getResources().getDrawable(R.drawable.ic_dth_light_96));
                moblie.setImageDrawable(getResources().getDrawable(R.drawable.ic_mobile_light_96));
                postpaid.setVisibility(View.GONE);
                prepaid.setVisibility(View.GONE);
                mobnum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            }
            oplist.clear();
            //new Getoperators().execute();
        }


        if (v == procced) {
            mob = mobnum.getText().toString();
            TotalAmount = amount.getText().toString();

            // opid=operatorId;
            try {

                if (opname.equals("Select Operator")) {
                    Toast.makeText(getApplicationContext(), "Please Select Operator", Toast.LENGTH_LONG).show();
                }

                if (!mob.equals("") && !TotalAmount.equals("") /*&& !EMAIL.equals("") && EMAIL.matches(Constants.emailPattern)*/) {

                    if (UserId.equals("5")) {
                        EMAIL = EmailID.getText().toString();
                        if (EMAIL.equals("") && !EMAIL.matches(Constants.emailPattern)) {

                            if (EMAIL.equals("")) {
                                EmailID.setError("Enter Email");
                            }

                            if (!EMAIL.matches(Constants.emailPattern)) {
                                EmailID.setError("Enter Valid Email");
                            }

                        }

                    }


                    if (rechatype == 5 || rechatype == 6 || rechatype == 3) {
                        if (mob.length() <= 9) {
                            mobnum.setError("Please Enter Valid Number");
                        } else {

                            pay();


                        }
                    } else if (rechatype == 1) {
                        if (mob.length() <= 12) {
                            mobnum.setError("Please Enter Valid Number");
                        } else {
                            pay();
                        }
                    } else {

                        if (mob.length() <= 12) {
                            mobnum.setError("Please Enter Valid Number");
                        } else {
                            pay();
                        }
                    }

                } else {
                    if (mob.equals("")) {
                        // updateSpinner();
                        mobnum.setError("Enter Number");
                    }
                    if (TotalAmount.equals("")) {
                        //updateSpinner();
                        amount.setError("Enter Amount");
                    }
                    if (opname.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Select Operator", Toast.LENGTH_LONG).show();

                    }
                }

            } catch (Exception e) {
                //updateSpinner();
                Toast.makeText(getApplicationContext(), "Please enter all required fields", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        }


    }


    public void pay() {
        new InitiateRecharge().execute();
    }

    /*public void updateSpinner() {
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
        opids.clear();
        Boolean firsttime=false;
        oplist.clear();
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, oplist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oplist.add("Please select operator");

        for (int i = 0; i < operatorsDTOs.size(); i++) {


            bean = new Operators_DTO();
            bean = operatorsDTOs.get(i);

            Collections.addAll(oplist, bean.getOpreatorname());
            Collections.addAll(opids, bean.getOperatorid());
        }
        if(opids.size()==0 && firsttime==true){
            error();
            firsttime=true;
        }

        operatr.setAdapter(dataAdapter);

        operatr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    operatorId = 0;
                } else {
                    operatorId = (int) opids.get(position - 1);
                    opname = parent.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dataAdapter.notifyDataSetChanged();

    }*/

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (prepaid.isChecked()) {
            rechatype = RechargeType.PrepaidMobile.typenum();
            oplist.clear();
            //new Getoperators().execute();
            mobnum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        } else {
            rechatype = RechargeType.PostpaidMobile.typenum();
            oplist.clear();
            mobnum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            //new Getoperators().execute();
        }
    }

    /*public class Getoperators extends AsyncTask<String, String, String> {

        String data;

        @Override
        protected String doInBackground(String... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient();


            HttpGet httpGet = new HttpGet(Constants.BASEURL+ Constants.operatorsurl + rechatype);
            httpGet.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpGet.setHeader("ConsumerSecret", Constants.ConsumerSecret);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                data = httpClient.execute(httpGet, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return data;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                operatorsDTOs.clear();
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    bean = new Operators_DTO();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    bean.setOperatorid(jsonObject.getInt("OperatorId"));
                    bean.setOpreatorname(jsonObject.getString("OperatorName"));
                    bean.setType(jsonObject.getInt("Type"));
                    operatorsDTOs.add(bean);
                }
                updateSpinner();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }*/


    @Override
    public void onBackPressed() {


        super.onBackPressed();
        Intent ip = new Intent(Recharge_MainActivity.this, MainActivity.class);
        ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ip);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private boolean validatePromoCode() {
        if (!Util.checkField(amount)) {
            Util.showMessage(Recharge_MainActivity.this, "Enter Amount");
            return false;
        } else if (!Util.checkField(EnteredPrET)) {
            Util.showMessage(Recharge_MainActivity.this, "Enter Promo code");
            return false;
        } else {
            return true;
        }
    }

    public void callValidatePromo() {
        for (int i = 0; i < promoCodesDTOs.length; i++) {

            if (EnteredPrET.getText().toString().equals(promoCodesDTOs[i].getCode()) && promoCodesDTOs[i].getServiceType() == 7) {
                ValidPromoCode.add(promoCodesDTOs[i]);
            }


        }

        if (ValidPromoCode.size() == 0) {
            showAlertDialog("Invalid PromoCode !");
        } else {

            total = Integer.parseInt(amount.getText().toString());
            String ValidFrom = ValidPromoCode.get(0).getValidFrom();
            String ValidTill = ValidPromoCode.get(0).getValidTill();
            double FromAmount = Double.parseDouble(String.valueOf(ValidPromoCode.get(0).getFromAmount()));
            double ToAmount = Double.parseDouble(String.valueOf(ValidPromoCode.get(0).getToAmount()));
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            String CurrentDate = output.format((c.getTime()));
            try {
                Date PromoCodeValidFromDate = output.parse(ValidFrom);
                Date PromoCodeValidTillDate = output.parse(ValidTill);
                Date todayDate = output.parse(CurrentDate);

                if (PromoCodeValidTillDate.after(todayDate) || todayDate.equals(PromoCodeValidTillDate) && PromoCodeValidFromDate.before(todayDate) || todayDate.equals(PromoCodeValidFromDate)) {
                    if (total >= FromAmount && total <= ToAmount) {
                        int DiscountType = ValidPromoCode.get(0).getDiscountType();
                        double Discount = Double.parseDouble(String.valueOf(ValidPromoCode.get(0).getDiscount()));
                        int dd = RoundOff(Discount);
                        PromoCode = ValidPromoCode.get(0).getCode();
                        if (DiscountType == 0) {
                            double DiscountFare = (total / 100.0f) * Discount;
                            int d = RoundOff(DiscountFare);
                            int TotalFare = total - d;

                            HavePromo.setVisibility(View.GONE);
                            ApplyPromoLyt.setVisibility(View.GONE);
                            PromoLayout.setVisibility(View.VISIBLE);
                            appliedPr.setText(EnteredPrET.getText().toString());
                            FareAfterDiscount.setText("" + (total) + "-" + d + "= " + "₹ " + TotalFare);
                            TotalAmount = String.valueOf(TotalFare);

                        } else {

                            if (total > dd) {
                                int TotalFare = total - dd;

                                HavePromo.setVisibility(View.GONE);
                                ApplyPromoLyt.setVisibility(View.GONE);
                                PromoLayout.setVisibility(View.VISIBLE);
                                appliedPr.setText(EnteredPrET.getText().toString());
                                FareAfterDiscount.setText("" + (total) + "-" + dd + "= " + "₹ " + TotalFare);
                                TotalAmount = String.valueOf(TotalFare);
                            } else {
                                showAlertDialog("Recharge amount is less than promo amount,\n\nRecharge amount: ₹ " + total + "\nPromo amount: ₹ " + dd + "\n\nTry with more than ₹ " + dd);
                            }
                        }
                    } else {
                        int From = RoundOff(FromAmount);
                        int To = RoundOff(ToAmount);
                        showAlertDialog("Promo Code is Valid only when \nTotal Amount is in between\n" + "₹ " + From
                                + " and " + "₹ " + To);
                    }

                } else {
                    showAlertDialog("Promocode Expired !");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    }


    private void callPromoCodes() {
        if (Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getPromocodes(Recharge_MainActivity.this, Constants.GETPROMOCODES);
        } else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }


    public void getPromoCodesResponse(String response) {
        System.out.println("Offers---" + response);
        //Util.showMessage(this,response);
        if (response != null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            GetPromoCodesDTO[] getPromoCodesDTOs = gson.fromJson(reader, GetPromoCodesDTO[].class);
            if (getPromoCodesDTOs != null) {
                promoCodesDTOs = getPromoCodesDTOs;
            }
        }
    }


    public class InitiateRecharge extends AsyncTask<String, String, String> {

        String data, str, status;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Recharge_MainActivity.this, "", "Please Wait...\nProcessing your recharge)");
        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject bcparam = new JSONObject();
            // List<NameValuePair> parm=new ArrayList<NameValuePair>();
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Constants.BASEURL + Constants.initiatercurl);
            httpPost.setHeader("ConsumerKey", Constants.ConsumerKey);
            httpPost.setHeader("ConsumerSecret", Constants.ConsumerSecret);
            try {

                bcparam.put("RechargeNumber", mob);
                bcparam.put("Operator", operatorId);
                bcparam.put("RechargeType", rechatype);
                bcparam.put("Amount", TotalAmount);
                bcparam.put("Name", Name);
                bcparam.put("Email", EMAIL);
                bcparam.put("UserType", UserId);
                bcparam.put("User", User_agentid);
                bcparam.put("City", "hyderabad");
                bcparam.put("State", "AP");
                bcparam.put("Address", "hyderabad");
                bcparam.put("Pincode", "500082");
                bcparam.put("DeviceModel", Util.getDeviceName());
                bcparam.put("DeviceOS", "Android");
                bcparam.put("DeviceOSVersion", Build.VERSION.RELEASE);
                bcparam.put("DeviceToken", Util.getDeviceTocken(Recharge_MainActivity.this));
                bcparam.put("ApplicationType", "Mobile");

                try {
                    StringEntity seEntity;
                    seEntity = new StringEntity(bcparam.toString(), "UTF_8");
                    seEntity.setContentType("application/json");
                    httpPost.setEntity(seEntity);
                    HttpResponse httpResponse;
                    httpResponse = httpClient.execute(httpPost);
                    inputStream = httpResponse.getEntity().getContent();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line = "";
                data = "";
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

            Log.v("rsponse", "" + data);

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(data);

                status = jsonObject.getString("Status");
                refno = jsonObject.getString("ReferenceNo");
                str = jsonObject.getString("Message");
                jsonObject.getString("BookingStatus");
                jsonObject.getString("ResponseStatus");
                jsonObject.getString("RefundResponse");
                jsonObject.getString("Provider");


                if (status.equalsIgnoreCase("1") && str.equalsIgnoreCase("OK")) {

                    int t = RoundOff(Double.parseDouble(TotalAmount));
                    final int c = RoundOff(ConvenienceFee);
                    Total = t + c;
                    final Context context = Recharge_MainActivity.this;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Payment Conformation");
                    alertDialogBuilder
                            .setMessage("Recharge Amount= ₹ " + TotalAmount + "\nConvenience Fee= ₹ " + c + "\n\nClick yes to pay ₹ " + Total)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    if (UserId.equals("4")) {
                                        ShowAentDialog();
                                    } else {
                                        Intent i = new Intent(Recharge_MainActivity.this, Payment_Gateway_Main.class);
                                        i.putExtra("name", Name);
                                        i.putExtra("email", EMAIL);
                                        i.putExtra("amount", String.valueOf(Total));
                                        i.putExtra("convFee", String.valueOf(c));
                                        i.putExtra("phone", mob);
                                        i.putExtra("referencenumber", refno);
                                        i.putExtra("BookingType", "Recharge");
                                        i.putExtra("OperatorName", opname);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);


                                    }
                                }
                            })
                            .setNegativeButton("No", new OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

                } else if (status.equalsIgnoreCase("2") && str.equalsIgnoreCase("Failed to process : Low Balance")) {
                    Toast.makeText(Recharge_MainActivity.this, "" + str, Toast.LENGTH_SHORT).show();

                } else {
                    error();

                }


            } catch (JSONException e) {
                error();
                e.printStackTrace();
            }

        }


    }


    private void callConvenienceFee() {
        //showProgressDialog("Please Wait...",this);
        if (Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getConvenienceFee(Recharge_MainActivity.this, Constants.getConvenienceFee + UserId + "&user=null");
        } else {
            Util.showMessage(Recharge_MainActivity.this, Constants.NO_INT_MSG);
        }
    }

    public void getStausresponse(String response) throws JSONException {

        String s = response.replaceAll("\"", "");

        if (response != null) {
            ConvenienceFee = Double.parseDouble(s);
        } else {
            ConvenienceFee = 0;
        }


    }


    public void ShowAentDialog() {
        agentpaymentDialog = new Dialog(Recharge_MainActivity.this);
        agentpaymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        agentpaymentDialog.setContentView(R.layout.agent_dialog);
        agentpaymentDialog.setCancelable(false);


        AvailableBal = (TextView) agentpaymentDialog.findViewById(R.id.wallet_bal);
        AmountToPay = (TextView) agentpaymentDialog.findViewById(R.id.AmountToPay);
        Pay = (TextView) agentpaymentDialog.findViewById(R.id.pay);
        can_Dialog = (ImageView) agentpaymentDialog.findViewById(R.id.can_dialog);
        AvailableBal.setText("₹ " + agentbal + "");
        AmountToPay.setText("₹ " + TotalAmount + "");


        agentpaymentDialog.show();
        Window window = agentpaymentDialog.getWindow();
        window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        //wallet = (ImageView) agentpaymentDialog.findViewById(R.id.walletimg);
        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogwallet = false;
                Pay.setBackgroundColor(getResources().getColor(R.color.colorAccent));


                //new Rechargedetails().execute();
                Intent i = new Intent(Recharge_MainActivity.this, RechargeResponseActivity.class);
                i.putExtra("Amount", TotalAmount);
                i.putExtra("operatorName", opname);
                i.putExtra("phone", mob);
                i.putExtra("referencenumber", refno);
                i.putExtra("isFromOrder", true);
                startActivity(i);

            }
        });


        can_Dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agentpaymentDialog.dismiss();
            }
        });
    }



    /*@Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    uriContact = data.getData();
                    //retrieveContactNumber();
                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        //String num=c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        //mobnum.setText(""+name);
                        Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
                        // TODO Fetch other Contact details as you want to use

                    }
                }
                break;
        }
    }*/

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);
        Toast.makeText(this, "" + contactNumber, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                opname = data.getStringExtra("OP_NAME");
                operatorId = data.getStringExtra("OP_ID");
                select_operator.setText("" + opname + "");
            }
        }
    }


    private void showAlertDialog(String s) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
        ////alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public Integer RoundOff(double TotalRate) {
        double dAbs = Math.abs(TotalRate);
        int amt2 = (int) dAbs;
        double result = dAbs - (double) amt2;
        int finalrate;
        if (result < 0.5) {
            finalrate = (int) Math.floor(TotalRate);
        } else {
            finalrate = (int) Math.ceil(TotalRate);
        }

        return finalrate;
    }


    public void error() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Failed to process")
                .setCancelable(false)
                .setPositiveButton("OK", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.cancel();
                    }
                });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }


}

