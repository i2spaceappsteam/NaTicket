package com.NaTicket.n.flights;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.common.MainActivity;
import com.NaTicket.n.custom.FontTypeface;
import com.NaTicket.n.flights.pojo.SelectedFlightDetailsDTO;
import com.NaTicket.n.utils.BackActivity;
import com.NaTicket.n.utils.Util;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Nagarjuna on 12/29/2017.
 */

public class Flights_Search_Activity extends BackActivity implements RadioGroup.OnCheckedChangeListener,DatePickerDialog.OnDateSetListener,View.OnClickListener {

    int cur = 0;
    DatePickerDialog datePickerDialog;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    Calendar  calendar,cal1,cal2,cal4,cal5;
    TextView  onward_date_tv,return_date_tv;
    int d,m,y;
    int Year, Month,Day ;
    TextView  searchFlights,adults,children,infants;
    TextView  travelClassType,fromPlaceName, toPlaceName;
    TextView  okButton,cancelButton;
    ImageView swap;

    RadioButton onewayradio,roundtripradio;
    RadioGroup radio_group;
    LinearLayout fromLinearLayout, toLinearLayout, travellersLayout,ArrivalCity,DestinationCity;
    NumberPicker n1,n2,n3;
    int adultsCountF = 1, childrenCountF = 0, infantsCountF = 0;
    int adultsCount = 1, childrenCount = 0, infantsCount = 0;
    String totalTravelersCount;
    String flightType="1",TripType="2",City_Type;
    ArrayList<String> classtype_array=new ArrayList<>();
    String Class_Type="Economy",Class_Type_Value="E";
    TextView domestic,international;
    String FromCity_Name,ToCity_Name,From_Country,To_Country,From_Airport,To_Airport;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search);
        init();
        setCurrentDateOnView();
        TextView toolbartitle = (TextView) findViewById(R.id.toolbartitle);
        toolbartitle.setText("Flights");
        ImageView ToolBar_Back= (ImageView) findViewById(R.id.backBtn);
        ToolBar_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Util.startHomeActivity(Flights_Search_Activity.this);
            }
        });
    }


    private void init(){

        swap= (ImageView) findViewById(R.id.swapButton);
        radio_group = (RadioGroup)findViewById(R.id.radio_group);
        onewayradio= (RadioButton) findViewById(R.id.oneway_radio);
        roundtripradio= (RadioButton) findViewById(R.id.roundtrip_radio);
        onward_date_tv = (TextView) findViewById(R.id.departonName);
        return_date_tv = (TextView) findViewById(R.id.ArrivalName);
        fromLinearLayout = (LinearLayout) findViewById(R.id.departLayout);
        toLinearLayout = (LinearLayout) findViewById(R.id.toLinearLayout);
        travellersLayout = (LinearLayout) findViewById(R.id.travellersLayout);
        travelClassType = (TextView) findViewById(R.id.EconomyClassesName);
        adults= (TextView) findViewById(R.id.adults_count);
        children= (TextView) findViewById(R.id.children_count);
        infants= (TextView) findViewById(R.id.infant_count);
        ArrivalCity = (LinearLayout) findViewById(R.id.fromLinearLayout);
        DestinationCity = (LinearLayout) findViewById(R.id.toPlaceLinearLayout);
        fromPlaceName = (TextView) findViewById(R.id.fromPlaceName);
        toPlaceName = (TextView) findViewById(R.id.toPlaceName);
        searchFlights = (TextView) findViewById(R.id.search);

        domestic = (TextView) findViewById(R.id.domesticButtom);
        international = (TextView) findViewById(R.id.internationalButton);

        fromLinearLayout.setOnClickListener(this);
        toLinearLayout.setOnClickListener(this);
        travellersLayout.setOnClickListener(this);
        travelClassType.setOnClickListener(this);
        ArrivalCity.setOnClickListener(this);
        DestinationCity.setOnClickListener(this);
        searchFlights.setOnClickListener(this);
        radio_group.setOnCheckedChangeListener(this);
        domestic.setOnClickListener(this);
        international.setOnClickListener(this);
        searchFlights.setOnClickListener(this);
        swap.setOnClickListener(this);

        classtype_array.add("Economy");
        classtype_array.add("Business");
        classtype_array.add("First");
        classtype_array.add("Premium Economy");

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.swapButton:

                if(!fromPlaceName.getText().toString().equals("Origin")&&!toPlaceName.getText().toString().equals("Destination")) {
                    String fname = fromPlaceName.getText().toString();
                    String dname = toPlaceName.getText().toString();

                    String Fcityname = FromCity_Name;
                    String Tcityname = ToCity_Name;
                    String Fcountryname = From_Country;
                    String Tcountryname = To_Country;
                    String Fairportname = From_Airport;
                    String Tairportname = To_Airport;


                    fromPlaceName.setText(dname);
                    toPlaceName.setText(fname);

                    FromCity_Name = Tcityname;
                    ToCity_Name = Fcityname;
                    From_Country = Tcountryname;
                    To_Country = Fcountryname;
                    From_Airport = Tairportname;
                    To_Airport = Fairportname;
                }


                break;
            case R.id.fromLinearLayout:
                Intent intent = new Intent(this, Flights_City_Search_Activity.class);
                City_Type="1";
                intent.putExtra("FlightType", flightType);
                intent.putExtra("CityType", City_Type);
                startActivityForResult(intent, 2);
                break;
            case R.id.toPlaceLinearLayout:
                Intent intent2 = new Intent(this, Flights_City_Search_Activity.class);
                City_Type="2";
                intent2.putExtra("FlightType", flightType);
                intent2.putExtra("CityType", City_Type);
                startActivityForResult(intent2, 3);
                break;
            case R.id.EconomyClassesName:
                getTravelClassType();
                break;
            case R.id.travellersLayout:
                getTravellers();
                break;
            case R.id.departLayout:
                cur = DATE_DIALOG_ID;
                datePickerDialog = DatePickerDialog.newInstance(Flights_Search_Activity.this, Year, Month, Day);
                cal1 = Calendar.getInstance();
                cal1.add(Calendar.DAY_OF_YEAR, 0);
                cal2=Calendar.getInstance();
                cal2.add(Calendar.DAY_OF_YEAR,365);
                datePickerDialog.setMinDate(cal1);
                datePickerDialog.setMaxDate(cal2);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Onward Date");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                break;
            case R.id.toLinearLayout:
                cur = DATE_DIALOG_ID2;
                datePickerDialog = DatePickerDialog.newInstance(Flights_Search_Activity.this, Year, Month, Day);
                cal4=Calendar.getInstance();
                cal4.add(Calendar.DAY_OF_YEAR,365);
                cal5=Calendar.getInstance();
                cal5.set(y,m,d);
                cal5.add(Calendar.DAY_OF_YEAR,0);
                datePickerDialog.setMinDate(cal5);
                datePickerDialog.setMaxDate(cal4);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Return Date");
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                break;
            case R.id.domesticButtom:
                domestic.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_left));
                domestic.setTextColor(getResources().getColor(R.color.white));
                international.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_right));
                international.setTextColor(getResources().getColor(R.color.colorPrimary));
                international.setText(getResources().getString(R.string.international));
                fromPlaceName.setText("Origin");
                toPlaceName.setText("Destination");
                flightType = "1";
                break;
            case R.id.internationalButton:
                international.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_reverse_right));
                international.setTextColor(getResources().getColor(R.color.white));
                domestic.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_reverse_left));
                domestic.setText(getResources().getString(R.string.domestic));
                domestic.setTextColor(getResources().getColor(R.color.colorPrimary));
                fromPlaceName.setText("Origin");
                toPlaceName.setText("Destination");
                flightType = "2";
                break;
            case R.id.search:
                if (validateValues()){
                    SelectedFlightDetailsDTO SelDetails=new SelectedFlightDetailsDTO();
                    SelDetails.setTrip_Type(TripType);
                    SelDetails.setFlight_Type(flightType);
                    SelDetails.setadult_count(Integer.parseInt(adults.getText().toString()));
                    SelDetails.setchild_count(Integer.parseInt(children.getText().toString()));
                    SelDetails.setinfant_count(Integer.parseInt(infants.getText().toString()));
                    SelDetails.setclass_type(Class_Type);
                    SelDetails.setClass_type_value(Class_Type_Value);
                    SelDetails.setOnward_date(onward_date_tv.getText().toString());
                    SelDetails.setFrom_City_Name(FromCity_Name);
                    SelDetails.setTo_City_Name(ToCity_Name);
                    SelDetails.setFrom_City_ID(fromPlaceName.getText().toString());
                    SelDetails.setTo_City_ID(toPlaceName.getText().toString());
                    SelDetails.setFrom_City_Full_Name(SelDetails.getFrom_City_Name()+", "+
                                                      From_Country+" - ("+SelDetails.getFrom_City_ID()+")-"+From_Airport);
                    SelDetails.setTo_City_Full_Name(SelDetails.getTo_City_Name()+", "+
                                                    To_Country+"- ("+SelDetails.getTo_City_ID()+")-"+To_Airport);
                    if (SelDetails.getFlight_Type().equals("1")){
                        if (SelDetails.getTrip_Type().equals("2")){
                            SelDetails.setReturn_date(return_date_tv.getText().toString());
                        }else {
                            SelDetails.setReturn_date(null);
                        }
                        Intent i = new Intent(Flights_Search_Activity.this,Flights_Domestic_Onward.class);
                        i.putExtra("selDetails",SelDetails);
                        startActivity(i);
                    }else {
                        if (SelDetails.getTrip_Type().equals("2")){
                            SelDetails.setReturn_date(return_date_tv.getText().toString());
                            Intent i = new Intent(Flights_Search_Activity.this,Flights_International_Return.class);
                            i.putExtra("selDetails",SelDetails);
                            startActivity(i);
                        }else {
                            SelDetails.setReturn_date(null);
                            Intent i = new Intent(Flights_Search_Activity.this,Flights_International_Onward.class);
                            i.putExtra("selDetails",SelDetails);
                            startActivity(i);
                        }
                    }

                }
                break;

        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==2){
            if (data!=null){
                fromPlaceName.setText(""+data.getStringExtra("CITY_ID")+"");
                FromCity_Name=data.getStringExtra("CITY_NAME");
                From_Country=data.getStringExtra("COUNTRY_NAME");
                From_Airport=data.getStringExtra("AIRPORT_NAME");
            }
        }else if (requestCode==3){
            if (data!=null){
                toPlaceName.setText(""+data.getStringExtra("CITY_ID")+"");
                ToCity_Name=data.getStringExtra("CITY_NAME");
                To_Country=data.getStringExtra("COUNTRY_NAME");
                To_Airport=data.getStringExtra("AIRPORT_NAME");
            }
        }
    }


    public void getTravelClassType() {
        final BottomSheetDialog dialog=new BottomSheetDialog(Flights_Search_Activity.this);
        dialog.setContentView(R.layout.flights_travel_classtype);
        dialog.setTitle("Class Details");
        LinearLayout classlayout= (LinearLayout)dialog.findViewById(R.id.class_layout);
        TextView[] myTextViews = new TextView[classtype_array.size()];
        for(int i=0;i<classtype_array.size();i++){
            final String class_name =classtype_array.get(i);
            final TextView rowTextView = new TextView(Flights_Search_Activity.this);
            rowTextView.setText(""+class_name+"");
            classlayout.addView(rowTextView);
            myTextViews[i] = rowTextView;
            FontTypeface fontTypeface=new FontTypeface(this);
            rowTextView.setTypeface(fontTypeface.getTypefaceAndroid());
            rowTextView.setPadding(15,15,15,15);
            rowTextView.setTextSize(18);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
               rowTextView.setLayoutParams(lp);

            rowTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Class_Type=rowTextView.getText().toString();
                    switch (Class_Type) {
                        case "Economy":
                            Class_Type_Value = "E";
                            break;
                        case "Business":
                            Class_Type_Value = "B";
                            break;
                        case "First":
                            Class_Type_Value = "F";
                            break;
                        case "Premium Economy":
                            Class_Type_Value = "ER";
                            break;
                    }

                    dialog.dismiss();
                    travelClassType.setText(Class_Type);
                }
            });
        }
        dialog.show();
    }


    public void getTravellers(){
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.flight_travellers_dialog);

        n1= (NumberPicker) dialog.findViewById(R.id.numberPickerAdult);
        assert n1 != null;
        n1.setMaxValue(9);
        n1.setMinValue(1);
        n1.setValue(adultsCountF);
        n1.setWrapSelectorWheel(false);

        n2= (NumberPicker) dialog.findViewById(R.id.numberPickerChildren);
        assert n2 != null;
        n2.setMaxValue(9);
        n2.setMinValue(0);
        n2.setValue(childrenCountF);
        n2.setWrapSelectorWheel(false);

        n3= (NumberPicker) dialog.findViewById(R.id.numberPickerInfant);
        assert n3 != null;
        n3.setMaxValue(9);
        n3.setMinValue(0);
        n3.setValue(infantsCountF);
        n3.setWrapSelectorWheel(false);


        okButton = (TextView) dialog.findViewById(R.id.okButton);
        // if button is clicked, close the custom dialog
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adultsCount=n1.getValue();
                childrenCount=n2.getValue();
                infantsCount=n3.getValue();
                int totalTraverlsCount = adultsCount + childrenCount;
                if (totalTraverlsCount <= 9 && infantsCount <= adultsCount) {
                    totalTravelersCount = String.valueOf(adultsCount) + " Adults " + String.valueOf(childrenCount)
                            + " Children " + String.valueOf(infantsCount) + " Infants";
                    adults.setText(String.valueOf(adultsCount));
                    infants.setText(String.valueOf(infantsCount));
                    children.setText(String.valueOf(childrenCount));
                    adultsCountF=adultsCount;
                    childrenCountF=childrenCount;
                    infantsCountF=infantsCount;
                    dialog.dismiss();

                }else if(infantsCount > adultsCount){
                    Util.showMessage(Flights_Search_Activity.this, "Please select infants less than or equals to adults");
                }else {
                    Util.showMessage(Flights_Search_Activity.this, "Please select total (Adults + Children) Lessthan or Equal 9 Passengers");
                }
            }
        });

        cancelButton = (TextView) dialog.findViewById(R.id.CancelButton);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adultsCount=n1.getValue();
                childrenCount=n2.getValue();
                infantsCount=n3.getValue();
                adultsCountF=adultsCount;
                childrenCountF=childrenCount;
                infantsCountF=infantsCount;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (onewayradio.isChecked()){
            TripType = "1";
            toLinearLayout.setVisibility(View.GONE);
        }else {
            TripType = "2";
            toLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateValues() {
        if(fromPlaceName.getText().toString().matches("Origin") || fromPlaceName.getText().toString().matches("")){
            Util.showMessage(this,"Please enter origin city");
            return false;
        }else if (toPlaceName.getText().toString().matches("Destination") || toPlaceName.getText().toString().matches("")){
            Util.showMessage(this,"Please enter Destination city");
            return false;
        }else if (fromPlaceName.getText().toString().equals(toPlaceName.getText().toString())){
            Util.showMessage(this,"Origin and Destination Can't be same");
            return false;
        }
        return true;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
        if(cur == DATE_DIALOG_ID){

            onward_date_tv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);

            d=Day;
            m=Month;
            y=Year;

            cal1=Calendar.getInstance();
            cal1.set(y,m,d);
            cal1.add(Calendar.DAY_OF_YEAR,1);
            Year = cal1.get(Calendar.YEAR);
            Month = cal1.get(Calendar.MONTH)+1;
            Day = cal1.get(Calendar.DAY_OF_MONTH);

            return_date_tv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month) + "-" + Year);

            if (TripType.equalsIgnoreCase("2")){
                onClick(toLinearLayout);
                Util.showMessage(this, "Select Returning Date");
            }
        }
        else{
            return_date_tv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);
        }
    }


    public void setCurrentDateOnView() {


////CheckIndate
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH) + 1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        onward_date_tv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month) + "-" + Year);
////CheckOutdate
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH) + 1;
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        return_date_tv.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month) + "-" + Year);
        calendar = Calendar.getInstance();
////Setting calender instance back to current date
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        d=Day;
        m=Month;
        y=Year;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ip=new Intent(Flights_Search_Activity.this,MainActivity.class);
        ip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ip);
    }
}
