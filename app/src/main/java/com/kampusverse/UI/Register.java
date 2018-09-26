package com.kampusverse.UI;

import android.app.ProgressDialog;
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

import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.Common;
import com.kampusverse.R;
import com.kampusverse.UI.Dialog.AddTransaksi;

public class Register extends AppCompatActivity {

    private ApiBase control = ApiBase.GetInstance();
    private EditText Email, Password;
    private TextView tError;
    private String email,password;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = findViewById(R.id.eUsernameRegister);
        Password = findViewById(R.id.ePasswordRegister);
        tError = findViewById(R.id.tvErrorLogin);
        dialog=new ProgressDialog(Register.this);
    }
    public void RegisterOnClick(View view){
        validasiData();
    }

    public void validasiData(){
        email=Email.getText().toString();
        password=Password.getText().toString();

        if(email.matches("")||email.matches("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);

            View view = LayoutInflater.from(Register.this).inflate(R.layout.dialogbox, null);

            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);

            title.setText("Kesalahan Input");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(Register.this, "Mohon perbaiki inputan", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setView(view);
            builder.show();
        }
        else{
            dialog.setMessage("Please Wait");
            dialog.show();
            control.Register(Register.this, Email.getText().toString(), Password.getText().toString(), new ApiBase.SimpleCallback() {
                @Override
                public void OnSuccess(String[] strings) {
                    dialog.dismiss();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                }

                @Override
                public void OnFailure(String message) { dialog.dismiss();tError.setText(message); }
            });
        }
    }

    public void LoginOnClick(View view){
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }
}
