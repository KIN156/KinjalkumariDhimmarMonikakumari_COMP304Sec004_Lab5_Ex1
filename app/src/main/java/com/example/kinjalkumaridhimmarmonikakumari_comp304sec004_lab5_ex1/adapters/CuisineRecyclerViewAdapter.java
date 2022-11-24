package com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.R;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.interfaces.OnItemClickListener;
import com.example.kinjalkumaridhimmarmonikakumari_comp304sec004_lab5_ex1.models.Cuisine;

import java.util.ArrayList;

public class CuisineRecyclerViewAdapter extends RecyclerView.Adapter<CuisineRecyclerViewAdapter.ViewHolder>{

    private final Context context;
    private static OnItemClickListener itemClickListener;
    private ArrayList<Cuisine> cuisines;

    public CuisineRecyclerViewAdapter(Context context, OnItemClickListener itemClickListener,
                                      ArrayList<Cuisine> cuisines){
        this.context = context;
        CuisineRecyclerViewAdapter.itemClickListener = itemClickListener;
        this.cuisines = cuisines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cusisine_layout_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cuisine cuisine = cuisines.get(position);
        holder.cuisineIDText.setText(cuisine.getCuisineName());
        holder.cuisineImageView.setImageResource(cuisine.getDrawableImageResource());
    }

    @Override
    public int getItemCount() {
        return this.cuisines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cuisineIDText;
        ImageView cuisineImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cuisineIDText = itemView.findViewById(R.id.cuisine_textview_id);
            cuisineImageView = itemView.findViewById(R.id.cuisine_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
