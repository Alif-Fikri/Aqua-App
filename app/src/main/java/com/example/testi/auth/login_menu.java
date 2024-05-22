package com.example.testi.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testi.R;
import com.example.testi.login;
import com.example.testi.main_menu;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login_menu extends AppCompatActivity {

    private boolean isPasswordVisible = false;
    private EditText passwordEditText;
    private ImageView togglePasswordVisibilityImageView;
    private EditText editTextEmail, editTextPass;
    private Button loginbt;
    private SignInButton googlebt;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        loginbt = findViewById(R.id.loginbt);
        googlebt = findViewById(R.id.Googlebt);
        passwordEditText = findViewById(R.id.editTextPass);
        togglePasswordVisibilityImageView = findViewById(R.id.imageView13);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(login_menu.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Waiting For A While!");
        progressDialog.setCancelable(false);

        if (loginbt != null) {
            loginbt.setOnClickListener(v -> {
                if (editTextEmail.getText().length() > 0 && editTextPass.getText().length() > 0) {
                    login(editTextEmail.getText().toString(), editTextPass.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please complete all fields first", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (googlebt != null) {
            googlebt.setOnClickListener(v -> googleSignIn());
        }
        if (togglePasswordVisibilityImageView != null) {
            togglePasswordVisibilityImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    togglePasswordVisibility();
                }
            });
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("900138671153-jgkleaefg62qgj5iegg1m248ifq1nkbg.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1001);
    }

    private void login(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    if (task.getResult().getUser() != null) {
                        reload();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void backlog(View view) {
        Intent backlog = new Intent(login_menu.this, login.class);
        startActivity(backlog);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("GOOGLE SIGN IN", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                int statusCode = e.getStatusCode();
                Log.w("GOOGLE SIGN IN", "Google sign in failed with code: " + statusCode);

                switch (statusCode) {
                    case GoogleSignInStatusCodes.SIGN_IN_CANCELLED:
                        Log.w("GOOGLE SIGN IN", "Sign in cancelled by user");
                        Toast.makeText(this, "Sign in cancelled by user", Toast.LENGTH_SHORT).show();
                        break;
                    case GoogleSignInStatusCodes.SIGN_IN_FAILED:
                        Log.w("GOOGLE SIGN IN", "Sign in failed, check your network and configuration");
                        Toast.makeText(this, "Sign in failed, check your network and configuration", Toast.LENGTH_SHORT).show();
                        break;
                    case GoogleSignInStatusCodes.NETWORK_ERROR:
                        Log.w("GOOGLE SIGN IN", "Network error occurred");
                        Toast.makeText(this, "Network error occurred", Toast.LENGTH_SHORT).show();
                        break;
                    // more cases as needed
                    default:
                        Log.w("GOOGLE SIGN IN", "Unknown error occurred");
                        Toast.makeText(this, "Unknown error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("GOOGLE SIGN IN", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            reload();
                        } else {
                            Log.w("GOOGLE SIGN IN", "signInWithCredential:failure", task.getException());
                            reload();
                        }
                    }
                });
    }
}