package com.scu.smartgrocerytracker.pantry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scu.smartgrocerytracker.R;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.List;



/**
 * Created by apar.sri on 2/20/2016.
 */
public class PantryListingAdapter extends ArrayAdapter<PantryItem> {

    private final List<PantryItem> pantryItems;

    public PantryListingAdapter(Context context, int resource, List<PantryItem> pantryItems) {
        super(context, resource, pantryItems);
        this.pantryItems = pantryItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PantryItem pantryItem = pantryItems.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.pantry_list_row, null);

        // Set the text
        TextView itemNameTextView = (TextView) row.findViewById(R.id.itemNameTextView);
        itemNameTextView.setText(pantryItem.getName());

        TextView quantityTextView = (TextView) row.findViewById(R.id.quantityTextView);
        quantityTextView.setText("("+pantryItem.getTotalQuantity()+""+pantryItem.getUnit()+")");

        TextView expDateTextView = (TextView) row.findViewById(R.id.expDateTextView);
        expDateTextView.setText(pantryItem.getExpiryDateString());

        return row;
    }
}

