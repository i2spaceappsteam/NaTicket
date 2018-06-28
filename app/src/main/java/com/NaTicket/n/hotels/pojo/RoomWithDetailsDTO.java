package com.NaTicket.n.hotels.pojo;

import java.util.ArrayList;

/**
 * Created by admin on 01-Jul-17.
 */

public class RoomWithDetailsDTO {

    private int roomID;

    private ArrayList<AdultsDTO> adults;

    private ArrayList<ChildDTO> childs;

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public ArrayList<AdultsDTO> getAdults() {
        return adults;
    }

    public void setAdults(ArrayList<AdultsDTO> adults) {
        this.adults = adults;
    }

    public ArrayList<ChildDTO> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<ChildDTO> childs) {
        this.childs = childs;
    }
}
