package com.kampusverse.Data;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

public class Profile {
    private String UID;
    private String Nama, Email;
    private Uri FotoURL;

    public Profile() {}

    public Profile(FirebaseUser user) {
        UID = user.getUid();
        Nama = user.getDisplayName();
        FotoURL = user.getPhotoUrl();
        Email = user.getEmail();
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Uri getFotoURL() {
        return FotoURL;
    }

    public void setFotoURL(Uri fotoURL) {
        FotoURL = fotoURL;
    }
}
