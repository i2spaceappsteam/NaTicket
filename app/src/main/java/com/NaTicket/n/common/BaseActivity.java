package com.NaTicket.n.common;

/**
 * Created by Ankit on 10/4/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.buses.Buses_MainActivity;
import com.NaTicket.n.common.cancel_pacakge.Cancel_Ticket_Activity;
import com.NaTicket.n.common.drawer_items.About_us_Activity;
import com.NaTicket.n.common.drawer_items.Faqs;
import com.NaTicket.n.common.drawer_items.FeedBackActivity;
import com.NaTicket.n.common.drawer_items.TermsandConditions;
import com.NaTicket.n.custom.CustomTypefaceSpan;
import com.NaTicket.n.custom.FontTypeface;
import com.NaTicket.n.flights.Flights_Search_Activity;
import com.NaTicket.n.holidays.HolidaySearchActivity;
import com.NaTicket.n.hotels.HotelSearchActivity;
import com.NaTicket.n.loginpackage.Change_Password_Activity;
import com.NaTicket.n.loginpackage.Login_Activity;
import com.NaTicket.n.loginpackage.Profile_Activity;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.loginpackage.pojo.User_Details_DTO;
import com.NaTicket.n.recharges.Recharge_MainActivity;
import com.NaTicket.n.serviceclasses.Service_Login_Package;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private DrawerLayout fullLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedNavItemId;
    public LinearLayout lLFilterId;
    public TextView tvName,tvBalance,NotSigned;
    LinearLayout accountLinear,balance_lyt;
    SharedPreferences sharedpreferences;
    public Toolbar toolbar;
    String EMAIL,UserName,Response;
    LinearLayout SignedIn,SignedOut;
    ImageView ham_icon,refresh_bal;
    Login_utils login_utils;
    User_Details_DTO getUserDetailsDTO;

    Intent intent;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer(layoutResID);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sharedpreferences = getSharedPreferences(Constants.MYPREFERENCES, Context.MODE_PRIVATE);

        //setupNavigationView();

       // intent = getIntent();



    }

    protected void onCreateDrawer(@LayoutRes int layoutResID) {
        /**
         * This is going to be our actual root content_back_toolbar.
         */
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(com.NaTicket.n.R.layout.activity_base, null);
        /**
         * {@link FrameLayout} to inflate the child's view. We could also use a {@link android.view.ViewStub}
         */
        FrameLayout activityContainer = (FrameLayout) fullLayout.findViewById(com.NaTicket.n.R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        /**
         * Note that we don't pass the child's layoutId to the parent,
         * instead we pass it our inflated content_back_toolbar.
         */
        super.setContentView(fullLayout);

        login_utils=new Login_utils(this);

        ham_icon= (ImageView) findViewById(com.NaTicket.n.R.id.ham_icon);
        toolbar = (Toolbar) findViewById(com.NaTicket.n.R.id.toolbar);
        navigationView = (NavigationView) findViewById(com.NaTicket.n.R.id.navigationView);
        changeTypeface(navigationView);


        //toolbartitle=(TextView)findViewById(R.id.toolbar_title);
        accountLinear = (LinearLayout)findViewById(com.NaTicket.n.R.id.accountLinear);

        View headerLayout = navigationView.getHeaderView(0);
  /*      headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullLayout.closeDrawer(GravityCompat.START);
                Intent i = new Intent(BaseActivity.this, LoginTabActivity.class);
                startActivity(i);

            }
        });*/

        SignedIn= (LinearLayout) headerLayout.findViewById(R.id.signedInLayout);
        SignedOut= (LinearLayout) headerLayout.findViewById(R.id.signedoutLayout);

        SignedIn.setVisibility(View.GONE);
        SignedOut.setVisibility(View.VISIBLE);

        /*SignInReg= (TextView)headerLayout.findViewById(R.id.SignInReg);
        SignInReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullLayout.closeDrawer(GravityCompat.START);
                Intent i = new Intent(BaseActivity.this, LoginTabActivity.class);
                startActivity(i);

            }
        });*/
        tvName = (TextView) headerLayout.findViewById(R.id.tvName);
        balance_lyt= (LinearLayout) headerLayout.findViewById(R.id.balance_lyt);
        tvBalance = (TextView) headerLayout.findViewById(R.id.tvBalance);
        NotSigned=(TextView) headerLayout.findViewById(R.id.notSignedIn);

        refresh_bal= (ImageView) headerLayout.findViewById(R.id.refresh_bal);

        NotSigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, Login_Activity.class));
            }
        });

        refresh_bal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAgentDetails();
            }
        });


        if (useToolbar()) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            setUpNavView(toolbar);
        } else {
            toolbar.setVisibility(View.GONE);
        }


        getLoginPrefernces();


    }

    public void callAgentDetails(){
        if (Util.isNetworkAvailable(this)){

            login_utils=new Login_utils(this);

            Service_Login_Package.getUserDetails(this, Constants.AGENTDETAILS + login_utils.getUserDetails(Constants.USEREMAIL));

        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getAgentDetailsResponses(String response) {
        InputStream stream = new ByteArrayInputStream(response.getBytes());
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(stream);
        getUserDetailsDTO = gson.fromJson(reader, User_Details_DTO.class);
        if(getUserDetailsDTO!=null){
            String balance="Bal : "+Constants.AGENT_CURRENCY_SYMBOL+getUserDetailsDTO.getBalance();
            tvBalance.setText(balance);
            callDecrpytmethod(getUserDetailsDTO.getUserId());
            Response=response;
        }
    }

    public void callDecrpytmethod(String UserId){
        if (Util.isNetworkAvailable(this)){
            Service_Login_Package.getDecrpted_User_Id(this,Constants.DECRIPTCODE+UserId);
        }else {
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getDecrptedUserResponses(String response) {
        if (Util.getResponseCode(response)==200){
            String Decrpted=Util.getResponseMessage(response);
            Login_utils loginutils=new Login_utils(this);

                loginutils.setUserDetails(getUserDetailsDTO.getFirstName() + " " + getUserDetailsDTO.getLastName(),
                        getUserDetailsDTO.getEmail(),
                        getUserDetailsDTO.getPassword(),
                        getUserDetailsDTO.getMobile(),
                        getUserDetailsDTO.getDOB(),
                        getUserDetailsDTO.getGender(),
                        getUserDetailsDTO.getUserId(),
                        Decrpted,
                        "4",
                        Response,
                        getUserDetailsDTO.getAddress(),
                        getUserDetailsDTO.getBalance());
            }

    }

    private void changeTypeface(NavigationView navigationView){
        FontTypeface fontTypeface = new FontTypeface(this);
        Typeface typeface = fontTypeface.getTypefaceAndroid();

        MenuItem item;

        item = navigationView.getMenu().findItem(R.id.flights);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.hotels);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.Buses);
        applyFontToItem(item, typeface);

        item=navigationView.getMenu().findItem(R.id.Recharges);
        applyFontToItem(item,typeface);

        item=navigationView.getMenu().findItem(R.id.login);
        applyFontToItem(item,typeface);

        item=navigationView.getMenu().findItem(R.id.Reports);
        applyFontToItem(item,typeface);

        item=navigationView.getMenu().findItem(R.id.nav_profile);
        applyFontToItem(item,typeface);

        item = navigationView.getMenu().findItem(R.id.Holidays);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.offers);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.cancel_ticket);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.offers);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.feedback);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.aboutus);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.faqs);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.changepwd);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.terms);
        applyFontToItem(item, typeface);

        item = navigationView.getMenu().findItem(R.id.nav_logout);
        applyFontToItem(item, typeface);
    }

    private void applyFontToItem(MenuItem item, Typeface font) {
        SpannableString mNewTitle = new SpannableString(item.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font, 16), 0 ,
                mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        item.setTitle(mNewTitle);
    }



    protected boolean useToolbar() {
        return true;
    }

    protected void setUpNavView(Toolbar toolbar) {
        navigationView.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this, fullLayout, toolbar,
                com.NaTicket.n.R.string.nav_drawer_opened, com.NaTicket.n.R.string.nav_drawer_closed);
        drawerToggle.setDrawerIndicatorEnabled(false);



        ham_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullLayout.isDrawerOpen(Gravity.LEFT)) {
                    fullLayout.closeDrawer(Gravity.LEFT);
                } else {
                    fullLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        //Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.hamburger_icon, this.getTheme());
        //drawerToggle.setHomeAsUpIndicator(drawable);

        /*if (useDrawerToggle()) { // use the hamburger menu
            drawerToggle = new ActionBarDrawerToggle(this, fullLayout, toolbar,
                    R.string.nav_drawer_opened, R.string.nav_drawer_closed) {





                //// Called when a drawer has settled in a completely closed state.
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    // Do whatever you want here
                }

                //// Called when a drawer has settled in a completely open state.
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);

                }
            };

            drawerToggle.setDrawerIndicatorEnabled(false);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.hamburger_icon, this.getTheme());
            drawerToggle.setHomeAsUpIndicator(drawable);




            fullLayout.setDrawerListener(drawerToggle);
            drawerToggle.syncState();
        } else if (useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           //getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.hamburger_icon));
        }*/
    }

    protected boolean useDrawerToggle() {
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        fullLayout.closeDrawer(GravityCompat.START);
        selectedNavItemId = menuItem.getItemId();

        return onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //Fragment  fragment=null;


        int id = item.getItemId();
        switch (id) {
            case R.id.Buses:
                startActivity(new Intent(this, Buses_MainActivity.class));
                return true;
            case R.id.flights:
                startActivity(new Intent(this, Flights_Search_Activity.class));
                return true;
            case R.id.hotels:
                startActivity(new Intent(this, HotelSearchActivity.class));
                return true;
            case R.id.login:
                startActivity(new Intent(this, Login_Activity.class));
                return true;
            case R.id.Holidays:
                startActivity(new Intent(this, HolidaySearchActivity.class));
                return true;
            case R.id.Recharges:
                startActivity(new Intent(this, Recharge_MainActivity.class));
                return true;
            case R.id.nav_profile:
                startActivity(new Intent(this, Profile_Activity.class));
                return true;
            case R.id.Reports:
                startActivity(new Intent(this, My_Trips_Activity.class));
                return true;
            case R.id.offers:
                startActivity(new Intent(this, Offers_Activity.class));
                return true;
            case R.id.aboutus:
                startActivity(new Intent(this, About_us_Activity.class));
                return true;
            case R.id.terms:
                startActivity(new Intent(this, TermsandConditions.class));
                return true;
            case R.id.cancel_ticket:
                startActivity(new Intent(this, Cancel_Ticket_Activity.class));
                return true;
            case R.id.faqs:
                startActivity(new Intent(this, Faqs.class));
                return true;
            case R.id.feedback:
                startActivity(new Intent(this, FeedBackActivity.class));
                return true;
            case R.id.changepwd:
                startActivity(new Intent(this, Change_Password_Activity.class));
                return true;
            case R.id.nav_logout:

                ShowLogoutDialog();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void getLoginPrefernces() {
        String sRole=login_utils.getUserDetails(Constants.USERTYPE);
        if(sRole.equals("6")||sRole.equals("4")){
            SignedIn.setVisibility(View.VISIBLE);
            SignedOut.setVisibility(View.GONE);
            EMAIL = login_utils.getUserDetails(Constants.USEREMAIL);
            UserName=login_utils.getUserDetails(Constants.USERNAME);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.login).setVisible(false);
            tvName.setText(UserName);

            if(sRole.equals("4")){
                String balance="Bal : "+Constants.AGENT_CURRENCY_SYMBOL+login_utils.getUserDetails(Constants.BALANCE);
                tvBalance.setText(balance);
                balance_lyt.setVisibility(View.VISIBLE);
                nav_Menu.findItem(R.id.offers).setVisible(false);
            }else{
                balance_lyt.setVisibility(View.GONE);
            }
        }else{
            SignedIn.setVisibility(View.GONE);
            SignedOut.setVisibility(View.VISIBLE);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.changepwd).setVisible(false);
            //nav_Menu.findItem(R.id.cancel_ticket).setVisible(false);
            nav_Menu.findItem(R.id.nav_profile).setVisible(false);
            nav_Menu.findItem(R.id.Reports).setVisible(false);
        }
    }


    private void ShowLogoutDialog(){

        final Dialog LogoutDialog = new Dialog(BaseActivity.this);
        LogoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LogoutDialog.setContentView(R.layout.logout_permisson_dialog);
        LogoutDialog.setCancelable(false);

        TextView Logout = (TextView) LogoutDialog.findViewById(R.id.logout);
        TextView cancel = (TextView) LogoutDialog.findViewById(R.id.dialog_cancel);




        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                login_utils.ClearUserDetails();
                SignedIn.setVisibility(View.GONE);
                SignedOut.setVisibility(View.VISIBLE);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_logout).setVisible(false);
                nav_Menu.findItem(R.id.changepwd).setVisible(false);
                nav_Menu.findItem(R.id.nav_profile).setVisible(false);
                nav_Menu.findItem(R.id.Reports).setVisible(false);
                SignedIn.setVisibility(View.GONE);
                SignedOut.setVisibility(View.VISIBLE);

                Util.showMessage(BaseActivity.this, "Logged out successfully");


                startActivity(new Intent(BaseActivity.this,Login_Activity.class));
                finish();

               /* Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishAffinity();
                System.exit(0);*/
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutDialog.dismiss();

            }
        });

        LogoutDialog.show();

    }

}
