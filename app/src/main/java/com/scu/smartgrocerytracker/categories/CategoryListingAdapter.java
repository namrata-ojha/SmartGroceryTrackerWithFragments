package com.scu.smartgrocerytracker.categories;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scu.smartgrocerytracker.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CategoryListingAdapter extends ArrayAdapter<Category> {

    private final List<Category> categories;

    public CategoryListingAdapter(Context context, int resource, List<Category> categories) {
        super(context, resource, categories);
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = categories.get(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.category_list_row, null);

        // Set the text
        TextView textView = (TextView) row.findViewById(R.id.categoryTextView);
        textView.setText(category.getCategoryName());

        // Set the image
        try {
            ImageView imageView = (ImageView) row.findViewById(R.id.categoryImageView);
            InputStream inputStream = getContext().getAssets().open(category.getImageName());
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return row;
    }
}
