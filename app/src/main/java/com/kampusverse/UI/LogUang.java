package com.kampusverse.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.Tugas;
import com.kampusverse.Data.Uang;
import com.kampusverse.Logic.ApiBase;
import com.kampusverse.R;
import com.kampusverse.UI.Adapter.AdapterUang;

import java.util.ArrayList;
import java.util.List;

public class LogUang extends AppCompatActivity {
    private RecyclerView rview;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    private ImageButton btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_uang);

        btnback = findViewById(R.id.btnBack);
        rview = findViewById(R.id.transaksi_log);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(getBaseContext());
        rview.setLayoutManager(layout);
        ApiBase api = ApiBase.GetInstance();
        api.GetLog(getBaseContext(), new ApiBase.ExtendedCallback() {
            @Override
            public void OnSuccess(List<Jadwal> listJadwal, List<Tugas> listTugas, List<Uang> listUang) {
                adapter = new AdapterUang(listUang, null, true);
                rview.setAdapter(adapter);
            }

            @Override
            public void OnFailure(String message) {
                adapter = new AdapterUang(new ArrayList<>(), null, true);
                rview.setAdapter(adapter);
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogUang.this, Beranda.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
