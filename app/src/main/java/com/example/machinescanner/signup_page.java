package com.example.machinescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup_page extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText verifyPasswordEditText;

    private Button saveButton;

    private FirebaseAuth firebaseAuth;

    private EditText firstName;
    private EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); //test1-2

        firebaseAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        verifyPasswordEditText = findViewById(R.id.verifyPasswordEditText);

        /*FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            Intent intent = new Intent(signup_page.this, login_page.class);
            startActivity(intent);
            finish();
        } */

        saveButton = findViewById(R.id.saveButton);

        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
    }

    public void createAccount(View view) {

        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        final String verifyPassword = verifyPasswordEditText.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(signup_page.this, "Account is created.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(signup_page.this, login_page.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(signup_page.this, "Account is not created.", Toast.LENGTH_LONG).show();
            }
        });

        //signUp icin asagidaki tarzda * li yerleri kontrol etmemiz gerekecek!

                       /* if (firstName.getText().toString().isEmpty()) {
                            Toast.makeText(signup_page.this,
                                    "First Name must not be empty.",
                                    Toast.LENGTH_LONG).show();
                        } else if (lastName.getText().toString().isEmpty()) {
                            Toast.makeText(signup_page.this,
                                    "Last Name must not be empty.",
                                    Toast.LENGTH_LONG).show();
                        } else if (email.isEmpty()) {
                            Toast.makeText(signup_page.this,
                                    "E-mail must not be empty.",
                                    Toast.LENGTH_LONG).show();
                        } else if (password.isEmpty()) {
                            Toast.makeText(signup_page.this,
                                    "Password must not bew empty.",
                                    Toast.LENGTH_LONG).show();
                        } else if (verifyPassword.isEmpty()) {
                            Toast.makeText(signup_page.this,
                                    "Verify password must not be empty.",
                                    Toast.LENGTH_LONG).show();
                        } else if (password.length() < 8) {
                            Toast.makeText(signup_page.this,
                                    "Password must not be empty.",
                                    Toast.LENGTH_LONG).show();
                        } else if (password != verifyPassword) {
                            Toast.makeText(signup_page.this,
                                    "Password must be same as Verify Password.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(signup_page.this,
                                    "Creation of account is failed.",
                                    Toast.LENGTH_LONG).show();
                        } */
    }

    //Intent intent = new Intent(signup_page.this, main_page.class);
    //startActivity(intent);
}