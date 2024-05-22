package com.example.testi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.testi.helper.UserPointsManager;

public class scanktp extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 1001;
    private static final int REQUEST_IMAGE_CAPTURE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanktp);

        Button scanButton = findViewById(R.id.button10);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        });
        TextView textViewPoints = findViewById(R.id.textViewPoints);
        int userPoints = UserPointsManager.getUserPoints(this);
        textViewPoints.setText(String.valueOf(userPoints));
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Set orientation to landscape
            takePictureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

    private boolean checkIfBarcodeRegistered(Bitmap imageBitmap) {
        return imageBitmap != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView scannedImageView = findViewById(R.id.scannedImage);
            scannedImageView.setImageBitmap(imageBitmap);

            // Lakukan logika pengecekan barcode
            boolean isBarcodeRegistered = checkIfBarcodeRegistered(imageBitmap);
        }
    }

    public void scansuccess(View view) {
        Intent scansuccess = new Intent(scanktp.this, scansuccess.class);
        startActivity(scansuccess);
        navigateToCongratulationsPage();
    }

    public void cancelktp(View view) {
        Intent cancelktp = new Intent(scanktp.this, ekstravaganza.class);
        startActivity(cancelktp);
    }

    private void navigateToCongratulationsPage() {
        Intent intent = new Intent(this, scansuccess.class);
        startActivity(intent);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kembali ke halaman utama
                navigateToMainPage();
            }
        }, 3000); // Waktu penundaan dalam milidetik (3000 ms = 3 detik)
    }

    private void navigateToMainPage() {
        // Pindah kembali ke halaman utama
        Intent intent = new Intent(this, main_menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Menghapus semua aktivitas lain di atas halaman utama
        startActivity(intent);
        finish();
    }
}
