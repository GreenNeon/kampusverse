package com.kampusverse.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kampusverse.Data.Jadwal;
import com.kampusverse.Logic.ApiBase;
import com.kampusverse.R;
import com.kampusverse.UI.Adapter.AdapterJadwal;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TestsJadwal extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ApiBase Apii = ApiBase.GetInstance();
    public DatabaseReference DataRoot = FirebaseDatabase.getInstance().getReference();
    public Calendar SavedDate = Calendar.getInstance();
    public Calendar LaterDate = Calendar.getInstance();

    private RecyclerView rview;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    private SwipeLayout swiper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_jadwal);

        swiper = findViewById(R.id.swiper);
        rview = findViewById(R.id.rctest);
        rview.setHasFixedSize(true);
        layout = new LinearLayoutManager(this);
        rview.setLayoutManager(layout);

        Calendar shit = Calendar.getInstance();
        Calendar another = Calendar.getInstance();
        shit.set(2018,Calendar.SEPTEMBER,8);
        List<Jadwal> bundle = new ArrayList<>();
        bundle.add(new Jadwal("Kalkulus","R3415",shit));
        another.set(2018,Calendar.SEPTEMBER,9);
        bundle.add(new Jadwal("Arsitektur Komputer","R3215",another));

        adapter = new AdapterJadwal(bundle);
        rview.setAdapter(adapter);

    }

    public void TestPicker(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog pick = DatePickerDialog.newInstance(TestsJadwal.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );

        // pick.show(getFragmentManager(), "Datepicker dialog");
        TimePickerDialog timepick = TimePickerDialog.newInstance(TestsJadwal.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true
        );
        timepick.show(getFragmentManager(), "TimePicker");
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        TextView tvtest = findViewById(R.id.textView5);
        tvtest.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = "You picked the following time: "+hourOfDay+"h"+minute+"m"+second;
        TextView tvtest = findViewById(R.id.textView5);
        tvtest.setText(time);
    }

}
