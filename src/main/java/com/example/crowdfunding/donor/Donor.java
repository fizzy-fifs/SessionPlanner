package com.example.crowdfunding.donor;

import com.example.crowdfunding.reward.Reward;
import com.example.crowdfunding.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Donor {

    @MongoId(value = FieldType.OBJECT_ID)
    @JsonProperty
    public String id;

    @JsonProperty
    @JsonBackReference
    private User donor;

    @JsonProperty
    private double amountDonated;

    @JsonProperty
    private String rewardName;

    public Donor(User donor, double amountDonated, String rewardName) {
        this.donor = donor;
        this.amountDonated = amountDonated;
        this.rewardName = rewardName;
    }
}
