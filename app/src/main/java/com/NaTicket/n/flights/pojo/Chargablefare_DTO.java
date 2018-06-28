package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class Chargablefare_DTO implements Serializable {

        private double ActualBaseFare;

        private double Tax;

        private PartnerFareDatailsDTO partnerFareDatailsDTO;

        public double getActualBaseFare() { return this.ActualBaseFare; }

        public void setActualBaseFare(double ActualBaseFare) { this.ActualBaseFare = ActualBaseFare; }



        public double getTax() { return this.Tax; }

        public void setTax(double Tax) { this.Tax = Tax; }

        private double STax;

        public double getSTax() { return this.STax; }

        public void setSTax(double STax) { this.STax = STax; }

        private double SCharge;

        public double getSCharge() { return this.SCharge; }

        public void setSCharge(double SCharge) { this.SCharge = SCharge; }

        private double TDiscount;

        public double getTDiscount() { return this.TDiscount; }

        public void setTDiscount(double TDiscount) { this.TDiscount = TDiscount; }

        private double TPartnerCommission;

        public double getTPartnerCommission() { return this.TPartnerCommission; }

        public void setTPartnerCommission(double TPartnerCommission) { this.TPartnerCommission = TPartnerCommission; }

        private double NetFare;

        public double getNetFare() { return this.NetFare; }

        public void setNetFare(double NetFare) { this.NetFare = NetFare; }

        private double Conveniencefee;

        public double getConveniencefee() { return this.Conveniencefee; }

        public void setConveniencefee(double Conveniencefee) { this.Conveniencefee = Conveniencefee; }

        private int ConveniencefeeType;

        public int getConveniencefeeType() { return this.ConveniencefeeType; }

        public void setConveniencefeeType(int ConveniencefeeType) { this.ConveniencefeeType = ConveniencefeeType; }


        public PartnerFareDatailsDTO getPartnerFareDatails() { return this.partnerFareDatailsDTO; }

        public void setPartnerFareDatails(PartnerFareDatailsDTO PartnerFareDatails) { this.partnerFareDatailsDTO = PartnerFareDatails; }

}
