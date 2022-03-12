package com.example.crowdfunding.user.role;

import com.example.crowdfunding.user.privilege.Privilege;
import com.example.crowdfunding.user.User;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Collection;

@Data
@Document(collection = "Roles")
public class Role {

    @MongoId(value = FieldType.OBJECT_ID)
    private String id;

    private String name;

    private Collection<User> users;

    private Collection<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }
}
