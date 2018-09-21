package com.kampusverse.UI;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kampusverse.Data.Profile;
import com.kampusverse.Logic.LocalDB;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.R;
import com.kampusverse.UI.Dialog.AddDialog;
import com.kampusverse.UI.Dialog.AddTransaksi;
import com.kampusverse.UI.Dialog.AddTugas;
import com.kampusverse.UI.Fragments.FragmentBeranda;
import com.kampusverse.UI.Fragments.FragmentJadwal;
import com.kampusverse.UI.Fragments.FragmentTugas;
import com.kampusverse.UI.Fragments.FragmentUang;
import com.yalantis.guillotine.animation.GuillotineAnimation;

public class Beranda extends AppCompatActivity {
    //View Variable
    Toolbar toolbar;
    FrameLayout root;
    BottomNavigationView bottombar;
    View contentHamburger;
    ImageView contentRight;
    TextView title;
    RelativeLayout.LayoutParams fragmentparams;
    View fragmentlayout;
    FloatingActionButton fab;
    //Global Variable
    SharedData sdata;
    static int view_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        IntializeView();
        // Shared Data
        sdata = SharedData.GetInstance();
        LocalDB db = LocalDB.GetInstance();

        sdata.AddArrayJadwal(db.ReadJadwal());
        sdata.AddArrayTugas(db.ReadTugas());
        sdata.AddArrayUang(db.ReadUang());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalDB db = LocalDB.GetInstance();

        db.SaveJadwal(sdata.GetKoleksiJadwal());
        db.SaveTugas(sdata.GetKoleksiTugas());
        db.SaveUang(sdata.GetKoleksiUang());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalDB db = LocalDB.GetInstance();

        db.SaveJadwal(sdata.GetKoleksiJadwal());
        db.SaveTugas(sdata.GetKoleksiTugas());
        db.SaveUang(sdata.GetKoleksiUang());
    }

    private void IntializeView() {
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.toolbar_title);
        bottombar = findViewById(R.id.NavigationBot);
        root = findViewById(R.id.root);
        contentHamburger = findViewById(R.id.content_hamburger);
        contentRight = findViewById(R.id.content_right);
        fragmentlayout = findViewById(R.id.scrollView2);
        fragmentparams = (RelativeLayout.LayoutParams) fragmentlayout.getLayoutParams();
        fab = findViewById(R.id.fab);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.hamburger, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        switch (getIntent().getIntExtra("addDialog",0)){
            case 0: bottombar.setSelectedItemId(R.id.navigation_beranda); switchfragment(R.id.navigation_beranda);break;
            case 1: bottombar.setSelectedItemId(R.id.navigation_jadwal); switchfragment(R.id.navigation_jadwal);break;
            case 2: bottombar.setSelectedItemId(R.id.navigation_tugas); switchfragment(R.id.navigation_tugas);break;
            case 3: bottombar.setSelectedItemId(R.id.navigation_uang); switchfragment(R.id.navigation_uang);break;
        }

        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_beranda:
                        if(view_position != 0) switchfragment(R.id.navigation_beranda);
                        return true;
                    case R.id.navigation_jadwal:
                        if(view_position != 1) switchfragment(R.id.navigation_jadwal);
                        return true;
                    case R.id.navigation_tugas:
                        if(view_position != 2) switchfragment(R.id.navigation_tugas);
                        return true;
                    case R.id.navigation_uang:
                        if(view_position != 3) switchfragment(R.id.navigation_uang);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    public void switchfragment(int id) {
        FragmentManager manager = getSupportFragmentManager();
        fragmentparams.removeRule(RelativeLayout.CENTER_IN_PARENT);
        switch (id) {
            case R.id.navigation_beranda:
                view_position = 0;
                manager.beginTransaction().replace(R.id.fragmentplace, new FragmentBeranda()).commit();
                title.setText("Kampus Verse");
                fragmentparams.addRule(RelativeLayout.CENTER_IN_PARENT);
                contentRight.setVisibility(View.INVISIBLE);
                fab.hide();
                break;
            case R.id.navigation_jadwal:
                view_position = 1;
                // Fragment
                FragmentJadwal frJadwal =  new FragmentJadwal();
                manager.beginTransaction().replace(R.id.fragmentplace,frJadwal).commit();
                fragmentparams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                // VIEW
                title.setText("Jadwal Kuliah");
                contentRight.setVisibility(View.VISIBLE);
                fab.show();
                break;
            case R.id.navigation_tugas:
                view_position = 2;
                manager.beginTransaction().replace(R.id.fragmentplace, new FragmentTugas()).commit();
                fragmentparams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                title.setText("Jadwal Tugas");
                contentRight.setVisibility(View.VISIBLE);
                fab.show();
                break;
            case R.id.navigation_uang:
                view_position = 3;
                manager.beginTransaction().replace(R.id.fragmentplace, new FragmentUang()).commit();
                fragmentparams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                title.setText("Keuangan Anda");
                contentRight.setVisibility(View.VISIBLE);
                fab.show();
                break;
        }
        fragmentlayout.setLayoutParams(fragmentparams);
    }

    public void FABonCLick(View view) {
        Intent i;
        switch (view_position) {
            case 1:
                i = new Intent(Beranda.this, AddDialog.class);
                i.putExtra("simpan", -1);
                startActivity(i);
                break;
            case 2:
                i = new Intent(Beranda.this, AddTugas.class);
                i.putExtra("simpan", -1);
                startActivity(i);
                break;
            case 3:
                i = new Intent(Beranda.this, AddTransaksi.class);
                i.putExtra("simpan", -1);
                startActivity(i);
                break;
        }
    }
}
