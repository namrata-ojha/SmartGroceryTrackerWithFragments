package com.scu.smartgrocerytracker.welcome;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.scu.smartgrocerytracker.R;


public class WelcomeMainActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private WelcomeTabAdapter mTabAdapter;
    private WelcomeClassesTabLayout mTabLayout;
    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        mTabLayout = (WelcomeClassesTabLayout) findViewById(R.id.tab_layout);

        mTabAdapter = new WelcomeTabAdapter(getFragmentManager(), WelcomeMainActivity.this);
        mViewPager.setAdapter(mTabAdapter);

        mTabLayout.createTabs();

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int arg0) {
              //  bar.setSelectedNavigationItem(arg0);
                Fragment fragment = ((WelcomeTabAdapter) mViewPager.getAdapter()).getFragment(arg0);
                if ((arg0 == 2 || arg0 == 3 || arg0 == 1 || arg0 == 4 ||  arg0 == 0) && fragment != null) {
                    fragment.onResume();
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()

    {
        @Override
        public void onTabSelected (TabLayout.Tab tab){
        mViewPager.setCurrentItem(tab.getPosition());
    }

        @Override
        public void onTabUnselected (TabLayout.Tab tab){
        //  nop
    }

        @Override
        public void onTabReselected (TabLayout.Tab tab){
    }
    });
}
}
