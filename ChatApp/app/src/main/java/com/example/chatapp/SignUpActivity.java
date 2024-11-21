package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {

    EditText usernameEditText, emailEditText, passwordEditText;
    TextView loginButton, signUpButton;

    String User_name, User_email, User_password;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.pass);
        loginButton = findViewById(R.id.loginTxt);
        signUpButton = findViewById(R.id.SignUpTxt);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User_name = usernameEditText.getText().toString();
                User_email = emailEditText.getText().toString();
                User_password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(User_name)) {
                    usernameEditText.setError("Please enter correct username");
                    usernameEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(User_email)) {
                    emailEditText.setError("Please enter correct email");
                    emailEditText.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(User_password)) {
                    passwordEditText.setError("Please enter correct pass");
                    passwordEditText.requestFocus();
                    return;
                }

                signUp();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        }
    }

    private void signUp() {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(User_email.trim(), User_password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(User_name).build();
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(userProfileChangeRequest);
                        UserModel userModel = new UserModel(FirebaseAuth.getInstance().getUid(), User_name, User_email, User_password);
                        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userModel);
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        intent.putExtra("name", User_name);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("fddfgdfgdfgfd", e.getMessage());
                        Toast.makeText(SignUpActivity.this, "SignUp faild", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}