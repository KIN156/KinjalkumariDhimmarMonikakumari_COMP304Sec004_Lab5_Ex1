package com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.adapters.RestaurantRecyclerViewAdapter;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.interfaces.OnItemClickListener;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.models.Restaurant;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.network.GoogleMapsAPI;

import java.util.ArrayList;

public class RestaurantsListActivity extends AppCompatActivity implements OnItemClickListener {

    //UI Related
    RecyclerView restaurantRecyclerView;
    TextView restaurantTitleTextView;
    ProgressBar progressBar;
    CardView progressCard;

    RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;

    String cuisineTitle;

    //Data
    ArrayList<Restaurant> allRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);

        //UI Initialization
        restaurantRecyclerView = findViewById(R.id.restaurant_recycler_view);
        restaurantTitleTextView = findViewById(R.id.cuisine_title);
        progressBar = findViewById(R.id.progressBar);
        progressCard = findViewById(R.id.progress_card);
        progressCard.setVisibility(View.GONE);

        //Set title
        cuisineTitle = getCuisineTitleValueFromIntent();
        restaurantTitleTextView.setText(cuisineTitle);

        allRestaurants = new ArrayList<>();

        new GetRestaurantsForCuisine().execute();

    }

    private String getCuisineTitleValueFromIntent() {
        String title = "";
        Intent intent = getIntent();

        if (intent.hasExtra("cuisine_title")) {
            title = intent.getStringExtra("cuisine_title");
        }
        return title;
    }

    //Async Tasks
    private class GetRestaurantsForCuisine extends AsyncTask<Void, Void, ArrayList<Restaurant>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressCard.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Restaurant> doInBackground(Void... voids) {
            GoogleMapsAPI googleMapsAPI = new GoogleMapsAPI(cuisineTitle);
            return googleMapsAPI.getRestaurantsForCuisine();
        }

        @Override
        protected void onPostExecute(ArrayList<Restaurant> restaurants) {
            super.onPostExecute(restaurants);
            progressCard.setVisibility(View.GONE);
            if(restaurants != null) {
                allRestaurants = restaurants;
                restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(
                        RestaurantsListActivity.this,
                        RestaurantsListActivity.this, allRestaurants);
                restaurantRecyclerView.setLayoutManager(
                        new LinearLayoutManager(RestaurantsListActivity.this));
                restaurantRecyclerView.setAdapter(restaurantRecyclerViewAdapter);

            }else{
                Toast.makeText(RestaurantsListActivity.this,
                        "Unable to get restaurants for cuisine", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //Executes when restaurant item is clicked
        Restaurant restaurant = allRestaurants.get(position);
        Intent intent = new Intent(RestaurantsListActivity.this,
                RestaurantMapLocationActivity.class);
        intent.putExtra("restaurant_lat", restaurant.getLatitude());
        intent.putExtra("restaurant_lng", restaurant.getLongitude());
        intent.putExtra("restaurant_name", restaurant.getName());
        startActivity(intent);
    }
}