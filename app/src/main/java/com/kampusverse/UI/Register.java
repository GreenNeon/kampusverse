package com.kampusverse.UI;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.Common;
import com.kampusverse.R;

public class Register extends AppCompatActivity {

    private ApiBase control = ApiBase.GetInstance();
    private EditText Email, Password;
    private TextView tError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = findViewById(R.id.eUsernameRegister);
        Password = findViewById(R.id.ePasswordRegister);
        tError = findViewById(R.id.tvErrorLogin);
    }
    public void RegisterOnClick(View view){
        control.Register(Register.this, Email.getText().toString(), Password.getText().toString(), new ApiBase.SimpleCallback() {
            @Override
            public void OnSuccess(String[] strings) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }

            @Override
            public void OnFailure(String message) { tError.setText(message); }
        });
    }
    public void LoginOnClick(View view){
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }
}
