package com.example.crowdfunding.donor;

import com.example.crowdfunding.reward.Reward;
import com.example.crowdfunding.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Data
public class Donor {

    @MongoId(value = FieldType.OBJECT_ID)
    @JsonProperty
    public String id;

    @JsonProperty
    private User donor;

    @JsonProperty
    private BigDecimal amountDonated;

    @JsonProperty
    private Reward reward;

    public Donor(User donor, BigDecimal amountDonated, Reward reward) {
        this.donor = donor;
        this.amountDonated = amountDonated;
        this.reward = reward;
    }
}
