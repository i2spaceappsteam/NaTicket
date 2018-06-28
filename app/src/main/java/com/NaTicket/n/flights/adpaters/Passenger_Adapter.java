package com.NaTicket.n.flights.adpaters;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.NaTicket.n.R;
import com.NaTicket.n.flights.Flight_Passenger_Information;
import com.NaTicket.n.flights.pojo.Flight_Utils;
import com.NaTicket.n.flights.pojo.Passenger_Info_DTO;
import com.NaTicket.n.flights.pojo.SelectedFlightDetailsDTO;
import com.NaTicket.n.utils.Util;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Nagarjuna on 1/2/2018.
 */

public class Passenger_Adapter extends RecyclerView.Adapter<Passenger_Adapter.ViewHolder> implements DatePickerDialog.OnDateSetListener{

    ArrayList<String> paxdata=new ArrayList<>();
    Flight_Passenger_Information activity;
    SelectedFlightDetailsDTO selDetails;
    ArrayList<Passenger_Info_DTO> passenger_info;
    int cur = 0;
    DatePickerDialog datePickerDialog;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    static final int DATE_DIALOG_ID3 = 3;
    Calendar  calendar;
    int Year, Month,Day ;
     TextView Dob,Issue_Date,Expiry_Date;

    public Passenger_Adapter(Flight_Passenger_Information flightPassengerInformation, ArrayList<String> pax_data,
                             SelectedFlightDetailsDTO SelDetails,ArrayList<Passenger_Info_DTO> pax) {
        this.activity=flightPassengerInformation;
        this.paxdata=pax_data;
        this.selDetails=SelDetails;
        this.passenger_info=pax;
    }


    @Override
    public int getItemCount() {
        return paxdata.size();
    }

