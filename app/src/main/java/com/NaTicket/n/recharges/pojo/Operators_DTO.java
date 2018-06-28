package com.NaTicket.n.recharges.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 01-03-2016.
 */
public class Operators_DTO implements Serializable{
    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    public String getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(String operatorId) {
        OperatorId = operatorId;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String OperatorName;
    public  String OperatorId;
    public int Type;





}
