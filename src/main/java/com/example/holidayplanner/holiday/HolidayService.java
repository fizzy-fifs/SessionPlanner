package com.example.holidayplanner.holiday;

import com.example.holidayplanner.interfaces.ServiceInterface;
import com.example.holidayplanner.user.User;
import com.example.holidayplanner.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService implements ServiceInterface<Holiday> {
    private HolidayRepository holidayRepository;
    private UserRepository userRepository;

    public HolidayService(HolidayRepository holidayRepository, UserRepository userRepository) {
        this.holidayRepository = holidayRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String create(Holiday holiday) {
        holidayRepository.insert(holiday);
        return "Holiday has been successfully created";
    }

    public String[] aggregateHolidayBudgets(String holidayId){
        Holiday holiday = holidayRepository.findById(holidayId).get();
        return holiday.aggregateHolidayBudgets();
    }

    public String addHolidayMaker(String holidayId, String userId) {
        User newHolidayMaker = userRepository.findById(userId).get();

        if (newHolidayMaker == null) { return "User with user id" + userId + "does not exists"; }

        Holiday holiday = holidayRepository.findById(holidayId).get();
        holiday.addHolidayMaker(newHolidayMaker);

        holidayRepository.save(holiday);

        return  newHolidayMaker.getFirstName() + " has been successfully added to " + holiday.getName();
    }

    @Override
    public List<Holiday> getAll() {
        return null;
    }

    @Override
    public String update(String entityId, Holiday newEntityInfo) {
        return null;
    }

    @Override
    public String delete(String entityId) {
        return null;
    }


    public String[] aggregateDates(String holidayId) {
        Holiday holiday = holidayRepository.findById(holidayId).get();
        return holiday.aggregateDates();
    }
}
