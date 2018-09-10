package com.kampusverse.UI.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.kampusverse.Data.SharedData;
import com.kampusverse.Data.Uang;
import com.kampusverse.R;
import com.kampusverse.UI.Beranda;

public class AddTransaksi extends AppCompatActivity {

    private SharedData sdata;
    private EditText eNama, ePerubahan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaksi);

        eNama = findViewById(R.id.addNama);
        ePerubahan = findViewById(R.id.addPerubahan);
        sdata = SharedData.GetInstance();
    }

    public void OnClickSimpan(View view) {
        Uang save = new Uang(eNama.getText().toString(), Double.parseDouble(ePerubahan.getText().toString()));
        Intent i = new Intent(AddTransaksi.this, Beranda.class);
        sdata.AddUang(save);
        startActivity(i);
    }
    public void OnClickBatal(View view) {

    }
}
