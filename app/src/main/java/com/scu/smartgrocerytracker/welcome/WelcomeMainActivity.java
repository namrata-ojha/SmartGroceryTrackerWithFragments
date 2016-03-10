package com.scu.smartgrocerytracker.welcome;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.pantry.ExpiryNotificationService;

import org.apache.commons.lang3.time.DateUtils;



public class WelcomeMainActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private WelcomeTabAdapter mTabAdapter;
    private WelcomeClassesTabLayout mTabLayout;
    ActionBar bar;
    boolean isExpiryNotificatonRegistered;

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
                if ((arg0 == 2 || arg0 == 3 || arg0 == 1 || arg0 == 4 || arg0 == 0 ) && fragment != null) {
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
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            //  nop
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    });

        if(!isExpiryNotificatonRegistered) {
            registerExpiryNotificationService();
        }
}

    /**
     * Register intent to alarm manager
     */
    private void registerExpiryNotificationService() {

        final int REQUEST_CODE = 0;
        Intent intent = new Intent(this, ExpiryNotificationService.class);
        //intent.setAction(Intent.ACTION_MAIN);
        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        // this code creates a PendingIntent to start an Activity.
        PendingIntent pendingIntent = PendingIntent.getService(this, REQUEST_CODE,
                intent, 0);

        int alarmType = AlarmManager.ELAPSED_REALTIME;
        final long EXPIRATION_JOB_INTERVAL = DateUtils.MILLIS_PER_MINUTE;

        // The AlarmManager, like most system services, isn't created by application code, but
        // requested from the system.
        AlarmManager alarmManager = (AlarmManager)
                this.getSystemService(this.ALARM_SERVICE);

        // setRepeating takes a start delay and period between alarms as arguments.
        // The below code fires after 1 day, and repeats every day.
        alarmManager.setRepeating(alarmType, SystemClock.elapsedRealtime() + EXPIRATION_JOB_INTERVAL,
                EXPIRATION_JOB_INTERVAL, pendingIntent);

        isExpiryNotificatonRegistered = true;
    }

    public void changeFragment(int pos){
        mViewPager.setCurrentItem(pos, true);
    }

}
