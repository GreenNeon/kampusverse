package com.kampusverse.UI;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.Common;
import com.kampusverse.R;

public class Login extends AppCompatActivity {

    private ApiBase control = ApiBase.GetInstance();
    private EditText Email, Password;
    private Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.eUsernameRegister);
        Password = findViewById(R.id.ePasswordRegister);
    }

    public void LoginOnClick(View view) {
        control.LoginEmail(Login.this, Email.getText().toString(), Password.getText().toString(),
                new ApiBase.ApiBaseCallback() {
                    @Override
                    public void OnComplete(Object o) {
                        Intent i = new Intent(Login.this,Beranda.class);
                        startActivity(i);
                    }

                    @Override
                    public void OnFail() {
                        new AlertDialog.Builder(Login.this)
                                .setTitle("Login Gagal !!")
                                .setMessage("Gagal mengotentifikasi akun anda ..")
                                .show();

                    }
                });
    }
}
