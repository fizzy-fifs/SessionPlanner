package com.example.crowdfunding.config;

import com.example.crowdfunding.donor.Donor;
import com.example.crowdfunding.reward.Reward;
import com.example.crowdfunding.user.User;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class GoogleSheetsService {
    private Sheets service;

    public Spreadsheet create() throws IOException {
        Spreadsheet spreadsheet = new Spreadsheet()
                .setProperties(new SpreadsheetProperties()
                        .setTitle("List of Donors and Rewards")
                );
        spreadsheet = service.spreadsheets().create(spreadsheet)
                .setFields("spreadsheetId")
                .execute()
        ;

        //Set column headers
        ValueRange columnNames = new ValueRange()
                .setValues(
                        Collections.singletonList(
                        Arrays.asList( "Donor's Name", "Donor's Email", "Amount Donated", "Reward", "Reward Id" ))
                )
                .setMajorDimension("ROWS")
        ;

        service.spreadsheets().values()
                .append(spreadsheet.getSpreadsheetId(), "Sheet1", columnNames)
                .setValueInputOption("USER_ENTERED")
                .execute()
        ;

        return spreadsheet;
    }

    public AppendValuesResponse addValues(String spreadsheetId, ArrayList<Donor> projectDonors)
            throws IOException {
        Sheets service = this.service;
        // [START sheets_update_values]

        // Arrange values to be added to the sheet
        List<List<Object>> values = new ArrayList<List<Object>>();

        for (var donor : projectDonors){
           List eachValue =  Arrays.asList(
                    donor.getDonor().getName(),
                    donor.getDonor().getEmail(),
                    donor.getAmountDonated(),
                    donor.getReward().getName(),
                    donor.getReward().getId()
           );
           values.add(eachValue);
        }

        ValueRange body = new ValueRange()
                .setValues(values)
                .setMajorDimension("ROWS");
        AppendValuesResponse result =
                service.spreadsheets().values().append(spreadsheetId, "Sheet1", body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();
        System.out.printf("%d cells updated.", result);
        // [END sheets_update_values]
        return result;
    }
}
