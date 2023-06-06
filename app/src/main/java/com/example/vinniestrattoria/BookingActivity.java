/**
    ONI LUCA 20200008@student.act.edu CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
    This activity allows the user to make a reservation by entering their details, such as last name,
    email, number of people, and date. It generates a unique reservation code for each reservation and
    inserts the reservation into the database. After successful creation, it displays a toast message
    with the credentials and navigates to the LoginActivity after a 5-second delay.
*/

package com.example.vinniestrattoria;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class BookingActivity extends AppCompatActivity {

    // Views
    private TextView codeTextView;
    private EditText lnameEditText, emailEditText, numPeopleEditText, dateEditText;
    private Button bookButton;

    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activity_layout);

        // Initialize views
        codeTextView = findViewById(R.id.code);
        lnameEditText = findViewById(R.id.lname);
        emailEditText = findViewById(R.id.email);
        numPeopleEditText = findViewById(R.id.numpeople);
        dateEditText = findViewById(R.id.date);
        bookButton = findViewById(R.id.book);

        // Initialize database helper
        dbHelper = new DataBaseHelper(this);

        // Set initial reservation code
        generateReservationCode();

        // Button click listener
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String lname = lnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                int numPeople = Integer.parseInt(numPeopleEditText.getText().toString());
                String date = dateEditText.getText().toString();

                // Generate a new reservation code if it already exists
                while (dbHelper.check(codeTextView.getText().toString())) {
                    generateReservationCode();
                }

                // Insert reservation into database
                boolean inserted = dbHelper.insertReservation(codeTextView.getText().toString(), lname, numPeople, email, date);

                if (inserted) {
                    Toast.makeText(BookingActivity.this, "Reservation created successfully!", Toast.LENGTH_SHORT).show();
                    showCredentialsToast(lname, codeTextView.getText().toString());
                    navigateToLogin();
                } else {
                    Toast.makeText(BookingActivity.this, "Failed to create reservation", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Generates a new reservation code
    private void generateReservationCode() {
        String code = generateRandomCode();
        codeTextView.setText("#" + code);
    }

    // Generates a random reservation code
    private String generateRandomCode() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "1234567890";
        StringBuilder code = new StringBuilder();

        // Generate two random letters
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            code.append(letters.charAt(random.nextInt(letters.length())));
        }

        // Generate three random numbers
        for (int i = 0; i < 3; i++) {
            code.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return code.toString();
    }

    // Displays a toast message with reservation credentials
    private void showCredentialsToast(String lname, String rCode) {
        String message = "Reservation created!\nLast Name: " + lname + "\nReservation Code: " + rCode + "\nThese are your credentials.";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // Navigates to the login activity after a delay
    private void navigateToLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(BookingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000); // Wait for 5 seconds before navigating to the login activity
    }
}
