package com.example.crowdfunding.config.stripe;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.math.BigDecimal;

public class CreatePayment {

        @SerializedName("items")
        Object[] items;

        public Object[] getItems() {
                return items;
        }

        public void setItems(Object[] items) {
                this.items = items;
        }
}
