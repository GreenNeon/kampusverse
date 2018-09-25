package com.kampusverse.UI.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.Tugas;
import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.LocalDB;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.R;
import com.kampusverse.UI.Beranda;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

public class AddTugas extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener {

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private SharedData sdata;
    private EditText eNama, eDesc;
    private TextView tTanggal;
    private Spinner spinner;
    private int simpan = -1;
    private Button btndate;
    private Calendar selecteddate;
    private String desc,nama,tanggal;
    private Map<String, String> spinnerdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tugas);

        eDesc = findViewById(R.id.addDesc);
        eNama = findViewById(R.id.addNama);
        spinner = findViewById(R.id.addJadwal);
        btndate =findViewById(R.id.addTanggal);
        tTanggal = findViewById(R.id.tTanggal);

        sdata = SharedData.GetInstance();
        selecteddate = Calendar.getInstance();

        spinnerdata = sdata.GetMapJadwal();
        String[] stringArray = Arrays.copyOf(spinnerdata.values().toArray(), spinnerdata.values().toArray().length, String[].class);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,(stringArray));
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);

        simpan = getIntent().getIntExtra("simpan",-1);
        if(simpan > -1){
            Tugas data = sdata.GetTugas(simpan);
            eNama.setText(data.getNama());
            eDesc.setText(data.getDesc());
            for(int i = 0; i < spinnerdata.keySet().size(); i++){
                if(data.getJUID().equals(spinnerdata.keySet().toArray()[i])){
                    spinner.setSelection(i);
                    break;
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("D/M/YYYY");
            tTanggal.setText(sdf.format(Calendar.getInstance().getTime()));
            tTanggal.setText(sdf.format(data.getReminder().getTime()));

        }
    }

    public void OnClickSimpan(View view) {
        validasiData();
    }

    public void validasiData(){
        nama=eNama.getText().toString();
        desc=eDesc.getText().toString();
        tanggal=tTanggal.getText().toString();

        if(nama.matches("")||desc.matches("")||tanggal.matches("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(AddTugas.this);

            View view = LayoutInflater.from(AddTugas.this).inflate(R.layout.dialogbox, null);

            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);

            title.setText("Kesalahan Input");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(AddTugas.this, "Mohon perbaiki inputan", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setView(view);
            builder.show();
        }
        else{
            String spintext = "Unknown";
            spintext = (String) spinnerdata.keySet().toArray()[spinner.getSelectedItemPosition()];
            Tugas save = new Tugas(eNama.getText().toString(), eDesc.getText().toString(), spintext, selecteddate);
            Intent i = new Intent(AddTugas.this, Beranda.class);
            i.putExtra("addDialog", 2);
            if(simpan < 0) sdata.AddTugas(save);
            else {
                save.setUID(sdata.GetTugas(simpan).getUID());
                sdata.UpdateTugas(save,simpan);
            }
            LocalDB db = LocalDB.GetInstance();

            db.SaveTugas(sdata.GetKoleksiTugas());
            startActivity(i);
        }
    }

    public void OnClickBatal(View view) {
        Intent i = new Intent(AddTugas.this, Beranda.class);
        i.putExtra("addDialog", 2);
        startActivity(i);
    }
    public void OnClickTanggal(View view) {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(AddTugas.this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(selecteddate.get(Calendar.YEAR), selecteddate.get(Calendar.MONTH), selecteddate.get(Calendar.DAY_OF_MONTH))
                .setDoneText("Yay")
                .setCancelText("Nop")
                .setThemeDark();
        cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        tTanggal.setText(dayOfMonth + "/ " + monthOfYear +"/ "+year);
        selecteddate.set(year,monthOfYear,dayOfMonth);
    }
}
