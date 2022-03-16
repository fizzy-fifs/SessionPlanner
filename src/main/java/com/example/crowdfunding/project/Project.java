package com.example.crowdfunding.project;

import com.example.crowdfunding.business.Business;
import com.example.crowdfunding.project.enums.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @DecimalMin(value ="0.00", inclusive = false)
    @Digits(integer = 7, fraction = 2)
    @NotBlank(message = "Please specify the amount of money you intend to raise")
    @JsonProperty
    private BigDecimal goal;

//    @JsonProperty
//    @NotBlank(message = "End date cannot be blank")
    public LocalDate endDate;

    @JsonProperty
    @NotBlank(message = "images cannot be blank")
    public ArrayList<String> images;

    @JsonProperty
    private BigDecimal amountRaised;

//    @JsonProperty
    public LocalDate daysLeft;

    @Valid
    @NotBlank(message = "Please attach the relevant business")
    @JsonProperty
    private Business projectOwner;

    public Project() {
    }

    public Project(String title, Category category, String description, BigDecimal goal, LocalDate endDate, ArrayList<String> images) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.goal = goal;
        this.endDate = endDate;
        this.images = images;
    }

    public Project(String name, String description, Category category, BigDecimal goal, Business projectOwner, LocalDate endDate, ArrayList<String> images) {
        this.title = name;
        this.description = description;
        this.category = category;
        this.goal = goal;
        this.projectOwner = projectOwner;
        this.endDate = endDate;
        this.images = images;
    }


}
