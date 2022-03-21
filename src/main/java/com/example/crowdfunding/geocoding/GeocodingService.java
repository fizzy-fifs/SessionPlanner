package com.example.crowdfunding.geocoding;

import com.example.crowdfunding.project.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

public class GeocodingService {

    public GeocodeLocation getLatAndLng(Project project) throws IOException, InterruptedException, ApiException {
        String address = project.getAddress().getConcatAddress();

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(System.getenv("google_maps_api_key"))
                .build()
        ;

        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonResults = gson.toJson(results[0].geometry.location);

        ObjectMapper objectMapper = new ObjectMapper();
        GeocodeLocation result = objectMapper.readValue(jsonResults, GeocodeLocation.class);

         return result;
    }
}
