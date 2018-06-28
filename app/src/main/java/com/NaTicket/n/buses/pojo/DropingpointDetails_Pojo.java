package com.NaTicket.n.buses.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 14-01-2018.
 */

public class DropingpointDetails_Pojo implements Serializable {


    private String Address;

    public String getAddress() { return this.Address; }

    public void setAddress(String Address) { this.Address = Address; }

    private String ContactNumbers;

    public String getContactNumbers() { return this.ContactNumbers; }

    public void setContactNumbers(String ContactNumbers) { this.ContactNumbers = ContactNumbers; }

    private String ContactPersons;

    public String getContactPersons() { return this.ContactPersons; }

    public void setContactPersons(String ContactPersons) { this.ContactPersons = ContactPersons; }

    private String PointId;

    public String getPointId() { return this.PointId; }

    public void setPointId(String PointId) { this.PointId = PointId; }

    private String Landmark;

    public String getLandmark() { return this.Landmark; }

    public void setLandmark(String Landmark) { this.Landmark = Landmark; }

    private String Location;

    public String getLocation() { return this.Location; }

    public void setLocation(String Location) { this.Location = Location; }

    private String Name;

    public String getName() { return this.Name; }

    public void setName(String Name) { this.Name = Name; }

    private String Time;

    public String getTime() { return this.Time; }

    public void setTime(String Time) { this.Time = Time; }
}
