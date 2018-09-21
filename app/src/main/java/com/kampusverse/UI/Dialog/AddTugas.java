package com.kampusverse.UI.Dialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.Tugas;
import com.kampusverse.Logic.LocalDB;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.R;
import com.kampusverse.UI.Beranda;

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
        }
    }

    public void OnClickSimpan(View view) {
        String spintext = "Unknown";
        spintext = (String) spinnerdata.keySet().toArray()[spinner.getSelectedItemPosition()];
        Tugas save = new Tugas(eNama.getText().toString(), eDesc.getText().toString(), spintext, selecteddate);
        Intent i = new Intent(AddTugas.this, Beranda.class);
        i.putExtra("addDialog", 2);
        if(simpan < 0) sdata.AddTugas(save);
        else sdata.UpdateTugas(save,simpan);
        LocalDB db = LocalDB.GetInstance();

        db.SaveTugas(sdata.GetKoleksiTugas());
        startActivity(i);
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
