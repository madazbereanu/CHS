package com.example.keepfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddActivity extends AppCompatActivity
{
    private static final int CAMERA_REQUEST_CODE = 1;

    private ImageView imageViewCamera;

    private ImageButton buttonCamera;
    private ImageButton buttonDecrease;
    private ImageButton buttonIncrease;
    private Button buttonCancel;
    private Button buttonSave;

    private EditText productQuantity;
    private EditText productName;
    private EditText productExpiryDate;

    private Spinner productCategory;

    private KeepFreshDatabaseHelper keepFreshDatabaseHelper;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imageViewCamera = (ImageView) findViewById(R.id.image_view_camera);

        buttonCamera = (ImageButton) findViewById(R.id.button_camera);
        buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonSave = (Button) findViewById(R.id.button_save);
        buttonDecrease = (ImageButton) findViewById(R.id.decrease_button);
        buttonIncrease = (ImageButton) findViewById(R.id.increase_button);

        productQuantity = (EditText) findViewById(R.id.quantity_field);
        productName = (EditText) findViewById(R.id.name_field);
        productCategory = (Spinner) findViewById(R.id.category_spinner);
        productExpiryDate = (EditText) findViewById(R.id.calendar_field);

        keepFreshDatabaseHelper = KeepFreshDatabaseHelper.getInstance(this);

        populateSpinnerCategories();

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openCamera();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String newProductName = productName.getText().toString();
                String newProductCategory = productCategory.getSelectedItem().toString();
                String newProductExpiryData = productExpiryDate.getText().toString();
                String newProductQuantity = productQuantity.getText().toString();
                Bitmap newProductImage = image;

                if(newProductName.length() != 0 &&
                newProductCategory.length() != 0 &&
                newProductExpiryData.length() != 0 &&
                newProductQuantity.length() != 0 &&
                newProductImage != null)
                {
                    AddProduct(newProductName, newProductCategory, newProductExpiryData, newProductQuantity, newProductImage);
                    productName.setText("");
                    productExpiryDate.setText("");
                    productQuantity.setText("");
                    imageViewCamera.setImageBitmap(null);
                }
                else
                {
                    Toast.makeText(AddActivity.this,"Must put something in text field!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AddProduct(String newProductName, String newProductCategory, String newProductExpiryData,
                            String newProductQuantity, Bitmap newProductImage)
    {
        boolean isInserted = keepFreshDatabaseHelper.addProduct(newProductName, newProductCategory, newProductExpiryData, newProductQuantity, newProductImage);

        if(isInserted)
        {
            Toast.makeText(AddActivity.this, "Product added", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(AddActivity.this, "Product not added", Toast.LENGTH_SHORT).show();
        }
    }

    private void increase()
    {
        int quantity = Integer.parseInt(productQuantity.getText().toString());
        quantity ++;
        productQuantity.setText(String.valueOf(quantity));
    }

    private void decrease()
    {
        int quantity = Integer.parseInt(productQuantity.getText().toString());
        if(quantity > 0)
        {
            quantity--;
            productQuantity.setText(String.valueOf(quantity));
        }
    }

    private void populateSpinnerCategories()
    {
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getAllCategories());
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategory.setAdapter(categoriesAdapter);
        categoriesAdapter.notifyDataSetChanged();
    }

    public List<String> getAllCategories()
    {
        Cursor res = keepFreshDatabaseHelper.getAllCategories();
        List<String> values = new ArrayList<>();

        while (res.moveToNext())
        {
            if (!res.getString(1).equals("All"))
            {
                values.add(res.getString(1));
            }
        }
        Collections.sort(values);
        return values;
    }


    private void openMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openCamera()
    {
        try
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null)
            {
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(AddActivity.this, e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            image = (Bitmap) extras.get("data");
            imageViewCamera.setImageBitmap(image);
        }
    }
}