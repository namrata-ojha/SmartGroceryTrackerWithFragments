package com.scu.smartgrocerytracker.welcome;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.scu.smartgrocerytracker.BarcodeScanner.BarcodeFragment;
import com.scu.smartgrocerytracker.Notes.NoteList;
import com.scu.smartgrocerytracker.categories.CategoryListingFragment;
import com.scu.smartgrocerytracker.pantry.PantryListingFragment;
import com.scu.smartgrocerytracker.settings.Settings;
import com.scu.smartgrocerytracker.shoppingList.ShoppingListFragment;

import java.util.HashMap;

public class WelcomeTabAdapter extends FragmentStatePagerAdapter {
 Context context;
    private Fragment categoryFragment;
    private Fragment soppingCartFragment;
    private Fragment pantryFragment;
    private Fragment settingFragment;
    private Fragment barcodeFragment;
    private HashMap mFragmentTags ;
    private FragmentManager mFragmentManager;
    private Fragment notesTaking;

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
                categoryFragment=new CategoryListingFragment();
               // categoryFragment.getView().bringToFront();
                //categoryFragment.getView().setVisibility(View.GONE);
                return categoryFragment;

            case 2:
                soppingCartFragment=new ShoppingListFragment();
                //soppingCartFragment.getView().bringToFront();
               // soppingCartFragment.getView().setVisibility(View.GONE);

                return soppingCartFragment;
            case 3:
                pantryFragment=new PantryListingFragment();
                return pantryFragment;
            case 4:
                barcodeFragment=new BarcodeFragment();
                return barcodeFragment;
            case 5:
                notesTaking=new NoteList();
                return notesTaking;
            case 7:
                settingFragment=new Settings();
                return settingFragment;

            case 6:
                settingFragment=new Settings();
                return settingFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 8;
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
