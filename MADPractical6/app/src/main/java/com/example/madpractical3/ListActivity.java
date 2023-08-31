package com.example.madpractical3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListActivity extends AppCompatActivity implements UserListOnClick{

    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        MainDBHandler dbHandler = new MainDBHandler(this);
        userList = dbHandler.getUsers();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), userList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        ImageView imageView2 = findViewById(R.id.imageView2);
        User user = userList.get(position);
        // Check if the last digit is 7
        if (user.getName().endsWith("7")) {
            imageView2.setVisibility(View.VISIBLE);
        } else {
            String username = user.getName();
            String description = user.getDescription();
            boolean followed = user.isFollowed();
            showAlertDialog(username, description, followed, position);
        }
    }

    private void showAlertDialog(String username, String description, boolean followed, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Profile");
        builder.setMessage(username);
        builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String TAG = "user info";
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("description", description);
                intent.putExtra("position", position);
                intent.putExtra("followed", followed);
                Log.d(TAG, "followed: " + followed);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private int generateRandomNumber() {
        // Generate a random integer between 0 and 99999 (inclusive)
        return (int) (Math.random() * 999999999);
    }
}

