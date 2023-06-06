/**
ONI LUCA 20200008@student.act.edu
CS300-MOBILE APPLICATIONS PROJECT SUBMISSION

LoginActivity handles user login and credential validation.
It allows users to enter their last name and reservation code to proceed to the DetailsActivity.
Users can also navigate to the BookingActivity to create a new reservation.

*/

package com.example.vinniestrattoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Declare variables
    private EditText lnameEditText;
    private EditText codeEditText;
    private Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        // Set up "New Reservation" button to open BookingActivity
        TextView textViewButton = findViewById(R.id.newreservation);
        textViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });

        // Initialize views
        lnameEditText = findViewById(R.id.lname);
        codeEditText = findViewById(R.id.code);
        proceedButton = findViewById(R.id.book);

        // Set up "Proceed" button click listener
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    // Method to handle login process
    private void login() {
        String lname = lnameEditText.getText().toString().trim();
        String r_code = codeEditText.getText().toString().trim();

        // Check if the credentials are valid
        boolean isValid = isValidCredentials(lname, r_code);

        // Perform necessary formatting and checks
        if (!r_code.startsWith("#")) {
            r_code = "#" + r_code;
        }
        r_code = r_code.toUpperCase();

        if (isValid) {
            // Proceed to DetailsActivity
            Intent intent = new Intent(LoginActivity.this, DetailsActivity.class);
            intent.putExtra("lname", lname);
            intent.putExtra("r_code", r_code);
            startActivity(intent);
            finish();
        } else {
            // Invalid credentials, show a toast message
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to validate user credentials
    private boolean isValidCredentials(String lname, String r_code) {
        if (lname.isEmpty()) {
            lnameEditText.setError("Last name is required");
            lnameEditText.requestFocus();
            return false;
        }

        if (r_code.isEmpty()) {
            codeEditText.setError("Reservation code is required");
            codeEditText.requestFocus();
            return false;
        }

        // Additional verification checks

        // Add "#" symbol to the reservation code if missing and convert to uppercase
        if (!r_code.startsWith("#")) {
            r_code = "#" + r_code;
        }
        r_code = r_code.toUpperCase();

        // Check if the reservation exists in the database
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        return dbHelper.checkReservation(r_code, lname);
    }
}
