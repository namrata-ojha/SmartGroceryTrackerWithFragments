package com.scu.smartgrocerytracker.pantry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.constants.Constants;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * Created by apar.sri on 2/20/2016.
 */
public class PantryListingAdapter extends ArrayAdapter<PantryItem> {

    private final List<PantryItem> pantryItems;

    private Set<Integer> checkedPantryItems ;

    public static final int POSITION = 1;
    public static final int PANTRY_ID = 2;

    public PantryListingAdapter(Context context, int resource, List<PantryItem> pantryItems, Set<Integer> checkedPantryItems) {
        super(context, resource, pantryItems);
        this.pantryItems = pantryItems;
        this.checkedPantryItems = checkedPantryItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PantryItem pantryItem = pantryItems.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.inventory_list_row, null);

        // Set the text
        TextView itemNameTextView = (TextView) row.findViewById(R.id.itemNameTextView);
        itemNameTextView.setText(pantryItem.getName());

        TextView quantityTextView = (TextView) row.findViewById(R.id.quantityTextView);
        quantityTextView.setText("(" + pantryItem.getTotalQuantity() + "" + pantryItem.getUnit() + ")");

        TextView expDateTextView = (TextView) row.findViewById(R.id.expDateTextView);
        expDateTextView.setText(pantryItem.getExpiryDateString());

        CheckBox checkBox = (CheckBox)row.findViewById(R.id.checkBox);
        checkBox.setTag(pantryItem.getId());



        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                int pantryId = (Integer) cb.getTag();
                if (cb.isChecked()) {
                    checkedPantryItems.add(pantryId);
                } else {
                    checkedPantryItems.remove(pantryId);
                }
            }
        });
        return row;
    }

    public Set<Integer> getCheckedPantryItems() {
        return checkedPantryItems;
    }

}

