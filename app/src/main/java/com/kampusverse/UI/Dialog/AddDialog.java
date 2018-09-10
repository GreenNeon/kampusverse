package com.kampusverse.UI.Dialog;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.SharedData;
import com.kampusverse.R;
import com.kampusverse.UI.Beranda;
import com.kampusverse.UI.Fragments.FragmentJadwal;

public class AddDialog extends AppCompatActivity {

    private SharedData sdata;
    private EditText eNama, eDesc;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);

        eDesc = findViewById(R.id.addDesc);
        eNama = findViewById(R.id.addNama);
        spinner = findViewById(R.id.addDate);
        sdata = SharedData.GetInstance();
    }

    public void OnClickSimpan(View view) {
        Jadwal save = new Jadwal(eNama.getText().toString(), eDesc.getText().toString(), spinner.getSelectedItemPosition()+1);
        Intent i = new Intent(AddDialog.this, Beranda.class);
        sdata.AddJadwal(save);
        startActivity(i);
    }
    public void OnClickBatal(View view) {

    }
}
