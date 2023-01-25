package com.example.first_app_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "PlacesDB";
    private static final String TABLE_NAME = "PlacesDBTable";
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
    private static final String IMAGE_URI = "image_uri";
    private static final String IS_ALREADY_SEEN = "is_already_seen";
    private static final String IS_WANT_TO_SEE = "is_want_to_see";
    private static final String IS_FAVOURITE = "is_favourite";

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
                .append(IMAGE_URI)
                .append(" TEXT, ")
                .append(IS_ALREADY_SEEN)
                .append(" BOOL, ")
                .append(IS_WANT_TO_SEE)
                .append(" BOOL, ")
                .append(IS_FAVOURITE)
                .append(" BOOL)");

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

    public void deletePlaceFromDB(Place place)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(TABLE_NAME, ID_FIELD + " =? ", new String[]{String.valueOf(place.getId())});
        Place.placeArrayList.remove(place);
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
        contentValues.put(IMAGE_URI, place.getImageUri().toString());
        contentValues.put(IS_ALREADY_SEEN, place.getIsAlreadySeen());
        contentValues.put(IS_WANT_TO_SEE, place.getIsWantToSee());
        contentValues.put(IS_FAVOURITE, place.getIsFavourite());

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
                    String image_uri_string = result.getString(9);
                    Uri image_uri = Uri.parse(image_uri_string);
                    float[] ratingArray = {tts_rating, price_rating, food_rating};
                    boolean isAlwaysSeen = result.getInt(10) > 0;
                    boolean isWantToSee = result.getInt(11) > 0;
                    boolean isFavourite = result.getInt(12) > 0;

                    Place place = new Place(id, year, name, image_uri, short_desc, long_desc, ratingArray, isAlwaysSeen, isWantToSee, isFavourite);
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
        contentValues.put(IMAGE_URI, place.getImageUri().toString());
        contentValues.put(IS_ALREADY_SEEN, place.getIsAlreadySeen());
        contentValues.put(IS_WANT_TO_SEE, place.getIsWantToSee());
        contentValues.put(IS_FAVOURITE, place.getIsFavourite());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }
}
