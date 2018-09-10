package com.kampusverse.Data;

import java.util.ArrayList;
import java.util.List;

public class SharedData {
    private static SharedData sdata = null;
    private List<Jadwal> KoleksiJadwal = new ArrayList<>();
    private Profile UserData = null;
    public final short KOLEKSI_JADWAL = 0;

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

    public List<Jadwal> GetKoleksiJadwal() {
        return KoleksiJadwal;
    }

    public boolean IsKoleksiEmpty(short type){
        switch (type) {
            case KOLEKSI_JADWAL:
                if(KoleksiJadwal!= null || KoleksiJadwal.size() > 0)
                    return true;
        }
        return false;
    }

}
