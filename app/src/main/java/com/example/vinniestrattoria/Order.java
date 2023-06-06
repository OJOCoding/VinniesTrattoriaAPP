/**
 * ONI LUCA 20200008@student.act.edu
 * CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 * Order represents an order made by a customer. It contains information such as the reservation code,
 * total amount, and transaction ID.
 */

package com.example.vinniestrattoria;

public class Order {
    private String r_code;
    private double total;
    private int tId;

    public Order(String rCode, double total, int tId) {
        this.r_code = rCode;
        this.total = total;
        this.tId = tId;
    }

    public String getRCode() {
        return r_code;
    }

    public double getTotal() {
        return total;
    }

    public int getTId() {
        return tId;
    }
}
