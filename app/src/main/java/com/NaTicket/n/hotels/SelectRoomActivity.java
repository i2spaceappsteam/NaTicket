package com.NaTicket.n.hotels;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.NaTicket.n.hotels.pojo.Room;
import com.google.gson.Gson;
import com.NaTicket.n.R;
import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.hotels.pojo.SelectionDetailsDTO;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Constants;
import com.NaTicket.n.utils.Util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class SelectRoomActivity extends BackActivity   {
    private AvailableHotelsDTO selHotel;
    private LinearLayout roomSelLinear;
    ArrayList<Room> roomList;
    private ProgressDialog mProgressDialog;
    private SelectionDetailsDTO selDetails;
    private int selectedID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room);
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        inittoolbar();
        toolbartitle.setText("Select Room");
       selHotel = (AvailableHotelsDTO) getIntent().getSerializableExtra("SelHotel");
        roomList = (ArrayList<Room>) getIntent().getSerializableExtra("roomList");
        selDetails = (SelectionDetailsDTO) getIntent().getSerializableExtra("selDetails");
        roomSelLinear =(LinearLayout)findViewById(R.id.roomSelLinear);
        TextView proceedToBackTv =(TextView)findViewById(R.id.proceedToBackTv);
        proceedToBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedID!=0) {
                    selDetails.setSelectedRoom(selectedID+"");
                    Intent i = new Intent(SelectRoomActivity.this, ReviewActivity.class);
                    i.putExtra("roomList", roomList);
                    i.putExtra("SelHotel", selHotel);
                    i.putExtra("selDetails", selDetails);
                    startActivity(i);
                }else{
                    Util.showMessage(SelectRoomActivity.this,"Select A Room");
                }
            }
        });
        callHotelDetails(selHotel);

    }

    private void callHotelDetails(AvailableHotelsDTO selHotel) {
        if(Util.isNetworkAvailable(getApplicationContext())) {
            showProgressDialog("Fetching Room Details...",SelectRoomActivity.this);
            String GETHOTELDETAILS = Constants.BASEURL+"Hotels/HotelDetails?hotelId="+selHotel.getHotelId()+"&webService="+selHotel.getWebService()+"&cityId="+selDetails.getCityId()+"&provider="+selHotel.getProvider()+"&adults="+getAdults()+"&children="+getChilds()+"&arrivalDate="+selDetails.getCheckIn()+"&departureDate="+selDetails.getCheckOut()+"&noOfDays="+selDetails.getNoofDays()+"&childrenAges=-1~-1~-1~-1~-1~-1~-1~-1&userType=5&hoteltype=2&user=X0A+GzvPr0djCm7Y9WohEA==&roomscount="+roomList.size()+"&secondaryProvider="+selHotel.getSecondaryProvider();
            //ServiceHandler.getHotelDeatils(SelectRoomActivity.this, GETHOTELDETAILS);
        }else{
            Util.showMessage(this, Constants.NO_INT_MSG);
        }
    }

    private String getChilds() {
        String adultsstr="";
        for (int i=0;i<4;i++){
            if(i<roomList.size()) {
                adultsstr = adultsstr + roomList.get(i).getChild();

            }else{
                adultsstr=adultsstr+"0";
            }
            if(!(i==3)){
                adultsstr=adultsstr+"~";
            }
        }
        return adultsstr;
    }

    private String getAdults() {
        String adultsstr="";
        for (int i=0;i<4;i++){
            if(i<roomList.size()) {
                adultsstr = adultsstr + roomList.get(i).getAdults();

            }else{
                adultsstr=adultsstr+"0";
            }
            if(!(i==3)){
                adultsstr=adultsstr+"~";
            }
        }
        return adultsstr;
    }


    public void getHotelDetailsResponse(String response) {
        hideProgressDialog();
        if(response!=null) {
            InputStream stream = new ByteArrayInputStream(response.getBytes());
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(stream);
            AvailableHotelsDTO hotelsDetailDTO = gson.fromJson(reader, AvailableHotelsDTO.class);
            if(hotelsDetailDTO!=null){
                //Util.showMessage(getApplicationContext(),hotelsDetailDTO.getRoomDetails().get(0).getRoomTotal());
                createSelections(hotelsDetailDTO);
            }
        }


    }

    private void createSelections(AvailableHotelsDTO hotelsDetailDTO) {

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Regular.ttf");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 20, 0, 0);
         //String roomChain = "1,2|3,4|5,6";
        String roomChain = hotelsDetailDTO.getRoomChain();
        if (hotelsDetailDTO.getRoomCombination()!=null && hotelsDetailDTO.getRoomCombination().matches("OPENCOMBINATION")) {
            if (roomChain.contains("|")) {
                String[] pipeSplits = roomChain.split("\\|");
                RadioGroup[] rg = new RadioGroup[pipeSplits.length];
                for (int i = 0; i < pipeSplits.length; i++) {
                    rg[i] = new RadioGroup(this); //create the RadioGroup
                    rg[i].setOrientation(RadioGroup.VERTICAL);
                    rg[i].setBackgroundResource(R.drawable.backgroundforrg);
                    rg[i].setLayoutParams(params);
                    rg[i].setPadding(10,10,10,10);
                    String[] commaSplits = pipeSplits[i].split(",");
                    RadioButton[] rb = new RadioButton[commaSplits.length];
                    for (int j = 0; j < commaSplits.length; j++) {
                        rb[j] = new RadioButton(this);
                        //rb[j].setText("radio" + commaSplits[j]);
                        rb[j].setId(hotelsDetailDTO.getRoomDetails().get(j).getRoomIndex());
                        rb[j].setText(hotelsDetailDTO.getRoomDetails().get(j).getRoomType());
                        rb[j].setTypeface(myTypeface);
                        rg[i].addView(rb[j]);
                    }
                    rg[i].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                            //Util.showMessage(SelectRoomActivity.this,checkedId+"");
                        }
                    });
                    //rg.removeAllViews();
                    roomSelLinear.addView(rg[i]);

                }
            } else {
                RadioGroup rg = new RadioGroup(this); //create the RadioGroup
                rg.setOrientation(RadioGroup.VERTICAL);
                rg.setLayoutParams(params);//or RadioGroup.VERTICAL
                rg.setPadding(10,10,10,10);
                String[] commaSplits = roomChain.split(",");
                RadioButton[] rb = new RadioButton[commaSplits.length];
                for (int j = 0; j < commaSplits.length; j++) {
                    rb[j] = new RadioButton(this);
                    //rb[j].setText("radio" + commaSplits[j]);
                    rb[j].setId(hotelsDetailDTO.getRoomDetails().get(j).getRoomIndex());
                    rb[j].setText(hotelsDetailDTO.getRoomDetails().get(j).getRoomType() + "-" + hotelsDetailDTO.getRoomDetails().get(j).getRoomTotal());
                    rb[j].setTypeface(myTypeface);
                    rg.addView(rb[j]);
                }
                //rg.removeAllViews();
                roomSelLinear.addView(rg);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    //    Util.showMessage(SelectRoomActivity.this,checkedId+"");
                        selectedID=checkedId;
                    }
                });
            }
        } else if (hotelsDetailDTO.getRoomCombination()!=null && hotelsDetailDTO.getRoomCombination().matches("FIXEDCOMBINATION")) {
            String[] pipeSplits = roomChain.split("\\|");
            RadioGroup rg = new RadioGroup(this);
            rg.setOrientation(RadioGroup.VERTICAL);

            rg.setBackgroundResource(R.drawable.backgroundforrg);
            rg.setLayoutParams(params);
            rg.setPadding(10,10,10,10);
            RadioButton[] rb = new RadioButton[pipeSplits.length];
            for (int i = 0; i < pipeSplits.length; i++) {
                rb[i] = new RadioButton(this);
                String[] commaSplits = pipeSplits[i].split(",");
                String msg = "";
                for (int j = 0; j < commaSplits.length; j++) {
                    msg = msg + hotelsDetailDTO.getRoomDetails().get(j).getRoomType();;

                }
                rb[i].setText(msg);
                rb[i].setTypeface(myTypeface);
                rg.addView(rb[i]);
                //rg.removeAllViews();



            }
            roomSelLinear.addView(rg);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    Util.showMessage(SelectRoomActivity.this,checkedId+"");
                }
            });


        }
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

}
