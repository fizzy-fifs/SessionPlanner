package com.example.crowdfunding.config.stripe;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.math.BigDecimal;

public class CreatePayment {

        @SerializedName("amount")
        BigDecimal amount;

        public BigDecimal getAmount() {
                return amount;
        }

        public void setItems(BigDecimal amount) {
                this.amount = amount;
        }
}
