package com.kampusverse.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Uang implements Parcelable {
    String nama;
    double perubahan;

    public Uang() {
    }
    public Uang(String nama, double perubahan) {
        this.nama = nama;
        this.perubahan = perubahan;
    }

    protected Uang(Parcel in) {
        nama = in.readString();
        perubahan = in.readDouble();
    }

    public static final Creator<Uang> CREATOR = new Creator<Uang>() {
        @Override
        public Uang createFromParcel(Parcel in) {
            return new Uang(in);
        }

        @Override
        public Uang[] newArray(int size) {
            return new Uang[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeDouble(perubahan);
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getPerubahan() {
        return perubahan;
    }

    public void setPerubahan(double perubahan) {
        this.perubahan = perubahan;
    }
}
