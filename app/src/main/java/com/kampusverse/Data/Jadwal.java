package com.kampusverse.Data;

import android.graphics.Camera;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kampusverse.Logic.Common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Jadwal implements Serializable{
    private String Nama, Desc, UID;
    private Calendar Reminder;
    private Common util = new Common();

    public Jadwal() {
    }

    public Jadwal(String nama, String desc, String UID, Long reminder) {
        Nama = nama;
        Desc = desc;
        this.UID = UID;
        Reminder = Calendar.getInstance();
        Reminder.setTimeInMillis(reminder);
    }

    public Jadwal(String nama, String desc, int hari) {
        UID = UUID.randomUUID().toString();
        Nama = nama;
        Desc = desc;
        Reminder = Calendar.getInstance();
        Reminder = util.calendarByDays(Reminder,hari + 1);
    }
    public Jadwal(String nama, String desc, int hari, int h, int m, int s) {
        UID = UUID.randomUUID().toString();
        Nama = nama;
        Desc = desc;
        Reminder = Calendar.getInstance();
        Reminder.set(
                Reminder.get(Calendar.YEAR),
                Reminder.get(Calendar.MONTH),
                Reminder.get(Calendar.DAY_OF_MONTH),
                h,m,s
        );
        Reminder = util.calendarByDays(Reminder,hari + 1);
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUID() { return UID; }
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

    public String GetTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(Reminder.getTime());
    }
    public boolean isToday(){
        Calendar Today = Calendar.getInstance();
        int day = util.timeSpanToNextMatkul(Today, Reminder);
        if(Today.get(Calendar.DAY_OF_WEEK) == Reminder.get(Calendar.DAY_OF_WEEK)) return true;
        return false;
    }
    public String GetElapsedAsString() {
        Calendar Today = Calendar.getInstance();
        Today.set(
                Today.get(Calendar.YEAR),
                Today.get(Calendar.MONTH),
                Today.get(Calendar.DAY_OF_MONTH),
                Reminder.get(Calendar.HOUR_OF_DAY),
                Reminder.get(Calendar.MINUTE)
                );
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

    public JsonObject toJSON(){
        JsonObject json = new JsonObject();

        json.addProperty("uid", getUID());
        json.addProperty("nama", getNama());
        json.addProperty("desc", getDesc());
        json.addProperty("reminder", getReminder().getTimeInMillis());
        return json;
    }
}
