package com.NaTicket.n.common.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 14-01-2018.
 */

public class GetPromoCodesDTO implements Serializable {

    private int PromoCodeId;

    private String PromoName;

    private int ServiceType;

    private int ApplicationType;

    private String Code;

    private String ValidFrom;

    private String ValidTill;

    private int DiscountType;

    private String Discount;

    private String ToAmount;

    private String FromAmount;

    public int getPromoCodeId() {
        return PromoCodeId;
    }

    public void setPromoCodeId(int promoCodeId) {
        PromoCodeId = promoCodeId;
    }

    public String getPromoName() {
        return PromoName;
    }

    public void setPromoName(String promoName) {
        PromoName = promoName;
    }

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int serviceType) {
        ServiceType = serviceType;
    }

    public int getApplicationType() {
        return ApplicationType;
    }

    public void setApplicationType(int applicationType) {
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

    public int getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(int discountType) {
        DiscountType = discountType;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getToAmount() {
        return ToAmount;
    }

    public void setToAmount(String toAmount) {
        ToAmount = toAmount;
    }

    public String getFromAmount() {
        return FromAmount;
    }

    public void setFromAmount(String fromAmount) {
        FromAmount = fromAmount;
    }
}

