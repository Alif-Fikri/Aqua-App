package com.example.testi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ekstravaganza extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekstravaganza);

        Button redeemButton = findViewById(R.id.redeemeks);
        ImageView spinImageView = findViewById(R.id.spinImageView);
        TextView resultTextView = findViewById(R.id.resultTextView); // TextView untuk menampilkan hasil

        // Distribusi angka pada wheel
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Sudut tengah untuk setiap sektor
        float[] sectorAngles = {342f, 306f, 270f, 234f, 198f, 162f, 126f, 90f, 54f, 18f};

        spinImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int randomSector = random.nextInt(10); // pilih sektor acak (0 sampai 9)
                float randomAngle = sectorAngles[randomSector];

                // Dapatkan angka berdasarkan sektor acak
                int selectedNumber = numbers[randomSector];

                // Hitung persentase dari angka
                int percentage = selectedNumber * 10; // karena setiap angka adalah 10% dari 1 hingga 10

                // Set posisi
                float currentRotation = spinImageView.getRotation() % 360;
                float totalRotation = 360f * 5 + randomAngle - currentRotation;

                // Pastikan totalRotation positif
                if (totalRotation < 0) {
                    totalRotation += 360;
                }

                ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(spinImageView, "rotation", currentRotation, currentRotation + totalRotation);
                rotateAnimator.setDuration(3000); // Durasi animasi dalam milidetik
                rotateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                rotateAnimator.start();

                // Tampilkan hasil setelah animasi selesai
                rotateAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        resultTextView.setText(String.valueOf(percentage));
                    }
                });
            }
        });

        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil nilai dari resultTextView
                String result = resultTextView.getText().toString();

                // Kirim data ke undiantraveloka menggunakan Intent
                Intent intent = new Intent(ekstravaganza.this, undiantraveloka.class);
                intent.putExtra("result", result);
                navigateToUndianPage();
                startActivity(intent);

            }
        });
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

    public void scsekstra(View view) {
        Intent scsekstra = new Intent(ekstravaganza.this, undiantraveloka.class);
        startActivity(scsekstra);
        navigateToUndianPage();
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