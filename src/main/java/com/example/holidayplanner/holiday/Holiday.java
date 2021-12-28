package com.example.holidayplanner.holiday;

import com.example.holidayplanner.availableDates.AvailableDates;
import com.example.holidayplanner.budget.Budget;
import com.example.holidayplanner.group.Group;
import com.example.holidayplanner.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;

@Data
@Document(collection = "Holidays")
public class Holiday {

    @MongoId(value = FieldType.OBJECT_ID)
    private String id;

    private String name;

    private Group group;

    private ArrayList<User> holidayMakers;

    private ArrayList<Budget> budget;

    private ArrayList<AvailableDates> availableDates;


    public Holiday() {}

    public Holiday(String name) { this.name = name; }

    public Holiday(String name, Group group) {
        this.name = name;
        this.group = group;
    }

    public Holiday(String name, Group group, ArrayList<User> holidayMakers) {
        this.name = name;
        this.group = group;
        this.holidayMakers = holidayMakers;
    }

    public Holiday(String name, Group group, ArrayList<User> holidayMakers, ArrayList<Budget> budget) {
        this.name = name;
        this.group = group;
        this.holidayMakers = holidayMakers;
        this.budget = budget;
    }

    public Holiday(String name, Group group, ArrayList<User> holidayMakers, ArrayList<Budget> budget, ArrayList<AvailableDates> availableDates) {
        this.name = name;
        this.group = group;
        this.holidayMakers = holidayMakers;
        this.budget = budget;
        this.availableDates = availableDates;
    }

    public String getBudget()  {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        try {
            return objectMapper.writeValueAsString(this.budget);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return null;
    }

    public String getAvailableDates() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        try {
            return objectMapper.writeValueAsString(this.availableDates);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return null;
    }
}
