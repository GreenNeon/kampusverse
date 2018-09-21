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
import com.kampusverse.UI.Adapter.AdapterUang;

public class FragmentUang extends Fragment {
    private SharedData sdata;
    private RecyclerView rview;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;

    private TextView tBalance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frguang,container,false);

        sdata = SharedData.GetInstance();

        rview = view.findViewById(R.id.transaksi_list);
        tBalance = view.findViewById(R.id.frgtBalanceUang);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getContext());
        rview.setLayoutManager(layout);

        adapter = new AdapterUang(sdata.GetKoleksiUang(), FragmentUang.this);
        rview.setAdapter(adapter);

        SetBalance();

        
        return view;
    }
    public void SetBalance(){tBalance.setText(String.valueOf(sdata.GetUserUang())); }
}
