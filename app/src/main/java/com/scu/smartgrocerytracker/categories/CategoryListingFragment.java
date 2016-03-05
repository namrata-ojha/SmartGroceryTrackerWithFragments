package com.scu.smartgrocerytracker.categories;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.constants.Constants;
import com.scu.smartgrocerytracker.items.ItemListingActivity;

import java.util.List;


/**
 *
 */
public class CategoryListingFragment extends Fragment {
    SmartGroceryDBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.category_listing, container, false);
        Log.d("Category Fragment ", "Called");


        ListView listView = (ListView) rootView.findViewById(R.id.categoryListView);
        dbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());
        //dbHelper.getWritableDatabase();
        List<Category> categories = dbHelper.getAll();
        for (Category c : categories) {
            Log.d("category from list", c.getCategoryName().toString());

        }
        listView.setAdapter(new CategoryListingAdapter(getActivity(), R.layout.category_list_row, categories));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getItemAtPosition(position);
                Intent i = new Intent(getActivity().getApplicationContext(), ItemListingActivity.class);
                i.putExtra(Constants.CATEGORY_NAME, category.getCategoryName());
                // i.putExtra(Constants.IMAGE_NAME, category.getImageName());
                startActivity(i);

            }
        });
        return rootView;
    }
}
