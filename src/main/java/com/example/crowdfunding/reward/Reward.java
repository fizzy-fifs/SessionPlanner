package com.example.crowdfunding.reward;

import com.example.crowdfunding.business.Business;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

@Data
@Document(collection = "Rewards")
public class Reward {

    @JsonProperty
    private UUID id = UUID.randomUUID();

    @JsonProperty
    private String name;

    @JsonProperty
    private Business associatedBusiness;

    @JsonIgnore
    private ArrayList<String> rewardsList = new ArrayList<>() {
        {
            add("Free cinema tickets for two");
            add("A romantic dinner for two");
            add("A $50 Amazon voucher");
            add("A Sony Playstation 5");
            add("An all expense paid trip to Santorini");
        }
    };

    public Reward() {}
}
