package com.example.testi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class undiantraveloka extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undiantraveloka);

        // Ambil nilai yang dikirim dari aktivitas sebelumnya
        String result = getIntent().getStringExtra("result");

        // Tampilkan hasil di TextView
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setText(result);
    }
}
