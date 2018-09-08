package com.kampusverse.Data;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Jadwal {
    String Nama, Desc;
    Calendar Reminder;

    public Jadwal() {
    }

    public Jadwal(String nama, String desc, Calendar reminder) {
        Nama = nama;
        Desc = desc;
        Reminder = reminder;
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
        Long Remaining = Reminder.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        long day = TimeUnit.MILLISECONDS.toDays(Remaining),
                hour = TimeUnit.MILLISECONDS.toHours(Remaining) % 24,
                minute = TimeUnit.MILLISECONDS.toMinutes(Remaining) % 60,
                second = TimeUnit.MILLISECONDS.toSeconds(Remaining) % 3600 % 60;

        return day + "d" + hour + "h" + minute + "m" +second +"s";
    }
}
