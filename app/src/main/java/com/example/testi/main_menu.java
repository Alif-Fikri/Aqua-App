package com.example.testi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class main_menu extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton btnOpenDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btnOpenDrawer = findViewById(R.id.btn_open_drawer);

        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buka navigation drawer saat tombol ditekan
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
    }
}
