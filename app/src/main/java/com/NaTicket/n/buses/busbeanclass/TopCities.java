package com.NaTicket.n.buses.busbeanclass;

/**
 * Created by Ankit on 7/6/2016.
 */
public class TopCities {

    private String TopCitiesName;
    private String TopCitiesId;

    public TopCities(String rank, String country ) {
        this.TopCitiesName = rank;
        this.TopCitiesId = country;

    }

    public String getTopCitiesName() {
        return this.TopCitiesName;
    }

    public String getTopCitiesId() {
        return this.TopCitiesId;
    }

}
