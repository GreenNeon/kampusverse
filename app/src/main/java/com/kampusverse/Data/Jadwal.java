package com.kampusverse.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Jadwal implements Parcelable{
    String Nama, Desc;
    Calendar Reminder;

    public Jadwal() {
    }

    public Jadwal(String nama, String desc, int hari) {
        Nama = nama;
        Desc = desc;
        Reminder = Calendar.getInstance();
        Reminder.setTimeInMillis(UpdateReminder(hari));
    }

    private Long UpdateReminder(int hari) {
        Calendar thisday = Calendar.getInstance();
        //contoh hari = 1 sunday, reminder 7 saturday
        int date = 0;
        if(thisday.get(Calendar.DAY_OF_WEEK) > hari)
            date = thisday.get(Calendar.DAY_OF_WEEK) - hari;
        else if(thisday.get(Calendar.DAY_OF_WEEK) == hari)
            date = 7;
        else
            date = hari - thisday.get(Calendar.DAY_OF_WEEK);
        thisday.add(Calendar.DATE, date);
        return thisday.getTimeInMillis();

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
        Long Remaining = Calendar.getInstance().getTimeInMillis() - Reminder.getTimeInMillis();
        long day = TimeUnit.MILLISECONDS.toDays(Remaining),
                hour = TimeUnit.MILLISECONDS.toHours(Remaining) % 24,
                minute = TimeUnit.MILLISECONDS.toMinutes(Remaining) % 60,
                second = TimeUnit.MILLISECONDS.toSeconds(Remaining) % 3600 % 60;

        if(Remaining > 1)
            return (Calendar.SATURDAY-day)+"days";
        else
            return (Calendar.SATURDAY-day)+"day";
    }
}
