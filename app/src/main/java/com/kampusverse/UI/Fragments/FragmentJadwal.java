package com.kampusverse.UI.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kampusverse.Logic.SharedData;
import com.kampusverse.R;
import com.kampusverse.UI.Adapter.AdapterJadwal;

import java.util.Calendar;

public class FragmentJadwal extends Fragment {
    private SharedData sdata;
    private RecyclerView rview;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;

    private TextView tglkit1, tglkit2,
            tglkim1, tglkim2,
            tglkat1, tglkat2,
            tglkam1, tglkam2,
            tglcet, tglcem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String[] days = {"Unknown","Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        String[] months = {"Unkown","Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt","Nov","Des"};
        View view = inflater.inflate(R.layout.frgjadwal, container, false);
        rview = view.findViewById(R.id.jadwal_list);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        rview.setLayoutManager(layout);

        sdata = SharedData.GetInstance();
        adapter = new AdapterJadwal(sdata.GetKoleksiJadwal(), getContext());
        rview.setAdapter(adapter);

        tglcet = view.findViewById(R.id.frgtjadwalcet);
        tglcem = view.findViewById(R.id.frgtjadwalcem);

        tglkit1 = view.findViewById(R.id.frgtjadwalkit1);
        tglkit2 = view.findViewById(R.id.frgtjadwalkit2);
        tglkim1 = view.findViewById(R.id.frgtjadwalkim1);
        tglkim2 = view.findViewById(R.id.frgtjadwalkim2);

        tglkat1 = view.findViewById(R.id.frgtjadwalkat1);
        tglkat2 = view.findViewById(R.id.frgtjadwalkat2);
        tglkam1 = view.findViewById(R.id.frgtjadwalkam1);
        tglkam2 = view.findViewById(R.id.frgtjadwalkam2);

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, -2);
        tglkit1.setText(String.valueOf(today.get(Calendar.DAY_OF_MONTH)));
        tglkim1.setText(months[today.get(Calendar.MONTH)]);

        today.add(Calendar.DATE, 1);
        tglkit2.setText(String.valueOf(today.get(Calendar.DAY_OF_MONTH)));
        tglkim2.setText(months[today.get(Calendar.MONTH)]);

        today.add(Calendar.DATE, 1);
        tglcet.setText(String.valueOf(today.get(Calendar.DAY_OF_MONTH)));
        tglcem.setText(months[today.get(Calendar.MONTH)]);

        today.add(Calendar.DATE, 1);
        tglkat1.setText(String.valueOf(today.get(Calendar.DAY_OF_MONTH)));
        tglkam1.setText(months[today.get(Calendar.MONTH)]);

        today.add(Calendar.DATE, 1);
        tglkat2.setText(String.valueOf(today.get(Calendar.DAY_OF_MONTH)));
        tglkam2.setText(months[today.get(Calendar.MONTH)]);

        return view;
    }
}
