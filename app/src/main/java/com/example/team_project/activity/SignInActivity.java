package com.example.team_project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email,password;
    private TextView signUp;
    private Button signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_in);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.signUp);
        signIn = findViewById(R.id.signInBtn);
        if(auth.getCurrentUser()!= null){
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "LoginSuccess", Toast.LENGTH_SHORT).show();
            finish();
        }
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLogin = email.getText().toString();
                String passwordLogin = password.getText().toString();

                if (emailLogin.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordLogin)) {
                    Toast.makeText(SignInActivity.this, "Enter Password Address", Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwordLogin.length() < 6) {
                    Toast.makeText(SignInActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignInActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Handle error
                                    Toast.makeText(SignInActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}