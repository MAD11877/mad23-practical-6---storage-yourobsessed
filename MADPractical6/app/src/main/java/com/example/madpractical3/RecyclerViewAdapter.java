package com.example.madpractical3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewViewHolder> {


    Context context;
    List<User> users;

    private UserListOnClick listener;

    public RecyclerViewAdapter(Context context, List<User>userList, UserListOnClick listener){
        this.context = context;
        this.users = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new RecyclerViewViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {
        holder.username.setText(users.get(holder.getAdapterPosition()).getName());
        holder.description.setText(users.get(holder.getAdapterPosition()).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


}
