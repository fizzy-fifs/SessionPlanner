package com.example.holidayplanner.holiday;

import com.example.holidayplanner.budget.Budget;
import com.example.holidayplanner.group.Group;
import com.example.holidayplanner.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HolidayTest {

    @Test
    void returnsBudgetInDesiredFormat() throws IOException {

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


        System.out.println("expected response: " + expectedResponse);


        System.out.println("Actual response: " +  holiday.getBudget());
        assertEquals(expectedResponse, holiday.getBudget());
    }

}
