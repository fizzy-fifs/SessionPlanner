package com.example.holidayplanner.holiday;

import com.example.holidayplanner.availableDates.AvailableDates;
import com.example.holidayplanner.budget.Budget;
import com.example.holidayplanner.group.Group;
import com.example.holidayplanner.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HolidayTest {

    @Test
    void returnsBudgetInDesiredFormat() throws JsonProcessingException {

        User user1 = new User(
                "1",
                "User",
                "User",
                "User",
                LocalDate.of(2002, MAY, 13),
                "user@user.com",
                "Password1"
        );

        User user2 = new User(
                "2",
                "User2",
                "User2",
                "User2",
                LocalDate.of(2004, OCTOBER, 21),
                "user2@user.com",
                "Password1"
        );

        User user3 = new User(
                "3",
                "User3",
                "User3",
                "User3",
                LocalDate.of(2000, JANUARY, 11),
                "user3@user.com",
                "Password1"
        );

        ArrayList<User> groupMembers = new ArrayList<>(List.of(user1, user2, user3));
        ArrayList<User> parisCrew = new ArrayList<>(List.of(user1, user3));

        Group group = new Group("Holiday Gang", groupMembers);

        Budget budgetForUser1 = new Budget(user1, 300, 200);
        Budget budgetForUser3 = new Budget(user3, 400, 250);

        ArrayList<Budget> budgets = new ArrayList<>(List.of(budgetForUser1, budgetForUser3));

        Holiday holiday = new Holiday("Paris Getaway", group, parisCrew, budgets);


        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        var expectedResponse = objectMapper.writeValueAsString(budgets);

        assertEquals(expectedResponse, holiday.getBudget());
    }

    @Test
    void returnsDatesInDesiredFormat() throws JsonProcessingException {

        User user1 = new User(
                "1",
                "User",
                "User",
                "User",
                LocalDate.of(2002, MAY, 13),
                "user@user.com",
                "Password1"
        );

        User user2 = new User(
                "2",
                "User2",
                "User2",
                "User2",
                LocalDate.of(2004, OCTOBER, 21),
                "user2@user.com",
                "Password1"
        );

        User user3 = new User(
                "3",
                "User3",
                "User3",
                "User3",
                LocalDate.of(2000, JANUARY, 11),
                "user3@user.com",
                "Password1"
        );


        ArrayList<User> groupMembers = new ArrayList<>(List.of(user1, user2, user3));
        ArrayList<User> parisCrew = new ArrayList<>(List.of(user1, user3));

        Group group = new Group("Holiday Gang", groupMembers);

        Budget budgetForUser1 = new Budget(user1, 300, 200);
        Budget budgetForUser3 = new Budget(user3, 400, 250);

        AvailableDates datesForUser1 = new AvailableDates(user1, LocalDate.of(2022, JANUARY, 01), LocalDate.of(2022, JANUARY, 07), 3 );
        AvailableDates datesForUser3 = new AvailableDates(user1, LocalDate.of(2022, JANUARY, 04), LocalDate.of(2022, JANUARY, 11), 3 );

        ArrayList<Budget> budgets = new ArrayList<>(List.of(budgetForUser1, budgetForUser3));
        ArrayList<AvailableDates> dates = new ArrayList<>(List.of(datesForUser1, datesForUser3));

        Holiday holiday = new Holiday("Paris Getaway", group, parisCrew, budgets, dates);


        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        var expectedResponse = objectMapper.writeValueAsString(dates);


        System.out.println("expected response: " + expectedResponse);
        System.out.println("Actual response: " +  holiday.getAvailableDates());

        assertEquals(6, datesForUser1.getNights());
        assertEquals(7, datesForUser3.getNights());
        assertEquals(expectedResponse, holiday.getAvailableDates());
    }

}
