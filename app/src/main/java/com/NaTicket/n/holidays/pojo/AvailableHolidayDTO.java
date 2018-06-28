package com.NaTicket.n.holidays.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 10-Jul-17.
 */

public class AvailableHolidayDTO implements Serializable {

    private ArrayList<AvailableHolidayPackagesDTO> AvailableHolidayPackages;

    private int ProvidersCount;

    private int ResponseStatus;

    private String Message;

    public ArrayList<AvailableHolidayPackagesDTO> getAvailableHolidayPackages() {
        return AvailableHolidayPackages;
    }

    public void setAvailableHolidayPackages(ArrayList<AvailableHolidayPackagesDTO> availableHolidayPackages) {
        AvailableHolidayPackages = availableHolidayPackages;
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
