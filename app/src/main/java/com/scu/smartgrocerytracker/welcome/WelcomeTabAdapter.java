package com.scu.smartgrocerytracker.welcome;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.scu.smartgrocerytracker.categories.CategoryListingActivity;
import com.scu.smartgrocerytracker.pantry.PantryFragment;
import com.scu.smartgrocerytracker.settings.Settings;
import com.scu.smartgrocerytracker.shoppingList.ShoppingList;

import java.util.HashMap;

public class WelcomeTabAdapter extends FragmentStatePagerAdapter {
 Context context;
    private Fragment categoryFragment;
    private Fragment soppingCartFragment;
    private Fragment pantryFragment;
    private Fragment settingFragment;
    private HashMap mFragmentTags ;
    private FragmentManager mFragmentManager;

    public WelcomeTabAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
       mFragmentTags = new HashMap<Integer,String>();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WelcomeFragment();
            case 1:
                categoryFragment=new CategoryListingActivity();
               // categoryFragment.getView().bringToFront();
                //categoryFragment.getView().setVisibility(View.GONE);
                return categoryFragment;

            case 2:
                soppingCartFragment=new ShoppingList();
                //soppingCartFragment.getView().bringToFront();
               // soppingCartFragment.getView().setVisibility(View.GONE);

                return soppingCartFragment;
            case 3:
                pantryFragment=new PantryFragment();
                return pantryFragment;

            case 4:
                settingFragment=new Settings();
                return settingFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment) {
            // record the fragment tag here.
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position, tag);
        }
        return obj;
    }

    public Fragment getFragment(int position) {
        String tag = (String)mFragmentTags.get(position);


        if (tag == null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
