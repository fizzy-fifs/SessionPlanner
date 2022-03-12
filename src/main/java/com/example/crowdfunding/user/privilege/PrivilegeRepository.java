package com.example.crowdfunding.user.privilege;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivilegeRepository extends MongoRepository<Privilege,String> {
    Privilege findByName(String name);
}
