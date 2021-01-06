package com.example.keepfresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity
{
    private Spinner spinnerCategories;
    private ImageButton buttonAdd;
    private ListView listViewProducts;

    private KeepFreshDatabaseHelper keepFreshDatabaseHelper;

    private String messageString;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keepFreshDatabaseHelper = KeepFreshDatabaseHelper.getInstance(this);

        listViewProducts = findViewById(R.id.product_list_view);
        buttonAdd = findViewById(R.id.add_button);
        spinnerCategories = findViewById(R.id.spinner_categories);

        populateSpinnerCategories();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String item = parent.getItemAtPosition(position).toString();
                viewAllProductsFromCategory(item);
                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        createNotificationChannel();
        expiredData();
    }

    private void expiredData()
    {
        Cursor res = keepFreshDatabaseHelper.getAllProducts();
        List<String> productsName = new ArrayList<>();
        List<String> productsExpiredData = new ArrayList<>();

        while (res.moveToNext())
        {
            productsName.add(res.getString(0));
            productsExpiredData.add(res.getString(3));
        }

        for(int i = 0; i < productsExpiredData.size(); i++)
        {
            if(isAlmostExpired(productsExpiredData.get(i), productsName.get(i)))
            {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notificationExpired")
                        .setSmallIcon(R.drawable.ic_baseline_calendar_today_24)
                        .setContentTitle("Keep Fresh")
                        .setContentText(messageString)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(100, builder.build());
            }
        }
    }

    private boolean isAlmostExpired(String expiredData, String productName)
    {
        messageString = null;
        String[] elements = expiredData.split("/");
        int expiredDay = Integer.parseInt(elements[0]);
        int expiredMonth = Integer.parseInt(elements[1]);
        int expiredYear = Integer.parseInt(elements[2]);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        if((expiredYear - year) == 0)
            if((expiredMonth - month) == 0)
            {
                if (((expiredDay - day) >= 0) && ((expiredDay - day) <= 3))
                {
                    messageString = "The product " + productName + " will expire soon";
                    return true;
                }
                if ((expiredDay - day) < 0)
                {
                    messageString = "The product " + productName + " is expired";
                    return true;
                }
            }
        return false;
    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "KeepFreshChannel";
            String description = "Channel for expiry data";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notificationExpired", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void populateSpinnerCategories()
    {
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getAllCategories());
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(categoriesAdapter);
        categoriesAdapter.notifyDataSetChanged();
    }

    public List<String> getAllCategories()
    {
        Cursor res = keepFreshDatabaseHelper.getAllCategories();
        List<String> values = new ArrayList<>();

        while (res.moveToNext())
        {
            values.add(res.getString(1));
        }

        Collections.sort(values);
        return values;
    }

    public void viewAllProductsFromCategory(String categoryName)
    {
        Cursor res = keepFreshDatabaseHelper.getAllProducts();
        List<String> values = new ArrayList<>();
        List<String> values1 = new ArrayList<>();

        if(categoryName.equals("All"))
        {
            while (res.moveToNext())
            {
                values.add(res.getString(0));
                values1.add(res.getString(1));
            }
        }
        else
        {
            while (res.moveToNext())
            {
                if (categoryName.equals(res.getString(2)))
                {
                    values.add(res.getString(0));
                    values1.add(res.getString(1));
                }
            }
        }
        listViewProducts.setAdapter(new ProductsCustomAdapter(this, values, values1));
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.setting)
        {
            Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return false;
        }
        if(id == R.id.shopping_list)
        {
            Toast.makeText(getApplicationContext(), "Shopping list", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
            startActivity(intent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}