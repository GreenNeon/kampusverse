package com.kampusverse.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.Profile;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.R;
import com.kampusverse.UI.Beranda;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.List;

public class FragmentBeranda extends Fragment {
    private SharedData sdata;
    private TextView tJadwal, tTugas, tPlus, tMinus;
    private TextView spWaktu1, spNama1,
            spWaktu2, spNama2,
            spWaktu3, spNama3;
    private TextView tSisa, NamaUser;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sdata = SharedData.GetInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgberanda,container,false);

        tJadwal = view.findViewById(R.id.frgtJadwalBeranda);
        tTugas= view.findViewById(R.id.frgtTugasBeranda);
        tPlus = view.findViewById(R.id.frgtPlusBeranda);
        tMinus = view.findViewById(R.id.frgtMinusBeranda);

        spWaktu1 = view.findViewById(R.id.spWaktu1);
        spWaktu2 = view.findViewById(R.id.spWaktu2);
        spWaktu3 = view.findViewById(R.id.spWaktu3);
        spNama1 = view.findViewById(R.id.spNama1);
        spNama2 = view.findViewById(R.id.spNama2);
        spNama3 = view.findViewById(R.id.spNama3);

        tSisa = view.findViewById(R.id.tTersembunyiBeranda);
        NamaUser = view.findViewById(R.id.tNamaBeranda);

        tJadwal.setText(String.valueOf(sdata.GetSizeOf(SharedData.KOLEKSI_JADWAL)));
        tTugas.setText(String.valueOf(sdata.GetSizeOf(SharedData.KOLEKSI_TUGAS)));
        tPlus.setText(String.valueOf(sdata.GetSizeUangPlus()));
        tMinus.setText(String.valueOf(sdata.GetSizeUangMinus()));

        TextView[] arrwaktu = {spWaktu1,spWaktu2,spWaktu3},
        arrnama = {spNama1,spNama2,spNama3};
        tSisa.setText("Tidak ada Jadwal hari ini");
        SetTable(arrwaktu,arrnama);

        Profile user = sdata.GetUser();
        if(user == null) getActivity().finish();
        if(user.getNama().trim().equalsIgnoreCase("")) NamaUser.setText("No Name");
        else NamaUser.setText(user.getNama());

        return view;
    }
    public void SetTable(TextView[] ltime, TextView[] lname){
        for (int i = 0; i < 3; i++){
            ltime[i].setText("");
        }
        for (int i = 0; i < 3; i++){
            lname[i].setText("");
        }
        Jadwal[] arr = sdata.GetArrayToday();
        for (int i = 0; i < arr.length; i++){
            ltime[i].setText(arr[i].GetTime());
        }
        for (int i = 0; i < arr.length; i++){
            lname[i].setText(arr[i].getNama() + " | " + arr[i].getDesc());
        }
        String sisa = (arr.length-3)+"+ tersembunyi";
        if(arr.length > 3) tSisa.setText(sisa);
        if(arr.length > 0) tSisa.setText("");
    }
}
