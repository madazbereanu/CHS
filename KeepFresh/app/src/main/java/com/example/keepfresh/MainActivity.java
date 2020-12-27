package com.example.keepfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
//    private Toolbar toolbar;
//    private TextView toolbarText;

    private Spinner spinnerCategories;
    private ImageButton buttonAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbarText = findViewById(R.id.toolbar_text);
//        if(toolbarText!=null && toolbar!=null)
//        {
//            toolbarText.setText(getTitle());
//            setSupportActionBar(toolbar);
//        }

//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        if(toolbarText!=null && toolbar!=null) {
//            toolbarText.setText(getTitle());
//            setSupportActionBar(toolbar);
//        }

        spinnerCategories=findViewById(R.id.spinner_categories);
        populateSpinnerCategories();

        buttonAdd = findViewById(R.id.add_button);
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateSpinnerCategories() {
//        String[] categories = {"Food", "Drink", "Pill"};
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getAllCategories());
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(categoriesAdapter);
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

    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.setting)
        {
            Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return false;
        }
        if(id == R.id.shopping_list) {
            Toast.makeText(getApplicationContext(), "Shopping list", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
            startActivity(intent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.setting:
//                 User chose the "Settings" item, show the app settings UI...
//                return true;
//
//
//            default:
//                 If we got here, the user's action was not recognized.
//                 Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//    }
   }