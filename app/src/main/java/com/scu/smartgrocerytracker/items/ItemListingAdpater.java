package com.scu.smartgrocerytracker.items;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by namrataojha on 2/17/16.
 */
public class ItemListingAdpater extends BaseAdapter implements Filterable {
    SmartGroceryDBHelper dbHelper;
    String itemNameToAdd;
    String categoryToAdd;
    int itemID;
    int quantity;
    ValueFilter valueFilter;
    List<Items> mFilterList;
    Context context;
    private List<Items> itemList; //data source

    public ItemListingAdpater(Context context, List<Items> itemListNew) {
        this.context = context;
        this.itemList = itemListNew;
        this.mFilterList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        dbHelper = SmartGroceryDBHelper.getInstance(this.context);
        Items item = itemList.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.content_detail_item_view, null);
        }

        // Set the text
        TextView textView = (TextView) convertView.findViewById(R.id.textViewItemName);
        textView.setText(item.getItemName());

        // Set the image
        try {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewItemImage);
            InputStream inputStream = this.context.getAssets().open(item.getItemPath());
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    // inner class
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Items> filterList = new ArrayList<Items>();
                constraint = constraint.toString().toUpperCase();
                for (int i = 0; i < mFilterList.size(); i++) {
                    if ((mFilterList.get(i).getItemName().toUpperCase()).contains(constraint.toString().toUpperCase())) {

                        Items item = new Items(mFilterList.get(i).getItemName(), mFilterList.get(i).getItemPath(), mFilterList.get(i).getItemCategory());

                        filterList.add(item);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mFilterList.size();
                results.values = mFilterList;
            }
            return results;

        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList = (ArrayList<Items>) results.values;
            notifyDataSetChanged();
        }
    }

}

