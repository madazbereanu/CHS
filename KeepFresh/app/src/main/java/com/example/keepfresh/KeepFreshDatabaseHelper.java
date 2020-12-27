package com.example.keepfresh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KeepFreshDatabaseHelper extends SQLiteOpenHelper
{
    //database
    private static final String DATABASE_NAME = "DatabaseKeepFresh";
    public static final int DATABASE_VERSION = 1;

    //table names
    private static final String TABLE_PRODUCTS_NAME = "product";
    private static final String TABLE_CATEGORIES_NAME = "category";

    //columns for PRODUCT table
    private static final String COLUMN_ID_PRODUCT = "id";
    private static final String COLUMN_NAME_PRODUCT = "name";
    private static final String COLUMN_CATEGORY_PRODUCT = "category";
    private static final String COLUMN_EXPIRY_DATE_PRODUCT = "expiry_data";
    private static final String COLUMN_QUANTITY_PRODUCT = "quantity";
    private static final String COLUMN_IMAGE_PRODUCT = "image";

    //columns for CATEGORY table
    private static final String COLUMN_ID_CATEGORY = "id";
    private static final String COLUMN_NAME_CATEGORY = "name";

    private static KeepFreshDatabaseHelper sInstance;

    public static synchronized KeepFreshDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new KeepFreshDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public KeepFreshDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                TABLE_CATEGORIES_NAME + " (" +
                COLUMN_ID_CATEGORY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_CATEGORY + " TEXT NOT NULL" +
                ");";

        db.execSQL(CREATE_CATEGORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES_NAME);
        onCreate(db);
    }

        // Insert a post into the database
    public boolean addCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CATEGORY, category);

        long result = db.insert(TABLE_CATEGORIES_NAME, null, contentValues);

        if(result == -1)
            return false;
        return true;
    }

    public Cursor getAllCategories()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CATEGORIES_NAME, null);
        return res;
    }

    public void addProduct(String productName,
                           String productCategory,
                           String productExpiryData,
                           String productQuantity,
                           byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "";
    }

//    public boolean updateCategory(String categoryId, String categoryName)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_ID_CATEGORY, categoryId);
//        contentValues.put(COLUMN_NAME_CATEGORY, categoryName);
//
//        db.update(TABLE_CATEGORIES_NAME, contentValues, "id = ?", new String[]{categoryId});
//        return true;
//    }

//////////////////////////////////////////////////////////////////////////////////////////////////

