package com.NaTicket.n.flights.pojo;

import java.io.Serializable;

/**
 * Created by Nagarjuna on 12/30/2017.
 */

public class FlightSegmentDTO implements Serializable {

    private String AirEquipType;

    public String getAirEquipType() { return this.AirEquipType; }

    public void setAirEquipType(String AirEquipType) { this.AirEquipType = AirEquipType; }

    private String ArrivalAirportCode;

    public String getArrivalAirportCode() { return this.ArrivalAirportCode; }

    public void setArrivalAirportCode(String ArrivalAirportCode) { this.ArrivalAirportCode = ArrivalAirportCode; }

    private String ArrivalDateTime;

    public String getArrivalDateTime() { return this.ArrivalDateTime; }

    public void setArrivalDateTime(String ArrivalDateTime) { this.ArrivalDateTime = ArrivalDateTime; }

    private String ArrivalDateTimeZone;

    public String getArrivalDateTimeZone() { return this.ArrivalDateTimeZone; }

    public void setArrivalDateTimeZone(String ArrivalDateTimeZone) { this.ArrivalDateTimeZone = ArrivalDateTimeZone; }

    private String DepartureAirportCode;

    public String getDepartureAirportCode() { return this.DepartureAirportCode; }

    public void setDepartureAirportCode(String DepartureAirportCode) { this.DepartureAirportCode = DepartureAirportCode; }

    private String DepartureDateTime;

    public String getDepartureDateTime() { return this.DepartureDateTime; }

    public void setDepartureDateTime(String DepartureDateTime) { this.DepartureDateTime = DepartureDateTime; }

    private String DepartureDateTimeZone;

    public String getDepartureDateTimeZone() { return this.DepartureDateTimeZone; }

    public void setDepartureDateTimeZone(String DepartureDateTimeZone) { this.DepartureDateTimeZone = DepartureDateTimeZone; }

    private String Duration;

    public String getDuration() { return this.Duration; }

    public void setDuration(String Duration) { this.Duration = Duration; }

    private String FlightNumber;

    public String getFlightNumber() { return this.FlightNumber; }

    public void setFlightNumber(String FlightNumber) { this.FlightNumber = FlightNumber; }

    private String OperatingAirlineCode;

    public String getOperatingAirlineCode() { return this.OperatingAirlineCode; }

    public void setOperatingAirlineCode(String OperatingAirlineCode) { this.OperatingAirlineCode = OperatingAirlineCode; }

    private String OperatingAirlineFlightNumber;

    public String getOperatingAirlineFlightNumber() { return this.OperatingAirlineFlightNumber; }

    public void setOperatingAirlineFlightNumber(String OperatingAirlineFlightNumber) { this.OperatingAirlineFlightNumber = OperatingAirlineFlightNumber; }

    private String RPH;

    public String getRPH() { return this.RPH; }

    public void setRPH(String RPH) { this.RPH = RPH; }

    private String StopQuantity;

    public String getStopQuantity() { return this.StopQuantity; }

    public void setStopQuantity(String StopQuantity) { this.StopQuantity = StopQuantity; }

    private String AirLineName;

    public String getAirLineName() { return this.AirLineName; }

    public void setAirLineName(String AirLineName) { this.AirLineName = AirLineName; }

    private String AirportTax;

    public String getAirportTax() { return this.AirportTax; }

    public void setAirportTax(String AirportTax) { this.AirportTax = AirportTax; }

    private String ImageFileName;

    public String getImageFileName() { return this.ImageFileName; }

    public void setImageFileName(String ImageFileName) { this.ImageFileName = ImageFileName; }

    private String ViaFlight;

    public String getViaFlight() { return this.ViaFlight; }

    public void setViaFlight(String ViaFlight) { this.ViaFlight = ViaFlight; }

    private String Discount;

    public String getDiscount() { return this.Discount; }

    public void setDiscount(String Discount) { this.Discount = Discount; }

    private String AirportTaxChild;

    public String getAirportTaxChild() { return this.AirportTaxChild; }

    public void setAirportTaxChild(String AirportTaxChild) { this.AirportTaxChild = AirportTaxChild; }

    private String AirportTaxInfant;

    public String getAirportTaxInfant() { return this.AirportTaxInfant; }

    public void setAirportTaxInfant(String AirportTaxInfant) { this.AirportTaxInfant = AirportTaxInfant; }

    private String AdultTaxBreakup;

    public String getAdultTaxBreakup() { return this.AdultTaxBreakup; }

    public void setAdultTaxBreakup(String AdultTaxBreakup) { this.AdultTaxBreakup = AdultTaxBreakup; }

    private String ChildTaxBreakup;

    public String getChildTaxBreakup() { return this.ChildTaxBreakup; }

    public void setChildTaxBreakup(String ChildTaxBreakup) { this.ChildTaxBreakup = ChildTaxBreakup; }

    private String InfantTaxBreakup;

    public String getInfantTaxBreakup() { return this.InfantTaxBreakup; }

    public void setInfantTaxBreakup(String InfantTaxBreakup) { this.InfantTaxBreakup = InfantTaxBreakup; }

    private String OcTax;

    public String getOcTax() { return this.OcTax; }

    public void setOcTax(String OcTax) { this.OcTax = OcTax; }

    private BookingClassDTO BookingClass;

