package com.example.crowdfunding.user.privilege;

import com.example.crowdfunding.user.role.Role;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Collection;

@Data
@Document(collection = "Privileges")
public class Privilege {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String name;

    private Collection<Role> roles;

    public Privilege(String name) {
        this.name = name;
    }
}
