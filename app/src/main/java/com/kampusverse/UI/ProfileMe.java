package com.kampusverse.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.LocalDB;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileMe extends AppCompatActivity {
    Button btnEdit;
    SharedData sharedData;
    TextView nama, jadwal, tugas, uang,email;
    CircleImageView foto;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedData = SharedData.GetInstance();
        btnEdit=findViewById(R.id.btnEdit);
        nama = findViewById(R.id.profilename);
        jadwal = findViewById(R.id.profilejadwal);
        tugas = findViewById(R.id.profiletugas);
        uang = findViewById(R.id.profileuang);
        email = findViewById(R.id.profileemail);

        dialog=new ProgressDialog(ProfileMe.this);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileMe.this,EditProfile.class);
                startActivity(intent);
            }
        });
        dialog.setMessage("Please Wait");
        dialog.show();
        ApiBase api = ApiBase.GetInstance();
        api.GetUserData(getApplicationContext(), sharedData.GetUser().getIDToken(), new ApiBase.SimpleCallback() {
            @Override
            public void OnSuccess(String[] strings) { UpdateData();}

            @Override
            public void OnFailure(String message) { UpdateData();}
        });
    }

    public void UpdateData(){
        nama.setText(sharedData.GetUser().getNama());
        jadwal.setText(String.valueOf(sharedData.GetSizeOf(SharedData.KOLEKSI_JADWAL)));
        tugas.setText(String.valueOf(sharedData.GetSizeOf(SharedData.KOLEKSI_TUGAS)));
        uang.setText(String.valueOf(sharedData.GetSizeOf(SharedData.KOLEKSI_UANG)));
        email.setText(sharedData.GetUser().getEmail());
        dialog.dismiss();
    }
}
