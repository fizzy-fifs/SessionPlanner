package user;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Data
@Document(collection="User")
public class User {
    @MongoId
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
}
