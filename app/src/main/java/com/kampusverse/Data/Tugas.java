package com.kampusverse.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;
import com.kampusverse.Logic.Common;
import com.kampusverse.Logic.SharedData;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class Tugas implements Serializable {
    private String Nama, Desc, UID, JUID;
    private Calendar Reminder;
    private Common util = new Common();

    public Tugas() { }

    public Tugas(String nama, String desc, String UID, String JUID, Long reminder) {
        Nama = nama;
        Desc = desc;
        this.UID = UID;
        this.JUID = JUID;
        Reminder = Calendar.getInstance();
        Reminder.setTimeInMillis(reminder);
    }

    public Tugas(String nama, String desc, String JUID, Calendar reminder) {
        Nama = nama;
        Desc = desc;
        this.UID = UUID.randomUUID().toString();
        this.JUID = JUID;
        Reminder = reminder;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    public String getJUID() {
        return JUID;
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

    public String GetNamaJadwal(SharedData sdata) {
        for (Jadwal jadwal:sdata.GetKoleksiJadwal()){
            if(jadwal.getUID().equals(JUID)) return jadwal.getNama();
        }
        return "Unknown";
    }

    public String GetReminderAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(Reminder.getTime());
    }

    public String GetElapsedAsString() {
        Calendar Today = Calendar.getInstance();
        int day = util.timeSpanToNextMatkul(Today, Reminder);

        if(day > 1)
            return day+"days";
        else if(day == 1)
            return "less than a day";
        else if(Today.get(Calendar.DAY_OF_WEEK) == Reminder.get(Calendar.DAY_OF_WEEK))
            return "now";
        else
            return "Overdue";
    }

    public JsonObject toJSON(){
        JsonObject json = new JsonObject();

        json.addProperty("UID", getUID());
        json.addProperty("nama", getNama());
        json.addProperty("desc", getDesc());
        json.addProperty("JUID", getJUID());
        json.addProperty("reminder", getReminder().getTimeInMillis());
        return json;
    }
}
