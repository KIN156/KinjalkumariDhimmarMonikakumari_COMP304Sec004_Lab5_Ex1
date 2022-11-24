package com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.network;

import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.constants.Constants;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GoogleMapsAPI {
    private String cuisine;
    private Request apiRequest;
    private OkHttpClient client;

    public GoogleMapsAPI(String cuisine){
        this.cuisine = cuisine.toLowerCase();
        client = new OkHttpClient().newBuilder().build();
        apiRequest = new Request.Builder()
                .url(Constants.BASE_URl +
                        "location="+ Constants.TORONTO_LAT+ "%2C"+ Constants.TORONTO_LON +"&"+
                        "radius="+ Constants.RADIUS + "&" +
                        "type=restaurant&" +
                        "keyword="+ cuisine +"&" +
                        "key="+Constants.MAPS_API_KEY)
                .method("GET", null)
                .build();
    }

    public ArrayList<Restaurant> getRestaurantsForCuisine() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        try {
            Response response = client.newCall(apiRequest).execute();
            if(response.body() != null && response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    String status = (String) jsonObject.get("status");
                    if(status.equals("OK")) {
                        JSONArray resultArray = (JSONArray) jsonObject.get("results");
                        int index;
                        for(index = 0; index < resultArray.length(); index++) {
                            JSONObject resultObject = (JSONObject) resultArray.get(index);
                            Restaurant restaurant = parseJSONObjectToRestaurant(resultObject);
                            if(restaurant != null) {
                                restaurants.add(restaurant);
                            }
                        }
                        return restaurants;
                    }else{
                        return null;
                    }
                }catch (JSONException jsonException) {
                    return null;
                }
            }
            return null;
        }catch (IOException ioException) {
            return null;
        }
    }

    private Restaurant parseJSONObjectToRestaurant(JSONObject resultObject) {
        Restaurant restaurant = null;
        if(resultObject.has("name") &&
                resultObject.has("rating") &&
                resultObject.has("user_ratings_total") &&
                resultObject.has("vicinity") &&
                resultObject.has("opening_hours") &&
                resultObject.has("geometry")
        ){
            try {
                String rest_name = resultObject.getString("name");
                Double rating = resultObject.getDouble("rating");
                int user_ratings_total =
                        resultObject.getInt("user_ratings_total");
                String vicinity = resultObject.getString("vicinity");
                JSONObject opening_hours =
                        resultObject.getJSONObject("opening_hours");
                JSONObject geometry = resultObject.getJSONObject("geometry");

                if (opening_hours.has("open_now") &&
                        geometry.has("location")) {
                    boolean isOpen = opening_hours.getBoolean("open_now");
                    JSONObject location = geometry.getJSONObject("location");
                    if (location.has("lat") && location.has("lng")) {
                        Double lat = location.getDouble("lat");
                        Double lng = location.getDouble("lng");

                        return new Restaurant(rest_name, vicinity, rating, user_ratings_total, lat,
                                lng, isOpen);
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
                return restaurant;
            }
        }
        return restaurant;
    }
}
