package com.kampusverse.Logic;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.Profile;
import com.kampusverse.Data.Tugas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedData {
    private static SharedData sdata = null;
    private List<Jadwal> KoleksiJadwal = new ArrayList<>();
    private List<Tugas> KoleksiTugas = new ArrayList<>();
    private Profile UserData = null;
    public final short KOLEKSI_JADWAL = 0;
    public final short KOLEKSI_TUGAS = 1;

    public static SharedData GetInstance() {
        if (sdata == null)
            sdata = new SharedData();
        return sdata;
    }
    private SharedData(){}

    public void InitializeKoleksi(){

    }

    public void AddJadwal(Jadwal Data){
        KoleksiJadwal.add(Data);
    }
    public void AddArrayJadwal(List<Jadwal> Data){
        KoleksiJadwal.addAll(Data);
    }
    public void RemoveJadwal(int i){
        KoleksiJadwal.remove(i);
    }
    public void UpdateJadwal(Jadwal Data, int i) { KoleksiJadwal.set(i, Data);}
    public Jadwal GetJadwal(int i) {return KoleksiJadwal.get(i);}

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
        KoleksiTugas.addAll(Data);
    }
    public void RemoveTugas(int i){
        KoleksiTugas.remove(i);
    }
    public void UpdateTugas(Tugas Data, int i) { KoleksiTugas.set(i, Data);}
    public Tugas GetTugas(int i) {return KoleksiTugas.get(i);}

    public List<Tugas> GetKoleksiTugas() {
        return KoleksiTugas;
    }

    public boolean IsKoleksiEmpty(short type){
        switch (type) {
            case KOLEKSI_JADWAL:
                if(KoleksiJadwal!= null || KoleksiJadwal.size() > 0)
                    return true;
                break;
            case KOLEKSI_TUGAS:
                if(KoleksiTugas!= null || KoleksiTugas.size() > 0)
                    return true;
                break;
        }
        return false;
    }

}
