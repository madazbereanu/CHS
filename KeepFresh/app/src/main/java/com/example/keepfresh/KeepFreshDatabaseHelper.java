package com.example.keepfresh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class KeepFreshDatabaseHelper extends SQLiteOpenHelper {
    private static final float PREFERRED_WIDTH = 250;
    private static final float PREFERRED_HEIGHT = 250;

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

        final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS_NAME + " (" +
                COLUMN_ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_PRODUCT + " TEXT NOT NULL, " +
                COLUMN_CATEGORY_PRODUCT + " TEXT NOT NULL, " +
                COLUMN_EXPIRY_DATE_PRODUCT + " TEXT NOT NULL, " +
                COLUMN_QUANTITY_PRODUCT + " TEXT NOT NULL, " +
                COLUMN_IMAGE_PRODUCT + " TEXT NOT NULL " +
                ");";

        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS_NAME);
        onCreate(db);
    }

    public boolean addCategory(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        Cursor res = getCategoriesName();
        List<String> values = new ArrayList<>();
        while (res.moveToNext()) {
            values.add(res.getString(0));
        }

        if(!values.contains(categoryName)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME_CATEGORY, categoryName);
            result = db.insert(TABLE_CATEGORIES_NAME, null, contentValues);
        }

        if(result == -1)
            return false;
        return true;
    }

    public boolean addProduct(String nameProduct, String categoryProduct, String expiryDataProduct, String quantityProduct, Bitmap image) {
        SQLiteDatabase db = getWritableDatabase();
        long result = -1;
        Cursor res = getAllProducts();
        List<String> values = new ArrayList<>();
        while (res.moveToNext()) {
            values.add(res.getString(0));
        }

        if(!values.contains(nameProduct)) {
            ContentValues contentValues = new ContentValues();

            String imageString = bitmapToString(resizeBitmap(image));
            contentValues.put(COLUMN_NAME_CATEGORY, nameProduct);
            contentValues.put(COLUMN_CATEGORY_PRODUCT, categoryProduct);
            contentValues.put(COLUMN_EXPIRY_DATE_PRODUCT, expiryDataProduct);
            contentValues.put(COLUMN_QUANTITY_PRODUCT, quantityProduct);
            contentValues.put(COLUMN_IMAGE_PRODUCT, imageString);
            result = db.insert(TABLE_PRODUCTS_NAME, null, contentValues);
        }

        if(result == -1)
            return false;
        return true;
    }

    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CATEGORIES_NAME, null);
        return res;
    }

    public Cursor getCategoriesName() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + COLUMN_NAME_CATEGORY + " from " + TABLE_CATEGORIES_NAME, null);
        return res;
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select " + COLUMN_NAME_PRODUCT + ", " + COLUMN_IMAGE_PRODUCT + ", " + COLUMN_CATEGORY_PRODUCT + ", "+ COLUMN_EXPIRY_DATE_PRODUCT + " from " + TABLE_PRODUCTS_NAME, null);
        return res;
    }

    private static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = PREFERRED_WIDTH / width;
        float scaleHeight = PREFERRED_HEIGHT / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }

    public void deleteAllCategories() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.delete(TABLE_CATEGORIES_NAME, null, null);
        db.setTransactionSuccessful();

        db.endTransaction();
    }

    public void deleteAllProducts() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        db.delete(TABLE_PRODUCTS_NAME, null, null);
        db.setTransactionSuccessful();

        db.endTransaction();
    }
}
