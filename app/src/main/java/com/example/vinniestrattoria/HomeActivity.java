/**
 ONI LUCA 20200008@student.act.edu
 CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 The HomeActivity class represents the main activity of the Vinnie's Trattoria app.
 It displays the home screen with buttons for navigating to the About Us and Login activities.
 It also checks if the menuitems table is empty in the database and inserts initial menu items if necessary. */

package com.example.vinniestrattoria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {


    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);

        // Initialize buttons
        Button about = findViewById(R.id.about);
        Button login = findViewById(R.id.login);

        // Set onClickListener for the About button
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AboutUsActivity
                Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for the Login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LoginActivity
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Initialize the database helper
        dbHelper = new DataBaseHelper(this);

        // Check if the menuitems table is empty
        if (dbHelper.isMenuItemsTableEmpty()) {
            // Insert menu items
            insertMenuItems();
        }
    }

    /**
     * Inserts menu items into the menuitems table in the database.
     */
    private void insertMenuItems() {
        dbHelper.insertMenuItem("Luigi Leonardo Rubicone IGT", 25, "Vini Bianci");
        dbHelper.insertMenuItem("Oenops Apla", 30, "Vini Bianci");
        dbHelper.insertMenuItem("Broglia Gavi Di Gavi", 37, "Vini Bianci");
        dbHelper.insertMenuItem("Syros Winery Fabrica", 35, "Vini Bianci");
        dbHelper.insertMenuItem("La Tunella Pinot Grigio del Friulli", 38, "Vini Bianci");
        dbHelper.insertMenuItem("Baglio Del Sole Nero D'Avola", 35, "Vini Rossi");
        dbHelper.insertMenuItem("Frattoria Selvepiana Chianti 'Rufina'", 40, "Vini Rossi");
        dbHelper.insertMenuItem("Dievole Chianti Classicov", 42, "Vini Rossi");
        dbHelper.insertMenuItem("Batatsiolo Barolo DOCG", 62, "Vini Rossi");
        dbHelper.insertMenuItem("Pio Cesare Barolo", 95, "Vini Rossi");
        dbHelper.insertMenuItem("Akriotou Estate Tupee", 27, "Vini Rosati");
        dbHelper.insertMenuItem("Lalikos - Variete", 30, "Vini Rosati");
        dbHelper.insertMenuItem("Batatsiolo Piemonte Rosato DOC", 32, "Vini Rosati");
        dbHelper.insertMenuItem("Maseria Surani - Negroamaro", 39, "Vini Rosati");
        dbHelper.insertMenuItem("Chateau Des Marres - Prestige", 55, "Vini Rosati");
        dbHelper.insertMenuItem("Aperol Spritz", 6.5, "Spritz");
        dbHelper.insertMenuItem("Campari Spritz", 7, "Spritz");
        dbHelper.insertMenuItem("Veneziano Spritz", 7, "Spritz");
        dbHelper.insertMenuItem("Amaro Spritz", 7, "Spritz");
        dbHelper.insertMenuItem("El Greco", 7, "Spritz");
        dbHelper.insertMenuItem("Bloody Mary", 8, "Aperitivi Cocktails");
        dbHelper.insertMenuItem("Dry Martini", 8, "Aperitivi Cocktails");
        dbHelper.insertMenuItem("Milano - Torino", 7, "Aperitivi Cocktails");
        dbHelper.insertMenuItem("Negroni", 8, "Aperitivi Cocktails");
        dbHelper.insertMenuItem("Montenegroni", 8, "Aperitivi Cocktails");
        dbHelper.insertMenuItem("Maracuja Spritz", 6.5, "Non-Alcoholic - Analcolico");
        dbHelper.insertMenuItem("Zero Zero", 5.5, "Non-Alcoholic - Analcolico");
        dbHelper.insertMenuItem("Maracuja Frizzannte", 5.5, "Non-Alcoholic - Analcolico");
        dbHelper.insertMenuItem("Natual Mineral", 2.5, "Water - Acque");
        dbHelper.insertMenuItem("Sparkling", 4.5, "Water - Acque");
        dbHelper.insertMenuItem("Focaccia Toscana", 6, "Antipasti");
        dbHelper.insertMenuItem("Caprese Mozzarella", 16, "Antipasti");
        dbHelper.insertMenuItem("Bruschetta Vitello Tonnato", 13, "Antipasti");
        dbHelper.insertMenuItem("Insalata", 12, "Antipasti");
        dbHelper.insertMenuItem("Calamari Fritti", 10, "Antipasti");
        dbHelper.insertMenuItem("Tagliere di Formaggi e Salumi", 14, "Antipasti");
        dbHelper.insertMenuItem("Gnocchi e Gorgonzola", 13, "Primi");
        dbHelper.insertMenuItem("Bucatini alle Amatriciana", 12, "Primi");
        dbHelper.insertMenuItem("Fettucce alle Vongole", 16, "Primi");
        dbHelper.insertMenuItem("Rigatoni Cacio e Peppe", 12, "Primi");
        dbHelper.insertMenuItem("Risotto Tartufo", 15, "Primi");
        dbHelper.insertMenuItem("Margherita", 10, "Pizza");
        dbHelper.insertMenuItem("Diavola", 12, "Pizza");
        dbHelper.insertMenuItem("Marinara", 10, "Pizza");
        dbHelper.insertMenuItem("Bianca", 13, "Pizza");
        dbHelper.insertMenuItem("Tartufo", 15, "Pizza");
        dbHelper.insertMenuItem("Escalope Milanaise", 16, "Secondi");
        dbHelper.insertMenuItem("Bistecca di Vitello", 26, "Secondi");
        dbHelper.insertMenuItem("Bistecca alla Fiorentina", 7, "Secondi");
        dbHelper.insertMenuItem("Tiramisu", 9, "Dolce");
        dbHelper.insertMenuItem("Cannoli", 9, "Dolce");

        // Insert a reservation for testing purposes
        dbHelper.insertReservation("#AB123", "Doe", 4, "doe@mail.com", "22/06/2023");
    }
}