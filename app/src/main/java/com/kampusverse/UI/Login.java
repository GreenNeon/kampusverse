package com.kampusverse.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kampusverse.Data.Profile;
import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.Common;
import com.kampusverse.Logic.LocalDB;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.R;
import com.kampusverse.UI.Dialog.AddTransaksi;

public class Login extends AppCompatActivity {

    private ApiBase control = ApiBase.GetInstance();
    private EditText Email, Password;
    private TextView tError;
    private LocalDB db;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.eUsernameRegister);
        Password = findViewById(R.id.ePasswordRegister);
        tError = findViewById(R.id.tvErrorLogin);

        db = LocalDB.GetInstance();
        db.InitializeDB(getApplicationContext());

        Profile current = db.GetCurrentUser();
        if(current != null){
            String[] strings = { "", "", "", "", "", "", ""};
            strings[0] = current.getUID();
            strings[1] = current.getNama();
            strings[2] = current.getEmail();
            strings[4] = current.getRefreshToken();

            if(strings[4].trim().equalsIgnoreCase("")){
                Email.setText(current.getEmail());
            } else {
                control.RefreshToken(Login.this, strings, new ApiBase.SimpleCallback() {
                    @Override
                    public void OnSuccess(String[] strings) { SavenKeep(); }

                    @Override
                    public void OnFailure(String message) { tError.setText(message); }
                });
            }
        }
    }

    private void SavenKeep(){
        SharedData sharedData = SharedData.GetInstance();
        db.SaveCurrentUser(sharedData.GetUser());

        sharedData.AddArrayJadwal(db.ReadJadwal());
        sharedData.AddArrayTugas(db.ReadTugas());
        sharedData.AddArrayUang(db.ReadUang());

        control.GetAll(Login.this, new ApiBase.SimpleCallback() {
            @Override
            public void OnSuccess(String[] strings) {
                Intent intent = new Intent(Login.this, Beranda.class);
                startActivity(intent);
            }

            @Override
            public void OnFailure(String message) {
                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, Beranda.class);
                startActivity(intent);
            }
        });
    }

    public void LoginOnClick(View view) {
        validasiData();
    }

    public void validasiData(){
        email=Email.getText().toString();
        password=Password.getText().toString();

        if(email.matches("")||email.matches("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

            View view = LayoutInflater.from(Login.this).inflate(R.layout.dialogbox, null);

            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);

            title.setText("Kesalahan Input");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(Login.this, "Mohon perbaiki inputan", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setView(view);
            builder.show();
        }
        else{
            control.Login(Login.this, Email.getText().toString(), Password.getText().toString(), new ApiBase.SimpleCallback() {
                @Override
                public void OnSuccess(String[] strings) { SavenKeep(); }
                @Override
                public void OnFailure(String message) { tError.setText(message);}
            });
        }
    }

    public void RegisterOnClick(View view){
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }
}
