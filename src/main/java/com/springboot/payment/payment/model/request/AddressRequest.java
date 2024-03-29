package com.springboot.payment.payment.model.request;

public class AddressRequest {

    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(final String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override public String toString() {
        return "AddressRequest{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", streetName='" + streetName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
