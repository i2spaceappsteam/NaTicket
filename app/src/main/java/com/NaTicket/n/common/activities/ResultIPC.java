package com.NaTicket.n.common.activities;

import com.NaTicket.n.flights.pojo.DomesticOnwardFlightDTO;
import com.NaTicket.n.flights.pojo.DomesticReturnFlightDTO;
import com.NaTicket.n.flights.pojo.InternationalFlightsDTO;
import com.NaTicket.n.hotels.pojo.AvailableHotelsDTO;

import java.util.ArrayList;

/**
 * Created by Ankit on 31-01-2018.
 */

public class ResultIPC {

    private static ResultIPC instance;

    public synchronized static ResultIPC get() {
        if (instance == null) {
            instance = new ResultIPC ();
        }
        return instance;
    }

    private int sync = 0;

    private ArrayList<AvailableHotelsDTO> largeData;
    public int setLargeData(ArrayList<AvailableHotelsDTO> largeData) {
        this.largeData =  largeData;
        return ++sync;
    }

    public ArrayList<AvailableHotelsDTO> getLargeData(int request) {
        return (request == sync) ? largeData : null;
    }

    private ArrayList<InternationalFlightsDTO> Inte_largeData;
    public int setIntLargeData(ArrayList<InternationalFlightsDTO> largeData) {
        this.Inte_largeData =  largeData;
        return ++sync;
    }

    public ArrayList<InternationalFlightsDTO> getIntLargeData(int request) {
        return (request == sync) ? Inte_largeData : null;
    }


    private ArrayList<DomesticOnwardFlightDTO> Dom_largeData;
    public int setDomLargeData(ArrayList<DomesticOnwardFlightDTO> largeData) {
        this.Dom_largeData =  largeData;
        return ++sync;
    }

    public ArrayList<DomesticOnwardFlightDTO> getDomLargeData(int request) {
        return (request == sync) ? Dom_largeData : null;
    }

    private ArrayList<DomesticReturnFlightDTO> Dom_ret_largeData;
    public int setDom_RetLargeData(ArrayList<DomesticReturnFlightDTO> largeData) {
        this.Dom_ret_largeData =  largeData;
        return ++sync;
    }

    public ArrayList<DomesticReturnFlightDTO> getDom_RetLargeData(int request) {
        return (request == sync) ? Dom_ret_largeData : null;
    }

}
