package com.NaTicket.n.flights.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nagarjuna on 07-01-2018.
 */

public class Flight_Filters_DTO implements Serializable {


    private ArrayList<HashMap<String,String>> Filter_Array;

    private ArrayList<String> Airlines;

    private ArrayList<String> Rule;

    private ArrayList<Integer> Stops;

    private ArrayList<String> Dept_Times;

    private int Min_Rate;

    private int Max_Rate;


    public int getMin_Rate() {
        return Min_Rate;
    }

    public void setMin_Rate(int min_Rate) {
        this.Min_Rate = min_Rate;
    }

    public int getMax_Rate() {
        return Max_Rate;
    }

    public void setMax_Rate(int Max_Rate) {
        this.Max_Rate = Max_Rate;
    }

    public ArrayList<HashMap<String,String>> getFilter_Array() {
        return Filter_Array;
    }

    public void setFilter_Array(ArrayList<HashMap<String,String>> filteredarray) {
        this.Filter_Array =filteredarray ;
    }


    public ArrayList<String> getArrival_Times() {
        return Arrival_Times;
    }

    public void setArrival_Times(ArrayList<String> arrival_Times) {
        Arrival_Times = arrival_Times;
    }

    private ArrayList<String> Arrival_Times;


    public ArrayList<String> getDept_Times() {
        return Dept_Times;
    }

    public void setDept_Times(ArrayList<String> dept_Times) {
        Dept_Times = dept_Times;
    }

    public ArrayList<String> getAirlines() {
        return Airlines;
    }

    public void setAirlines(ArrayList<String> airlines) {
        Airlines = airlines;
    }

    public ArrayList<String> getRule() {
        return Rule;
    }

    public void setRule(ArrayList<String> rule) {
        Rule = rule;
    }

    public ArrayList<Integer> getStops() {
        return Stops;
    }

    public void setStops(ArrayList<Integer> stops) {
        Stops = stops;
    }



}
