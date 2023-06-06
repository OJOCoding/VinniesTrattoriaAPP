/**
 * ONI LUCA 20200008@student.act.edu
 * CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 * MenuItem represents a single item in the menu. It contains information such as the item ID,
 * name, price, and category.
 */

package com.example.vinniestrattoria;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private int id;
    private String itemName;
    private double price;
    private String category;

    public MenuItem(int id, String itemName, double price, String category) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
