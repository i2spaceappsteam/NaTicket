package com.NaTicket.n.hotels.pojo;

import java.io.Serializable;

/**
 * Created by Ankit on 15-01-2018.
 */
public class PartnerAgentDetails_Pojo implements Serializable {

    private String AgentId;

    public String getAgentId() { return this.AgentId; }

    public void setAgentId(String AgentId) { this.AgentId = AgentId; }

    private String ServiceId;

    public String getServiceId() { return this.ServiceId; }

    public void setServiceId(String ServiceId) { this.ServiceId = ServiceId; }

    private String ServiceName;

    public String getServiceName() { return this.ServiceName; }

    public void setServiceName(String ServiceName) { this.ServiceName = ServiceName; }

    private String Amount;

    public String getAmount() { return this.Amount; }

    public void setAmount(String Amount) { this.Amount = Amount; }

    private String UserCharges;

    public String getUserCharges() { return this.UserCharges; }

    public void setUserCharges(String UserCharges) { this.UserCharges = UserCharges; }

    private String ResCode;

    public String getResCode() { return this.ResCode; }

    public void setResCode(String ResCode) { this.ResCode = ResCode; }

    private String ResDesc;

    public String getResDesc() { return this.ResDesc; }

    public void setResDesc(String ResDesc) { this.ResDesc = ResDesc; }

    private String Requestid;

    public String getRequestid() { return this.Requestid; }

    public void setRequestid(String Requestid) { this.Requestid = Requestid; }
}