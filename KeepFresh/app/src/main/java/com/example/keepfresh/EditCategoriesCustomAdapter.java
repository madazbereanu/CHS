//package com.example.keepfresh;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class EditCategoriesCustomAdapter extends BaseAdapter {
//
//    private static LayoutInflater inflater;
//
//    private Context context;
//    private String[] categoryName;
//
//    public EditCategoriesCustomAdapter(Context context, String[] categoryName)
//    {
//        this.categoryName = categoryName;
//        this.context = context;
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        View rowView = inflater.inflate(R.layout.row_edit_categories, null);
//
//        TextView rowEditCategories = rowView.findViewById(R.id.row_edit_categories);
//        ImageButton saveEditCategories = rowView.findViewById(R.id.save_edit_categories);
//        ImageButton removeEditCategories = rowView.findViewById(R.id.remove_edit_categories);
//
//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+categoryName[position], Toast.LENGTH_LONG).show();
//            }
//        });
//        return rowView;
//    }
//}
