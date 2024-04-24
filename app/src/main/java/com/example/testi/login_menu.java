package com.example.testi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class login_menu extends AppCompatActivity {

    private boolean isPasswordVisible = false;
    private EditText passwordEditText;
    private ImageView togglePasswordVisibilityImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);

        passwordEditText = findViewById(R.id.editText1);
        togglePasswordVisibilityImageView = findViewById(R.id.imageView13);

        togglePasswordVisibilityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });
    }

    public void backlog(View view) {
        Intent backlog = new Intent(login_menu.this, login.class);
        startActivity(backlog);
    }

    public void fastballs(View view) {
        Intent fastballs = new Intent(login_menu.this, main_menu.class);
        startActivity(fastballs);
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide the password
            passwordEditText.setInputType(129); // 129 is the input type for password
            isPasswordVisible = false;
            togglePasswordVisibilityImageView.setImageResource(R.drawable.ant_design_eye_invisible_outlined__2_);
        } else {
            // Show the password
            passwordEditText.setInputType(1); // 1 is the input type for normal text
            isPasswordVisible = true;
            togglePasswordVisibilityImageView.setImageResource(R.drawable.streamline_visible__1_);
        }
    }
}
