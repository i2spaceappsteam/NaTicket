package com.NaTicket.n.common.drawer_items;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.TextView;

import com.NaTicket.n.common.drawer_items.pojo.Dynamic_Data_DTO;
import com.NaTicket.n.loginpackage.pojo.Login_utils;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Nagarjuna on 15-01-2018.
 */

public class Faqs extends BackActivity {
   Login_utils login_utils;
    WebView mwebview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_loading);
        inittoolbar();
        TextView toolbar_title= (TextView) findViewById(R.id.toolbartitle);
        toolbar_title.setText(""+"FAQ's"+"");
        login_utils=new Login_utils(this);
        mwebview= (WebView) findViewById(R.id.webView);
        mwebview.getSettings().setJavaScriptEnabled(true);
        String response=login_utils.getDynamicData(Constants.CONTENT);
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            Dynamic_Data_DTO[] data= gson.fromJson(reader, Dynamic_Data_DTO[].class);
            try {
                setdata(data);
            }catch(Exception e){
                Util.showMessage(this,"No data found");
            }
        }
    }

    private void setdata(Dynamic_Data_DTO[] data){
        for (Dynamic_Data_DTO page : data) {
            if (page.getPage().equals("6")) {
                mwebview.loadDataWithBaseURL("", page.getContentDescription(), "text/html", "UTF-8", "");
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Util.startHomeActivity(Faqs.this);
        finish();
    }
}
