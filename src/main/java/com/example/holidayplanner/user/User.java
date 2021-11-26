package com.example.holidayplanner.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Data
@Document(collection="User")
public class User {
    @MongoId
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private String password;

//    public User() {}
//
//    public User(Long id, String firstName, String lastName, LocalDate dob, String email) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.dob = dob;
//        this.email = email;
//    }
//
//    public User(String firstName, String lastName, LocalDate dob, String email) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.dob = dob;
//        this.email = email;
//    }
}
