package com.example.madpractical3;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView username,description;

    public RecyclerViewViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        username = itemView.findViewById(R.id.username);
        description = itemView.findViewById(R.id.description);

    }
}
