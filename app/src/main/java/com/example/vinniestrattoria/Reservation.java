/*
 * ONI LUCA 20200008@student.act.edu
 * CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 * Reservation represents a class that represents a reservation made by a customer.
 */

package com.example.vinniestrattoria;

public class Reservation {
    private String r_code;
    private String lname;
    private int numpeople;
    private String email;
    private String date;

    public Reservation(String r_code, String lname, int numpeople, String email, String date) {
        this.r_code = r_code;
        this.lname = lname;
        this.numpeople = numpeople;
        this.email = email;
        this.date = date;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setNumPeople(int numpeople) {
        this.numpeople = numpeople;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRCode() {
        return r_code;
    }

    public String getLname() {
        return lname;
    }

    public int getNumPeople() {
        return numpeople;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }
}