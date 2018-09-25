package com.kampusverse.UI.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;
import com.kampusverse.Data.Jadwal;
import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.LocalDB;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.R;
import com.kampusverse.UI.Beranda;
import com.kampusverse.UI.EditProfile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddDialog extends AppCompatActivity implements RadialTimePickerDialogFragment.OnTimeSetListener{

    private SharedData sdata;
    private EditText eNama, eDesc;
    private Spinner spinner;
    private TextView tJam;
    private Button btnJam;
    private int simpan = -1;
    private int h,m,s;
    private String nama,desc,jam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);

        eDesc = findViewById(R.id.addDesc);
        eNama = findViewById(R.id.addNama);
        spinner = findViewById(R.id.addJadwal);
        btnJam = findViewById(R.id.btnJam);
        tJam = findViewById(R.id.tvJam);
        sdata = SharedData.GetInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        tJam.setText(sdf.format(Calendar.getInstance().getTime()));
        h = Calendar.getInstance().get(Calendar.HOUR);
        m = Calendar.getInstance().get(Calendar.MINUTE);
        s = 0;

        simpan = getIntent().getIntExtra("simpan",-1);
        if(simpan > -1){
            Jadwal data = sdata.GetJadwal(simpan);
            eNama.setText(data.getNama());
            eDesc.setText(data.getDesc());
            spinner.setSelection(data.getReminder().get(Calendar.DAY_OF_WEEK) - 1);
            tJam.setText(sdf.format(data.getReminder().getTime()));
        }
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        h = hourOfDay; m = minute;
        String jam =  h + ":" + m;
        tJam.setText(jam);
    }

    public void OnClickSimpan(View view) {
            validatasiData();
    }

    public void validatasiData(){
        nama=eNama.getText().toString();
        desc=eDesc.getText().toString();
        jam=tJam.getText().toString();

        if(nama.matches("")||desc.matches("")||jam.matches("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(AddDialog.this);

            View view = LayoutInflater.from(AddDialog.this).inflate(R.layout.dialogbox, null);

            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);

            title.setText("Kesalahan Input");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(AddDialog.this, "Mohon perbaiki inputan", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setView(view);
            builder.show();
        }
        else{
            Jadwal save = new Jadwal(
                    eNama.getText().toString(),
                    eDesc.getText().toString(),
                    spinner.getSelectedItemPosition(),
                    h,m,s
            );
            Intent i = new Intent(AddDialog.this, Beranda.class);
            i.putExtra("addDialog", 1);
            if(simpan < 0) sdata.AddJadwal(save);
            else {
                save.setUID(sdata.GetJadwal(simpan).getUID());
                sdata.UpdateJadwal(save,simpan);
            }

            LocalDB db = LocalDB.GetInstance();

            db.SaveJadwal(sdata.GetKoleksiJadwal());
            startActivity(i);
        }
    }

    public void OnClickBatal(View view) {
        Intent i = new Intent(AddDialog.this, Beranda.class);
        i.putExtra("addDialog", 1);
        startActivity(i);
    }
    public void OnClickJam(View view) {
        RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(AddDialog.this)
                .setStartTime(h, m)
                .setDoneText("Done")
                .setCancelText("Cancel")
                .setThemeDark();
        rtpd.show(getSupportFragmentManager(), "FRAG_RADIAL_PICKER");
    }
}
