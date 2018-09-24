package com.kampusverse.UI.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.LocalDB;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.Data.Uang;
import com.kampusverse.R;
import com.kampusverse.UI.Beranda;

import java.util.Calendar;

public class AddTransaksi extends AppCompatActivity {

    private SharedData sdata;
    private EditText eNama, ePerubahan;
    private int simpan = -1;
    private String nama,perubahan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaksi);

        eNama = findViewById(R.id.addNama);
        ePerubahan = findViewById(R.id.addPerubahan);
        sdata = SharedData.GetInstance();

        simpan = getIntent().getIntExtra("simpan",-1);
        if(simpan > -1){
            Uang data = sdata.GetUang(simpan);
            eNama.setText(data.getNama());
            ePerubahan.setText(String.valueOf(data.getPerubahan()));
        }
    }

    public void OnClickSimpan(View view) {
        validasiData();
    }

    public void validasiData(){
        nama=eNama.getText().toString();
        perubahan=ePerubahan.getText().toString();

        if(nama.matches("")||perubahan.matches("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(AddTransaksi.this);

            View view = LayoutInflater.from(AddTransaksi.this).inflate(R.layout.dialogbox, null);

            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);

            title.setText("Kesalahan Input");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(AddTransaksi.this, "Mohon perbaiki inputan", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setView(view);
            builder.show();
        }
        else
        {
            Uang save;
            try {
                save = new Uang(eNama.getText().toString(), Double.parseDouble(ePerubahan.getText().toString()));
            } catch (NumberFormatException ne) {
                save = new Uang(eNama.getText().toString(), 0);
            }
            Intent i = new Intent(AddTransaksi.this, Beranda.class);
            i.putExtra("addDialog", 3);
            if(simpan < 0) sdata.AddUang(save);
            else {
                save.setUID(sdata.GetUang(simpan).getUID());
                sdata.UpdateUang(save,simpan);
            }
            LocalDB db = LocalDB.GetInstance();

            db.SaveUang(sdata.GetKoleksiUang());
            startActivity(i);
        }
    }

    public void OnClickBatal(View view) {
        Intent i = new Intent(AddTransaksi.this, Beranda.class);
        i.putExtra("addDialog", 3);
        startActivity(i);
    }
}
