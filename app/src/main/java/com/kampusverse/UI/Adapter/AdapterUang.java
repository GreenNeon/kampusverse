package com.kampusverse.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.Data.Uang;
import com.kampusverse.R;
import com.kampusverse.UI.Dialog.AddDialog;
import com.kampusverse.UI.Dialog.AddTransaksi;
import com.kampusverse.UI.Fragments.FragmentUang;

import java.util.ArrayList;
import java.util.List;

public class AdapterUang extends RecyclerView.Adapter<AdapterUang.MyViewHolder> {

    private List<Uang> UangBundle = new ArrayList<>();
    private SharedData sdata;
    private FragmentUang activity;
    private boolean log;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, perubahan, symbol;
        public LinearLayout topWraper;
        public LinearLayout bottomWraper;

        public MyViewHolder(View v) {
            super(v);
            nama = v.findViewById(R.id.frgtNamaTransaksi);
            perubahan = v.findViewById(R.id.frgtPerubahan);
            bottomWraper = v.findViewById(R.id.bottom_wrapper);
            topWraper = v.findViewById(R.id.top_wrapper);
            symbol = v.findViewById(R.id.frgtSymbolUang);
        }
    }

    public AdapterUang(List<Uang> UangBundle, FragmentUang activity, boolean log) {
        this.UangBundle = UangBundle;
        this.activity = activity;
        this.log = log;
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
        final Uang data = UangBundle.get(i);

        final int ifinal = vh.getAdapterPosition();
        vh.nama.setText(data.getNama());

        if(data.getPerubahan() > 0){
            vh.symbol.setText("+");
            vh.perubahan.setTextColor(Color.parseColor("#ff00ddff"));
            vh.symbol.setTextColor(Color.parseColor("#ff00ddff"));
        }
        else if (data.getPerubahan() < 0){
            vh.symbol.setText("-");
            vh.perubahan.setTextColor(Color.parseColor("#ffc92d2d"));
            vh.symbol.setTextColor(Color.parseColor("#ffc92d2d"));
        }

        vh.perubahan.setText(String.valueOf(data.getPerubahan()));
        vh.topWraper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(log) return;
                ApiBase api = ApiBase.GetInstance();
                api.DeleteUang(activity.getContext(), data.getUID(), new ApiBase.SimpleCallback() {
                    @Override
                    public void OnSuccess(String[] strings) {}

                    @Override
                    public void OnFailure(String message) {}
                });
                Intent intent = new Intent(activity.getContext(), AddTransaksi.class);
                intent.putExtra("simpan", i);
                activity.startActivity(intent);
            }
        });
        vh.bottomWraper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(log) return;
                sdata.RemoveUang(ifinal);
                notifyItemRemoved(ifinal);
                notifyItemRangeChanged(ifinal, getItemCount());
            }
        });
    }


    @Override
    public int getItemCount() {
        return UangBundle.size();
    }
}
