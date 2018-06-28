package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 15-01-2018.
 */

public class HotelPolicy_DTO implements Serializable{

        private String HotelCancellationPolicy;

        public String getHotelCancellationPolicy() { return this.HotelCancellationPolicy; }

        public void setHotelCancellationPolicy(String HotelCancellationPolicy) { this.HotelCancellationPolicy = HotelCancellationPolicy; }

        private String HotelPolicyRules;

        public String getHotelPolicyRules() { return this.HotelPolicyRules; }

        public void setHotelPolicyRules(String HotelPolicyRules) { this.HotelPolicyRules = HotelPolicyRules; }

}
