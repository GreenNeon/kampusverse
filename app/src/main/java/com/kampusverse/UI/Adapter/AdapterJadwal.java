package com.kampusverse.UI.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterJadwal extends RecyclerView.Adapter<AdapterJadwal.MyViewHolder>{

    private List<Jadwal> JadwalBundle = new ArrayList<>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Nama, Desc, Reminder;
        // each data item is just a string in this case
        public MyViewHolder(View v) {
            super(v);
            Nama = v.findViewById(R.id.frgtNamaJadwal);
            Desc = v.findViewById(R.id.frgtDescJadwal);
            Reminder = v.findViewById(R.id.frgtReminder);
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
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJadwal.MyViewHolder vh, int i) {
        Jadwal data = JadwalBundle.get(i);
        vh.Nama.setText(data.getNama());
        vh.Desc.setText(data.getDesc());
        vh.Reminder.setText(data.GetElapsedAsString());
    }

    @Override
    public int getItemCount() {
        return JadwalBundle.size();
    }
}
