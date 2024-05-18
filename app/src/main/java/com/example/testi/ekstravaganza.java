package com.example.testi;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ekstravaganza extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekstravaganza);

        ImageView spinImageView = findViewById(R.id.spinImageView);

        spinImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int randomSector = random.nextInt(10);
                float randomAngle = randomSector * 36f + 18f;  // 18 derajat agar lebih spesifik

                // set posisi
                float currentRotation = spinImageView.getRotation() % 360;
                float totalRotation = 360f * 5 + randomAngle - currentRotation;

                ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(spinImageView, "rotation", currentRotation, currentRotation + totalRotation);
                rotateAnimator.setDuration(3000); // Durasi animasi dalam milidetik
                rotateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                rotateAnimator.start();
            }
        });
    }

    public void scsekstra(View view) {
        Intent scsekstra = new Intent(ekstravaganza.this, undiantraveloka.class);
        startActivity(scsekstra);
        navigateToUndianPage();
    }

    private void navigateToUndianPage() {
        Intent intent = new Intent(this, undiantraveloka.class);
        startActivity(intent);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kembali ke halaman utama
                navigateToKtpPage();
            }
        }, 3000); // Waktu penundaan dalam milidetik (3000 ms = 3 detik)
    }

    private void navigateToKtpPage() {
        // Pindah kembali ke halaman utama
        Intent intent = new Intent(this, scanktp.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Menghapus semua aktivitas lain di atas halaman utama
        startActivity(intent);
        finish();
    }

    public void backmain(View view) {
        Intent backmain = new Intent(ekstravaganza.this, main_menu.class);
        startActivity(backmain);
    }
}