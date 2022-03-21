package com.example.crowdfunding.geocoding;

import com.example.crowdfunding.project.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.net.URLEncoder;

public class GeocodingService {

    public GeocodeLocation getLatAndLng(Project project) throws IOException {
        String address = project.getAddress().getConcatAddress();

        OkHttpClient client = new OkHttpClient();
        String encodedAddress = URLEncoder.encode(address, "UTF-8");
        Request request = new Request.Builder()
                .url("https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=en&address=" + encodedAddress)
                .get()
                .addHeader("x-rapidapi-host", "google-maps-geocoding.p.rapidapi.com")
                .addHeader("x-rapidapi-key", System.getenv("google_maps_api_key"))
                .build()
        ;

        ResponseBody responseBody = client.newCall(request).execute().body();

        ObjectMapper objectMapper = new ObjectMapper();
        GeocodeLocation result = objectMapper.readValue(responseBody.toString(), GeocodeLocation.class);

        return result;
    }
}
