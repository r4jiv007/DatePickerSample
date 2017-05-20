package com.sample.datepickerdemo;

import java.util.Date;

public interface OnCheckInOutDateListener {

    void onCheckInDateChanged(Date checkInDate);
    void onCheckOutDateChanged(Date checkOutDate);
}
