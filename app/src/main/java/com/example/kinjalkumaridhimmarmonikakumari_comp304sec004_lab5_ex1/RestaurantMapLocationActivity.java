package com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.constants.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.databinding.ActivityRestaurantMapLocationBinding;

public class RestaurantMapLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityRestaurantMapLocationBinding binding;
    private double longitude;
    private double latitude;
    private String restName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRestaurantMapLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Get lat and long from intent
        getLocationValuesFromIntent();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng torontoRestaurantLocation = new LatLng(this.latitude, this.longitude);
        mMap.addMarker(new MarkerOptions().position(torontoRestaurantLocation).title(this.restName));
        mMap.animateCamera(CameraUpdateFactory
                .newLatLngZoom(torontoRestaurantLocation, 20.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void getLocationValuesFromIntent() {
        Intent intent = getIntent();
        if(intent.hasExtra("restaurant_lat") &&
                intent.hasExtra("restaurant_lng") &&
                intent.hasExtra("restaurant_name")
        ) {
            this.latitude = intent.getDoubleExtra("restaurant_lat",
                    Double.parseDouble(Constants.TORONTO_LAT));
            this.longitude = intent.getDoubleExtra("restaurant_lng",
                    Double.parseDouble(Constants.TORONTO_LON));
            this.restName = intent.getStringExtra("restaurant_name");
        }
    }
}