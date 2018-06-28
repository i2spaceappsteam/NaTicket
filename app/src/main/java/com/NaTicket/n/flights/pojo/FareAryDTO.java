package com.NaTicket.n.flights.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ankit on 30-12-2017.
 */

public class FareAryDTO implements Serializable {

    private String IntPassengerType;

    public String getIntPassengerType() { return this.IntPassengerType; }

    public void setIntPassengerType(String IntPassengerType) { this.IntPassengerType = IntPassengerType; }

    private String IntBaseFare;

    public String getIntBaseFare() { return this.IntBaseFare; }

    public void setIntBaseFare(String IntBaseFare) { this.IntBaseFare = IntBaseFare; }

    private String IntTax;

    public String getIntTax() { return this.IntTax; }

    public void setIntTax(String IntTax) { this.IntTax = IntTax; }

    private ArrayList<IntTaxDataArray_DTO> IntTaxDataArray;

    public ArrayList<IntTaxDataArray_DTO> getIntTaxDataArray() { return this.IntTaxDataArray; }

    public void setIntTaxDataArray(ArrayList<IntTaxDataArray_DTO> IntTaxDataArray) { this.IntTaxDataArray = IntTaxDataArray; }
}
