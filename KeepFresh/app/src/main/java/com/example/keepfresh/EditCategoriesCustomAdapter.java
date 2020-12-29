package com.example.keepfresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EditCategoriesCustomAdapter extends BaseAdapter {
    //TO DO: add functionality to edit and remove button

    private static LayoutInflater inflater = null;

    private Context context;
    private List<String> categoryName;

    public EditCategoriesCustomAdapter(Context context, List<String> categoryName)
    {
        this.categoryName = categoryName;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryName.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TextView rowEditCategories;
        ImageButton saveEditCategories;
        ImageButton removeEditCategories;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.row_edit_categories, null);

        holder.rowEditCategories = rowView.findViewById(R.id.row_edit_categories_field);
        holder.saveEditCategories = rowView.findViewById(R.id.save_edit_categories);
        holder.removeEditCategories = rowView.findViewById(R.id.remove_edit_categories);

        holder.rowEditCategories.setText(categoryName.get(position));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+categoryName.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
