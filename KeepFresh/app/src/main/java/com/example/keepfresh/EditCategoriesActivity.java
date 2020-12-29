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

public class EditCategoriesActivity extends AppCompatActivity {
    //TO DO: introduce just unique categories


    //hardcoded values for categories
    private static final List<String> CATEGORIES = Arrays.asList("All", "Drink", "Food");

    private EditText categoryName;
    private Button addNewCategoryButton;
    private ListView listViewCategories;

    private Context context;

    private KeepFreshDatabaseHelper keepFreshDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);

        context = this;

        categoryName = findViewById(R.id.add_new_categories_field);
        addNewCategoryButton = findViewById(R.id.add_new_category_button);
        listViewCategories = findViewById(R.id.list_view_categories);

        keepFreshDatabaseHelper = KeepFreshDatabaseHelper.getInstance(this);

//        for (String category: CATEGORIES)
//        {
//            AddData(category);
//        }
        addNewCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = categoryName.getText().toString();
                if(categoryName.length() != 0)
                {
                    AddData(newEntry);
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

    public void AddData(String newEntry) {
        boolean isInserted = keepFreshDatabaseHelper.addCategory(newEntry);

        if(isInserted) {
            Toast.makeText(EditCategoriesActivity.this, "Category added", Toast.LENGTH_SHORT).show();
            viewAllCategories();
        }
        else
            Toast.makeText(EditCategoriesActivity.this, "Category not added", Toast.LENGTH_SHORT).show();

    }
    public void viewAllCategories()
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
        Collections.sort(values);
        listViewCategories.setAdapter(new EditCategoriesCustomAdapter(this, values));
    }


}