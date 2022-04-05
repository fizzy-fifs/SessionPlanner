package com.example.crowdfunding.reward;

import com.example.crowdfunding.project.Project;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;

@Data
@Document(collection = "Rewards")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class Reward {

    @JsonProperty
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    @JsonProperty
    private String name;

    @JsonBackReference
    private Project associatedProject;

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
