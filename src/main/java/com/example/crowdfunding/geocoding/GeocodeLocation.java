package com.example.crowdfunding.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GeocodeLocation {

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty
    private String longitude;

    public GeocodeLocation() {
    }
}
