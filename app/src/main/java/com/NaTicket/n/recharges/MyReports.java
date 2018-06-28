package com.NaTicket.n.recharges;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by Administrator on 09-03-2016.
 */
public class MyReports extends AppCompatActivity {
    ListView reportslist;

    HorizontalScrollView scrollView;
    LinearLayout vertical;
    LinearLayout horizotal;

    TextView psaname,amount,refno,source,destination,status;
    int listWidth, listHeight, listWidthParams, listHeightParams,
            locationHeightParams;
    int deviceWidth,deviceHeight,textHeight;
    int textwidth;
    SharedPreferences pref1;
    SharedPreferences.Editor editor1;

    RechargeReportsAdapter rechargeReportsAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display deviceDisplay=((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        deviceWidth = deviceDisplay.getWidth();//480
        deviceHeight = deviceDisplay.getHeight();//800
        textHeight=(int)(deviceHeight*10/133.3333);//60
        textwidth=(deviceWidth);

        pref1 =getSharedPreferences("ServiceId", 0);
        editor1=pref1.edit();





        scrollView=new HorizontalScrollView(this);
        HorizontalScrollView.LayoutParams scrLayoutParams=new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scrLayoutParams);

        vertical=new LinearLayout(this);
        LinearLayout.LayoutParams verticalParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        vertical.setOrientation(LinearLayout.VERTICAL);
        vertical.setBackgroundColor(Color.WHITE);
        vertical.setLayoutParams(verticalParams);


        horizotal=new LinearLayout(this);
        LinearLayout.LayoutParams horizontalParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        horizotal.setOrientation(LinearLayout.HORIZONTAL);
        horizotal.setLayoutParams(horizontalParams);

        psaname=new TextView(this);
        LinearLayout.LayoutParams psaParams=new LinearLayout.LayoutParams(textwidth,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        psaname.setText(" Transcation Report ");

        psaname.setTextColor(Color.BLACK);
        psaParams.setMargins(deviceWidth / 3, 0, 0, 0);
        psaname.setPadding(10, 0, 0, 0);
        psaname.setLayoutParams(psaParams);





        status=new TextView(this);
        LinearLayout.LayoutParams statusParams=new LinearLayout.LayoutParams(textwidth,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        statusParams.setMargins(textwidth / 2, 0, 0, 0);
        status.setText("Amount \nBooking Status");
        status.setTextColor(Color.BLACK);
        status.setPadding(10, 0, 0, 0);
        status.setLayoutParams(statusParams);


        destination=new TextView(this);
        LinearLayout.LayoutParams destinationParams=new LinearLayout.LayoutParams(textwidth,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        destinationParams.setMargins(20, 0, 0, 0);
        destination.setText("User Name\nRecharge Number");
        destination.setTextColor(Color.BLACK);
        destination.setPadding(10, 0, 0, 0);
        destination.setLayoutParams(destinationParams);









        reportslist=new ListView(this);
        LinearLayout.LayoutParams reportsParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);


   //     rechargeReportsAdapter = new RechargeReportsAdapter(MyReports.this, Recharge_MainActivity.reportsBeanArrayList);

        reportslist.setPadding(10, 0, 0, 0);

        reportslist.setAdapter((ListAdapter) rechargeReportsAdapter);
        reportslist.setLayoutParams(reportsParams);
        reportslist.getContext();

        setContentView(vertical);

        vertical.addView(horizotal);

        horizotal.addView(psaname);

        /*horizotal.addView(destination);
        horizotal.addView(status);*/
        vertical.addView(reportslist);





        //  relativeLayout.addView(reportslist);
    }



}

