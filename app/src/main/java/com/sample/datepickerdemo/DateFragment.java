package com.sample.datepickerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DateFragment extends Fragment {

    @Bind(R.id.calendar_view)
    CalendarPickerView calendarView;
    private Date checkInDate;
    private Date checkOutDate;
    private DateType dateType = DateType.CHECKIN;

    private OnCheckInOutDateListener checkInOutDateListener;

    public static DateFragment getFragment(Bundle bundle) {
        DateFragment f = new DateFragment();
        Bundle args = bundle;
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        checkInOutDateListener = (OnCheckInOutDateListener) activity;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
        initCalendar();
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
        initCalendar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        ButterKnife.bind(this, view);
        initData();
        initViews();
        return view;

    }

    private void initData() {
        dateType = (DateType) getArguments().getSerializable("key");
    }

    private void initViews() {
        initCalendar();
        calendarView.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
            @Override
            public void onInvalidDateSelected(Date date) {

            }
        });




        calendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                if (dateType == DateType.CHECKIN) {
                    checkInOutDateListener.onCheckInDateChanged(date);
                } else {
                    if (checkInDate != null && date != null && 0 == checkInDate.compareTo(date)) {
                        Toast.makeText(getContext(), "please select future date", 2000).show();
                        initCalendar();
                        return ;
                    }

                        checkInOutDateListener.onCheckOutDateChanged(date);
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void initCalendar() {

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);


        if(checkInDate==null && checkOutDate==null) {
            calendarView.init(new Date(), nextYear.getTime()) //
                    .inMode(CalendarPickerView.SelectionMode.RANGE); //
        }else if(checkInDate!=null && checkOutDate==null){

        }else if(checkInDate !=null && checkOutDate!=null){

        }

        if (checkInDate != null) {
            if (dateType == DateType.CHECKIN) {
                calendarView.init(new Date(), nextYear.getTime()) //
                        .inMode(CalendarPickerView.SelectionMode.RANGE); //
            } else {
                calendarView.init(checkInDate, nextYear.getTime()) //
                        .inMode(CalendarPickerView.SelectionMode.RANGE); //
            }
            calendarView.selectDate(checkInDate);
        }

        if (checkInDate != null && checkOutDate != null) {
            ArrayList<Date> dates = new ArrayList<Date>();
            dates.add(checkInDate);
            dates.add(checkOutDate);
            if (dateType == DateType.CHECKIN) {
                calendarView.init(new Date(), nextYear.getTime()) //
                        .inMode(CalendarPickerView.SelectionMode.RANGE).withSelectedDates(dates); //
            } else {
                calendarView.init(checkInDate, nextYear.getTime()) //
                        .inMode(CalendarPickerView.SelectionMode.RANGE).withSelectedDates(dates); //
            }

        }
    }

    public enum DateType {
        CHECKIN,
        CHECKOUT
    }
}
