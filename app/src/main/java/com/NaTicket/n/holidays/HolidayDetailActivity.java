package com.NaTicket.n.holidays;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.pojo.Currency_Utils;
import com.NaTicket.n.holidays.pojo.AvailableHolidayPackagesDTO;
import com.NaTicket.n.holidays.pojo.SelectedHolidayDetailsDTO;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Util;
import com.bumptech.glide.Glide;

public class HolidayDetailActivity extends BackActivity implements View.OnClickListener{
    private ImageView hotelImage;
    private RatingBar hotelRating;
    private TextView hotelName,guestDetails,hotelPolicy,toolbartitle,totalAmountTv,duartionTv;
    ImageView backBtn;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    AvailableHolidayPackagesDTO SelHoliday;
    private WebView descwebView,itinerarywebView,inclusionswebView,exclusionswebView,cancelpolicywebView,termswebview,AddInfo;
    private SelectedHolidayDetailsDTO selDetails;
    RatingBar holiday_rating;
    Currency_Utils currency_utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        backBtn= (ImageView) findViewById(R.id.backBtn);
        currency_utils=new Currency_Utils(this);
        setSupportActionBar(toolbar);
        inittoolbar();
        SelHoliday = (AvailableHolidayPackagesDTO) getIntent().getSerializableExtra("SelHoliday");
        selDetails = (SelectedHolidayDetailsDTO) getIntent().getSerializableExtra("selDetails");
        init();
        setHolidayData();
    }

    private void setHolidayData() {
        toolbartitle.setText(SelHoliday.getName());
        guestDetails.setText(SelHoliday.getSubCategory());
        hotelName.setText(SelHoliday.getName());

        double currValue= Double.parseDouble(currency_utils.getCurrencyValue("Currency_Value"));
        String Currency_Symbol=currency_utils.getCurrencyValue("Currency_Symbol");
        double price=Double.valueOf(SelHoliday.getFareDetails().get(0).getAdultFare())*currValue;
        totalAmountTv.setText(Currency_Symbol+ Util.getprice(price)+"");

        duartionTv.setText(" ( "+SelHoliday.getDuration()+ " Nights / "+getDays(SelHoliday.getDuration()) +" Days ) ");
        Glide.with(this)
                .load(SelHoliday.getHolidayPackageImages().get(0).getImagepath())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(hotelImage);
        descwebView.loadData(SelHoliday.getDescription(), "text/html", "UTF-8");
        WebSettings settings = itinerarywebView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        System.out.println("html - "+SelHoliday.getItinerary());
        //itinerarywebView.getSettings().setUseWideViewPort(true);

        String a="<div class='content_wrapper' style='font-family: Arial, Helvetica, sans-serif; border: 0px solid red; font-size: 12px;'><form name='sendspecialsform' method='post' action='http://psa.arzoo.com/guestDetailsPackage.do' id='request'><div class='wrapper search_form' style='border-width: 0px; border-style: solid; border-color: rgb(217, 217, 217) red red; border-image: initial; min-width: 350px; max-width: 998px; margin: auto; padding-bottom: 10px; font-size: 14px; vertical-align: top;'><div class='package_detail_right' style='border: 0px solid red; width: 683px; padding: 5px; margin-left: 5px; float: right; font-size: 12px; line-height: 20px;'><div class='package_info' style='border: 0px solid red;'><div style='border: 0px solid red;'><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td>";

        String c=SelHoliday.getItinerary();


        if(c.contains("a")){
            c=c.replace(a,"");
        }
        itinerarywebView.loadDataWithBaseURL(null,c, "text/html", "utf-8", null);
        //itinerarywebView.loadData(SelHoliday.getItinerary(), "text/html", "charset=UTF-8");

        inclusionswebView.loadData(SelHoliday.getInclusions(), "text/html", "UTF-8");
        exclusionswebView.loadData(SelHoliday.getExclusions(), "text/html", "UTF-8");
        cancelpolicywebView.loadData(SelHoliday.getCancellationPolicy(), "text/html", "UTF-8");
        termswebview.loadData(SelHoliday.getTerms(),"text/html","UTF-8");
        AddInfo.loadData(SelHoliday.getAdditionalInfo(),"text/html","UTF-8");

        int Rating=SelHoliday.getHotelRating();

        if (Rating==51){
            holiday_rating.setRating(0);
        }else if (Rating==52){
            holiday_rating.setRating(1);
        }else if (Rating==53){
            holiday_rating.setRating(2);
        }else if (Rating==54){
            holiday_rating.setRating(3);
        }else if (Rating==55){
            holiday_rating.setRating(4);
        }else if (Rating==56){
            holiday_rating.setRating(5);
        }else{
            holiday_rating.setRating(5);
        }
    }

    private int getDays(int duration) {
        return duration+1;
    }

    private void init() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        hotelImage=(ImageView)findViewById(R.id.hotelImage);
        hotelName = (TextView)findViewById(R.id.hotelName);
        guestDetails=(TextView)findViewById(R.id.guestDetails);
        totalAmountTv=(TextView)findViewById(R.id.totalAmountTv);
        duartionTv=(TextView)findViewById(R.id.duartionTv);
        holiday_rating= (RatingBar) findViewById(R.id.holidayRating);
        descwebView=(WebView)findViewById(R.id.descwebView);
        itinerarywebView=(WebView)findViewById(R.id.itinerarywebView);
        inclusionswebView=(WebView)findViewById(R.id.inclusionswebView);
        exclusionswebView=(WebView)findViewById(R.id.exclusionswebView);
        cancelpolicywebView=(WebView)findViewById(R.id.cancelpolicywebView);
        termswebview= (WebView) findViewById(R.id.termswebview);
        AddInfo= (WebView) findViewById(R.id.addInfowebView);

        collapsingToolbarLayout.setTitle("Hotel Name");
        TextView selectRoomTv = (TextView)findViewById(R.id.selectRoomTv);

        toolbartitle.setTextColor(getResources().getColor(R.color.black));
        backBtn.setColorFilter(getResources().getColor(R.color.black));

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
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
                    //toolbar.setBackgroundResource(R.color.colorPrimary);
                    toolbartitle.setTextColor(getResources().getColor(R.color.colorWhite));
                    backBtn.setColorFilter(getResources().getColor(R.color.colorWhite));

                } else if (isShow) {
                    isShow = false;
                    toolbartitle.setTextColor(getResources().getColor(R.color.black));
                    backBtn.setColorFilter(getResources().getColor(R.color.black));
                    //toolbar.setBackgroundResource(R.color.Transparent);

                }
            }
        });

        selectRoomTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.selectRoomTv:
                Intent i = new Intent(HolidayDetailActivity.this, CheckAvailabilityActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("SelHoliday",SelHoliday);
                i.putExtra("selDetails",selDetails);
                startActivity(i);
                break;


        }
    }

}
