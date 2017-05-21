package com.sample.datepickerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FinalScreen extends AppCompatActivity {

    @Bind(R.id.tvCheckIn)
    TextView tvCheckIn;
    @Bind(R.id.vSeparator)
    View vSeparator;
    @Bind(R.id.tvCheckOut)
    TextView tvCheckOut;
    @Bind(R.id.tvTotalStay)
    TextView tvTotalStay;
    @Bind(R.id.card_view)
    CardView cardView;
    private Date checkInDate;
    private Date checkOutDate;
    private int days;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalscreen);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpViews();
    }

    private void setUpViews() {
        Intent intent = getIntent();
        checkInDate = (Date) intent.getSerializableExtra(Constants.CHECKIN);
        checkOutDate = (Date) intent.getSerializableExtra(Constants.CHECKOUT);
        days = intent.getIntExtra(Constants.TOTAL_STAY, 0);
        updateCheckInOutDays();
        updateNightStay();

    }

    private void updateCheckInOutDays() {
        Calendar mCalendar = Calendar.getInstance();

        mCalendar.setTime(checkInDate);

        String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        String day = mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        int date = mCalendar.get(Calendar.DAY_OF_MONTH);


        tvCheckIn.setText(String.format("%s \n %d \n %s", month, date, day));

        mCalendar.setTime(checkOutDate);

        month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        day = mCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        date = mCalendar.get(Calendar.DAY_OF_MONTH);


        tvCheckOut.setText(String.format("%s \n %d \n %s", month, date, day));
    }


    private void updateNightStay() {
        if (checkInDate != null && checkOutDate != null) {
            if (days < 1) {
                tvTotalStay.setText("Please pick the days");
            } else if (days == 1) {
                tvTotalStay.setText(String.format("Total stay: %d night", days));
            } else {
                tvTotalStay.setText(String.format("Total stay: %d nights", days));
            }
        }
    }
}
