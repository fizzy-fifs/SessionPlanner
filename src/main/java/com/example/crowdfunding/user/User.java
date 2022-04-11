package com.example.crowdfunding.user;

import com.example.crowdfunding.bankAccount.BankAccount;
import com.example.crowdfunding.reward.Reward;
import com.example.crowdfunding.reward.RewardService;
import com.example.crowdfunding.user.role.Role;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Document(collection="Users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonProperty
    private String id;

    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name can only contain letters")
    @JsonProperty
    private String name;

    @NotBlank(message = "User name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "User name can only contain letters, numbers and underscores")
    @JsonProperty
    private String userName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message="Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    @JsonProperty
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must have a minimum of 8 characters")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", message = "Password must contain at least one number, one uppercase character and one lowercase character")
    @JsonProperty
    private String password;

    @JsonProperty
    private String image;

    @JsonProperty
    private BankAccount bankAccount;

    @JsonProperty
    private ArrayList<Reward> earnedRewards = new ArrayList<>();

    @JsonProperty
    private Collection<Role> roles;

    @JsonProperty
    private boolean enabled;

    @JsonProperty
    private boolean tokenExpired;

    @Autowired
    private RewardService rewardService;

    public User() {}

    public User(String id, String name, String userName, String email, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void addToRewardsList(Reward reward) {
        earnedRewards.add(reward);
    }
}
