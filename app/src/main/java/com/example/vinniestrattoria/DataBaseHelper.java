/**
    ONI LUCA 20200008@student.act.edu CS300-MOBILE APPLICATIONS PROJECT SUBMISSION

    This class provides helper methods for managing the SQLite database used in the application.
    It includes methods for creating and upgrading tables, inserting reservations and orders,
    checking for existing records, retrieving reservation and order details, deleting records,
    inserting menu items, checking if the menu items table is empty, and retrieving all menu items.
*/

package com.example.vinniestrattoria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DBName = "VinniesDB";
    private DataBaseHelper db;
    private static final String TABLE_MENU_ITEMS = "menuitems";
    private static final String COL_ID = "id";
    private static final String COL_ITEM_NAME = "item_name";
    private static final String COL_PRICE = "price";
    private static final String COL_CATEGORY = "category";

    public DataBaseHelper(Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE reservation(r_code TEXT primary key, lname TEXT, numpeople INTEGER, email TEXT, date TEXT)");

        String createMenuItemsTableQuery = "CREATE TABLE menuitems ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "item_name TEXT,"
                + "price REAL,"
                + "category TEXT"
                + ")";
        db.execSQL(createMenuItemsTableQuery);

        String createOrderTableQuery = "CREATE TABLE orders ("
                + "r_code TEXT,"
                + "total REAL,"
                + "t_id INTEGER,"
                + "FOREIGN KEY(r_code) REFERENCES reservation(r_code)"
                + ")";
        db.execSQL(createOrderTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS reservation");
        db.execSQL("DROP TABLE IF EXISTS menuitems");
        db.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(db);
    }

    public Boolean insertReservation(String r_code, String lname, Integer numpeople, String email, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("r_code", r_code);
        contentValues.put("lname", lname);
        contentValues.put("numpeople", numpeople);
        contentValues.put("email", email);
        contentValues.put("date", date);
        long result = db.insert("reservation", null, contentValues);
        return result != -1;
    }

    public Boolean check(String r_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from reservation where r_code =?", new String[]{r_code});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }

    public boolean checkReservation(String r_code, String lname) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM reservation WHERE r_code=? AND lname=?", new String[]{r_code, lname});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean updateReservation(String rCode, String lname, int numPeople, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lname", lname);
        contentValues.put("numpeople", numPeople);
        contentValues.put("email", email);

        int rowsAffected = db.update("reservation", contentValues, "r_code = ?", new String[]{rCode});

        return rowsAffected > 0;
    }

    public Reservation getReservationDetails(String r_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM reservation WHERE r_code = ?", new String[]{r_code});
        if (cursor.moveToFirst()) {
            String lname = cursor.getString(cursor.getColumnIndex("lname"));
            int numpeople = cursor.getInt(cursor.getColumnIndex("numpeople"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String date = cursor.getString(cursor.getColumnIndex("date"));

            Reservation reservation = new Reservation(r_code, lname, numpeople, email, date);
            cursor.close();
            return reservation;
        }
        cursor.close();
        return null;
    }

    public Boolean deleteReservation(String r_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM reservation WHERE r_code=?", new String[]{r_code});
        return true;
    }

    public Boolean insertOrder(String r_code, double total, int t_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("r_code", r_code);
        contentValues.put("total", total);
        contentValues.put("t_id", t_id);
        long result = db.insert("orders", null, contentValues);
        return result != -1;
    }

    public Boolean checkOrder(String r_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE r_code=?", new String[]{r_code});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Order getOrderDetails(String r_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE r_code=?", new String[]{r_code});
        if (cursor.moveToFirst()) {
            double total = cursor.getDouble(cursor.getColumnIndex("total"));
            int t_id = cursor.getInt(cursor.getColumnIndex("t_id"));

            Order order = new Order(r_code, total, t_id);
            cursor.close();
            return order;
        }
        cursor.close();
        return null;
    }

    public Boolean deleteOrder(String r_code) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM orders WHERE r_code=?", new String[]{r_code});
        return true;
    }

    public void insertMenuItem(String itemName, double price, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ITEM_NAME, itemName);
        contentValues.put(COL_PRICE, price);
        contentValues.put(COL_CATEGORY, category);
        db.insert(TABLE_MENU_ITEMS, null, contentValues);
    }

    public boolean isMenuItemsTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MENU_ITEMS, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count == 0;
        }
        return true;
    }

    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_ID, COL_ITEM_NAME, COL_PRICE, COL_CATEGORY};

        Cursor cursor = db.query(TABLE_MENU_ITEMS, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            String itemName = cursor.getString(cursor.getColumnIndex(COL_ITEM_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE));
            String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));

            MenuItem menuItem = new MenuItem(id, itemName, price, category);
            menuItems.add(menuItem);
        }

        cursor.close();
        db.close();

        return menuItems;
    }
}
