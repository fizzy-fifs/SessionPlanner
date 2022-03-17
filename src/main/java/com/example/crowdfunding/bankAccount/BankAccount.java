package com.example.crowdfunding.bankAccount;

import com.example.crowdfunding.address.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "Bank Accounts")
public class BankAccount {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private Address billingAddress;
}
