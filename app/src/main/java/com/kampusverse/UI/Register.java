package com.kampusverse.UI;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.Common;
import com.kampusverse.R;

public class Register extends AppCompatActivity {

    private ApiBase control = ApiBase.GetInstance();
    private EditText Email, Password;
    private Common common;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = findViewById(R.id.eUsernameRegister);
        Password = findViewById(R.id.ePasswordRegister);
    }
    public void RegisterOnClick(){
        control.LoginEmail(Register.this, Email.getText().toString(), Password.getText().toString(),
                new ApiBase.ApiBaseCallback() {
                    @Override
                    public void OnComplete(Object o) {
                        Intent i = new Intent(Register.this,Login.class);
                        startActivity(i);
                    }

                    @Override
                    public void OnFail() {
                        new AlertDialog.Builder(Register.this)
                                .setTitle("Registrasi Gagal !!")
                                .setMessage("Gagal menghubungi server ..")
                                .show();

                    }
                });
    }
}
