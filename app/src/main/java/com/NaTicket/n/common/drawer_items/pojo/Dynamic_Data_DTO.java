package com.NaTicket.n.common.drawer_items.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 15-01-2018.
 */

public class Dynamic_Data_DTO implements Serializable {


    private String Page;

    private String ContentDescription;

    public String getContentDescription() {
        return ContentDescription;
    }

    public void setContentDescription(String contentDescription) {
        ContentDescription = contentDescription;
    }

    public String getPage() {

        return Page;
    }

    public void setPage(String page) {
        Page = page;
    }
}
