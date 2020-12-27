package com.example.keepfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditCategoriesActivity extends AppCompatActivity {

    private EditText categoryName;
    private Button addNewCategoryButton;
    private ListView listViewCategories;

    private Context context;

    private static final String[] list = new String[]{"food", "pills"};
//    private KeepFreshDatabaseHelper keepFreshDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);

        context = this;

        categoryName = findViewById(R.id.add_new_categories_field);
        addNewCategoryButton = findViewById(R.id.add_new_category_button);
        listViewCategories = findViewById(R.id.list_view_categories);


//        EditCategoriesCustomAdapter editCategoriesCustomAdapter = new EditCategoriesCustomAdapter(getApplicationContext(), list);
//        listViewCategories.setAdapter(editCategoriesCustomAdapter);
        listViewCategories.setAdapter(new EditCategoriesCustomAdapter(this, list));

//        keepFreshDatabaseHelper = KeepFreshDatabaseHelper.getInstance(this);

//        addNewCategoryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newEntry = categoryName.getText().toString();
//                if(categoryName.length() != 0)
//                {
//                    AddData(newEntry);
//                    categoryName.setText("");
//                }
//                else
//                {
//                    Toast.makeText(EditCategoriesActivity.this,"Must put something in text field!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
//
//    public void AddData(String newEntry)
//    {
//        // Get singleton instance of database
//        keepFreshDatabaseHelper = KeepFreshDatabaseHelper.getInstance(this);
//        // Create sample data
////        Item sampleUser = new Item();
////        Post samplePost = new Post();
////        sampleUser.name = newEntry;
////        samplePost.item = sampleUser;
//
//        // Add sample post to the database
//        boolean insertData = keepFreshDatabaseHelper.addCategory(newEntry);
//        if(insertData == true)
//        {
//            Toast.makeText(EditCategoriesActivity.this,"Successfully Entered", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(EditCategoriesActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
//
//        }
//        try {
//            List<String> categories = keepFreshDatabaseHelper.getAllPosts();
//            String[] items = new String[100];// = new String;
//            int i = 0;
//            for (String post : categories) {
//                items[i] = post;
//                i++;
//            }
////        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
////            ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
////            listViewCategories.setAdapter(adp);
//            EditCategoriesCustomAdapter editCategoriesCustomAdapter = new EditCategoriesCustomAdapter(getApplicationContext(), items);
//            listViewCategories.setAdapter(editCategoriesCustomAdapter);
//        }catch (Exception e)
//        {
//
//        }
//    }
}