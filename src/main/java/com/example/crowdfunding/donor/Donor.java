package com.example.crowdfunding.donor;

import com.example.crowdfunding.reward.Reward;
import com.example.crowdfunding.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Donor {

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
