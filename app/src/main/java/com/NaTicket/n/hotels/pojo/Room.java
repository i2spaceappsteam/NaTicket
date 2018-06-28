package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;

/**
 * Created by admin on 14-Jun-17.
 */

public class Room implements Serializable{

    private int roommNo;

    private int adults;

    private int child;

    private int room1adults,room2adults,room3adults,room4adults;

    private int room1children,room2children,room3children,room4children;

    private int child1age,child2age;

    public int getRoommNo() {
        return roommNo;
    }

    public void setRoommNo(int roommNo) {
        this.roommNo = roommNo;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getRoom1adults(){return room1adults;}

    public void  setRoom1adults(int room1adults){this.room1adults=room1adults;}

    public int getRoom2adults(){return room2adults;}

    public void  setRoom2adults(int room2adults){this.room2adults=room2adults;}

    public int getRoom3adults(){return room3adults;}

    public void  setRoom3adults(int room3adults){this.room3adults=room3adults;}

    public int getRoom4adults(){return room4adults;}

    public void  setRoom4adults(int room4adults){this.room4adults=room4adults;}

    public int getroom1children(){return room1children;}

    public void  setroom1children(int room1children){this.room1children=room1children;}

    public int getroom2children(){return room2children;}

    public void  setroom2children(int room2children){this.room2children=room2children;}

    public int getroom3children(){return room3children;}

    public void  setroom3children(int room3children){this.room3children=room3children;}

    public int getroom4children(){return room4children;}

    public void  setroom4children(int room4children){this.room4children=room4children;}

    public void setChild1age(int child1age){this.child1age=child1age;}

    public int getChild1age() {return  child1age;}

    public int getChild2age(){return  child2age;}

    public void setChild2age(int child2age){this.child2age=child2age;}


}
