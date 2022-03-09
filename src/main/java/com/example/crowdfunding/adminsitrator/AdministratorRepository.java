package com.example.crowdfunding.adminsitrator;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdministratorRepository extends MongoRepository<Administrator, String> {
}
