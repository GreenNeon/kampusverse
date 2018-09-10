package com.kampusverse.UI.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.SharedData;
import com.kampusverse.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterJadwal extends RecyclerView.Adapter<AdapterJadwal.MyViewHolder>{

    private List<Jadwal> JadwalBundle = new ArrayList<>();
    private SharedData sdata;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Nama, Desc, Reminder;
        public LinearLayout topWraper;
        public LinearLayout bottomWraper;

        public MyViewHolder(View v) {
            super(v);
            Nama = v.findViewById(R.id.frgtNamaJadwal);
            Desc = v.findViewById(R.id.frgtDescJadwal);
            Reminder = v.findViewById(R.id.frgtReminder);
            bottomWraper= v.findViewById(R.id.bottom_wrapper);
            topWraper = v.findViewById(R.id.top_wrapper);
        }
    }

    public AdapterJadwal(List<Jadwal> JadwalBundle) {
        this.JadwalBundle = JadwalBundle;
    }

    @NonNull
    @Override
    public AdapterJadwal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclejadwal, viewGroup, false);
        sdata = SharedData.GetInstance();
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJadwal.MyViewHolder vh, final int i) {
        Jadwal data = JadwalBundle.get(i);
        vh.Nama.setText(data.getNama());
        vh.Desc.setText(data.getDesc());
        vh.Reminder.setText(data.GetElapsedAsString());
        vh.bottomWraper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JadwalBundle.remove(i-1);
                sdata.RemoveJadwal(i-1);
                notifyItemRemoved(i-1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return JadwalBundle.size();
    }
}
