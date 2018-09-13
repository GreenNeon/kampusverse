package com.kampusverse.UI.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdapterTugas extends RecyclerView.Adapter<AdapterTugas.MyViewHolder> {
    private List<Jadwal> JadwalBundle = new ArrayList<>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Nama, Desc, Reminder, Hari;
        public LinearLayout topWraper;
        public LinearLayout bottomWraper;

        public MyViewHolder(View v) {
            super(v);
            Nama = v.findViewById(R.id.frgtNamaJadwal);
            Desc = v.findViewById(R.id.frgtDescJadwal);
            Reminder = v.findViewById(R.id.frgtReminder);
            bottomWraper = v.findViewById(R.id.bottom_wrapper);
            topWraper = v.findViewById(R.id.top_wrapper);
            Hari = v.findViewById(R.id.frgtHariJadwal);
        }
    }

    public AdapterTugas(List<Jadwal> JadwalBundle) {
        this.JadwalBundle = JadwalBundle;
    }

    @NonNull
    @Override
    public AdapterTugas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclejadwal, viewGroup, false);
        return new AdapterTugas.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTugas.MyViewHolder vh, final int i) {
        String[] days = {"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        Jadwal data = JadwalBundle.get(i);
        final int ifinal = vh.getAdapterPosition();
        vh.Nama.setText(data.getNama());
        vh.Desc.setText(data.getDesc());
        vh.Reminder.setText(data.GetElapsedAsString());
        vh.Hari.setText(days[data.getReminder().get(Calendar.DAY_OF_WEEK)]);
        vh.bottomWraper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JadwalBundle.remove(ifinal);
                notifyItemRemoved(ifinal);
                notifyItemRangeChanged(ifinal, getItemCount());
            }
        });
    }
    @Override
    public int getItemCount() {
        return JadwalBundle.size();
    }
}
