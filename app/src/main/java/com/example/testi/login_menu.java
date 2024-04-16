package com.example.testi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class login_menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);
    }

    public void backlog(View view) {
        Intent backlog = new Intent(login_menu.this, login.class);
        startActivity(backlog);
    }
    public void fastballs(View view) {
        Intent fastballs = new Intent(login_menu.this, main_menu.class);
        startActivity(fastballs);
    }
}