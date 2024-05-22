package com.example.testi.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class UserPointsManager {

    private static final String PREF_NAME = "UserPointsPref";
    private static final String KEY_POINTS = "user_points";
    private static final int INITIAL_POINTS = 30; // Poin awal

    public interface PointsUpdateListener {
        void onPointsUpdated(int newPoints);
    }

    private static final List<PointsUpdateListener> listeners = new ArrayList<>();


    // Menambahkan poin ke poin pengguna
    public static void addPoints(Context context, int pointsToAdd) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int currentPoints = getUserPoints(context); // Mendapatkan jumlah poin saat ini
        int updatedPoints = currentPoints + pointsToAdd;
        sharedPreferences.edit().putInt(KEY_POINTS, updatedPoints).apply();

        // Panggil semua listeners untuk memberi tahu tentang perubahan poin
        for (PointsUpdateListener listener : listeners) {
            listener.onPointsUpdated(updatedPoints);
        }
    }

    // Mendapatkan jumlah poin pengguna
    public static int getUserPoints(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // Jika belum ada poin disimpan, kembalikan nilai awal (30)
        return sharedPreferences.getInt(KEY_POINTS, INITIAL_POINTS);
    }

    // Mendaftarkan listener untuk pembaruan poin
    public static void registerPointsUpdateListener(PointsUpdateListener listener) {
        listeners.add(listener);
    }

    // Menghapus listener untuk pembaruan poin
    public static void unregisterPointsUpdateListener(PointsUpdateListener listener) {
        listeners.remove(listener);
    }
}

