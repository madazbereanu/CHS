package com.example.keepfresh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProductsCustomAdapter extends BaseAdapter
{
    private static LayoutInflater inflater = null;

    private Context context;
    private List<String> productNameList;
    private List<String> productImageList;

    public ProductsCustomAdapter(Context context, List<String> productName, List<String> productImage)
    {
        this.context = context;
        this.productNameList = productName;
        this.productImageList = productImage;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder
    {
        TextView productName;
        ImageView productImage;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.row_main, null);

        holder.productName = rowView.findViewById(R.id.image_name);
        holder.productImage = rowView.findViewById(R.id.image_view);

        holder.productName.setText(productNameList.get(position));
        holder.productImage.setImageBitmap(stringToBitmap(productImageList.get(position)));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "You Clicked "+productNameList.get(position), Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }
    private static Bitmap stringToBitmap(String encodedString)
    {
        try
        {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }
        catch (Exception e)
        {
            e.getMessage();
            return null;
        }
    }
}
