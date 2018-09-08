package com.kampusverse.UI;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.kampusverse.R;
import com.kampusverse.UI.Fragments.FragmentBeranda;
import com.kampusverse.UI.Fragments.FragmentJadwal;
import com.kampusverse.UI.Fragments.FragmentTugas;
import com.kampusverse.UI.Fragments.FragmentUang;
import com.yalantis.guillotine.animation.GuillotineAnimation;

public class Beranda extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;


    Toolbar toolbar;
    FrameLayout root;
    BottomNavigationView bottombar;
    View contentHamburger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        toolbar = findViewById(R.id.toolbar);
        bottombar = findViewById(R.id.NavigationBot);
        root = findViewById(R.id.root);
        contentHamburger = findViewById(R.id.content_hamburger);

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


        switchfragment(R.id.navigation_beranda);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_beranda:
                        switchfragment(R.id.navigation_beranda);
                        return true;
                    case R.id.navigation_jadwal:
                        switchfragment(R.id.navigation_jadwal);
                        return true;
                    case R.id.navigation_tugas:
                        switchfragment(R.id.navigation_tugas);
                        return true;
                    case R.id.navigation_uang:
                        switchfragment(R.id.navigation_uang);
                        return true;
                }
                return false;
            }
        });
    }

    public void switchfragment(int id) {
        FragmentManager manager = getSupportFragmentManager();
        switch (id){
            case R.id.navigation_beranda:
                manager.beginTransaction().replace(R.id.fragmentplace, new FragmentBeranda()).commit();
                break;
            case R.id.navigation_jadwal:
                manager.beginTransaction().replace(R.id.fragmentplace, new FragmentJadwal()).commit();
                break;
            case R.id.navigation_tugas:
                manager.beginTransaction().replace(R.id.fragmentplace, new FragmentTugas()).commit();
                break;
            case R.id.navigation_uang:
                manager.beginTransaction().replace(R.id.fragmentplace, new FragmentUang()).commit();
                break;
        }
    }

}
