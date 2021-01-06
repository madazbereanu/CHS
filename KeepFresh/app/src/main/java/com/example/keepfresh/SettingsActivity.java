package com.example.keepfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * This class manage activities
 */
public class SettingsActivity extends AppCompatActivity
{
    private TextView editCategories;
    private TextView help;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editCategories = (TextView) findViewById(R.id.edit_categories);
        help = (TextView) findViewById(R.id.help);

        editCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openEditCategories();
            }
        });

        help.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openHelp();
            }
        });
    }

    public void openEditCategories()
    {
        Intent intent = new Intent(this, EditCategoriesActivity.class);
        startActivity(intent);
    }

    private void openHelp()
    {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}