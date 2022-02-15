package com.example.holidayplanner.holiday;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HolidayRepository extends MongoRepository<Holiday, String> {
}
