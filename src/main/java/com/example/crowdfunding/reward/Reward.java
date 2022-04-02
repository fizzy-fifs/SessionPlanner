package com.example.crowdfunding.reward;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;

@Data
@Document(collection = "Rewards")
public class Reward {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    private ArrayList<String> rewardsList = new ArrayList<>() {
        {
            add("Two cinema tickets");
            add("Romantic dinner for two");
            add("$50 Amazon voucher");
            add("Sony Playstation 5");
            add("All expense paid trip to Santorini");
        }
    };

    public Reward() {}
}