    @Override
    public Passenger_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flight_pax, viewGroup, false);
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(final Passenger_Adapter.ViewHolder viewHolder, final int position) {

        viewHolder.Pax_Tv.setText("" + paxdata.get(position) + "");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.flights_passenger_dilog);
                dialog.setTitle("TRAVELLER DETAILS");

                final Spinner Surname_Spinner= (Spinner) dialog.findViewById(R.id.surname);
                final Spinner Gender_Spinner= (Spinner) dialog.findViewById(R.id.gender);
                final EditText FirstName= (EditText) dialog.findViewById(R.id.firstName);
                final EditText LastName= (EditText) dialog.findViewById(R.id.lastName);
                 Dob= (TextView) dialog.findViewById(R.id.dob_tv);

                TextView Save_tv= (TextView) dialog.findViewById(R.id.save);
                final LinearLayout Passport_layout= (LinearLayout) dialog.findViewById(R.id.passport_details_layout);

                final EditText Passport_Number= (EditText) dialog.findViewById(R.id.pass_port_number);
                final EditText Passport_Place= (EditText) dialog.findViewById(R.id.pass_port_place);
                 Issue_Date= (TextView) dialog.findViewById(R.id.passport_issue_date);
                 Expiry_Date= (TextView) dialog.findViewById(R.id.passport_expiry_date);

                LinearLayout DOB_Layout= (LinearLayout) dialog.findViewById(R.id.dob_layout);
                LinearLayout Issue_Layout= (LinearLayout) dialog.findViewById(R.id.passport_issue_layout);
                LinearLayout Expiry_Layout= (LinearLayout) dialog.findViewById(R.id.passport_expiry_layout);

                if (selDetails.getFlight_Type().equals("2")){
                    Passport_layout.setVisibility(View.VISIBLE);
                }


                DOB_Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getcurrent();
                        cur = DATE_DIALOG_ID;
                        datePickerDialog = DatePickerDialog.newInstance(Passenger_Adapter.this, Year, Month, Day);
                        if (paxdata.get(position).contains("Adult")){
                            Calendar cal1 = Calendar.getInstance();
                            cal1.add(Calendar.YEAR, -12);
                            Calendar cal2=Calendar.getInstance();
                            cal2.add(Calendar.YEAR,-100);
                            datePickerDialog.setMinDate(cal2);
                            datePickerDialog.setMaxDate(cal1);
                        }else if (paxdata.get(position).contains("Child")){
                            Calendar cal1 = Calendar.getInstance();
                            cal1.add(Calendar.YEAR, -2);
                            Calendar cal2=Calendar.getInstance();
                            cal2.add(Calendar.YEAR,-12);
                            datePickerDialog.setMinDate(cal2);
                            datePickerDialog.setMaxDate(cal1);
                        }else {
                            Calendar cal1 = Calendar.getInstance();
                            cal1.add(Calendar.YEAR, 0);
                            Calendar cal2=Calendar.getInstance();
                            cal2.add(Calendar.YEAR,-2);
                            datePickerDialog.setMinDate(cal2);
                            datePickerDialog.setMaxDate(cal1);
                        }
                        setdatepicker(datePickerDialog,"Select Date of Birth");
                    }
                });


                Issue_Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getcurrent();
                        cur = DATE_DIALOG_ID2;
                        datePickerDialog = DatePickerDialog.newInstance(Passenger_Adapter.this, Year, Month, Day);
                        Calendar cal=Calendar.getInstance();
                        cal.add(Calendar.YEAR,0);
                        datePickerDialog.setMaxDate(cal);
                        setdatepicker(datePickerDialog,"Select Issue Date");

                    }
                });

                Expiry_Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getcurrent();
                        cur = DATE_DIALOG_ID3;
                        datePickerDialog = DatePickerDialog.newInstance(Passenger_Adapter.this, Year, Month, Day);
                        Calendar cal=Calendar.getInstance();
                        cal.add(Calendar.YEAR,0);
                        datePickerDialog.setMinDate(cal);
                        setdatepicker(datePickerDialog,"Select Expiry Date");

                    }
                });

                List<String> categories = new ArrayList<String>();
                categories.add("Mr.");
                categories.add("Ms.");
                categories.add("Mrs.");

                List<String> chdcategories = new ArrayList<String>();
                chdcategories.add("Mstr.");

                List<String> gender_cat=new ArrayList<String>();
                gender_cat.add("Male");
                gender_cat.add("Female");

                ArrayAdapter<String> childadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, chdcategories);
                childadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                ArrayAdapter<String> genderadapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, gender_cat);
                genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Gender_Spinner.setAdapter(genderadapter);


                if (paxdata.get(position).contains("Adult")){
                    Surname_Spinner.setAdapter(dataAdapter);
                }else {
                    Surname_Spinner.setAdapter(dataAdapter);
                }

                Save_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            if(!Util.checkField(FirstName)||!Util.checkField(LastName)||Dob.getText().toString().matches("Date of Birth")||Dob.getText().toString().matches("")){
                               if (!Util.checkField(FirstName)){
                                   Util.showMessage(activity,"Fill First Name");
                               }else if (!Util.checkField(LastName)){
                                   Util.showMessage(activity,"Fill Last Name");
                               }else if (Dob.getText().toString().matches("Date of Birth")||Dob.getText().toString().matches("")){
                                   Util.showMessage(activity,"Fill Date of Birth");
                               }
                            } else if (selDetails.getFlight_Type().equals("2")){
                                if (!Util.checkField(Passport_Number)||!Util.checkField(Passport_Place)||
                                        Expiry_Date.getText().toString().matches("Expiry Date")||Expiry_Date.getText().toString().matches("")||
                                        Issue_Date.getText().toString().matches("Issue Date")||Issue_Date.getText().toString().matches("")){
                                    if (!Util.checkField(Passport_Number)){
                                        Util.showMessage(activity,"Fill PassPort Number");
                                    }else if (!Util.checkField(Passport_Place)){
                                        Util.showMessage(activity,"Fill PassPort Issued Place");
                                    }else if ( Expiry_Date.getText().toString().matches("Expiry Date")||Expiry_Date.getText().toString().matches("")){
                                        Util.showMessage(activity,"Fill Expiry Date");
                                    }else if (Issue_Date.getText().toString().matches("Issue Date")||Issue_Date.getText().toString().matches("")){
                                        Util.showMessage(activity,"Fill Issued Date");
                                    }
                                }
                            }else {
                                Passenger_Info_DTO passengers=new Passenger_Info_DTO();
                                String passengerType;
                                if (paxdata.get(position).contains("Adult")) {
                                    passengerType = "ADT";
                                } else if (paxdata.get(position).contains("Child")) {
                                    passengerType = "CHD";
                                } else {
                                    passengerType = "INF";
                                }

                                String Names=""+Surname_Spinner.getSelectedItem()+"|"+FirstName.getText().toString()+"|"+LastName.getText().toString()+"|"+passengerType;
                                passengers.setPassenger_Names(Names);
                                passengers.setDOB(Dob.getText().toString());
                                passengers.setAges(Flight_Utils.getAge(Dob.getText().toString()));
                                passengers.setGender(Gender_Spinner.getSelectedItem().toString());
                                if (selDetails.getFlight_Type().equals("2")){
                                  passengers.setPassPort_Details("|"+Passport_Number.getText().toString()+"|"+Issue_Date.getText().toString()+"|"+
                                  Expiry_Date.getText().toString()+"|"+Passport_Place.getText().toString());
                                }
                                 viewHolder.Pax_Tv.setText(""+Surname_Spinner.getSelectedItem()+" "+FirstName.getText().toString()+" "+LastName.getText().toString());
                                 notifyDataSetChanged();
                                 dialog.dismiss();
                            }
                    }
                });
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
        if (cur==DATE_DIALOG_ID){
            Dob.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);
        }

        if (cur==DATE_DIALOG_ID2){
            Issue_Date.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);
        }

        if (cur==DATE_DIALOG_ID3){
            Expiry_Date.setText(String.format("%02d", Day) + "-" + String.format("%02d", Month+1) + "-" + Year);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Pax_Tv;
        public ViewHolder(View view) {
            super(view);
            Pax_Tv = (TextView) view.findViewById(R.id.AdultsCount);
        }
    }

    private void setdatepicker(DatePickerDialog datePickerDialog,String title){
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(activity.getResources().getColor(R.color.colorAccent));
        datePickerDialog.setTitle(title);
        datePickerDialog.show(activity.getFragmentManager(), "DatePickerDialog");
    }

    private void getcurrent(){
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
    }


}
