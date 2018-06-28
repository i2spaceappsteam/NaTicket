package com.NaTicket.n.hotels;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.custom.CustomTypefaceSpan;
import com.NaTicket.n.custom.FontTypeface;
import com.NaTicket.n.hotels.adapters.Hotel_Amenities;
import com.NaTicket.n.hotels.adapters.Hotel_Details;
import com.NaTicket.n.hotels.adapters.Hotel_Map;
import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.NaTicket.n.utils.Constants;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.hotels.pojo.SelectionDetailsDTO;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



public class HotelDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private ImageView hotelImage;
    private RatingBar hotelRating;
    private TextView hotelName,toolbartitle;
    private LinearLayout amenitiesLayout,parentLayout;
    private PopupWindow mPopupWindow;
    private AvailableHotelsDTO selHotel;
    ArrayList<Room> roomList;
    private SelectionDetailsDTO selDetails;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager,images_pager;
    LinearLayout hotelname_layout,dotsLayout;
    private AvailableHotelsDTO hotelDetailselHotel;
    private ProgressDialog mProgressDialog;
    private String[] layouts;
    private TextView[] dots;
    private MyViewPagerAdapter myViewPagerAdapter;


    String Adults,Children,ChildAges;
    String Rooms;
    Hotel_utils hotel_utils;
    Login_utils login_utils;
    String UserId, User_agentid,UserType;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        login_utils=new Login_utils(this);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbartitle= (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initView();
        roomList = (ArrayList<Room>) getIntent().getSerializableExtra("roomList");
        selHotel = (AvailableHotelsDTO) getIntent().getSerializableExtra("SelHotel");
        selDetails = (SelectionDetailsDTO) getIntent().getSerializableExtra("selDetails");
        toolbartitle.setText(selHotel.getHotelName());
        setHotelData(selHotel);

        hotel_utils=new Hotel_utils(this);

        Adults= hotel_utils.getPaxDetails("Adults");
        Children=hotel_utils.getPaxDetails("Child");
        Rooms=hotel_utils.getPaxDetails("Rooms");
        ChildAges=hotel_utils.getPaxDetails("ChildAges");
        getLoginPreferences();
        callHotelDetails(selHotel);

    }

    private void getLoginPreferences(){
        if (login_utils.getUserDetails(Constants.USERTYPE).equals("6")) {
            UserId = "6";
            UserType="User";
            User_agentid = login_utils.getUserDetails(Constants.USERID);

        } else if (login_utils.getUserDetails(Constants.USERTYPE).equals("4")) {
            UserId = "4";
            UserType="Agent";
            User_agentid = login_utils.getUserDetails(Constants.USERID);
        } else {
            UserId = "5";
            UserType="Guest";
            User_agentid =null;
        }
    }
    private void callHotelDetails(AvailableHotelsDTO selHotel) {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Hotel Details",HotelDetailActivity.this);
            String GETHOTELDETAILS = Constants.GETROOMDETAILS+selHotel.getHotelId()+"&webService="+selHotel.getWebService()
                    +"&cityId="+selDetails.getCityId()+"&provider="+selHotel.getProvider()
                    +"&adults="+Adults+"&children="+Children+
                    "&arrivalDate="+selDetails.getCheckIn()+"&departureDate="+selDetails.getCheckOut()
                    +"&noOfDays="+selDetails.getNoofDays()+"&childrenAges="+ChildAges+
                    "&userType="+UserId+"&hoteltype="+selDetails.getHoteltype()+"&user="+User_agentid+
                    "&roomscount="+Rooms+"&secondaryProvider="+selHotel.getSecondaryProvider();
            System.out.println("Url=="+GETHOTELDETAILS);
            ServiceClasses.getHotelDeatils(HotelDetailActivity.this, GETHOTELDETAILS);
        }else{
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getHotelDetailsResponse(String response) {
        hideProgressDialog();
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            AvailableHotelsDTO hotelsDetailDTO = gson.fromJson(reader, AvailableHotelsDTO.class);

            if(hotelsDetailDTO!=null&&hotelsDetailDTO.getHotelId()!=null&&!hotelsDetailDTO.getHotelId().equals("null")){
                hotelDetailselHotel=hotelsDetailDTO;

                try {
                    JSONObject object=new JSONObject(response);
                    hotelsDetailDTO.setmRoomDetail(String.valueOf(object));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Util.showMessage(getApplicationContext(),hotelsDetailDTO.getRoomDetails().get(0).getRoomTotal());
                setHotelData(hotelsDetailDTO);
                setupNavigationView();
                //setHoteloverview(hotelDetailselHotel);
            }else {
                showAlertDialog("No Rooms Available \nPlease select another Hotel.");
            }
        }


    }

    public void errorreponse (String response){
        hideProgressDialog();
        showAlertDialogforError("Timed out! \nTry again");
    }



    private void initView() {

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //hotelImage=(ImageView)findViewById(R.id.hotelImage);
        hotelRating=(RatingBar)findViewById(R.id.hotelRating);
        hotelName = (TextView)findViewById(R.id.hotelName);

        TextView selectRoomTv = (TextView)findViewById(R.id.selectRoomTv);
        collapsingToolbarLayout.setTitle("Hotel Name");
        hotelname_layout= (LinearLayout) findViewById(R.id.hotel_name_layout);
        //tabLayout= (TabLayout)findViewById(R.id.tab_layout);
        //viewPager= (ViewPager)findViewById(R.id.view_Pager);

        images_pager = (ViewPager) findViewById(R.id.images_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Regular.ttf");

        selectRoomTv.setOnClickListener(this);


        bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.inflateMenu(R.menu.activity_hotel_details);

   /*     for (int i = 0; i < tabLayout.getChildCount(); ++i) {
            View nextChild = tabLayout.getChildAt(i);
            if (nextChild instanceof TextView) {
                TextView textViewToConvert = (TextView) nextChild;
                textViewToConvert.setTypeface(myTypeface);
            }
        }*/




    }



    private void setHotelData(AvailableHotelsDTO hotelsDetailDTO) {
        //Util.showMessage(this,hotelsDetailDTO.getHotelName());
        toolbartitle.setText(hotelsDetailDTO.getHotelName());
        hotelRating.setRating(hotelsDetailDTO.getStarRating());
        hotelName.setText(hotelsDetailDTO.getHotelName());
        //guestDetails.setText(hotelsDetailDTO.getHotelAddress());
        //hotelPolicy.setText(hotelsDetailDTO.getRoomDetails().get(0).getRoomCancellationPolicy());

        ArrayList<String> slidestemp = new ArrayList<>();
        for (int i = 0; i < hotelsDetailDTO.getHotelImages().size(); i++) {
            slidestemp.add(hotelsDetailDTO.getHotelImages().get(i).getImagepath());
        }


        layouts = new String[slidestemp.size()];
        layouts = slidestemp.toArray(layouts);
        init();

       /* Glide.with(this)
                .load(hotelsDetailDTO.getHotelImages().get(0).getImagepath())
                .placeholder(R.drawable.tempbg)
                .error(R.drawable.tempbg)
                .into(hotelImage);*/



        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    hotelname_layout.setVisibility(View.GONE);

                } else if (isShow) {
                    isShow = false;
                    hotelname_layout.setVisibility(View.VISIBLE);



                }
            }
        });



        double Servicetax=Double.valueOf(hotelsDetailDTO.getRoomDetails().get(0).getServicetaxTotal());
        double TotalServiceTax=Servicetax/selDetails.getNoofDays();
        double TotalFare= Double.valueOf(hotelsDetailDTO.getRoomDetails().get(0).getRoomTotal()) + TotalServiceTax;


        Currency_Utils currency_utils=new Currency_Utils(this);
        double currencyValue= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));

        String Total= String.valueOf(Util.getprice(TotalFare*currencyValue));

       Hotel_utils hotel_utils=new Hotel_utils(this);
       hotel_utils.addHotelDetails(hotelsDetailDTO.getHotelName(),
                                   hotelsDetailDTO.getDescription(),
                                   hotelsDetailDTO.getHotelAddress(),
                                   hotelsDetailDTO.getFacilities(),
                                   hotelsDetailDTO.getLatitude(),
                                   hotelsDetailDTO.getLongitude(),
                                   Total,currency_utils.getCurrencyValue("Currency_Symbol"));
        //tab_layout();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

           /* case R.id.amenitiesLayout:
                openPopUpWindow(selHotel);

                break;*/
            case R.id.selectRoomTv:
                Intent i = new Intent(HotelDetailActivity.this, SelectRoomActivityNew.class);
                i.putExtra("SelHotel",hotelDetailselHotel);
                i.putExtra("roomList",roomList);
                i.putExtra("selDetails",selDetails);
                startActivity(i);

                break;


        }
    }


    private void setHoteloverview(AvailableHotelsDTO hoteloverview){

        Bundle bundle=new Bundle();
        bundle.putSerializable("HotelInfo",hoteloverview);

        Hotel_Details hotel_details=new Hotel_Details();
        hotel_details.setArguments(bundle);

        Hotel_Amenities hotel_amenities=new Hotel_Amenities();
        hotel_amenities.setArguments(bundle);
    }




 /*   public  void tab_layout(){
        tabLayout.setupWithViewPager(viewPager);
        Custom_Tabs.wrapTabIndicatorToTitle(tabLayout,10,10);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setupViewPager(viewPager);
    }*/

    private void setupNavigationView() {

        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        changeTypeface(bottomNavigationView);

        if (bottomNavigationView!=null){
            Menu menu=bottomNavigationView.getMenu();
            selectFragment(menu.getItem(0));

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectFragment(item);
                    return false;
                }
            });
        }
    }


    private void selectFragment(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()){
            case R.id.action_overview:
                pushFragment(new Hotel_Details());
                break;
            case R.id.action_amenities:
                pushFragment(new Hotel_Amenities());
                break;
            case R.id.action_location:
                pushFragment(new Hotel_Map());
                break;
        }
    }


    protected void pushFragment(android.app.Fragment fragment) {
        if (fragment == null)
            return;

        android.app.FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {

            if (!isFinishing()){
                android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content, fragment);
                ft.commitAllowingStateLoss();
            }

           /* android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.content, fragment);
                ft.commit();
            }*/
        }
    }

    private void changeTypeface(BottomNavigationView navigationView){
        FontTypeface fontTypeface = new FontTypeface(this);
        Typeface typeface = fontTypeface.getTypefaceAndroid();

        MenuItem item;

        item = navigationView.getMenu().findItem(R.id.action_overview);
        applyFontToItem(item, typeface);
        item = navigationView.getMenu().findItem(R.id.action_amenities);
        applyFontToItem(item, typeface);
        item = navigationView.getMenu().findItem(R.id.action_location);
        applyFontToItem(item, typeface);
    }

    private void applyFontToItem(MenuItem item, Typeface font) {
        SpannableString mNewTitle = new SpannableString(item.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font, 16), 0 ,
                mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        item.setTitle(mNewTitle);
    }



