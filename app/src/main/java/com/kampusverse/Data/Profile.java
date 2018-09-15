package com.kampusverse.Data;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile {
    private String UID;
    private String Nama, Email;
    private Uri FotoURL;

    public Profile() {}

    public Profile(JSONObject user) {
        try {
            UID = user.getString("uid");
            Nama = user.getString("nama");
            FotoURL = Uri.parse(user.getString("fotourl"));
            Email = user.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
