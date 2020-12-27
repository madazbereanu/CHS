package com.example.keepfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity
{
    private ImageButton buttonCamera;
    private ImageButton buttonDecrease;
    private ImageButton buttonIncrease;
    private EditText productQuantity;
    private EditText productName;
    private Spinner productCategory;
    private EditText productExpiryDate;
    private Button buttonCancel;
    private Button buttonSave;
    private static final int CAMERA_REQUEST_CODE = 1;
//    private StorageReference storage;
//    private ProgressDialog progressDialog;

//    private ImageButton buttonCalendar;
//    private EditText expiryDateField;
//    private DatePickerDialog datePickerDialog;
//    private int year;
//    private int month;
//    private int day;
//    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

//        storage = FirebaseStorage.getInstance().getReference();
//        progressDialog = new ProgressDialog(this);

        buttonCamera = (ImageButton) findViewById(R.id.button_camera);
        buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonSave = (Button) findViewById(R.id.button_save);
        buttonDecrease = (ImageButton) findViewById(R.id.decrease_button);
        buttonIncrease = (ImageButton) findViewById(R.id.increase_button);

        productQuantity = (EditText) findViewById(R.id.quantity_field);
        productName = (EditText) findViewById(R.id.name_field);
        productCategory = (Spinner) findViewById(R.id.category_spinner);
        productExpiryDate = (EditText) findViewById(R.id.calendar_field);

        populateSpinnerCategories();

//        buttonCalendar = findViewById(R.id.calendar_button);
//        expiryDateField = findViewById(R.id.expiry_date);
//
//        findViewById(R.id.calendar_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog();
//            }
//        });

//        buttonCalendar.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              calendar = Calendar.getInstance();
//              year = calendar.get(Calendar.YEAR);
//              month = calendar.get(Calendar.MONTH);
//              day = calendar.get(Calendar.DAY_OF_MONTH);
//              datePickerDialog = new DatePickerDialog(AddActivity.this,
//                      new DatePickerDialog.OnDateSetListener() {
//                          @Override
//                          public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                              expiryDateField.setText(dayOfMonth + "/" + month + "/" + year);
//                          }
//                      }, year, month, day);
//              datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//              datePickerDialog.show();
//          }
//        });


        buttonCamera.setOnClickListener(new View.OnClickListener(){
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
    }

    private void populateSpinnerCategories() {
//        String[] categories = {"Food", "Drink", "Pill"};
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getAllCategories());
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategory.setAdapter(categoriesAdapter);
//        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categories_array));
//        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategories.setAdapter(categoriesAdapter);
    }
    public List<String> getAllCategories()
    {
        KeepFreshDatabaseHelper keepFreshDatabaseHelper = KeepFreshDatabaseHelper.getInstance(this);
        Cursor res = keepFreshDatabaseHelper.getAllCategories();
        if(res.getCount() == 0)
        {
            //show message no data available
        }
        List<String> values = new ArrayList<>();
        while (res.moveToNext()){
            values.add(res.getString(1));
        }

        return values;
//        listViewCategories.setAdapter(new EditCategoriesCustomAdapter(this, values));
    }
    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openCamera()
    {
        try
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);

        }
        catch (Exception e)
        {

        }
    }
//    private void showDatePickerDialog() {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                this,
//                this,
//                Calendar.getInstance().get(Calendar.YEAR),
//                Calendar.getInstance().get(Calendar.MONTH),
//                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.show();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK)
//        {
//            progressDialog.setMessage("Uploading Image ...");
//            progressDialog.show();
//
//            Uri uri = data.getData();
//            StorageReference filePath = storage.child("Photos").child(uri.getLastPathSegment());
//            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
//
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    progressDialog.dismiss();
//                    Toast.makeText(AddActivity.this, "Uploading Finished ...", Toast.LENGTH_SHORT);
//                }
//            });
//        }
//    }

//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        String date = "month/day/year: " + month + "/" + dayOfMonth + "/" + year;
//        expiryDateField.setText(date);
//    }
}