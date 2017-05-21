package com.sample.datepickerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SampleActivity extends AppCompatActivity implements OnCheckInOutDateListener {

    public final String CHECKIN = "Check In";
    public final String CHECKOUT = "Check Out";
    @Bind(R.id.action_bar)
    Toolbar actionBar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    DateFragment checkInDateFragment;
    DateFragment checkOutDateFragment;
    private Date checkInDate;
    private Date checkOutDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);
        ButterKnife.bind(this);
        setSupportActionBar(actionBar);
        setupViewPager();
    }

    private void setupViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle checkinBundle = new Bundle();
        Bundle checkOutBundle = new Bundle();
        checkinBundle.putSerializable("key", DateFragment.DateType.CHECKIN);
        checkOutBundle.putSerializable("key", DateFragment.DateType.CHECKOUT);
        checkInDateFragment = DateFragment.getFragment(checkinBundle);
        checkOutDateFragment = DateFragment.getFragment(checkOutBundle);

        adapter.addFrag(checkInDateFragment, CHECKIN);
        adapter.addFrag(checkOutDateFragment, CHECKOUT);

        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((DateFragment) ((ViewPagerAdapter) viewpager.getAdapter()).getFragment(position)).initCalendar();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabs.setupWithViewPager(viewpager);
    }

    @Override
    public void onCheckInDateChanged(Date checkInDate) {
        this.checkInDate = checkInDate;
        checkInDateFragment.setCheckInDate(checkInDate);
        checkOutDateFragment.setCheckInDate(checkInDate);
        viewpager.setCurrentItem(1);
    }

    @Override
    public void onCheckOutDateChanged(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
        checkInDateFragment.setCheckOutDate(checkOutDate);
        checkOutDateFragment.setCheckOutDate(checkOutDate);
        if (checkInDate == null) {
            viewpager.setCurrentItem(0);
        }
    }

    @Override
    public void onCheckInDateNotSelected() {

    }


}