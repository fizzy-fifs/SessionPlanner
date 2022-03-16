package com.example.crowdfunding.user;

import com.example.crowdfunding.business.Business;
import com.example.crowdfunding.user.role.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Document(collection="Users")
public class User {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonProperty
    private String id;

    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Name can only contain letters")
    @JsonProperty
    private String name;

    @NotBlank(message = "User name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "User name can only contain letters, numbers and underscores")
    @JsonProperty
    private String userName;

    @JsonFormat( pattern = "dd/MM/yyyy" )
    @JsonProperty
    private LocalDate dob;

    @NotBlank(message = "Email cannot be blank")
    @Email(message="Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    @JsonProperty
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must have a minimum of 8 characters")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "Password must contain at least one number, one uppercase character and one lowercase character")
    @JsonProperty
    private String password;

    @JsonProperty
    private String image;

    @JsonProperty
    private Collection<Role> roles;

    @JsonProperty
    private ArrayList<Business> businesses;

    @JsonProperty
    private boolean enabled;

    @JsonProperty
    private boolean tokenExpired;

    public User() {}

    public User(String id, String name, String userName, LocalDate dob, String email, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.dob = dob;
        this.email = email;
        this.password = password;
    }

    public User(String name, String userName, LocalDate dob, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.dob = dob;
        this.email = email;
        this.password = password;
    }

    public void addToListOfBusinesses(Business business){
        businesses.add(business);
    }
}
