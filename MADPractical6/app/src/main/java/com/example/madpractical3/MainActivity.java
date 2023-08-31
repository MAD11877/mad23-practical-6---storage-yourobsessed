package com.example.madpractical3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Retrieve the random number from the intent
        Intent intent = getIntent();
        String Username_text = getIntent().getStringExtra("username");
        String Description_text = getIntent().getStringExtra("description");
        boolean isFollowed = getIntent().getBooleanExtra("followed", false);

        TextView username = findViewById(R.id.username);
        TextView description = findViewById(R.id.description);
        Button followButton = findViewById(R.id.follow);



        // Initialize the User object with the retrieved values
        User user = new User();

        user.followed = isFollowed;
        user.Name = Username_text;
        user.Description = Description_text;

        username.setText(Username_text);
        description.setText(Description_text);
        followButton.setText(isFollowed ? "Unfollow" : "Follow");

        // Add click listener to the follow button
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setFollowed(!user.isFollowed());
                followButton.setText(user.isFollowed() ? "Unfollow" : "Follow");
                Toast.makeText(getApplicationContext(), user.isFollowed() ? "Followed" : "Unfollowed", Toast.LENGTH_SHORT).show();

                MainDBHandler dbHandler = new MainDBHandler(MainActivity.this);
                dbHandler.updateUser(user);
            }
        });
    }
}