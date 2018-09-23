package com.kampusverse.UI.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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
    public void OnClickBatal(View view) {
        Intent i = new Intent(AddTransaksi.this, Beranda.class);
        i.putExtra("addDialog", 3);
        startActivity(i);
    }
}
