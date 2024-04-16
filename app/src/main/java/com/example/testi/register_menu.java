package com.example.testi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class register_menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);
    }

    public void backlog(View view) {
        Intent backlog = new Intent(register_menu.this, login.class);
        startActivity(backlog);
    }
    public void tombola(View view) {
        Intent tombola = new Intent(register_menu.this, main_menu.class);
        startActivity(tombola);
    }
}