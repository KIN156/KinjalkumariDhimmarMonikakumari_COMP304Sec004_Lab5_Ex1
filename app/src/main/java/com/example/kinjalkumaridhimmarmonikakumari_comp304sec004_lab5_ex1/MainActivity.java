package com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    OkHttpClient client;
    Request apiRequest;
    String cuisine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cuisine = "indian";
        client = new OkHttpClient().newBuilder().build();
        apiRequest = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                        "location="+ Constants.TORONTO_LAT+ "%2C"+ Constants.TORONTO_LON +"&"+
                        "radius="+ Constants.RADIUS + "&" +
                        "type=restaurant&" +
                        "keyword="+ cuisine +"&" +
                        "key="+Constants.MAPS_API_KEY)
                .method("GET", null)
                .build();
        new GetNearbyRestaurantsApiTask().execute();
    }

    private class GetNearbyRestaurantsApiTask extends AsyncTask<Void, Void, ResponseBody> {

        @Override
        protected ResponseBody doInBackground(Void... voids) {
            try{
                Response response = client.newCall(apiRequest).execute();
                if(response.body() != null && response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    return responseBody;
                }
                return null;
            }catch (IOException ioException){
                System.out.println(ioException.getLocalizedMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(ResponseBody responseBody) {
            super.onPostExecute(responseBody);
            if(responseBody != null) {
                ResponseBody receivedResponse = responseBody;
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    System.out.println("json");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}