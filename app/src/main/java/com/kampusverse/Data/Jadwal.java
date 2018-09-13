package com.kampusverse.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.kampusverse.Logic.Common;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Jadwal implements Parcelable{
    private String Nama, Desc;
    private Calendar Reminder;
    private Common util = new Common();

    public Jadwal() {
    }

    public Jadwal(String nama, String desc, int hari) {
        Nama = nama;
        Desc = desc;
        Reminder = Calendar.getInstance();
        Reminder = util.calendarByDays(Reminder,hari + 1);
    }

    protected Jadwal(Parcel in) {
        Nama = in.readString();
        Desc = in.readString();
        Long millis = in.readLong();
        Reminder = Calendar.getInstance();
        Reminder.setTimeInMillis(millis);
    }

    public static final Creator<Jadwal> CREATOR = new Creator<Jadwal>() {
        @Override
        public Jadwal createFromParcel(Parcel in) {
            return new Jadwal(in);
        }

        @Override
        public Jadwal[] newArray(int size) {
            return new Jadwal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Nama);
        dest.writeString(Desc);
        dest.writeLong(Reminder.getTimeInMillis());
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Calendar getReminder() {
        return Reminder;
    }

    public void setReminder(Calendar reminder) {
        Reminder = reminder;
    }

    public String GetElapsedAsString() {
        Calendar Today = Calendar.getInstance();
        int day = util.timeSpanToNextMatkul(Today, Reminder);
        if(day < 0) {
            setReminder(util.nextWeekAfter(Today, Reminder));
            day = util.timeSpanToNextMatkul(Today, Reminder);
        }

        if(day > 1)
            return day+"days";
        else if(day == 1)
            return "less than a day";
        else if(Today.get(Calendar.DAY_OF_WEEK) == Reminder.get(Calendar.DAY_OF_WEEK))
            return "now";
        else
            return "less than a day";
    }
}
