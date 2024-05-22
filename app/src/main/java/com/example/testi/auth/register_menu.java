package com.example.testi.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testi.R;
import com.example.testi.login;
import com.example.testi.main_menu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class register_menu extends AppCompatActivity {

    private boolean isPasswordVisible1 = false;
    private boolean isPasswordVisible2 = false;
    private EditText passwordEditText1;
    private EditText passwordEditText2;
    private EditText editTextName, editTextEmail, editTextPass, editTextPassCo;
    private ImageView togglePasswordVisibilityImageView1;
    private ImageView togglePasswordVisibilityImageView2;
    private Button regisbt;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);
        passwordEditText1 = findViewById(R.id.editTextPass);
        togglePasswordVisibilityImageView1 = findViewById(R.id.imageView12);
        passwordEditText2 = findViewById(R.id.editTextPassCo);
        togglePasswordVisibilityImageView2 = findViewById(R.id.imageView11);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        editTextPassCo = findViewById(R.id.editTextPassCo);
        regisbt = findViewById(R.id.regisbt);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(register_menu.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Waiting For A While!");
        progressDialog.setCancelable(false);

        regisbt.setOnClickListener(v -> {
            if (editTextName.getText().length() > 0 && editTextEmail.getText().length() > 0 && editTextPass.getText().length() > 0 && editTextPassCo.getText().length() > 0) {
                if (editTextPass.getText().toString().equals(editTextPassCo.getText().toString())) {
                    regis(editTextName.getText().toString(), editTextEmail.getText().toString(), editTextPass.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Password Invalid", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please complete all fields", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void regis(String name, String email, String pass) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful() && task.getResult() != null) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null) {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    reload();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
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

    private void reload() {
        startActivity(new Intent(getApplicationContext(), main_menu.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }
}
