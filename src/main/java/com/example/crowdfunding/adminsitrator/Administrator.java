package com.example.crowdfunding.adminsitrator;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;

@Document(collection = "Administrators")
public class Administrator {

    @MongoId(value = FieldType.OBJECT_ID)
    @JsonProperty
    private String id;

    @NotBlank(message = "Name cannot be blank")
    @JsonProperty
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @JsonProperty
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @JsonProperty
    private String password;

    @JsonProperty
    private Double percentageCut;

    public Administrator(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
