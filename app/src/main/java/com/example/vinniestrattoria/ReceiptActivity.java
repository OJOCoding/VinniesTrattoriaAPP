/**
 * ONI LUCA 20200008@student.act.edu
 * CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 * ReceiptActivity represents the activity where the user can view the receipt of their order,
 * including the selected items, subtotal, tax, total, and transaction ID.
 */

package com.example.vinniestrattoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class ReceiptActivity extends AppCompatActivity {

    private TextView itemsTextView;
    private TextView priceTextView;
    private TextView subtotalTextView;
    private TextView taxTextView;
    private TextView totalTextView;
    private TextView transactionIdTextView;
    private Button homeButton;

    private ArrayList<String> selectedItems;
    private double subtotal;
    private double tax;
    private double total;
    private String rCode;
    private int tId;

    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_activity_layout);

        dbHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        rCode = intent.getStringExtra("r_code");
        itemsTextView = findViewById(R.id.selecteditemsname);
        priceTextView = findViewById(R.id.selecteditemsprice);
        subtotalTextView = findViewById(R.id.subtotalnum);
        taxTextView = findViewById(R.id.taxnum);
        totalTextView = findViewById(R.id.total);
        transactionIdTextView = findViewById(R.id.tid);
        homeButton = findViewById(R.id.home);

        selectedItems = getIntent().getStringArrayListExtra("selectedItems");

        subtotal = calculateSubtotal(selectedItems);
        tax = calculateTax(subtotal);
        total = subtotal + tax;

        // Generate a random t_id
        tId = generateTId();

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        itemsTextView.setText(formatItems(selectedItems));
        priceTextView.setText(formatPrice(selectedItems));
        subtotalTextView.setText(decimalFormat.format(subtotal) + " $");
        taxTextView.setText(decimalFormat.format(tax) + " $");
        totalTextView.setText(decimalFormat.format(total) + " $");
        transactionIdTextView.setText(String.valueOf(tId));

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the order already exists in the order table
                if (!dbHelper.checkOrder(rCode)) {
                    // Insert the order into the order table
                    dbHelper.insertOrder(rCode, total, tId);
                }

                // Return to the home activity
                Intent intent = new Intent(ReceiptActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Calculate the subtotal of the selected items
    private double calculateSubtotal(ArrayList<String> items) {
        double subtotal = 0.0;
        for (String item : items) {
            String[] parts = item.split(" - ");
            if (parts.length == 2) {
                double price = Double.parseDouble(parts[1]);
                subtotal += price;
            }
        }
        return subtotal;
    }

    // Calculate the tax based on the subtotal
    private double calculateTax(double subtotal) {
        return subtotal * 0.045;
    }

    // Format the selected items to display in the receipt
    private String formatItems(ArrayList<String> items) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : items) {
            String[] parts = item.split(" - ");
            if (parts.length == 2) {
                String itemName = parts[0];
                stringBuilder.append(itemName).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    // Format the prices of the selected items to display in the receipt
    private String formatPrice(ArrayList<String> items) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : items) {
            String[] parts = item.split(" - ");
            if (parts.length == 2) {
                String itemPrice = parts[1];
                stringBuilder.append(itemPrice).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    // Generate a random t_id
    private int generateTId() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }
}