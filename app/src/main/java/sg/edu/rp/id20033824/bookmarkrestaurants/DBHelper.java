package sg.edu.rp.id20033824.bookmarkrestaurants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_RESTAURANT = "restaurant";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CUISINE = "cuisine";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_RESTAURANT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_CUISINE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_STARS + " INTEGER ) ";
        db.execSQL(createSongTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_RESTAURANT + " ADD COLUMN  module_name TEXT ");
    }

    public long insertRestaurant(String name,String cuisine,String description,int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CUISINE, cuisine);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_RESTAURANT, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<restaurant> getAllRestaurant(){
        ArrayList<restaurant> restaurants = new ArrayList<restaurant>();

        String selectQuery = "SELECT " + COLUMN_ID+","+ COLUMN_NAME + "," + COLUMN_CUISINE + "," + COLUMN_DESCRIPTION + "," +COLUMN_STARS + " FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String cuisine = cursor.getString(2);
                String description = cursor.getString(3);
                int stars = cursor.getInt(4);
                restaurant restaurant = new restaurant(id,name,cuisine,description,stars);
                restaurants.add(restaurant);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return restaurants;
    }

    public ArrayList<restaurant> getAllRestaurantsByStar(){
        ArrayList<restaurant> restaurants = new ArrayList<restaurant>();

        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANT + " WHERE " + COLUMN_STARS + " =5 ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String cuisine = cursor.getString(2);
                String description = cursor.getString(3);
                int stars = cursor.getInt(4);
                restaurant restaurant = new restaurant(id, name, cuisine, description, stars);
                restaurants.add(restaurant);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return restaurants;
    }

    public ArrayList<restaurant> getAllRestaurantsByCuisine(String keyword) {
        ArrayList<restaurant> restaurants = new ArrayList<restaurant>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME,COLUMN_CUISINE,COLUMN_DESCRIPTION,COLUMN_STARS};
        String condition = COLUMN_CUISINE + " LIKE ?";
        String[] args = {"%" + keyword + "%"};
        Cursor cursor = db.query(TABLE_RESTAURANT, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String cuisine = cursor.getString(2);
                String description = cursor.getString(3);
                int stars = cursor.getInt(4);
                restaurant restaurant = new restaurant(id, name, cuisine, description, stars);
                restaurants.add(restaurant);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return restaurants;
    }


    public int updateRestaurant(restaurant data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_CUISINE, data.getCuisine());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_RESTAURANT, values, condition, args);
        db.close();
        return result;
    }

    public int deleteRestaurant(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_RESTAURANT, condition, args);
        db.close();
        return result;
    }

    public ArrayList<String> getAllCuisine(){
        ArrayList<String> cuisines = new ArrayList<String>();

        String selectQuery = "SELECT " + COLUMN_ID+","+ COLUMN_NAME + "," + COLUMN_CUISINE + "," + COLUMN_DESCRIPTION + "," +COLUMN_STARS + " FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                String cuisine = cursor.getString(2);
                cuisines.add(cuisine);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cuisines;
    }
}
