package com.example.testi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testi.helper.UserPointsManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class scan_menu extends AppCompatActivity implements UserPointsManager.PointsUpdateListener {

    private static final int REQUEST_CAMERA_PERMISSION = 1001;
    private static final int REQUEST_IMAGE_CAPTURE = 1002;
    public TextView textViewPoints;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_menu);

        Button scanButton = findViewById(R.id.button10);
        textViewPoints = findViewById(R.id.textViewPoints);
        textView = findViewById(R.id.text);

        // Daftarkan diri sebagai listener untuk pembaruan poin
        UserPointsManager.registerPointsUpdateListener(this);

        // Atur jumlah poin awal pada TextView
        textViewPoints.setText(String.valueOf(UserPointsManager.getUserPoints(this)));

        scanButton.setOnClickListener(v -> {
            IntentIntegrator IntentIntegrator = new IntentIntegrator(scan_menu.this);
            IntentIntegrator.setOrientationLocked(true);
            IntentIntegrator.setDesiredBarcodeFormats(com.google.zxing.integration.android.IntentIntegrator.QR_CODE);
            IntentIntegrator.initiateScan();
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lakukan penyimpanan atau tindakan yang sesuai
                navigateToCongratulationsPage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String barcodeContents = result.getContents(); // Ambil isi barcode
            if (barcodeContents != null) {
                Bitmap barcodeBitmap = encodeAsBitmap(barcodeContents); // Ubah isi barcode menjadi gambar
                ImageView scannedImageView = findViewById(R.id.scannedImage); // Dapatkan ImageView
                scannedImageView.setImageBitmap(barcodeBitmap);// Tampilkan gambar barcode di ImageView
                Button submitButton = findViewById(R.id.submitButton);
                submitButton.setEnabled(true);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Metode untuk mengubah string barcode menjadi gambar barcode (bitmap)
    private Bitmap encodeAsBitmap(String contents) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            BitMatrix bitMatrix = barcodeEncoder.encode(contents, BarcodeFormat.QR_CODE, 300, 300);
            return toBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Metode untuk mengubah BitMatrix menjadi bitmap
    private Bitmap toBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserPointsManager.unregisterPointsUpdateListener(this);
    }

    // Implementasi dari interface PointsUpdateListener
    @Override
    public void onPointsUpdated(int newPoints) {
        // Perbarui TextView dengan jumlah poin yang baru
        textViewPoints.setText(String.valueOf(newPoints));
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
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

    private boolean checkIfBarcodeRegistered(Bitmap barcodeImage) {
        return barcodeImage != null;
    }

    public void onSubmitButtonClick(View view) {
        // Ambil ImageView yang menampilkan gambar barcode
        ImageView scannedImageView = findViewById(R.id.scannedImage);

        // Ambil gambar barcode dari ImageView
        Bitmap barcodeImage = ((BitmapDrawable) scannedImageView.getDrawable()).getBitmap();

        // Periksa apakah barcode terdaftar
        boolean isBarcodeRegistered = checkIfBarcodeRegistered(barcodeImage);

        // Tampilkan ImageView yang sesuai berdasarkan hasil pengecekan
        ImageView imageViewTrue = findViewById(R.id.imageViewTrue);
        ImageView imageViewFalse = findViewById(R.id.imageViewFalse);
        Button submitButton = findViewById(R.id.submitButton);
        Button saveButton = findViewById(R.id.saveButton);

        if (isBarcodeRegistered) {
            imageViewTrue.setVisibility(View.VISIBLE);
            imageViewFalse.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.GONE);
        } else {
            imageViewTrue.setVisibility(View.GONE);
            imageViewFalse.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.GONE);
            submitButton.setVisibility(View.VISIBLE);
        }
    }

    private void navigateToCongratulationsPage() {
        UserPointsManager.addPoints(this, 270);
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

    public void backmain(View view) {
        Intent backmain = new Intent(scan_menu.this, main_menu.class);
        startActivity(backmain);
    }

    public void cancelmain(View view) {
        Intent cancelmain = new Intent(scan_menu.this, main_menu.class);
        startActivity(cancelmain);
    }
}
