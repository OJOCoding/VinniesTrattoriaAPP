/**
 * ONI LUCA 20200008@student.act.edu
 * CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 * MenuActivity displays a menu with a list of items. Users can select items, view the total price,
 * sort the items in Italian order, and proceed to the order confirmation screen.
 */

package com.example.vinniestrattoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private ListView listView;
    private ItemAdapter itemAdapter;
    private TextView totalPriceTextView;
    private Button checkOrderButton;
    private Button sortButton;

    private ArrayList<MenuItem> selectedItems;
    private double totalPrice;
    private boolean isItalianOrder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity_layout);

        // Get the reservation code from the intent
        Intent intent = getIntent();
        String reservationCode = intent.getStringExtra("r_code");

        listView = findViewById(R.id.list);
        totalPriceTextView = findViewById(R.id.total);
        checkOrderButton = findViewById(R.id.loadmenuButton);
        sortButton = findViewById(R.id.sort);

        // Initialize the selectedItems ArrayList
        selectedItems = new ArrayList<>();

        // Create menu items
        List<MenuItem> menuItems = MenuItems();

        // Create the item adapter
        itemAdapter = new ItemAdapter(this, menuItems);

        // Set the adapter to the ListView
        listView.setAdapter(itemAdapter);

        // Set item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected menu item from the currently displayed list
                MenuItem selectedItem = (MenuItem) itemAdapter.getItem(position);

                // Add the selected item to the selectedItems ArrayList
                selectedItems.add(selectedItem);

                // Update the total price
                totalPrice += selectedItem.getPrice();
                totalPriceTextView.setText("Total - " + totalPrice + " $");
            }
        });

        // Set click listener for the "Check your Order" button
        checkOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the OrderActivity and pass the selectedItems ArrayList
                Intent intent = new Intent(MenuActivity.this, OrderActivity.class);
                intent.putExtra("selectedItems", selectedItems);
                intent.putExtra("r_code", reservationCode);
                startActivity(intent);
            }
        });

        // Set click listener for the "Sort" button
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItalianOrder) {
                    // Display the original list
                    itemAdapter.setItems(menuItems);
                    sortButton.setText("ORDER LIKE AN ITALIAN");
                } else {
                    // Sort the items in the specific order
                    List<MenuItem> sortedItems = sortItemsInItalianOrder(menuItems);
                    itemAdapter.setItems(sortedItems);
                    sortButton.setText("ORDER LIKE THE REST");
                }
                isItalianOrder = !isItalianOrder;
            }
        });
    }

    // Method to create a list of menu items
    private List<MenuItem> MenuItems() {
        // Fetch menu items from the database
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        return dbHelper.getAllMenuItems();
    }

    // Helper method to sort the items in the specific Italian order
    private List<MenuItem> sortItemsInItalianOrder(List<MenuItem> items) {
        List<MenuItem> sortedItems = new ArrayList<>();
        List<String> categoriesInOrder = Arrays.asList(
                "Aperitivi Cocktails",
                "Antipasti",
                "Primi",
                "Secondi",
                "Pizza",
                "Dolce",
                "Non-Alcoholic - Analcolico",
                "Spritz",
                "Vini Bianci",
                "Vini Rosati",
                "Vini Rossi",
                "Water - Acque"
        );

        for (String category : categoriesInOrder) {
            for (MenuItem item : items) {
                if (item.getCategory().equals(category)) {
                    sortedItems.add(item);
                }
            }
        }

        return sortedItems;
    }
}