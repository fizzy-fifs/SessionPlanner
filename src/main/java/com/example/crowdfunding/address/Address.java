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

    public Address() {
    }

    public String getConcatAddress() {
        return line1 + ", " + line2 + ", " + city + ", "  + state + ", " + country + ", " + postalCode;
    }
}
