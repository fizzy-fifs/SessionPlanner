package com.example.crowdfunding.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Address {
    @JsonProperty
    private String id;

    @JsonProperty
    private String line1;

    @JsonProperty
    private String line2 = "";

    @JsonProperty
    private String city;

    @JsonProperty
    private String state;

    @JsonProperty
    private String country;

    @JsonProperty
    private String postalCode;

    public Address(String id, String line1, String city, String state, String country, String postalCode) {
        this.id = id;
        this.line1 = line1;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }

    public Address(String id, String line1, String line2, String city, String state, String country, String postalCode) {
        this.id = id;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getConcatAddress() {
        return line1 + line2 + city + state + country + postalCode;
    }
}
