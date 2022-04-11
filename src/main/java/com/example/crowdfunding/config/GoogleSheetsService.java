package com.example.crowdfunding.config;

import com.example.crowdfunding.donor.Donor;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class GoogleSheetsService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public File create() throws IOException, GeneralSecurityException {

        // Create Google drive
        GoogleCredentials driveCredentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Arrays.asList(DriveScopes.DRIVE, SheetsScopes.SPREADSHEETS))
        ;
        HttpRequestInitializer driveRequestInitializer = new HttpCredentialsAdapter(
                driveCredentials)
        ;
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, driveRequestInitializer)
                .setApplicationName("Funded Local")
                .build()
        ;

        // Create sheets and put in drive
        File file = new File();
        file.setName("List of Donors and rewards");
        file.setMimeType("application/vnd.google-apps.spreadsheet");
        File spreadsheet = drive.files().create(file).execute();

        /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
            guides on implementing OAuth2 for your application. */
        GoogleCredentials sheetCredentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Arrays.asList(SheetsScopes.SPREADSHEETS, DriveScopes.DRIVE));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                sheetCredentials);

        // Create the sheets API client
        Sheets service = new Sheets.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Funded Local")
                .build()
        ;

        //Set column headers
        ValueRange columnNames = new ValueRange()
            .setValues(
                Collections.singletonList(
                    Arrays.asList( "Donor's Name", "Donor's Email", "Amount Donated($)", "Reward", "Reward Id" ))
            )
            .setMajorDimension("ROWS")
        ;

        service.spreadsheets().values()
            .append(spreadsheet.getId(), "Sheet1", columnNames)
            .setValueInputOption("USER_ENTERED")
            .execute()
        ;


        //Set Permission to anyone
        insertPermission(drive, spreadsheet.getId());

        return spreadsheet;
    }

    public AppendValuesResponse addValues(String spreadsheetId, ArrayList<Donor> projectDonors)
            throws IOException {

        /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
            guides on implementing OAuth2 for your application. */
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Arrays.asList(DriveScopes.DRIVE, SheetsScopes.SPREADSHEETS));
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

    public String getSheetUrl(String spreadsheetId) {

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);

        String url = "https://sheets.googleapis.com/v4/spreadsheets/" + spreadsheetId + "?key=" + System.getenv("google_sheets_api_key");
        Object sheet = restTemplate.getForObject(url, Object.class);

        System.out.println(sheet);

        Spreadsheet sheet1 = (Spreadsheet) sheet;
        System.out.println(sheet1);

        return sheet1.getSpreadsheetUrl();

    }

    private Permission insertPermission(Drive service, String fileId) throws IOException {
        Permission newPermission = new Permission();
        newPermission.setType("anyone");
        newPermission.setRole("reader");
        return service.permissions().create(fileId, newPermission).execute();
    }
}
