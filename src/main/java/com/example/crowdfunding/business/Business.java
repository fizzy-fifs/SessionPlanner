package com.example.crowdfunding.business;

import com.example.crowdfunding.bankAccount.BankAccount;
import com.example.crowdfunding.project.Project;
import com.example.crowdfunding.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;

@Data
@Document(collection = "Businesses")
public class Business {

    @MongoId(value = FieldType.OBJECT_ID)
    @JsonProperty
    private String id;

    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Name can only contain letters")
    @JsonProperty
    private String name;

    @NotBlank(message = "Owners  cannot be blank")
    @JsonProperty
    private User owner;

    @NotBlank(message = "Description  cannot be blank")
    @JsonProperty
    private String description;

    @NotBlank(message = "Images  cannot be blank")
    @JsonProperty
    private ArrayList<String> images;

    @NotBlank(message = "Bank Account  cannot be blank")
    @JsonProperty
    private BankAccount bankAccount;

    @JsonProperty
    private ArrayList<Project> listedProjects;

    public Business(String name, User owner, String description, ArrayList<String> images, BankAccount bankAccount) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.images = images;
        this.bankAccount = bankAccount;
    }

    public void addToListedProjects(Project project) {
        listedProjects.add(project);
    }
}
