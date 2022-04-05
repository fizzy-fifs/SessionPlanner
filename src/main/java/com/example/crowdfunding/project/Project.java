package com.example.crowdfunding.project;

import com.example.crowdfunding.address.Address;
import com.example.crowdfunding.business.Business;
import com.example.crowdfunding.donor.Donor;
import com.example.crowdfunding.project.enums.Category;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Document(collection = "Projects")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Project {

    @MongoId(value = FieldType.OBJECT_ID)
    @JsonProperty
    private String id;

    @NotBlank(message = "Name cannot be blank")
    @JsonProperty
    private String title;

    @JsonProperty
    @NotBlank(message = "Please select a category")
    private Category category;

    @NotBlank(message = "Description cannot be blank")
    @JsonProperty
    private String description;


    @NotBlank(message = "Please specify the amount of money you intend to raise")
    @JsonProperty
    private double goal;

    @JsonProperty

    @NotBlank(message = "End date cannot be blank")
    public LocalDate endDate;

    @JsonProperty
    @NotBlank(message = "images cannot be blank")
    public ArrayList<String> images;

    @JsonProperty
    private double amountRaised = 0;

    @JsonProperty
    public LocalDate daysLeft;

    @Valid
    @NotBlank(message = "Please attach the relevant business")
    @JsonProperty
    @JsonBackReference
    private Business projectOwner;

    @JsonProperty
    @JsonManagedReference
    private ArrayList<Donor> projectDonors = new ArrayList<>();

    @JsonProperty
    private Address address;

    @JsonProperty
    private String Latitude;

    @JsonProperty
    private String Longitude;

    public Project() {
    }

    public Project(String title, Category category, String description, double goal, LocalDate endDate, ArrayList<String> images) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.goal = goal;
        this.endDate = endDate;
        this.images = images;
    }

    public Project(String name, String description, Category category, double goal, Business projectOwner, LocalDate endDate, ArrayList<String> images) {
        this.title = name;
        this.description = description;
        this.category = category;
        this.goal = goal;
        this.projectOwner = projectOwner;
        this.endDate = endDate;
        this.images = images;
    }

    public void addDonationToAmountRaised(double donation){
        amountRaised += donation;
    }

    public void addToDonorsList(Donor donor) {
        projectDonors.add(donor);
    }

}
