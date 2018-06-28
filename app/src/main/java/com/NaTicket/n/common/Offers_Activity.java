package com.NaTicket.n.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.common.adapter.OffersAdapter;
import com.NaTicket.n.common.pojo.GetPromoCodesDTO;
import com.NaTicket.n.serviceclasses.Service_Promo;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Nagarjuna on 14-01-2018.
 */

public class Offers_Activity extends BackActivity {

    RecyclerView offer_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers);
        inittoolbar();
        TextView toolbar_title= (TextView) findViewById(R.id.toolbartitle);
        toolbar_title.setText(""+"Offers"+"");
        init();
    }

    private void init() {
        offer_view= (RecyclerView) findViewById(R.id.recycler_view);
        callPromoCodes();

    }

    private void callPromoCodes() {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            Service_Promo.getPromocodes(Offers_Activity.this, Constants.GETPROMOCODES);
        }else{
            Util.showMessage(this,Constants.NO_INT_MSG);
        }
    }



    public void getPromoCodesResponse(String response) {
        System.out.println("Offers---"+response);
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            GetPromoCodesDTO[] getPromoCodesDTOs = gson.fromJson(reader, GetPromoCodesDTO[].class);
            if(getPromoCodesDTOs!=null){
                OffersAdapter mAdapter = new OffersAdapter(getPromoCodesDTOs,this);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(Offers_Activity.this);
                offer_view.setLayoutManager(mLayoutManager);
                offer_view.setAdapter(mAdapter);
            }
        }
    }

}