/*    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Hotel_Details(), "Overview");
        adapter.addFragment(new Hotel_Amenities(), "Amenities");
        adapter.addFragment(new Hotel_Map(),"Location");
        viewPager.setAdapter(adapter);





  *//*      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_player_information view with this fragment,
        transaction.add(R.id.content_frame, hotel_details);
        transaction.commit();

        FragmentTransaction transaction1=getSupportFragmentManager().beginTransaction();
        transaction1.add(R.id.content_frame1,hotel_amenities);
        transaction1.commit();*//*
    }*/

/*    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            //addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                //btnNext.setText(getString(R.string.start));
                //btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                //btnNext.setText(getString(R.string.next));
                //btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private int getItem(int i) {
        return images_pager.getCurrentItem() + i;
    }


    private void init() {
        //layouts = new int[]{R.drawable.sliderone,R.drawable.slidertwo,R.drawable.sliderthree,R.drawable.sliderfour};



       /* addBottomDots(0);*/
        myViewPagerAdapter = new MyViewPagerAdapter();
        images_pager.setAdapter(myViewPagerAdapter);
        images_pager.addOnPageChangeListener(viewPagerPageChangeListener);
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                int currentPage = getItem(+1);
                if ( currentPage== myViewPagerAdapter.getCount()) {
                    currentPage = 0;
                }
                images_pager.setCurrentItem(currentPage++, true);
            }
        };


        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);
    }



    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
           /* layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);*/
            ImageView iV = new ImageView(HotelDetailActivity.this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

            iV.setLayoutParams(params);
            iV.setScaleType(ImageView.ScaleType.FIT_XY);
            if(layouts[position]!=null) {
                Glide.with(HotelDetailActivity.this)
                        .load(layouts[position])
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .into(iV);
                //iV.setBackgroundResource(layouts[position]);

            }else{
                Glide.with(HotelDetailActivity.this)
                        .load(R.drawable.image_placeholder)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_placeholder)
                        .into(iV);
            }

            container.addView(iV);

            return iV;
        }





        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive =getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive =getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(HotelDetailActivity.this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
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


    private void showAlertDialog(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        pushFragment(new Hotel_Details());
        bottomNavigationView.getMenu().getItem(0).setChecked(true);


    /*    getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {

                    }
                });*/
    }

    private void showAlertDialogforError(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //alertDialog.setTitle("Alert!");
        alertDialog.setMessage(s);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                callHotelDetails(selHotel);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                   finish();
            }
        });

        alertDialog.show();
    }

}
