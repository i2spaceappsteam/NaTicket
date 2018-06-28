package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 30-12-2017.
 */

public class InternationalreturnBookingClass_DTO implements Serializable{


        private String Availability;

        public String getAvailability() { return this.Availability; }

        public void setAvailability(String Availability) { this.Availability = Availability; }

        private String ResBookDesigCode;

        public String getResBookDesigCode() { return this.ResBookDesigCode; }

        public void setResBookDesigCode(String ResBookDesigCode) { this.ResBookDesigCode = ResBookDesigCode; }

        private String IntBIC;

        public String getIntBIC() { return this.IntBIC; }

        public void setIntBIC(String IntBIC) { this.IntBIC = IntBIC; }

}
