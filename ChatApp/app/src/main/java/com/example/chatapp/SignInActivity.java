package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    EditText Email, Password;
    TextView loginbu, signupbu;
    String User_email, User_password;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        Email = findViewById(R.id.username);
        Password = findViewById(R.id.pass);
        loginbu = findViewById(R.id.loginTxt);
        signupbu = findViewById(R.id.createTxt);


        loginbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User_email = Email.getText().toString();
                User_password = Password.getText().toString();

                if (TextUtils.isEmpty(User_email)) {
                    Email.setError("Please enter correct email");
                    Email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(User_password)) {
                    Password.setError("Please enter correct pass");
                    Password.requestFocus();
                    return;
                }

                signin();
            }
        });

        signupbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }
    }

    private void signin() {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(User_email.trim(), User_password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String User_name=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.putExtra("name", User_name);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthEmailException) {
                            Toast.makeText(SignInActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignInActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}