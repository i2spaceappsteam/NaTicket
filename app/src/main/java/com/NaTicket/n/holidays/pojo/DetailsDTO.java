package com.NaTicket.n.holidays.pojo;

import java.io.Serializable;

/**
 * Created by admin on 10-Jul-17.
 */

public class DetailsDTO implements Serializable{

    private int Id;

    private String Name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
