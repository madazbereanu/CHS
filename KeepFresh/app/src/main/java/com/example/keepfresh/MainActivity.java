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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private Spinner spinnerCategories;
    private ImageButton buttonAdd;
    private ListView listViewProducts;
    private KeepFreshDatabaseHelper keepFreshDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keepFreshDatabaseHelper = KeepFreshDatabaseHelper.getInstance(this);
//        listViewProducts = findViewById(R.id.product_list_view);
        buttonAdd = findViewById(R.id.add_button);

        spinnerCategories = findViewById(R.id.spinner_categories);
        populateSpinnerCategories();

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateSpinnerCategories() {
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getAllCategories());
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(categoriesAdapter);
    }
    public List<String> getAllCategories()
    {
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
    }

    public void viewAllProducts(){
        Cursor res = keepFreshDatabaseHelper.getAllProducts();
        if(res.getCount() == 0){
            //show message no data available
        }
        List<String> values = new ArrayList<>();
        List<String> values1 = new ArrayList<>();
        while (res.moveToNext()){
            values.add(res.getString(1));
            values1.add(res.getString(2));
        }
//        listViewProducts.setAdapter(new ProductsCustomAdapter(this, values, values1));
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
}