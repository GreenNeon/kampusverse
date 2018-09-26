package com.kampusverse.Logic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.Profile;
import com.kampusverse.Data.Tugas;
import com.kampusverse.Data.Uang;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalDB {
    private static LocalDB localDB = null;

    private DB db;

    public static LocalDB GetInstance(){
        if(localDB != null) return localDB;
        localDB = new LocalDB();
        return localDB;
    }
    private LocalDB(){}

    public void InitializeDB(Context context){
        try {
            db = DBFactory.open(context);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void SaveCurrentUser(Profile current){
        try {
            db.put("CurrentUser", current);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public Profile GetCurrentUser(){
        Profile current = null;
        try {
            current = db.getObject("CurrentUser",Profile.class);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        if(current != null )
            return current;
        return null;
    }

    public void LogOut(){
        SharedData.GetInstance().GetUser().setRefreshToken("");
        Profile logout = GetCurrentUser();

        // UBAH INI
        SharedData.GetInstance().SetUserUang(0);
        SharedData.GetInstance().ReplaceUser(null);
        SharedData.GetInstance().GetKoleksiJadwal().clear();
        SharedData.GetInstance().GetKoleksiTugas().clear();
        SharedData.GetInstance().GetKoleksiUang().clear();

        logout.setRefreshToken("");
        SaveCurrentUser(logout);

        try {
            db.del("Jadwal");
            db.del("Tugas");
            db.del("Uang");
            db.del("TotalUang");
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public List<Jadwal> ReadJadwal(){
        Jadwal[] array = null;
        try {
            array = db.getArray("Jadwal",Jadwal.class);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        if(array != null )
            return Arrays.asList(array);
        return null;
    }

    public List<Tugas> ReadTugas(){
        Tugas[] array = null;
        try {
            array = db.getArray("Tugas",Tugas.class);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        if(array != null )
            return Arrays.asList(array);
        return null;
    }

    public List<Uang> ReadUang(){
        Uang[] array = null;
        try {
            array = db.getArray("Uang",Uang.class);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
        if(array != null )
            return Arrays.asList(array);
        return null;
    }

    public Double ReadTotalUang(){
        double total = 0.0;
        try {
            total = db.getDouble("TotalUang");
        } catch (SnappydbException e) {
            e.printStackTrace();
        }

        return total;
    }

    public void SaveJadwal(List<Jadwal> array){
        try {
            db.put("Jadwal", array.toArray());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void SaveTugas(List<Tugas> array){
        try {
            db.put("Tugas", array.toArray());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void SaveUang(List<Uang> array){
        try {
            db.put("Uang", array.toArray());
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void SaveTotalUang(double total){
        try {
            db.putDouble("TotalUang", total);
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }


    public void CloseDB(){
        try {
            db.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }
}
