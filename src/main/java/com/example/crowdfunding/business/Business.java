package com.example.crowdfunding.business;

import com.example.crowdfunding.address.Address;
import com.example.crowdfunding.bankAccount.BankAccount;
import com.example.crowdfunding.project.Project;
import com.example.crowdfunding.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @JsonProperty
    @JsonBackReference
    private User owner;

    @NotBlank(message = "Description  cannot be blank")
    @JsonProperty
    private String description;

    @JsonProperty
    private ArrayList<String> images;

    @JsonProperty
    private BankAccount bankAccount;

    @JsonProperty
    private Address address;


    public Business() {
    }

    public Business(String name, User owner, String description, BankAccount bankAccount) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.bankAccount = bankAccount;
    }

    public Business(String name, User owner, String description, ArrayList<String> images, BankAccount bankAccount) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.images = images;
        this.bankAccount = bankAccount;
    }

//    public void addToListedProjects(Project project) {
//        listedProjects.add(project);
//    }
}
