package com.example.keepfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity
{
    private PostsDatabaseHelper databaseHelper;
    private EditText itemName;
    private Button addButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        listView = findViewById(R.id.item_list_view);
        itemName = findViewById(R.id.item_field);
        addButton = findViewById(R.id.item_add_new_button);

        databaseHelper = PostsDatabaseHelper.getInstance(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = itemName.getText().toString();
                if(itemName.length() != 0)
                {
                    AddData(newEntry);
                    itemName.setText("");
                }
                else
                {
                    Toast.makeText(ShoppingListActivity.this,"Must put something in text field!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        try {
            List<Post> posts = databaseHelper.getAllPosts();
            List<String> items = new ArrayList<>();
            for (Post post : posts) {
                items.add(post.item.name);
            }
//        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
            ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
            listView.setAdapter(adp);
        }catch (Exception e)
        {

        }
//        databaseHelper.deleteAllPostsAndItems();
    }

    public void AddData(String newEntry)
    {
        // Get singleton instance of database
        databaseHelper = PostsDatabaseHelper.getInstance(this);
        // Create sample data
        Item sampleUser = new Item();
        Post samplePost = new Post();
        sampleUser.name = newEntry;
        samplePost.item = sampleUser;
        // Add sample post to the database
        boolean insertData = databaseHelper.addPost(samplePost);
        if(insertData == true)
        {
            Toast.makeText(ShoppingListActivity.this,"Successfully Entered", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(ShoppingListActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();

        }
        try {
            List<Post> posts = databaseHelper.getAllPosts();
            List<String> items = new ArrayList<>();
            for (Post post : posts) {
                items.add(post.item.name);
            }
//        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
            ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
            listView.setAdapter(adp);
        }catch (Exception e)
        {

        }
    }
}