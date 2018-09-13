package com.kampusverse.UI.Dialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.R;
import com.kampusverse.UI.Beranda;

import java.util.Calendar;

public class AddDialog extends AppCompatActivity {

    private SharedData sdata;
    private EditText eNama, eDesc;
    private Spinner spinner;
    private int simpan = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);

        eDesc = findViewById(R.id.addDesc);
        eNama = findViewById(R.id.addNama);
        spinner = findViewById(R.id.addDate);
        sdata = SharedData.GetInstance();

        simpan = getIntent().getIntExtra("simpan",-1);
        if(simpan > -1){
            Jadwal data = sdata.GetJadwal(simpan);
            eNama.setText(data.getNama());
            eDesc.setText(data.getDesc());
            spinner.setSelection(data.getReminder().get(Calendar.DAY_OF_WEEK) - 1);
        }
    }

    public void OnClickSimpan(View view) {
        Jadwal save = new Jadwal(eNama.getText().toString(), eDesc.getText().toString(), spinner.getSelectedItemPosition());
        Intent i = new Intent(AddDialog.this, Beranda.class);
        i.putExtra("addDialog", 1);
        if(simpan < 0) sdata.AddJadwal(save);
        else sdata.UpdateJadwal(save,simpan);
        startActivity(i);
    }
    public void OnClickBatal(View view) {
        Intent i = new Intent(AddDialog.this, Beranda.class);
        i.putExtra("addDialog", 1);
        startActivity(i);
    }
}
