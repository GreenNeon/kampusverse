package com.kampusverse.Logic;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.Profile;
import com.kampusverse.Data.Tugas;
import com.kampusverse.Data.Uang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedData {
    private static SharedData sdata = null;
    private List<Jadwal> KoleksiJadwal = new ArrayList<>();
    private List<Tugas> KoleksiTugas = new ArrayList<>();
    private List<Uang> KoleksiUang = new ArrayList<>();

    private Profile UserData = null;
    private double UserUang = 0;
    public static final short KOLEKSI_JADWAL = 0;
    public static final short KOLEKSI_TUGAS = 1;
    public static final short KOLEKSI_UANG = 2;

    public static SharedData GetInstance() {
        if (sdata == null)
            sdata = new SharedData();
        return sdata;
    }
    private SharedData(){}

    public void ReplaceUser(Profile user){
        UserData = user;
    }
    public Profile GetUser() {
        return UserData;
    }

    public double GetUserUang() {
        return UserUang;
    }

    public void AddJadwal(Jadwal Data){
        KoleksiJadwal.add(Data);
    }
    public void AddArrayJadwal(List<Jadwal> Data){
        if(Data != null) {
            KoleksiJadwal.clear();
            KoleksiJadwal.addAll(Data);
        }
    }
    public void RemoveJadwal(int i){
        KoleksiJadwal.remove(i);
    }
    public void UpdateJadwal(Jadwal Data, int i) { KoleksiJadwal.set(i, Data);}
    public Jadwal GetJadwal(int i) {return KoleksiJadwal.get(i);}
    public Jadwal[] GetArrayToday(){
        List<Jadwal> lj = new ArrayList<>();
        for (Jadwal j :
                KoleksiJadwal) {
            if (j.isToday()) lj.add(j);
        }
        return Arrays.copyOf(lj.toArray(),lj.size(),Jadwal[].class);
    }

    public List<Jadwal> GetKoleksiJadwal() {
        return KoleksiJadwal;
    }
    public Map<String,String> GetMapJadwal() {
        Map<String,String> hm = new HashMap<>();
        for (Jadwal jadwal : KoleksiJadwal) {
            hm.put(jadwal.getUID(),jadwal.getNama());
        }
        return hm;
    }

    public void AddTugas(Tugas Data){
        KoleksiTugas.add(Data);
    }
    public void AddArrayTugas(List<Tugas> Data){
        if(Data != null){
            KoleksiTugas.clear();
            KoleksiTugas.addAll(Data);
        }
    }
    public void RemoveTugas(int i){
        KoleksiTugas.remove(i);
    }
    public void UpdateTugas(Tugas Data, int i) { KoleksiTugas.set(i, Data);}
    public Tugas GetTugas(int i) {return KoleksiTugas.get(i);}

    public List<Tugas> GetKoleksiTugas() {
        return KoleksiTugas;
    }

    public void AddUang(Uang Data){
        KoleksiUang.add(Data);
        UserUang += Data.getPerubahan();
    }
    public void AddArrayUang(List<Uang> Data){
        if(Data != null){
            KoleksiUang.clear();
            KoleksiUang.addAll(Data);
        }
    }
    public void RemoveUang(int i){
        UserUang -= KoleksiUang.get(i).getPerubahan();
        KoleksiUang.remove(i);
    }
    public void UpdateUang(Uang Data, int i) {
        UserUang -= KoleksiUang.get(i).getPerubahan();
        UserUang += Data.getPerubahan();
        KoleksiUang.set(i, Data);
    }
    public Uang GetUang(int i) {return KoleksiUang.get(i);}
    public int GetSizeUangMinus() {
        int total = 0;
        for (Uang u:
                KoleksiUang) {
            if(u.getPerubahan() < 0)
                total++;
        }
        return total;
    }
    public int GetSizeUangPlus() {
        int total = 0;
        for (Uang u:
             KoleksiUang) {
            if(u.getPerubahan() > 0)
                total++;
        }
        return total;
    }

    public List<Uang> GetKoleksiUang() {
        return KoleksiUang;
    }

    public int GetSizeOf(short type){
        switch (type) {
            case KOLEKSI_JADWAL:
                return KoleksiJadwal.size();

            case KOLEKSI_TUGAS:
                return KoleksiTugas.size();

            case KOLEKSI_UANG:
                return KoleksiUang.size();

        }
        return 0;
    }
}
