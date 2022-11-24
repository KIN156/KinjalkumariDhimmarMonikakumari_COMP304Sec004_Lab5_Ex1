package com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.adapters.CuisineRecyclerViewAdapter;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.interfaces.OnItemClickListener;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.models.Cuisine;

import java.util.ArrayList;

public class CuisineListActivity extends AppCompatActivity implements OnItemClickListener {

    //UI Related
    RecyclerView cuisineRecyclerView;

    //Adapter
    CuisineRecyclerViewAdapter cuisineRecyclerViewAdapter;

    //Cuisine data
    ArrayList<Cuisine> allCuisines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_list);

        //Initialize View
        this.cuisineRecyclerView = findViewById(R.id.cuisine_recycler_view);

        //Receive all cuisine datas
        this.allCuisines = getCuisines();

        //Initialize Adapter
        this.cuisineRecyclerViewAdapter = new CuisineRecyclerViewAdapter(this.getApplicationContext(),
                this, this.allCuisines);

        //Initialize Recycler View
        this.cuisineRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.cuisineRecyclerView.setAdapter(this.cuisineRecyclerViewAdapter);

    }

    private ArrayList<Cuisine> getCuisines() {
        ArrayList<Cuisine> cuisines = new ArrayList<>();
        cuisines.add(new Cuisine(getString(R.string.indian_cuisine), R.drawable.paneer));
        cuisines.add(new Cuisine(getString(R.string.italian_cuisine), R.drawable.pasta));
        cuisines.add(new Cuisine(getString(R.string.mexican_cuisine), R.drawable.tacos));
        cuisines.add(new Cuisine(getString(R.string.chinese_cuisine), R.drawable.ramen));
        cuisines.add(new Cuisine(getString(R.string.japanese_cuisine), R.drawable.sushi_roll));
        cuisines.add(new Cuisine(getString(R.string.thai_cuisine), R.drawable.thai));
        cuisines.add(new Cuisine(getString(R.string.boba_cuisine), R.drawable.boba));
        return cuisines;
    }

    @Override
    public void onItemClick(View view, int position) {
        //Executes when cuisine item is clicked
        Intent intent = new Intent(CuisineListActivity.this,
                RestaurantsListActivity.class);
        Cuisine cuisine = allCuisines.get(position);
        intent.putExtra("cuisine_title", cuisine.getCuisineName());
        startActivity(intent);
    }
}