package com.example.keepfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The class will manage the functionalities  for edit categories activity
 */
public class EditCategoriesActivity extends AppCompatActivity
{
    //hardcoded values for categories
    private static final List<String> CATEGORIES = Arrays.asList("All", "Drink", "Food");

    private EditText categoryName;
    private Button addNewCategoryButton;
    private ListView listViewCategories;

    private Context context;

    private KeepFreshDatabaseHelper keepFreshDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);

        context = this;

        categoryName = findViewById(R.id.add_new_categories_field);
        addNewCategoryButton = findViewById(R.id.add_new_category_button);
        listViewCategories = findViewById(R.id.list_view_categories);

        keepFreshDatabaseHelper = KeepFreshDatabaseHelper.getInstance(context);

        //add hardcoded categories
        for (String category: CATEGORIES)
        {
            addData(category);
        }

        addNewCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String newEntry = categoryName.getText().toString();
                if(categoryName.length() != 0)
                {
                    addData(newEntry);
                    categoryName.setText("");
                }
                else
                {
                    Toast.makeText(EditCategoriesActivity.this,"Must put something in text field!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewAllCategories();
    }

    /**
     * This method adds a new category to database
     * @param newEntry
     */
    public void addData(String newEntry)
    {
        boolean isInserted = keepFreshDatabaseHelper.addCategory(newEntry);

        if(isInserted)
        {
            Toast.makeText(EditCategoriesActivity.this, "Category added", Toast.LENGTH_SHORT).show();
            viewAllCategories();
        }
        else
        {
            Toast.makeText(EditCategoriesActivity.this, "Category not added", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method sets all categories to list view using adaptor
     */
    public void viewAllCategories()
    {
        Cursor res = keepFreshDatabaseHelper.getAllCategories();
        List<String> values = new ArrayList<>();

        while (res.moveToNext())
        {
            values.add(res.getString(1));
        }
        Collections.sort(values);
        listViewCategories.setAdapter(new EditCategoriesCustomAdapter(this, values));
    }
}