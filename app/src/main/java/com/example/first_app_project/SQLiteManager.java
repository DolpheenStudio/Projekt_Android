package com.example.first_app_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "PlaceRatingDB";
    private static final String TABLE_NAME = "PlaceRating";
    private static final String COUNTER = "Counter";
    private static final int DATABASE_VERSION = 1;

    private static final String ID_FIELD = "id";
    private static final String TTS_RATING = "tts_rating";
    private static final String PRICE_RATING = "price_rating";
    private static final String FOOD_RATING = "food_rating";
    private static final String YEAR_FIELD = "year_field";
    private static final String NAME_FIELD = "name_field";
    private static final String SHORT_DESC = "short_desc";
    private static final String LONG_DESC = "long_desc";
    private static final String IMAGE_URL = "image_url";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
        {
            sqLiteManager = new SQLiteManager(context);
        }
        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT,")
                .append(TTS_RATING)
                .append(" FLOAT, ")
                .append(PRICE_RATING)
                .append(" FLOAT, ")
                .append(FOOD_RATING)
                .append(" FLOAT, ")
                .append(YEAR_FIELD)
                .append(" INT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(SHORT_DESC)
                .append(" TEXT, ")
                .append(LONG_DESC)
                .append(" TEXT, ")
                .append(IMAGE_URL)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void clearPlaceDB()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        for (Place place : Place.placeArrayList)
        {
            sqLiteDatabase.delete(TABLE_NAME, ID_FIELD + " =? ", new String[]{String.valueOf(place.getId())});
        }
    }

    public void updatePlaceRatingDB(Place place)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, place.getId());
        contentValues.put(TTS_RATING, place.getRatingArray(0));
        contentValues.put(PRICE_RATING, place.getRatingArray(1));
        contentValues.put(FOOD_RATING, place.getRatingArray(2));
        contentValues.put(YEAR_FIELD, place.getYear());
        contentValues.put(NAME_FIELD, place.getName());
        contentValues.put(SHORT_DESC, place.getShortDesc());
        contentValues.put(LONG_DESC, place.getLongDesc());
        contentValues.put(IMAGE_URL, place.getImageUrl());

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(place.getId())});
    }

    public void populatePlaceArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try(Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if(result.getCount() != 0)
            {
                while(result.moveToNext())
                {
                    int id = result.getInt(1);
                    float tts_rating = result.getFloat(2);
                    float price_rating = result.getFloat(3);
                    float food_rating = result.getFloat(4);
                    int year = result.getInt(5);
                    String name = result.getString(6);
                    String short_desc = result.getString(7);
                    String long_desc = result.getString(8);
                    String image_url = result.getString(9);
                    float[] ratingArray = {tts_rating, price_rating, food_rating};

                    Place place = new Place(id, year, name, image_url, short_desc, long_desc, ratingArray);
                    Place.placeArrayList.add(place);
                }
            }
        }
    }

    public void addPlaceRatingToDatabase(Place place) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, place.getId());
        contentValues.put(TTS_RATING, place.getRatingArray(0));
        contentValues.put(PRICE_RATING, place.getRatingArray(1));
        contentValues.put(FOOD_RATING, place.getRatingArray(2));
        contentValues.put(YEAR_FIELD, place.getYear());
        contentValues.put(NAME_FIELD, place.getName());
        contentValues.put(SHORT_DESC, place.getShortDesc());
        contentValues.put(LONG_DESC, place.getLongDesc());
        contentValues.put(IMAGE_URL, place.getImageUrl());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }
}
