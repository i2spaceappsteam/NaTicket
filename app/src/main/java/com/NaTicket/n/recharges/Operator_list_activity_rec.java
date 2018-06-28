package com.NaTicket.n.recharges;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.recharges.pojo.Operators_DTO;
import com.NaTicket.n.serviceclasses.ServiceClasses;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Nagarjuna on 1/10/2018.
 */

public class Operator_list_activity_rec extends BackActivity {

    private RecyclerView operators_view;
    private SearchView searchCityView;
    int Category_Type;
    Operators_DTO[] operators_dto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operator_search);

        inittoolbar();
        TextView toolbar_title= (TextView) findViewById(R.id.toolbartitle);
        toolbar_title.setText(""+"Select Operator"+"");
        Intent ip=getIntent();
        Category_Type=ip.getIntExtra("Category",0);
        initView();
    }


    private void initView() {
        //searchCityView = (SearchView) findViewById(R.id.searchCityView);
        //searchCityView.setVisibility(View.GONE);
        operators_view= (RecyclerView) findViewById(R.id.cities_view);
        callCategoriesDeatils(Category_Type);
    }


    private void callCategoriesDeatils(int i) {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            ServiceClasses.getOperatorsrecharge(Operator_list_activity_rec.this, Constants.operatorsurl+i);
        }else{
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    public void getCategoriesResponse(String response) {
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            operators_dto=gson.fromJson(reader, Operators_DTO[].class);
            if(operators_dto!=null && operators_dto.length>0) {
                Operators_adapter_REC mAdapter = new Operators_adapter_REC(operators_dto,this);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
                operators_view.setLayoutManager(mLayoutManager);
                operators_view.setAdapter(mAdapter);
            }
        }
    }



    public void getOperatorData(Operators_DTO operators_dto) {
        // Util.showMessage(this,cityCTO.getCityId()+"");
        Intent intent=new Intent();
        intent.putExtra("OP_NAME",operators_dto.getOperatorName());
        intent.putExtra("OP_ID",operators_dto.getOperatorId());
        intent.putExtra("OP_TYPE",operators_dto.getType());


        setResult(1,intent);
        finish();//finishing activity
    }
}
