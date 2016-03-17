package com.scu.smartgrocerytracker.welcome;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.scu.smartgrocerytracker.R;


public class WelcomeClassesTabLayout extends TabLayout {
    public WelcomeClassesTabLayout(Context context) {
        super(context);
    }

    public WelcomeClassesTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WelcomeClassesTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void createTabs() {
        addTab(R.drawable.home, R.string.home);
        addTab(R.drawable.tab_grocery_icon, R.string.grocery);
        addTab(R.drawable.shoppinglist, R.string.shopping_bag);
        addTab(R.drawable.inventory, R.string.inventory);
        addTab(R.drawable.barcode, R.string.barcode);
        addTab(R.drawable.notes_edit, R.string.notes);
        addTab(R.drawable.maps, R.string.maps);
        addTab(R.drawable.settings, R.string.settings);
    }

    private void addTab(@DrawableRes int iconId, @StringRes int contentDescriptionId) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_icon, null);
        ImageView imageView = (ImageView) tabView.findViewById(R.id.tab_icon);
        imageView.setImageResource(iconId);
//        TextView tabText = (TextView)tabView.findViewById(R.id.textViewTabName);
//        tabText.setText(contentDescriptionId);
        Tab tab = newTab();
        tab.setCustomView(tabView).setContentDescription(contentDescriptionId);
        addTab(tab);
    }
}
