package com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.R;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.interfaces.OnItemClickListener;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.models.Restaurant;

import java.util.ArrayList;

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.ViewHolder>{
    private final Context context;
    private static OnItemClickListener itemClickListener;
    private ArrayList<Restaurant> restaurants;

    public RestaurantRecyclerViewAdapter(Context context,
                                         OnItemClickListener itemClickListener,
                                         ArrayList<Restaurant> restaurants) {
        this.context = context;
        RestaurantRecyclerViewAdapter.itemClickListener = itemClickListener;
        this.restaurants = restaurants;
    }


    @NonNull
    @Override
    public RestaurantRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_layout_item,
                parent, false);
        return new RestaurantRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantRecyclerViewAdapter.ViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.restNameTextView.setText(restaurant.getName());
        holder.restRatingTextView.setText(String.valueOf(restaurant.getRatings()));
        holder.restReviewTextView.setText(String.valueOf(restaurant.getTotalUserRatings()));
        holder.restAddressTextView.setText(restaurant.getAddress());

        String openNowText = "Closed";
        if(restaurant.getOpenNow()) {
            openNowText = "Open";
        }
        holder.restOpenNow.setText(openNowText);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView restNameTextView;
        TextView restRatingTextView;
        TextView restReviewTextView;
        TextView restAddressTextView;
        TextView restOpenNow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restNameTextView = itemView.findViewById(R.id.card_rest_name);
            restRatingTextView = itemView.findViewById(R.id.card_rest_rating);
            restReviewTextView = itemView.findViewById(R.id.card_rest_reviews);
            restAddressTextView = itemView.findViewById(R.id.card_rest_address);
            restOpenNow = itemView.findViewById(R.id.card_rest_openNow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
