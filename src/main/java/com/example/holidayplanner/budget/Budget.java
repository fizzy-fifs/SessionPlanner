package com.example.holidayplanner.budget;

import com.example.holidayplanner.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "Budgets")
public class Budget {

    @MongoId(value = FieldType.OBJECT_ID)
    @JsonProperty
    private String id;

    @JsonProperty
    private User user;

    @JsonProperty
    private int budgetUpperLimit;

    @JsonProperty
    private int budgetLowerLimit;



    public Budget(User user, int budgetUpperLimit, int budgetLowerLimit) {
        this.user = user;
        this.budgetUpperLimit = budgetUpperLimit;
        this.budgetLowerLimit = budgetLowerLimit;
    }

}
