package com.kampusverse.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.Tugas;
import com.kampusverse.Logic.ApiBase;
import com.kampusverse.R;
import com.kampusverse.UI.Dialog.AddDialog;
import com.kampusverse.UI.Dialog.AddTugas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdapterTugas extends RecyclerView.Adapter<AdapterTugas.MyViewHolder> {
    private List<Tugas> TugasBundle = new ArrayList<>();
    private Context context;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Nama, Desc, Reminder, Hari;
        public LinearLayout topWraper;
        public LinearLayout bottomWraper;

        public MyViewHolder(View v) {
            super(v);
            Nama = v.findViewById(R.id.frgtNamaTugas);
            Desc = v.findViewById(R.id.frgtDescTugas);
            Reminder = v.findViewById(R.id.frgtReminder);
            bottomWraper = v.findViewById(R.id.bottom_wrapper);
            topWraper = v.findViewById(R.id.top_wrapper);
            Hari = v.findViewById(R.id.frgtHariTugas);
        }
    }

    public AdapterTugas(List<Tugas> TugasBundle, Context context) {
        this.TugasBundle = TugasBundle;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterTugas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycletugas, viewGroup, false);
        return new AdapterTugas.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTugas.MyViewHolder vh, final int i) {
        String[] days = {"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        final Tugas data = TugasBundle.get(i);
        final int ifinal = vh.getAdapterPosition();
        vh.Nama.setText(data.getNama());
        vh.Desc.setText(data.getDesc());
        vh.Reminder.setText(data.GetElapsedAsString());
        vh.Hari.setText(data.GetReminderAsString());
        vh.topWraper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddTugas.class);
                intent.putExtra("simpan", i);
                context.startActivity(intent);
            }
        });
        vh.bottomWraper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiBase api = ApiBase.GetInstance();
                api.DeleteTugas(context, data.getUID(), new ApiBase.SimpleCallback() {
                    @Override
                    public void OnSuccess(String[] strings) {}

                    @Override
                    public void OnFailure(String message) {}
                });
                TugasBundle.remove(ifinal);
                notifyItemRemoved(ifinal);
                notifyItemRangeChanged(ifinal, getItemCount());
            }
        });
    }
    @Override
    public int getItemCount() {
        return TugasBundle.size();
    }
}
