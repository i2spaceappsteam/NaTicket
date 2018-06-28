package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;

/**
 * Created by admin on 18-Jun-17.
 */

public class CityCTO implements Serializable{

    private int CityId;

    private int CityKeyId;

    private String CityName;

    private int LocationId;

    private String LocationName;

    private int StateCode;

    private int CountryCode;

    private String CountryName;

    private boolean IsAlias;

    private int StateId;

    private String CreatedBy;

    private String CreatedDate;

    private String ModifiedBy;

    private String ModifiedDate;

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public int getCityKeyId() {
        return CityKeyId;
    }

    public void setCityKeyId(int cityKeyId) {
        CityKeyId = cityKeyId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public int getStateCode() {
        return StateCode;
    }

    public void setStateCode(int stateCode) {
        StateCode = stateCode;
    }

    public int getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(int countryCode) {
        CountryCode = countryCode;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public boolean isAlias() {
        return IsAlias;
    }

    public void setAlias(boolean alias) {
        IsAlias = alias;
    }

    public int getStateId() {
        return StateId;
    }

    public void setStateId(int stateId) {
        StateId = stateId;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }
}
