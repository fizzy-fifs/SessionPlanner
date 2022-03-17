package com.example.crowdfunding.config.stripe;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePayment {

        @SerializedName("items")
        BigDecimal amount;
}
