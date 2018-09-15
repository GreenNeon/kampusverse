package com.kampusverse.Logic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {
    public Common() {
    }

    public int timeSpanToOnce(Calendar today, Calendar dayOfBirth) {
        if (dayOfBirth.getTimeInMillis() < today.getTimeInMillis())
            return -1;
        return timeSpanInDays(today, dayOfBirth);
    }
    public int timeSpanToNextMatkul(Calendar today, Calendar dayOfBirth) {
        Calendar nextWeekAfter = nextWeekAfter(today, dayOfBirth);
        if (dayOfBirth.getTimeInMillis() < today.getTimeInMillis())
            return -1;
        return timeSpanInDays(today, nextWeekAfter);
    }

    private int timeSpanInDays(Calendar cal1, Calendar cal2) {
        return (int) ((Math.abs(cal1.getTimeInMillis() - cal2.getTimeInMillis())) / (1000 * 60 * 60 * 24));
    }

    public Calendar nextWeekAfter(Calendar today, Calendar dayOfBirth) {
        Calendar nextWeekAfter = (Calendar) today.clone();

        int hari = dayOfBirth.get(Calendar.DAY_OF_WEEK);
        int addeddate = 0, daynext = nextWeekAfter.get(Calendar.DAY_OF_WEEK);
        if (daynext > hari)
            addeddate = 7 - daynext + hari;
        else if (daynext == hari)
            addeddate = 0;
        else
            addeddate = hari - daynext;
        nextWeekAfter.add(Calendar.DATE, addeddate);

        return nextWeekAfter;
    }

    public Calendar calendarByDays(Calendar today, int hari) {
        Calendar nextByDays = (Calendar) today.clone();

        int addeddate = 0, daynext = nextByDays.get(Calendar.DAY_OF_WEEK);
        if (daynext > hari)
            addeddate = 7 - daynext + hari;
        else if (daynext == hari)
            addeddate = 0;
        else
            addeddate = hari - daynext;
        nextByDays.add(Calendar.DATE, addeddate);

        return nextByDays;
    }
}
