package com.kampusverse.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

public class Uang implements Serializable {
    String nama, UID;
    double perubahan;

    public Uang() {
    }
    public Uang(String nama, double perubahan) {
        this.UID = UUID.randomUUID().toString();
        this.nama = nama;
        this.perubahan = perubahan;
    }

    protected Uang(Parcel in) {
        nama = in.readString();
        perubahan = in.readDouble();
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
}
