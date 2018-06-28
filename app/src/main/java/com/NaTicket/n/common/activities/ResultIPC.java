package com.NaTicket.n.common.activities;

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
}
