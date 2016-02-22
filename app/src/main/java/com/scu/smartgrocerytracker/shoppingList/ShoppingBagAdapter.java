package com.scu.smartgrocerytracker.shoppingList;

/**
 * Created by Noel on 2/18/2016.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import com.scu.smartgrocerytracker.*;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.items.Items;

/**
 * Created by namrataojha on 2/17/16.
 */
public class ShoppingBagAdapter extends ArrayAdapter<Items> {
    static boolean buttonClick = false;
    private final List<Items> itemList;
    SmartGroceryDBHelper dbHelper;
    String itemNameToAdd;
    String categoryToAdd;
    int itemID;
    int quantity;


    public ShoppingBagAdapter(Context context, int resource, List<Items> itemListNew) {

        super(context, resource, itemListNew);
        this.itemList = itemListNew;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        dbHelper = SmartGroceryDBHelper.getInstance(getContext());
        Items item = itemList.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.content_shopping_bag_view, null);

        // set the category
       // EditText editText = (EditText) row.findViewById(R.id.editTextShoppingBagDetail);
        //editText.setText("1".toString());

        TextView textView2 = (TextView) row.findViewById(R.id.textViewCategoryNameShoppingBag);
        textView2.setText(item.getItemCategory());//
        // Set the text
        TextView textView = (TextView) row.findViewById(R.id.textViewItemNameShoppingBag);
        textView.setText(item.getItemName());

        // Set the image
        try {
            ImageView imageView = (ImageView) row.findViewById(R.id.imageViewItemImageShoppingBag);
            InputStream inputStream = getContext().getAssets().open(item.getItemPath());
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        Button addButton = (Button) row.findViewById(R.id.buttonAddItem);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            Items item=   ItemListingActivity.addButtonClicks();
//                itemNameToAdd = item.getItemName();
//               categoryToAdd = item.getItemCategory();
//                itemID = item.getId();
//                quantity = 1;
//
//                    dbHelper.insertItemInShoppingList(dbHelper.getDB(), itemNameToAdd, categoryToAdd, quantity, itemID);
//                    Log.d("itemNameToAdd", "itemNameToAdd");
//                Log.d("categoryToAdd", "categoryToAdd");
//                    ItemListingAdpater.buttonClick = false;
//
//               Log.d("Button Value","true");
//            }
//        });


        return row;
    }


}


