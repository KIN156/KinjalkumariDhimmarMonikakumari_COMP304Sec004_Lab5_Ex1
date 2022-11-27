package com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.adapters.RestaurantRecyclerViewAdapter;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.interfaces.OnItemClickListener;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.models.Restaurant;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.network.GoogleMapsAPI;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

public class RestaurantsListActivity extends AppCompatActivity implements OnItemClickListener {

    //UI Related
    RecyclerView restaurantRecyclerView;
    TextView restaurantTitleTextView;
    ProgressBar progressBar;
    CardView progressCard;
    RadioGroup mapsRadioGroup;
    LinearLayout mapLinearLayoutButton;
    RadioButton normalRadioButton;
    Button saveMapTypeButton;
    Button cancelMapTypeButton;
    CardView mapOptions;

    RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;

    private String cuisineTitle;

    private int lastVisibleItem = 0;
    private int visibleThreshold = 5;

    GoogleMapsAPI googleMapsAPI;

    boolean isFetchInProgress = false;

    //Data
    ArrayList<Restaurant> allRestaurants;
    LinearLayoutManager linearLayoutManager;

    enum MapTypes {
        NORMAL(GoogleMap.MAP_TYPE_NORMAL),
        HYBRID(GoogleMap.MAP_TYPE_HYBRID),
        TERRAIN(GoogleMap.MAP_TYPE_TERRAIN),
        SATELLITE(GoogleMap.MAP_TYPE_SATELLITE);

        private int mapType;
        public int getMapType() {
            return mapType;
        }
        private MapTypes(int mapType) {
            this.mapType = mapType;
        }
    }
    int chosenMapType;
    int currentMapType;

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
        mapLinearLayoutButton = findViewById(R.id.map_btn_layout);
        mapsRadioGroup = findViewById(R.id.map_type_radioGroup);
        normalRadioButton = findViewById(R.id.radio_normal);
        saveMapTypeButton = findViewById(R.id.save_mapType_btn);
        cancelMapTypeButton = findViewById(R.id.cancel_map_btn);
        mapOptions = findViewById(R.id.map_options_card);

        //Map type by default
        chosenMapType = MapTypes.NORMAL.getMapType();
        mapsRadioGroup.check(R.id.radio_normal);

        //Set title
        cuisineTitle = getCuisineTitleValueFromIntent();
        restaurantTitleTextView.setText(cuisineTitle);

        allRestaurants = new ArrayList<>();

        //Map Options card must be invisible initially
        mapOptions.setVisibility(View.GONE);

        //On Click
        mapLinearLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Map Options
                if(mapOptions.getVisibility() == View.GONE) {
                    mapOptions.setVisibility(View.VISIBLE);
                }
            }
        });
        saveMapTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set current map type as chosen Map type
                currentMapType = chosenMapType;
                mapOptions.setVisibility(View.GONE);
            }
        });
        cancelMapTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dismiss Map options window
                mapOptions.setVisibility(View.GONE);
            }
        });

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

    public void onMapRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_normal:
                if (checked)
                    // Normal Chosen
                    chosenMapType = MapTypes.NORMAL.getMapType();
                    break;
            case R.id.radio_hybrid:
                if (checked)
                    // Hybrid Chosen
                    chosenMapType = MapTypes.HYBRID.getMapType();
                    break;
            case R.id.radio_terrain:
                if (checked)
                    // Terrain Chosen
                    chosenMapType = MapTypes.TERRAIN.getMapType();
                    break;
            case R.id.radio_satellite:
                if (checked)
                    // Satellite Chosen
                    chosenMapType = MapTypes.SATELLITE.getMapType();
                    break;
        }
    }

    private class GetMoreRestaurantsForCuisine extends AsyncTask<Void, Void, ArrayList<Restaurant>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressCard.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Restaurant> doInBackground(Void... voids) {
            if(googleMapsAPI != null) {
                return googleMapsAPI.getMoreRestaurantsForCuisine();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Restaurant> restaurants) {
            super.onPostExecute(restaurants);
            isFetchInProgress = false;
            progressCard.setVisibility(View.GONE);
            if(restaurants != null) {
                int lastItemIndexBeforeAdding = allRestaurants.size()-1;
                allRestaurants.addAll(restaurants);
                restaurantRecyclerViewAdapter
                        .notifyItemRangeInserted(lastItemIndexBeforeAdding, restaurants.size());
            }
        }
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
            googleMapsAPI = new GoogleMapsAPI(cuisineTitle);
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
                linearLayoutManager = new LinearLayoutManager(RestaurantsListActivity.this);
                restaurantRecyclerView.setLayoutManager(linearLayoutManager);
                restaurantRecyclerView.setAdapter(restaurantRecyclerViewAdapter);
                restaurantRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager
                                .findLastVisibleItemPosition();
                        //20 <= (15 + 5)
                        if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            // End has been reached
                            // Do something
                            if(!isFetchInProgress && googleMapsAPI != null
                                    && !googleMapsAPI.getPAGE_TOKEN().equals("")){
                                isFetchInProgress = true;
                                new GetMoreRestaurantsForCuisine().execute();
                            }
                        }
                    }
                });

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
        intent.putExtra("map_type", chosenMapType);
        startActivity(intent);
    }
}