    public BookingClassDTO getBookingClass() { return this.BookingClass; }

    public void setBookingClass(BookingClassDTO BookingClass) { this.BookingClass = BookingClass; }

    private BookingClassFareDTO BookingClassFare;

    public BookingClassFareDTO getBookingClassFare() { return this.BookingClassFare; }

    public void setBookingClassFare(BookingClassFareDTO BookingClassFare) { this.BookingClassFare = BookingClassFare; }

    private String IntNumStops;

    public String getIntNumStops() { return this.IntNumStops; }

    public void setIntNumStops(String IntNumStops) { this.IntNumStops = IntNumStops; }

    private String IntOperatingAirlineName;

    public String getIntOperatingAirlineName() { return this.IntOperatingAirlineName; }

    public void setIntOperatingAirlineName(String IntOperatingAirlineName) { this.IntOperatingAirlineName = IntOperatingAirlineName; }

    private String IntArrivalAirportName;

    public String getIntArrivalAirportName() { return this.IntArrivalAirportName; }

    public void setIntArrivalAirportName(String IntArrivalAirportName) { this.IntArrivalAirportName = IntArrivalAirportName; }

    private String IntDepartureAirportName;

    public String getIntDepartureAirportName() { return this.IntDepartureAirportName; }

    public void setIntDepartureAirportName(String IntDepartureAirportName) { this.IntDepartureAirportName = IntDepartureAirportName; }

    private String IntMarketingAirlineCode;

    public String getIntMarketingAirlineCode() { return this.IntMarketingAirlineCode; }

    public void setIntMarketingAirlineCode(String IntMarketingAirlineCode) { this.IntMarketingAirlineCode = IntMarketingAirlineCode; }

    private String IntLinkSellAgrmnt;

    public String getIntLinkSellAgrmnt() { return this.IntLinkSellAgrmnt; }

    public void setIntLinkSellAgrmnt(String IntLinkSellAgrmnt) { this.IntLinkSellAgrmnt = IntLinkSellAgrmnt; }

    private String IntConx;

    public String getIntConx() { return this.IntConx; }

    public void setIntConx(String IntConx) { this.IntConx = IntConx; }

    private String IntAirpChg;

    public String getIntAirpChg() { return this.IntAirpChg; }

    public void setIntAirpChg(String IntAirpChg) { this.IntAirpChg = IntAirpChg; }

    private String IntInsideAvailOption;

    public String getIntInsideAvailOption() { return this.IntInsideAvailOption; }

    public void setIntInsideAvailOption(String IntInsideAvailOption) { this.IntInsideAvailOption = IntInsideAvailOption; }

    private String IntGenTrafRestriction;

    public String getIntGenTrafRestriction() { return this.IntGenTrafRestriction; }

    public void setIntGenTrafRestriction(String IntGenTrafRestriction) { this.IntGenTrafRestriction = IntGenTrafRestriction; }

    private String IntDaysOperates;

    public String getIntDaysOperates() { return this.IntDaysOperates; }

    public void setIntDaysOperates(String IntDaysOperates) { this.IntDaysOperates = IntDaysOperates; }

    private String IntJourneyTime;

    public String getIntJourneyTime() { return this.IntJourneyTime; }

    public void setIntJourneyTime(String IntJourneyTime) { this.IntJourneyTime = IntJourneyTime; }

    private String IntEndDate;

    public String getIntEndDate() { return this.IntEndDate; }

    public void setIntEndDate(String IntEndDate) { this.IntEndDate = IntEndDate; }

    private String IntStartTerminal;

    public String getIntStartTerminal() { return this.IntStartTerminal; }

    public void setIntStartTerminal(String IntStartTerminal) { this.IntStartTerminal = IntStartTerminal; }

    private String IntEndTerminal;

    public String getIntEndTerminal() { return this.IntEndTerminal; }

    public void setIntEndTerminal(String IntEndTerminal) { this.IntEndTerminal = IntEndTerminal; }

    private String IntFltTm;

    public String getIntFltTm() { return this.IntFltTm; }

    public void setIntFltTm(String IntFltTm) { this.IntFltTm = IntFltTm; }

    private String IntLSAInd;

    public String getIntLSAInd() { return this.IntLSAInd; }

    public void setIntLSAInd(String IntLSAInd) { this.IntLSAInd = IntLSAInd; }

    private String IntMile;

    public String getIntMile() { return this.IntMile; }

    public void setIntMile(String IntMile) { this.IntMile = IntMile; }

    private String Cabin;

    public String getCabin() { return this.Cabin; }

    public void setCabin(String Cabin) { this.Cabin = Cabin; }

    private String itineraryNumber;

    public String getItineraryNumber() { return this.itineraryNumber; }

    public void setItineraryNumber(String itineraryNumber) { this.itineraryNumber = itineraryNumber; }

    private BaggageAllowedDTO BaggageAllowed;

    public BaggageAllowedDTO getBaggageAllowed() { return this.BaggageAllowed; }

    public void setBaggageAllowed(BaggageAllowedDTO BaggageAllowed) { this.BaggageAllowed = BaggageAllowed; }

    private String AccumulatedDuration;

    public String getAccumulatedDuration() { return this.AccumulatedDuration; }

    public void setAccumulatedDuration(String AccumulatedDuration) { this.AccumulatedDuration = AccumulatedDuration; }
}
