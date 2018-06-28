package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 18-Jun-17.
 */

public class HotelsMainDTO  implements Serializable{

    private ArrayList<AvailableHotelsDTO> AvailableHotels;

    private int ProvidersCount;

    private int ResponseStatus;

    private String Message;

    public ArrayList<AvailableHotelsDTO> getAvailableHotels() {
        return AvailableHotels;
    }

    public void setAvailableHotels(ArrayList<AvailableHotelsDTO> availableHotels) {
        AvailableHotels = availableHotels;
    }

    public int getProvidersCount() {
        return ProvidersCount;
    }

    public void setProvidersCount(int providersCount) {
        ProvidersCount = providersCount;
    }

    public int getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        ResponseStatus = responseStatus;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
