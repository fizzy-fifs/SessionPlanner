package user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private LocalDate createdAt;
}
