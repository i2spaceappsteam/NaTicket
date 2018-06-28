package com.NaTicket.n.hotels;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.NaTicket.n.hotels.pojo.AdultsDTO;
import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;
import com.NaTicket.n.hotels.pojo.ChildDTO;
import com.NaTicket.n.hotels.pojo.Room;
import com.NaTicket.n.hotels.pojo.RoomWithDetailsDTO;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.R;
import com.NaTicket.n.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class AddDetailsActivity extends BackActivity {
    private AvailableHotelsDTO selHotel;
    ArrayList<Room> roomList;
    LinearLayout detailsLinear,parentLayout;
    ArrayList<RoomWithDetailsDTO> roomWithDetailsList;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        TextView toolbartitle=(TextView)findViewById(R.id.toolbartitle);
        inittoolbar();
        toolbartitle.setText("Add Guest Details");
        selHotel = (AvailableHotelsDTO) getIntent().getSerializableExtra("SelHotel");
        roomList = (ArrayList<Room>) getIntent().getSerializableExtra("roomList");
        detailsLinear = (LinearLayout)findViewById(R.id.detailsLinear);
        parentLayout=(LinearLayout)findViewById(R.id.parentLayout);
        roomWithDetailsList =new ArrayList<RoomWithDetailsDTO>();
        TextView savecontinue = (TextView)findViewById(R.id.savecontinue);
        savecontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkAllValues()){
                    Util.showMessage(AddDetailsActivity.this,"Enter all the Guest Details");

                }else{
                    //Util.showMessage(AddDetailsActivity.this,"good");
                    getAllNames();
                }
            }
        });
        createNoofRooms();
    }

    private void getAllNames() {
        String totalNames="";
        String totalAges="";
        String totalGenders="";
        for(int i=0;i<roomWithDetailsList.size();i++) {
            String adultschildNames="";
            String adultschildAges="";
            String adultschildGenders="";
            for (int j=0;j<roomWithDetailsList.get(i).getAdults().size();j++){
                adultschildNames=adultschildNames+roomWithDetailsList.get(i).getAdults().get(j).getSurName()+"|"+roomWithDetailsList.get(i).getAdults().get(j).getFirstName()+"|"+roomWithDetailsList.get(i).getAdults().get(j).getLastName()+"|"+"adt";
                adultschildAges=adultschildAges+roomWithDetailsList.get(i).getAdults().get(j).getAge();
                adultschildGenders=adultschildGenders+roomWithDetailsList.get(i).getAdults().get(j).getGender();
                if(!(j+1==roomWithDetailsList.get(i).getAdults().size())) {
                    adultschildNames = adultschildNames + "~";
                    adultschildAges = adultschildAges+"~";
                    adultschildGenders=adultschildGenders+"~";
                }
            }
            for (int j=0;j<roomWithDetailsList.get(i).getChilds().size();j++){
                adultschildNames=adultschildNames+"~";
                adultschildAges = adultschildAges+"~";
                adultschildGenders=adultschildGenders+"~";
                adultschildNames=adultschildNames+"Mstr."+"|"+roomWithDetailsList.get(i).getChilds().get(j).getFirstName()+"|"+roomWithDetailsList.get(i).getChilds().get(j).getLastName()+"|"+"chd";
                adultschildAges=adultschildAges+roomWithDetailsList.get(i).getChilds().get(j).getAge();
                adultschildGenders=adultschildGenders+roomWithDetailsList.get(i).getChilds().get(j).getGender();
                if(!(j+1==roomWithDetailsList.get(i).getChilds().size())) {
                    adultschildNames = adultschildNames + "~";
                    adultschildAges = adultschildAges+"~";
                    adultschildGenders=adultschildGenders+"~";
                }
            }
            totalNames=totalNames+adultschildNames;
            totalAges =totalAges+adultschildAges;
            totalGenders=totalGenders+adultschildGenders;

            if(!(i+1==roomWithDetailsList.size())) {
                totalNames = totalNames + "-";
                totalAges =totalAges+"-";
                totalGenders=totalGenders+"-";
            }
        }
        Intent intent=new Intent();
        String newages=totalAges.replace("~~","~");
        String newNames=totalNames.replace("~~","~");
        String newGenders=totalGenders.replace("~~","~");
        System.out.println("new Ages==="+newages);
        intent.putExtra("totalNames",newNames);
        intent.putExtra("totalAges",newages);
        intent.putExtra("totalGenders",newGenders);
        setResult(2,intent);
        finish();
        Log.d("mssg",totalNames);
        Log.d("mssg",totalAges);
        Log.d("Genders",totalGenders);
    }

    private boolean checkAllValues() {
        for(int i=0;i<roomWithDetailsList.size();i++) {
            for (int j=0;j<roomWithDetailsList.get(i).getAdults().size();j++){
                if(roomWithDetailsList.get(i).getAdults().get(j).getFirstName()==null){
                    return false;
                }
            }
        }
        return true;
    }

    private void createNoofRooms() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 20, 0, 0);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Regular.ttf");

        for(int i=0;i<roomList.size();i++){
            RoomWithDetailsDTO roomWithDetailsDTO = new RoomWithDetailsDTO();
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setBackgroundResource(R.drawable.backgroundforrg);
            ll.setLayoutParams(params);
            //ll.setPadding(10,10,10,10);
            TextView room = new TextView(this);
            room.setText(" Room"+ (i+1));
            room.setTextSize(getResources().getDimension(R.dimen.room_text));
            room.setTextColor(getResources().getColor(R.color.mdtp_white));
            room.setBackgroundResource(R.drawable.backgroundforroom);
            room.setPadding(5,5,5,5);
            room.setTypeface(myTypeface);
            ll.addView(room);
            ArrayList<AdultsDTO> adultsDTOList= new ArrayList<AdultsDTO>();
            ArrayList<ChildDTO> childDTOList= new ArrayList<ChildDTO>();

            for (int j=0;j<roomList.get(i).getAdults();j++){
                final AdultsDTO adultsDTO = new AdultsDTO();
                final TextView adult = new TextView(this);
                adult.setText(" Adult "+ (j+1));
                adult.setTypeface(myTypeface);
                adult.setTextColor(getResources().getColor(R.color.black));
                adult.setPadding(5,5,5,5);
                adult.setTextSize(getResources().getDimension(R.dimen.adult_text));
                ll.addView(adult);
                adult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                                openAdultInfoPopUp(adultsDTO,adult);
                    }
                });
                adultsDTOList.add(adultsDTO);
            }
            for (int j=0;j<roomList.get(i).getChild();j++){
                final ChildDTO childDTO = new ChildDTO();
                final TextView child = new TextView(this);
                child.setText(" Child "+ (j+1));
                child.setTypeface(myTypeface);
                child.setTextColor(getResources().getColor(R.color.black));
                child.setPadding(5,5,5,5);
                child.setTextSize(getResources().getDimension(R.dimen.adult_text));
                ll.addView(child);
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openChildInfoPopUp(childDTO,child);
                    }
                });

                childDTOList.add(childDTO);
            }
            roomWithDetailsDTO.setAdults(adultsDTOList);
            roomWithDetailsDTO.setChilds(childDTOList);
            detailsLinear.addView(ll);
            roomWithDetailsList.add(roomWithDetailsDTO);
        }
    }

    private void openChildInfoPopUp(final ChildDTO adultsDTO,final TextView adultTv) {
        List<String> categories = new ArrayList<String>();
        categories.add("Mr.");
        categories.add("Ms.");
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popup_layout_child,null);
        ImageView closePopup = (ImageView)customView.findViewById(R.id.closePopup);
        final Spinner surname = (Spinner)customView.findViewById(R.id.surname);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddDetailsActivity.this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        surname.setAdapter(dataAdapter);
        final EditText firstName =(EditText) customView.findViewById(R.id.firstName);
        final EditText lastName =(EditText) customView.findViewById(R.id.lastName);
        final EditText age =(EditText) customView.findViewById(R.id.age);
        if(adultsDTO.getSurName()!=null){
            int spinnerPosition = dataAdapter.getPosition(adultsDTO.getSurName());
            surname.setSelection(spinnerPosition);
        }
        if(adultsDTO.getFirstName()!=null){
            firstName.setText(adultsDTO.getFirstName());
        }else{
            firstName.setText("");
        }
        if(adultsDTO.getLastName()!=null){
            lastName.setText(adultsDTO.getLastName());
        }else{
            lastName.setText("");
        }
        if(adultsDTO.getAge()!=null){
            age.setText(adultsDTO.getAge());
        }else{
            age.setText("");
        }
        TextView save = (TextView)customView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Util.checkField(firstName)){
                    Util.showMessage(AddDetailsActivity.this,"Please fill FirstName");

                }else if(!Util.checkField(lastName)) {
                    Util.showMessage(AddDetailsActivity.this, "Please fill LastName");

                }else if(!Util.checkField(age)) {
                    Util.showMessage(AddDetailsActivity.this, "Please fill Age");

                }else{
                    adultsDTO.setSurName(surname.getSelectedItem().toString());
                    adultsDTO.setFirstName(firstName.getText().toString());
                    adultsDTO.setLastName(lastName.getText().toString());
                    adultsDTO.setAge(age.getText().toString());
                    adultsDTO.setGender("M");

                    adultTv.setText(surname.getSelectedItem().toString()+"|"+firstName.getText().toString()+"|"+lastName.getText().toString()+"|"+age.getText().toString());
                    mPopupWindow.dismiss();
                }

            }
        });
        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow = new PopupWindow(
                customView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }
        mPopupWindow.setFocusable(true);
        mPopupWindow.update();
        if (!isFinishing()) {
            mPopupWindow.showAtLocation(parentLayout, Gravity.CENTER,0,0);
        }

    }

    private void openAdultInfoPopUp(final AdultsDTO adultsDTO,final TextView adultTv) {
        List<String> categories = new ArrayList<String>();
        categories.add("Mr.");
        categories.add("Ms.");
        categories.add("Mrs.");

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popup_layout_adult,null);
        ImageView closePopup = (ImageView)customView.findViewById(R.id.closePopup);
       final Spinner surname = (Spinner)customView.findViewById(R.id.surname);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddDetailsActivity.this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        surname.setAdapter(dataAdapter);
        final EditText firstName =(EditText) customView.findViewById(R.id.firstName);
        final EditText lastName =(EditText) customView.findViewById(R.id.lastName);
        final EditText age =(EditText) customView.findViewById(R.id.age);
        if(adultsDTO.getSurName()!=null){
            int spinnerPosition = dataAdapter.getPosition(adultsDTO.getSurName());
             surname.setSelection(spinnerPosition);
        }
        if(adultsDTO.getFirstName()!=null){
            firstName.setText(adultsDTO.getFirstName());
        }else{
            firstName.setText("");
        }
        if(adultsDTO.getLastName()!=null){
            lastName.setText(adultsDTO.getLastName());
        }else{
            lastName.setText("");
        }
        if(adultsDTO.getAge()!=null){
            age.setText(adultsDTO.getAge());
        }else{
            age.setText("");
        }
        TextView save = (TextView)customView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Util.checkField(firstName)){
                    Util.showMessage(AddDetailsActivity.this,"Please fill FirstName");

                }else if(!Util.checkField(lastName)) {
                    Util.showMessage(AddDetailsActivity.this, "Please fill LastName");

                }else if(!Util.checkField(age)) {
                    Util.showMessage(AddDetailsActivity.this, "Please fill Age");

                }else{
                    adultsDTO.setSurName(surname.getSelectedItem().toString());
                    adultsDTO.setFirstName(firstName.getText().toString());
                    adultsDTO.setLastName(lastName.getText().toString());
                    adultsDTO.setAge(age.getText().toString());
                    adultsDTO.setGender("M");

                  /*  if (surname.getSelectedItem().toString().matches("Mr.")){
                        adultsDTO.setGender("M");
                    }else {
                        adultsDTO.setGender("F");
                    }*/


                    adultTv.setText(surname.getSelectedItem().toString()+"|"+firstName.getText().toString()+"|"+lastName.getText().toString()+"|"+age.getText().toString());
                    mPopupWindow.dismiss();
                }

            }
        });
        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow = new PopupWindow(
                customView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }
            mPopupWindow.setFocusable(true);
            mPopupWindow.update();
        if (!isFinishing()) {
            mPopupWindow.showAtLocation(parentLayout, Gravity.CENTER,0,0);
        }

    }
}
