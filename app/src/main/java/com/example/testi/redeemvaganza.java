package com.example.testi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testi.helper.UserPointsManager;

public class redeemvaganza extends AppCompatActivity {

    private TextView textViewtotal1;
    private TextView textViewtotal2;
    private TextView textViewtotal3;
    private TextView textViewtotal4;
    private int points1 = 1;
    private int points2 = 1;
    private int points3 = 1;
    private int points4 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeemvaganza);

        textViewtotal1 = findViewById(R.id.textViewtotal1);
        textViewtotal2 = findViewById(R.id.textViewtotal2);
        textViewtotal3 = findViewById(R.id.textViewtotal3);
        textViewtotal4 = findViewById(R.id.textViewtotal4);

        updatePointsDisplay();
        TextView textViewPoints = findViewById(R.id.textViewPoints);
        int userPoints = UserPointsManager.getUserPoints(this);
        textViewPoints.setText(String.valueOf(userPoints));
    }

    public void increasetotal1(View view) {
        points1++;
        updatePointsDisplay();
    }

    public void decreasetotal1(View view) {
        points1--;
        updatePointsDisplay();
    }

    public void increasetotal2(View view) {
        points2++;
        updatePointsDisplay();
    }

    public void decreasetotal2(View view) {
        points2--;
        updatePointsDisplay();
    }

    public void increasetotal3(View view) {
        points3++;
        updatePointsDisplay();
    }

    public void decreasetotal3(View view) {
        points3--;
        updatePointsDisplay();
    }

    public void increasetotal4(View view) {
        points4++;
        updatePointsDisplay();
    }

    public void decreasetotal4(View view) {
        points4--;
        updatePointsDisplay();
    }

    private void updatePointsDisplay() {
        textViewtotal1.setText(String.valueOf(points1));
        textViewtotal2.setText(String.valueOf(points3));
        textViewtotal3.setText(String.valueOf(points2));
        textViewtotal4.setText(String.valueOf(points4));
    }

    public void traveloka(View view) {
        Intent traveloka = new Intent(redeemvaganza.this, traveloka.class);
        startActivity(traveloka);
    }

    public void backmain(View view) {
        Intent backmain = new Intent(redeemvaganza.this, main_menu.class);
        startActivity(backmain);
    }
}
