package com.example.testi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class register_menu extends AppCompatActivity {

    private boolean isPasswordVisible1 = false;
    private boolean isPasswordVisible2 = false;
    private EditText passwordEditText1;
    private EditText passwordEditText2;
    private ImageView togglePasswordVisibilityImageView1;
    private ImageView togglePasswordVisibilityImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);

        passwordEditText1 = findViewById(R.id.editText4);
        togglePasswordVisibilityImageView1 = findViewById(R.id.imageView11);

        passwordEditText2 = findViewById(R.id.editText6);
        togglePasswordVisibilityImageView2 = findViewById(R.id.imageView12);

        togglePasswordVisibilityImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility1();
            }
        });

        togglePasswordVisibilityImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility2();
            }
        });
    }

    public void backlog(View view) {
        Intent backlog = new Intent(register_menu.this, login.class);
        startActivity(backlog);
    }

    public void tombola(View view) {
        Intent tombola = new Intent(register_menu.this, main_menu.class);
        startActivity(tombola);
    }

    private void togglePasswordVisibility1() {
        if (isPasswordVisible1) {
            // Hide the password
            passwordEditText1.setInputType(129); // 129 is the input type for password
            isPasswordVisible1 = false;
            togglePasswordVisibilityImageView1.setImageResource(R.drawable.ant_design_eye_invisible_outlined__2_);
        } else {
            // Show the password
            passwordEditText1.setInputType(1); // 1 is the input type for normal text
            isPasswordVisible1 = true;
            togglePasswordVisibilityImageView1.setImageResource(R.drawable.streamline_visible__1_);
        }
    }

    private void togglePasswordVisibility2() {
        if (isPasswordVisible2) {
            // Hide the password
            passwordEditText2.setInputType(129); // 129 is the input type for password
            isPasswordVisible2 = false;
            togglePasswordVisibilityImageView2.setImageResource(R.drawable.ant_design_eye_invisible_outlined__2_);
        } else {
            // Show the password
            passwordEditText2.setInputType(1); // 1 is the input type for normal text
            isPasswordVisible2 = true;
            togglePasswordVisibilityImageView2.setImageResource(R.drawable.streamline_visible__1_);
        }
    }
}
