package com.kampusverse.Data;

public class Profile {
    private String UID;
    private String Nama, Email;
    private String FotoURL;
    private String IDToken, RefreshToken;
    private Boolean Verified;

    public Profile() {
    }

    public Profile(String UID, String nama, String email, String IDToken, String refreshToken) {
        this.UID = UID;
        Nama = nama;
        Email = email;
        FotoURL = null;
        this.IDToken = IDToken;
        RefreshToken = refreshToken;
        Verified = false;
    }

    public Profile(String UID, String nama, String email, String fotoURL, String IDToken, String refreshToken, Boolean verified) {
        this.UID = UID;
        Nama = nama;
        Email = email;
        FotoURL = fotoURL;
        this.IDToken = IDToken;
        RefreshToken = refreshToken;
        Verified = verified;
    }

    public Boolean getVerified() {
        return Verified;
    }

    public void setVerified(Boolean verified) {
        Verified = verified;
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

    public String getFotoURL() {
        return FotoURL;
    }

    public void setFotoURL(String fotoURL) {
        FotoURL = fotoURL;
    }

}
