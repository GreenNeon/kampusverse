package com.kampusverse.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.UUID;

public class Uang implements Serializable {
    String nama, UID;
    double perubahan;

    public Uang() {
    }

    public Uang(String nama, String UID, double perubahan) {
        this.nama = nama;
        this.UID = UID;
        this.perubahan = perubahan;
    }

    public Uang(String nama, double perubahan) {
        this.UID = UUID.randomUUID().toString();
        this.nama = nama;
        this.perubahan = perubahan;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUID() { return UID; }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public double getPerubahan() {
        return perubahan;
    }
    public void setPerubahan(double perubahan ) {
        this.perubahan = perubahan;
    }

    public JsonObject toJSON(){
        JsonObject json = new JsonObject();

        json.addProperty("uid", getUID());
        json.addProperty("nama", getNama());
        json.addProperty("perubahan", getPerubahan());
        return json;
    }
}
