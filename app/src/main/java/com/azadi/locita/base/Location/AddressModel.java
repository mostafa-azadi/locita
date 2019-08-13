package com.azadi.locita.base.Location;

public class AddressModel {


    private String country;
    private String state;
    private String city;

    public AddressModel(String country, String state , String city){

        this.country = country;
        this.state = state;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }
}
