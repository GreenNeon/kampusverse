package com.kampusverse.Data;

import android.net.Uri;

public class Profile {
    private String UID;
    private String Nama, Email;
    private Uri FotoURL;
    private String IDToken, RefreshToken;

    public Profile() {}

    public Profile(String UID, String nama, String email, Uri fotoURL, String IDToken, String refreshToken) {
        this.UID = UID;
        Nama = nama;
        Email = email;
        FotoURL = fotoURL;
        this.IDToken = IDToken;
        RefreshToken = refreshToken;
    }

    public Profile(String UID, String nama, String email, String IDToken, String refreshToken) {
        this.UID = UID;
        Nama = nama;
        Email = email;
        this.IDToken = IDToken;
        RefreshToken = refreshToken;
    }

    public void UpdateToken(String UID, String IDToken, String refreshToken) {
        this.UID = UID;
        this.IDToken = IDToken;
        RefreshToken = refreshToken;
    }

    public String getIDToken() {
        return IDToken;
    }

    public void setIDToken(String IDToken) {
        this.IDToken = IDToken;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        RefreshToken = refreshToken;
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
