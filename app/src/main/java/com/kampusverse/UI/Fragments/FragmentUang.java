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

import com.kampusverse.Data.SharedData;
import com.kampusverse.R;
import com.kampusverse.UI.Adapter.AdapterUang;

public class FragmentUang extends Fragment {
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
        View view = inflater.inflate(R.layout.frguang,container,false);

        rview = view.findViewById(R.id.transaksi_list);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        rview.setLayoutManager(layout);

        sdata = SharedData.GetInstance();
        adapter = new AdapterUang(sdata.GetKoleksiTransaksi());
        rview.setAdapter(adapter);
        
        return view;
    }
}
