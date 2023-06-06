/**
 * ONI LUCA 20200008@student.act.edu
 * CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 * LoadMenuActivity is a loading screen menu with a curated paragraph describing the menu.
 * It starts the MenuActivity and passes the reservation code as an extra in the intent.
 */

package com.example.vinniestrattoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoadMenuActivity extends AppCompatActivity {
    private Button goToMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuload_activity_layout);

        // Get the reservation code from the intent
        Intent intent = getIntent();
        String reservationCode = intent.getStringExtra("r_code");

        goToMenuButton = findViewById(R.id.loadmenuButton);

        // Set click listener for the load menu button
        goToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle load menu button click
                Intent intent = new Intent(LoadMenuActivity.this, MenuActivity.class);
                intent.putExtra("r_code", reservationCode);
                startActivity(intent);
            }
        });
    }
}