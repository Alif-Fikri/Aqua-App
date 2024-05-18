package com.example.testi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class traveloka extends AppCompatActivity {

    private TextView textViewtotal5;
    private TextView textViewTotalNeededPoints; // TextView baru untuk menampilkan total poin yang diperlukan
    private int points5 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveloka);

        textViewtotal5 = findViewById(R.id.textViewtotal5);
        textViewTotalNeededPoints = findViewById(R.id.accumulation);
        updatePointsDisplay();
    }

    public void increasetotal5(View view) {
        points5++;
        updatePointsDisplay();
    }

    public void decreasetotal5(View view) {
        points5--;
        updatePointsDisplay();
    }

    private void updatePointsDisplay() {
        textViewtotal5.setText(String.valueOf(points5));

        // Hitung total poin yang diperlukan untuk melakukan redeem
        int neededPoints = points5 * 200;

        // Tampilkan total poin yang diperlukan pada TextView baru
        textViewTotalNeededPoints.setText(String.valueOf(neededPoints));
    }
}
