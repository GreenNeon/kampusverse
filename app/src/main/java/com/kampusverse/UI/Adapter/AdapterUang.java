package com.kampusverse.UI.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kampusverse.Data.SharedData;
import com.kampusverse.Data.Uang;
import com.kampusverse.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterUang extends RecyclerView.Adapter<AdapterUang.MyViewHolder>{

    private List<Uang> UangBundle = new ArrayList<>();
    private SharedData sdata;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, perubahan;
        public LinearLayout topWraper;
        public LinearLayout bottomWraper;

        public MyViewHolder(View v) {
            super(v);
            nama = v.findViewById(R.id.frgtNamaTransaksi);
            perubahan = v.findViewById(R.id.frgtPerubahan);
            bottomWraper= v.findViewById(R.id.bottom_wrapper);
            topWraper = v.findViewById(R.id.top_wrapper);
        }
    }

    public AdapterUang(List<Uang> UangBundle) {
        this.UangBundle = UangBundle;
    }

    @NonNull
    @Override
    public AdapterUang.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycleuang, viewGroup, false);
        sdata = SharedData.GetInstance();
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUang.MyViewHolder vh, final int i) {
            Uang data = UangBundle.get(i);
            vh.nama.setText(data.getNama());
            vh.perubahan.setText(String.valueOf(data.getPerubahan()));
            vh.bottomWraper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UangBundle.remove(i-1);
                sdata.RemoveUang(i-1);
                notifyItemRemoved(i-1);
            }
        });
    }


    @Override
    public int getItemCount()  {
        return UangBundle.size();
    }
}
