package com.NaTicket.n.flights.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ankit on 30-12-2017.
 */

public class IntOnwardDTO implements Serializable {

        private ArrayList<FlightSegmentDTO> FlightSegments;

        public ArrayList<FlightSegmentDTO> getFlightSegments() { return this.FlightSegments; }

        public void setFlightSegments(ArrayList<FlightSegmentDTO> FlightSegments) { this.FlightSegments = FlightSegments; }

}
