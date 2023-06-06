/**
 * ONI LUCA 20200008@student.act.edu
 * CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 * OrderActivity represents the activity where the user can review their selected items,
 * remove items, calculate the total price, and proceed to receipt. When the user covers their screen, detected through sensors,
 * the items are dropped from the array and user is sent back to menuactivity.
 */

package com.example.vinniestrattoria;

import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OrderActivity extends AppCompatActivity implements SensorEventListener {

    private ListView listView;
    private ItemAdapter itemAdapter;
    private TextView totalPriceTextView;
    private Button proceedButton;

    private ArrayList<MenuItem> selectedItems;
    private double totalPrice;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private boolean isLightSensorAvailable;
    private int currentRotationMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_layout);
        Intent intent = getIntent();
        String reservationCode = intent.getStringExtra("r_code");

        // Initialize views
        listView = findViewById(R.id.list);
        totalPriceTextView = findViewById(R.id.total);
        proceedButton = findViewById(R.id.loadmenuButton);

        // Get selected items from the intent
        selectedItems = (ArrayList<MenuItem>) getIntent().getSerializableExtra("selectedItems");

        // Create item adapter and set it to the ListView
        itemAdapter = new ItemAdapter(this, selectedItems);
        listView.setAdapter(itemAdapter);

        // Set click listener for each item in the list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked menu item
                MenuItem clickedItem = selectedItems.get(position);

                // Remove the clicked item from the selectedItems ArrayList
                selectedItems.remove(position);

                // Update the total price by subtracting the item's price
                totalPrice -= clickedItem.getPrice();
                totalPriceTextView.setText("Total - " + totalPrice + " $");

                // Notify the adapter about the data change
                itemAdapter.notifyDataSetChanged();

                // Check if the order is empty and handle accordingly
                checkAndHandleEmptyOrder();
            }
        });

        // Calculate the initial total price
        calculateTotalPrice();

        // Set click listener for the "Proceed to Payment" button
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the ReceiptActivity and pass the selectedItems ArrayList
                Intent intent = new Intent(OrderActivity.this, ReceiptActivity.class);
                intent.putExtra("selectedItems", convertToStringList(selectedItems));
                intent.putExtra("r_code", reservationCode);
                startActivity(intent);
            }
        });

        // Initialize the light sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            isLightSensorAvailable = (lightSensor != null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the light sensor listener
        if (isLightSensorAvailable) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the light sensor listener
        if (isLightSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightValue = event.values[0];
            // Check if light level is below a threshold (e.g., no light)
            if (lightValue <= 5.0) {
                // Perform actions when there is no light (e.g., drop the order)
                selectedItems.clear();
                itemAdapter.notifyDataSetChanged();
                totalPrice = 0.0;
                totalPriceTextView.setText("Total - " + totalPrice + " $");
                Toast.makeText(OrderActivity.this, "Order dropped!", Toast.LENGTH_SHORT).show();

                // Start a timer to switch to the menu activity after 5 seconds
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(OrderActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }
                }, 5000);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Empty method, not used
    }

    private void calculateTotalPrice() {
        totalPrice = 0.0;
        for (MenuItem item : selectedItems) {
            totalPrice += item.getPrice();
        }
        totalPriceTextView.setText("Total - " + totalPrice + " $");
    }

    private void checkAndHandleEmptyOrder() {
        if (selectedItems.size() == 0) {
            Toast.makeText(OrderActivity.this, "Order emptied", Toast.LENGTH_SHORT).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(OrderActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            }, 5000);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        currentRotationMode = newConfig.orientation;
        checkAndHandleEmptyOrder();
    }

    // Convert the ArrayList of MenuItem objects to an ArrayList of strings
    private ArrayList<String> convertToStringList(ArrayList<MenuItem> items) {
        ArrayList<String> stringList = new ArrayList<>();
        for (MenuItem item : items) {
            stringList.add(item.getItemName() + " - " + item.getPrice());
        }
        return stringList;
    }
}
