package com.NaTicket.n.flights.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ankit on 30-12-2017.
 */

public class IntReturnDTO implements Serializable {

        private ArrayList<ReturnFlightSegmentDTO> FlightSegments;

        public ArrayList<ReturnFlightSegmentDTO> getFlightSegments() { return this.FlightSegments; }

        public void setFlightSegments(ArrayList<ReturnFlightSegmentDTO> FlightSegments) { this.FlightSegments = FlightSegments; }

}
