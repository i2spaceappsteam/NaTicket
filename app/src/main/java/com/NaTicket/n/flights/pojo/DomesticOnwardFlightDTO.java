package com.NaTicket.n.flights.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class DomesticOnwardFlightDTO implements Serializable{

    private FareDetailsDTO FareDetails;

    public FareDetailsDTO getFareDetails() { return this.FareDetails; }

    public void setFareDetails(FareDetailsDTO FareDetails) { this.FareDetails = FareDetails; }

    private ArrayList<FlightSegmentDTO> FlightSegments;

    public ArrayList<FlightSegmentDTO> getFlightSegments() { return this.FlightSegments; }

    public void setFlightSegments(ArrayList<FlightSegmentDTO> FlightSegments) { this.FlightSegments = FlightSegments; }

    private OriginDestinationoptionIdDTO OriginDestinationoptionId;

    public OriginDestinationoptionIdDTO getOriginDestinationoptionId() { return this.OriginDestinationoptionId; }

    public void setOriginDestinationoptionId(OriginDestinationoptionIdDTO OriginDestinationoptionId) { this.OriginDestinationoptionId = OriginDestinationoptionId; }

    private String IntOnward;

    public String getIntOnward() { return this.IntOnward; }

    public void setIntOnward(String IntOnward) { this.IntOnward = IntOnward; }

    private String IntReturn;

    public String getIntReturn() { return this.IntReturn; }

    public void setIntReturn(String IntReturn) { this.IntReturn = IntReturn; }

    private String IntMulti;

    public String getIntMulti() { return this.IntMulti; }

    public void setIntMulti(String IntMulti) { this.IntMulti = IntMulti; }

    private RequestDetailsDTO RequestDetails;

    public RequestDetailsDTO getRequestDetails() { return this.RequestDetails; }

    public void setRequestDetails(RequestDetailsDTO RequestDetails) { this.RequestDetails = RequestDetails; }

    private String Provider;

    public String getProvider() { return this.Provider; }

    public void setProvider(String Provider) { this.Provider = Provider; }

    private String AffiliateId;

    public String getAffiliateId() { return this.AffiliateId; }

    public void setAffiliateId(String AffiliateId) { this.AffiliateId = AffiliateId; }

    private boolean IsLCC;

    public boolean getIsLCC() { return this.IsLCC; }

    public void setIsLCC(boolean IsLCC) { this.IsLCC = IsLCC; }
}
