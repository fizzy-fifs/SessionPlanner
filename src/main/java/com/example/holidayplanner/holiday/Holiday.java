package com.example.holidayplanner.holiday;

import com.example.holidayplanner.availableDates.AvailableDates;
import com.example.holidayplanner.budget.Budget;
import com.example.holidayplanner.group.Group;
import com.example.holidayplanner.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Median;
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

//  Constructors
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

//    Methods
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

    public void addHolidayMaker(User newHolidayMaker){ this.holidayMakers.add(newHolidayMaker); }

    public void removeHolidayMaker(String id) {
        this.holidayMakers.removeIf(
                holidayMaker -> holidayMaker.getId().equals(id));
    }

    public double[] aggregateHolidayBudgets() {

        double medianBudget[] = new double[this.budget.size()];

        for (int i=0; i<this.budget.size(); i++) {
            var median = calculateMedian(this.budget.get(i).getBudgetUpperLimit(), this.budget.get(i).getBudgetLowerLimit());
            medianBudget[i] = median;
        }

        var findMean = new Mean();
        var average = findMean.evaluate(medianBudget);

//        var findSd = new StandardDeviation();
//        var sd = findSd.evaluate(medianBudget);

        var sd = calculateStandardDeviation(medianBudget);

        var aggregatedBudget = new double[]{average - sd, average, average + sd};
        return aggregatedBudget;
    }

    private double calculateMedian(double upperLimit, double lowerLimit) {

        var range = (upperLimit - lowerLimit) + 1;
        var medianIndex = Math.floor(range / 2);

        if (range % 2 != 0) {
            return lowerLimit + medianIndex;
        }else {
            return ( (lowerLimit + medianIndex) + (lowerLimit + medianIndex) - 1) / 2;
        }
    }

    private double calculateStandardDeviation(double[] medianBudget) {

        var findMean = new Mean();
        var mean = findMean.evaluate(medianBudget);

        double[] squaredDifferences = new double[medianBudget.length];
        for (var median : medianBudget) {
            var diff = median - mean;
            var square = diff * diff;
            squaredDifferences[(int) median] = square;
        }

        var meanOfSquaredDifferences = findMean.evaluate(squaredDifferences);
        return Math.sqrt(meanOfSquaredDifferences);
    }


}
