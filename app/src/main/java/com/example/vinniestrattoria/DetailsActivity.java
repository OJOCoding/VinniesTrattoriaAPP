/**
 * ONI LUCA 20200008@student.act.edu CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 * DetailsActivity displays the details of a reservation and provides options to edit or delete it.
 * It retrieves the reservation details from the database based on the reservation code received
 * from the intent. The activity displays the last name, email, number of people, date, and reservation code.
 * It allows the user to load the menu for the reservation, edit the reservation details, or cancel the reservation.
 * If the user chooses to cancel the reservation, a confirmation dialog is shown. Upon cancellation,
 * the reservation is deleted from the database and the user is redirected to the login screen.
 * If the user chooses to edit the reservation details, the EditDetailsActivity is launched. After editing,
 * the updated details are displayed and the reservation is updated in the database.
 */


package com.example.vinniestrattoria;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    private TextView lnameTextView;
    private TextView lnametopTextView;
    private TextView emailTextView;
    private TextView numPeopleTextView;
    private TextView dateTextView;
    private TextView codeTextView;
    private Button loadMenuButton;
    private Button editInfoButton;
    private Button deleteButton;
    private DataBaseHelper dbHelper;
    private Reservation reservation;

    private static final int EDIT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity_layout);

        // Initialize database helper
        dbHelper = new DataBaseHelper(this);

        // Get the reservation code from the intent
        Intent intent = getIntent();
        String reservationCode = intent.getStringExtra("r_code");

        // Retrieve the reservation details from the database
        Reservation reservation = dbHelper.getReservationDetails(reservationCode);

        // Initialize the TextViews
        lnametopTextView = findViewById(R.id.lnametop);
        lnameTextView = findViewById(R.id.lname);
        emailTextView = findViewById(R.id.email);
        numPeopleTextView = findViewById(R.id.numpeople);
        dateTextView = findViewById(R.id.date);
        codeTextView = findViewById(R.id.code);

        // Update the TextViews with the reservation details
        if (reservation != null) {
            lnametopTextView.setText(reservation.getLname());
            lnameTextView.setText(reservation.getLname());
            emailTextView.setText(reservation.getEmail());
            numPeopleTextView.setText(String.valueOf(reservation.getNumPeople()));
            dateTextView.setText(reservation.getDate());
            codeTextView.setText(reservation.getRCode());
        }

        loadMenuButton = findViewById(R.id.loadmenu);
        editInfoButton = findViewById(R.id.editinfo);
        deleteButton = findViewById(R.id.delete);

        // Set click listeners
        loadMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle load menu button click
                Intent intent = new Intent(DetailsActivity.this, LoadMenuActivity.class);
                intent.putExtra("r_code", reservation.getRCode());
                startActivity(intent);
            }
        });

        editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit info button click
                Intent intent = new Intent(DetailsActivity.this, EditDetailsActivity.class);
                intent.putExtra("r_code", reservation.getRCode());
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailsActivity.this);
                alertDialogBuilder.setTitle("Confirm Cancellation");
                alertDialogBuilder.setMessage("Are you sure you want to cancel your reservation?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDeleted = dbHelper.deleteReservation(reservationCode);
                        if (isDeleted) {
                            Toast.makeText(DetailsActivity.this, "Row deleted successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailsActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(DetailsActivity.this, "Failed to delete the row", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialogBuilder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Retrieve the updated reservation details from the intent
            String rCode = data.getStringExtra("r_code");
            String lname = data.getStringExtra("lname");
            int numPeople = data.getIntExtra("numpeople", 0);
            String email = data.getStringExtra("email");

            // Update the TextViews with the updated reservation details
            lnametopTextView.setText(lname);
            lnameTextView.setText(lname);
            emailTextView.setText(email);
            numPeopleTextView.setText(String.valueOf(numPeople));

            // Update the reservation object with the updated details
            reservation.setLname(lname);
            reservation.setNumPeople(numPeople);
            reservation.setEmail(email);

            // Update the reservation details in the database (if needed)
            dbHelper.updateReservation(rCode, lname, numPeople, email);

            Toast.makeText(this, "Reservation updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}