package com.example.testi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testi.auth.login_menu;
import com.example.testi.auth.register_menu;


public class login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void loginp (View view) {
        Intent loginp= new Intent(login.this, login_menu.class);
        startActivity(loginp);
    }
    public void registerp (View view) {
        Intent registerp= new Intent(login.this, register_menu.class);
        startActivity(registerp);
    }
}