//    //database
//    private static final String DATABASE_NAME = "Database";
//    public static final int DATABASE_VERSION = 1;
//
//    //table names
//    private static final String TABLE_PRODUCTS_NAME = "products";
//    private static final String TABLE_CATEGORIES_NAME = "categories";
//
//    //columns for PRODUCT table
//    private static final String COLUMN_ID_PRODUCT = "id";
//    private static final String COLUMN_NAME_PRODUCT = "name";
//    private static final String COLUMN_CATEGORY_PRODUCT = "category";
//    private static final String COLUMN_EXPIRY_DATE_PRODUCT = "expiry_date";
//    private static final String COLUMN_QUANTITY_PRODUCT = "quantity";
//    private static final String COLUMN_IMAGE_PRODUCT = "image";
//
//    //columns for CATEGORY table
//    private static final String COLUMN_ID_CATEGORY = "id";
//    private static final String COLUMN_NAME_CATEGORY = "name";
//
//    private static KeepFreshDatabaseHelper sInstance;
//
//    public static synchronized KeepFreshDatabaseHelper getInstance(Context context) {
//        // Use the application context, which will ensure that you
//        // don't accidentally leak an Activity's context.
//        // See this article for more information: http://bit.ly/6LRzfx
//        if (sInstance == null) {
//            sInstance = new KeepFreshDatabaseHelper(context.getApplicationContext());
//        }
//        return sInstance;
//    }
//    public KeepFreshDatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
//                TABLE_CATEGORIES_NAME + " (" +
//                COLUMN_ID_CATEGORY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_NAME_CATEGORY + " TEXT NOT NULL" +
//                ");";
//
//        db.execSQL(CREATE_CATEGORIES_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES_NAME);
//        onCreate(db);
//    }
//
//    // Insert a post into the database
//    public boolean addCategory(String category) {
//        // Create and/or open the database for writing
//        SQLiteDatabase db = getWritableDatabase();
//        long result = -1;
//
//        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
//        // consistency of the database.
//        db.beginTransaction();
//        try {
//            // The item might already exist in the database (i.e. the same item created multiple posts).
//            long itemId = addOrUpdateItem(category);
//
//            ContentValues values = new ContentValues();
//            values.put(COLUMN_NAME_CATEGORY, itemId);
////            values.put(KEY_POST_TEXT, post.text);
//
//            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
//            result = db.insertOrThrow(TABLE_CATEGORIES_NAME, null, values);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to add post to database");
//        } finally {
//            db.endTransaction();
//        }
//        if(result == -1)
//        {
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//    }
//
//    // Insert or update a user in the database
//    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
//    // user already exists) optionally followed by an INSERT (in case the user does not already exist).
//    // Unfortunately, there is a bug with the insertOnConflict method
//    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
//    // verbose option of querying for the item's primary key if we did an update.
//    public long addOrUpdateItem(String category) {
//        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
//        SQLiteDatabase db = getWritableDatabase();
//        long itemId = -1;
//
//        db.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            values.put(COLUMN_NAME_CATEGORY, category);
////            values.put(KEY_ITEM_PROFILE_PICTURE_URL, item.profilePictureUrl);
//
//            // First try to update the item in case the item already exists in the database
//            // This assumes itemNames are unique
//            int rows = db.update(TABLE_CATEGORIES_NAME, values, COLUMN_NAME_CATEGORY + "= ?", new String[]{category});
//
//            // Check if update succeeded
//            if (rows == 1) {
//                // Get the primary key of the item we just updated
//                String itemsSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
//                        COLUMN_ID_CATEGORY, TABLE_CATEGORIES_NAME, COLUMN_NAME_CATEGORY);
//                Cursor cursor = db.rawQuery(itemsSelectQuery, new String[]{String.valueOf(category)});
//                try {
//                    if (cursor.moveToFirst()) {
//                        itemId = cursor.getInt(0);
//                        db.setTransactionSuccessful();
//                    }
//                } finally {
//                    if (cursor != null && !cursor.isClosed()) {
//                        cursor.close();
//                    }
//                }
//            } else {
//                // item with this itemName did not already exist, so insert new item
//                itemId = db.insertOrThrow(TABLE_CATEGORIES_NAME, null, values);
//                db.setTransactionSuccessful();
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to add or update item");
//        } finally {
//            db.endTransaction();
//        }
//        return itemId;
//    }
//
//    // Get all posts in the database
//    public List<String> getAllPosts() {
//        List<String> posts = new ArrayList<>();
//
//        // SELECT * FROM POSTS
//        // LEFT OUTER JOIN ITEMS
//        // ON POSTS.KEY_POST_ITEM_ID_FK = ITEMS.KEY_ITEM_ID
//        String POSTS_SELECT_QUERY =
////                String.format("SELECT * FROM %s LEFT OUTER JOIN %s ON %s.%s = %s.%s",
//                String.format("SELECT * FROM %s",
//                        TABLE_CATEGORIES_NAME);
//
//        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
//        // disk space scenarios)
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
//        try {
//            if (cursor.moveToFirst()) {
//                do {
//                    String newItem = new String();
//                    newItem = cursor.getString(cursor.getColumnIndex(COLUMN_ID_CATEGORY));
////                    newItem.profilePictureUrl = cursor.getString(cursor.getColumnIndex(KEY_ITEM_PROFILE_PICTURE_URL));
//
//                    String newPost = new String();
//                    newPost = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATEGORY));
////                    newPost.item = newItem;
//                    posts.add(newPost);
//                } while(cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to get posts from database");
//        } finally {
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//        return posts;
//    }
}
