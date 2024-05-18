package com.example.testi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class main_menu extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton btnOpenDrawer;
    private int notificationCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btnOpenDrawer = findViewById(R.id.btn_open_drawer);

        TextView textViewPoints = findViewById(R.id.textViewPoints);
        int userPoints = UserPointsManager.getUserPoints(this);
        textViewPoints.setText(String.valueOf(userPoints));
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
                decrementNotificationCount();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.logouts) {
                    logouts();
                    return true;
                } else if (id == R.id.notifikasis) {
                    notification(); // Call the method to handle notification creation
                    return true;
                }
                return false;
            }
        });
    }

    // Method to handle notification creation
    private void notification() {
        Intent intent = new Intent(getApplicationContext(), undiantraveloka.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "CH1")
                .setSmallIcon(R.drawable.group_358__2_)
                .setContentText("Ada yang baru nih!!")
                .setContentTitle("Hadiah Menarik")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CH1", "Hadiah Menarik", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
        incrementNotificationCount();
    }

    private void updateBadgeVisibility() {
        TextView badgeTextView = findViewById(R.id.badgeTextView);
        if (notificationCount > 0) {
            badgeTextView.setText(String.valueOf(notificationCount));
            badgeTextView.setVisibility(View.VISIBLE);
        } else {
            badgeTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void incrementNotificationCount() {
        notificationCount++;
        updateBadgeVisibility();
    }

    private void decrementNotificationCount() {
        if (notificationCount > 0) {
            notificationCount--;
            updateBadgeVisibility();
        }
    }

    public void scan(View view) {
        Intent scan = new Intent(main_menu.this, scan_menu.class);
        startActivity(scan);
    }

    private void logouts() {
        Intent intent = new Intent(main_menu.this, login_menu.class);
        startActivity(intent);
        finish();
    }

    public void redeem1(View view) {
        Intent redeem1 = new Intent(main_menu.this, redeemvaganza.class);
        startActivity(redeem1);
    }

    public void scanning(View view) {
        Intent scanning = new Intent(main_menu.this, scan_menu.class);
        startActivity(scanning);
    }

    public void ekstrabt(View view) {
        Intent ekstrabt = new Intent(main_menu.this, ekstravaganza.class);
        startActivity(ekstrabt);
    }

    public void royalbt(View view) {
        Intent royalbt = new Intent(main_menu.this, royalvaganza.class);
        startActivity(royalbt);
    }
}