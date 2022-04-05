package com.example.crowdfunding.config;

import com.example.crowdfunding.donor.Donor;
import com.example.crowdfunding.reward.Reward;
import com.example.crowdfunding.user.User;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import lombok.Data;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.util.Collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class GoogleSheetsService {

    public Spreadsheet create() throws IOException {

        /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
            guides on implementing OAuth2 for your application. */
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);

        // Create the sheets API client
        Sheets service = new Sheets.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Sheets samples")
                .build();


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

        /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
            guides on implementing OAuth2 for your application. */
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);

        // Create the sheets API client
        Sheets service = new Sheets.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Sheets samples")
                .build();


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

        AppendValuesResponse result = null;
        try {
            ValueRange body = new ValueRange()
                    .setValues(values)
                    .setMajorDimension("ROWS");
            result = service.spreadsheets().values().append(spreadsheetId, "Sheet1", body)
                            .setValueInputOption("USER_ENTERED")
                            .execute();
            System.out.printf("%d cells updated.", result.getUpdates().getUpdatedCells());
        }catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 404) {
                System.out.printf("Spreadsheet not found with id '%s'.\n",spreadsheetId);
            } else {
                throw e;
            }
        }

        // [END sheets_update_values]
        return result;
    }
}
