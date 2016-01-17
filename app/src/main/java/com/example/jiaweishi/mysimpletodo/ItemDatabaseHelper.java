package com.example.jiaweishi.mysimpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaweishi on 1/14/16.
 */
public class ItemDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = ItemDatabaseHelper.class.getSimpleName();
    private static final String DB_NAME = "itemDatabase";
    private static final int DB_VERSION = 1;

    private static final String KEY_ITEM_TITLE = "title";
    private static final String KEY_ITEM_PRIORITY = "priority";

    private static ItemDatabaseHelper mInstance;

    public static synchronized  ItemDatabaseHelper getInstance(Context context){
        if(mInstance == null)
            mInstance = new ItemDatabaseHelper(context);

        return mInstance;
    }


    private ItemDatabaseHelper(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + DB_NAME + "("
                    + KEY_ITEM_TITLE +" TEXT PRIMARY KEY,"
                    + KEY_ITEM_PRIORITY + " TEXT"
                    + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DB_NAME);
            onCreate(sqLiteDatabase);
        }
    }

    public List<Item> readAllItems(){
        List<Item> items = new ArrayList<>();

        String SELECT_ALL_ITEMS_QUERY = "SELECT * FROM " + DB_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_ITEMS_QUERY, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    String itemTitle = cursor.getString(cursor.getColumnIndex(KEY_ITEM_TITLE));
                    String itemPriority = cursor.getString(cursor.getColumnIndex(KEY_ITEM_PRIORITY));
                    items.add(new Item(itemTitle, itemPriority));
                }while(cursor.moveToNext());
            }
        } catch (Exception e){
            Log.e(TAG, "Error while reading rows in the db");
        } finally {
            if(cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return items;
    }

    public void addItem(Item item){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try{
            ContentValues value = new ContentValues();
            value.put(KEY_ITEM_TITLE, item.getTitle());
            value.put(KEY_ITEM_PRIORITY, item.getPriority());

            db.insertOrThrow(DB_NAME, null ,value);
            db.setTransactionSuccessful();
        } catch (Exception e){
            Log.e(TAG, "Error while adding new item to DB");
        } finally {
            db.endTransaction();
        }
    }


    public void updateItem(Item item){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try{
            ContentValues value = new ContentValues();
            value.put(KEY_ITEM_TITLE, item.getTitle());
            value.put(KEY_ITEM_PRIORITY, item.getPriority());

            db.update(DB_NAME, value, KEY_ITEM_TITLE + "= ?", new String[]{item.getTitle()});

            db.setTransactionSuccessful();
        } catch (Exception e){
            Log.e(TAG, "Error while updating or adding item");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteAllItems(){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try{
            db.delete(DB_NAME, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e){
            Log.e(TAG, "Error while deleting all items from DB");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteItem(Item item){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try{
            db.delete(DB_NAME, KEY_ITEM_TITLE + "= ?", new String[]{item.getTitle()});
            db.setTransactionSuccessful();
        } catch (Exception e){
            Log.e(TAG, "Error while deleting item from DB");
        } finally {
            db.endTransaction();
        }
    }
}
