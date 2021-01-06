package com.example.keepfresh;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
public class PostsDatabaseHelper extends SQLiteOpenHelper
{
    // Database Info
    private static final String DATABASE_NAME = "KeepFresh";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_POSTS = "posts";
    private static final String TABLE_ITEMS = "items";

    // Post Table Columns
    private static final String KEY_POST_ID = "id";
    private static final String KEY_POST_ITEM_ID_FK = "itemId";
    private static final String KEY_POST_TEXT = "text";

    // Item Table Columns
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_NAME = "itemName";

    private static PostsDatabaseHelper sInstance;

    public static synchronized PostsDatabaseHelper getInstance(Context context)
    {
        if (sInstance == null)
        {
            sInstance = new PostsDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private PostsDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_POSTS +
                "(" +
                KEY_POST_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_POST_ITEM_ID_FK + " INTEGER REFERENCES " + TABLE_ITEMS + "," + // Define a foreign key
                KEY_POST_TEXT + " TEXT" +
                ")";

        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS +
                "(" +
                KEY_ITEM_ID + " INTEGER PRIMARY KEY," +
                KEY_ITEM_NAME + " TEXT" +
                ")";

        db.execSQL(CREATE_POSTS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (oldVersion != newVersion)
        {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(db);
        }
    }

    public boolean addPost(Post post)
    {
        SQLiteDatabase db = getWritableDatabase();
        long result = -1;

        db.beginTransaction();
        try
        {
            long itemId = addOrUpdateItem(post.item);

            ContentValues values = new ContentValues();
            values.put(KEY_POST_ITEM_ID_FK, itemId);

            result = db.insertOrThrow(TABLE_POSTS, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.d(TAG, "Error while trying to add post to database");
        }
        finally
        {
            db.endTransaction();
        }
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public long addOrUpdateItem(Item item)
    {
        SQLiteDatabase db = getWritableDatabase();
        long itemId = -1;

        db.beginTransaction();
        try
        {
            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_NAME, item.name);

            int rows = db.update(TABLE_ITEMS, values, KEY_ITEM_NAME + "= ?", new String[]{item.name});

            if (rows == 1)
            {
                String itemsSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_ITEM_ID, TABLE_ITEMS, KEY_ITEM_NAME);
                Cursor cursor = db.rawQuery(itemsSelectQuery, new String[]{String.valueOf(item.name)});
                try
                {
                    if (cursor.moveToFirst())
                    {
                        itemId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                }
                finally
                {
                    if (cursor != null && !cursor.isClosed())
                    {
                        cursor.close();
                    }
                }
            }
            else
            {
                itemId = db.insertOrThrow(TABLE_ITEMS, null, values);
                db.setTransactionSuccessful();
            }
        }
        catch (Exception e)
        {
            Log.d(TAG, "Error while trying to add or update item");
        }
        finally
        {
            db.endTransaction();
        }
        return itemId;
    }

    public List<Post> getAllPosts()
    {
        List<Post> posts = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s LEFT OUTER JOIN %s ON %s.%s = %s.%s",
                        TABLE_POSTS,
                        TABLE_ITEMS,
                        TABLE_POSTS, KEY_POST_ITEM_ID_FK,
                        TABLE_ITEMS, KEY_ITEM_ID);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    Item newItem = new Item();
                    newItem.name = cursor.getString(cursor.getColumnIndex(KEY_ITEM_NAME));

                    Post newPost = new Post();
                    newPost.item = newItem;
                    posts.add(newPost);
                }
                while(cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
            Log.d(TAG, "Error while trying to get posts from database");
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
        }
        return posts;
    }

    public void deleteAllPostsAndItems()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try
        {
            db.delete(TABLE_POSTS, null, null);
            db.delete(TABLE_ITEMS, null, null);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.d(TAG, "Error while trying to delete all posts and items");
        }
        finally
        {
            db.endTransaction();
        }
    }
}
