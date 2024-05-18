package com.example.testi;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class royalvaganza extends AppCompatActivity {

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

    public void backmain(View view) {
        Intent backmain = new Intent(royalvaganza.this, main_menu.class);
        startActivity(backmain);
    }
}