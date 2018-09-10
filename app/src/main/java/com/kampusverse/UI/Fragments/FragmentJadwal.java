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

import com.daimajia.swipe.SwipeLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.SharedData;
import com.kampusverse.Logic.ApiBase;
import com.kampusverse.R;
import com.kampusverse.UI.Adapter.AdapterJadwal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentJadwal extends Fragment{
    private SharedData sdata;
    private RecyclerView rview;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frgjadwal,container,false);
        rview = view.findViewById(R.id.jadwal_list);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        rview.setLayoutManager(layout);

        sdata = SharedData.GetInstance();
        adapter = new AdapterJadwal(sdata.GetKoleksiJadwal());
        rview.setAdapter(adapter);

        return view;
    }
}
