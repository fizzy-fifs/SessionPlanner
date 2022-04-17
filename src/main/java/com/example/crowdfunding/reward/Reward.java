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
    private double minimumThreshold;

    @JsonProperty
    private String minimumThresholdReward;

    @JsonProperty
    private double higherThreshold;

    @JsonProperty
    private String higherThresholdReward;

    public Reward() {}
}
