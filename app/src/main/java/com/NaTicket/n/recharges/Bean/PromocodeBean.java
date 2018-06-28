package com.NaTicket.n.recharges.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 22-03-2016.
 */
public class PromocodeBean implements Serializable {


    public  String PromoCodeId;
    public  String PromoName;
    public  String ServiceType;
    public  String ApplicationType;
    public  String Code;
    public  String ValidFrom;
    public  String ValidTill;

    public String getPromoCodeId() {
        return PromoCodeId;
    }

    public void setPromoCodeId(String promoCodeId) {
        PromoCodeId = promoCodeId;
    }

    public String getPromoName() {
        return PromoName;
    }

    public void setPromoName(String promoName) {
        PromoName = promoName;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getApplicationType() {
        return ApplicationType;
    }

    public void setApplicationType(String applicationType) {
        ApplicationType = applicationType;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getValidFrom() {
        return ValidFrom;
    }

    public void setValidFrom(String validFrom) {
        ValidFrom = validFrom;
    }

    public String getValidTill() {
        return ValidTill;
    }

    public void setValidTill(String validTill) {
        ValidTill = validTill;
    }

    public String getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(String discountType) {
        DiscountType = discountType;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public  String DiscountType;
    public  String Discount;



}
