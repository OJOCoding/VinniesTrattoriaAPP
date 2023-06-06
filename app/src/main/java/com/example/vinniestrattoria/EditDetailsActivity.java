/**
 * ONI LUCA 20200008@student.act.edu
 * CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 * EditDetailsActivity allows the user to edit the details of a reservation.
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

public class EditDetailsActivity extends AppCompatActivity {
    private EditText lnameEditText;
    private TextView lnametopText;
    private EditText numPeopleEditText;
    private EditText emailEditText;
    private Button updateButton;
    private Button cancelButton;
    private DataBaseHelper dbHelper;
    private String reservationCode;
    private Reservation reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdetails_activity_layout);

        // Initialize database helper
        dbHelper = new DataBaseHelper(this);

        // Get the reservation code from the intent
        Intent intent = getIntent();
        reservationCode = intent.getStringExtra("r_code");

        // Retrieve the reservation details from the database
        reservation = dbHelper.getReservationDetails(reservationCode);

        // Initialize the EditText fields
        lnametopText = findViewById(R.id.lnametop);
        lnameEditText = findViewById(R.id.lname);
        numPeopleEditText = findViewById(R.id.numpeople);
        emailEditText = findViewById(R.id.email);

        // Set the initial values in the EditText fields
        if (reservation != null) {
            lnametopText.setText(reservation.getLname());
            lnameEditText.setText(reservation.getLname());
            numPeopleEditText.setText(String.valueOf(reservation.getNumPeople()));
            emailEditText.setText(reservation.getEmail());
        }

        updateButton = findViewById(R.id.update);
        cancelButton = findViewById(R.id.cancel);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle update button click
                String lname = lnameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                int numPeople = Integer.parseInt(numPeopleEditText.getText().toString().trim());

                // Update the reservation details in the database
                boolean isUpdated = dbHelper.updateReservation(reservationCode, lname, numPeople, email);

                if (isUpdated) {
                    Toast.makeText(EditDetailsActivity.this, "Reservation details updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditDetailsActivity.this, DetailsActivity.class);
                    intent.putExtra("r_code", reservationCode);
                    startActivity(intent);
                    finish(); // Finish the EditDetailsActivity
                } else {
                    Toast.makeText(EditDetailsActivity.this, "Failed to update reservation details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle cancel button click
                Toast.makeText(EditDetailsActivity.this, "Edit canceled", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}