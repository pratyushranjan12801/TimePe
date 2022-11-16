package com.prandroid.timepe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.prandroid.timepe.Models.Users;
import com.prandroid.timepe.R;
import com.prandroid.timepe.databinding.ActivitySignupBinding;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {


    ActivitySignupBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account.");


        //btn to register the user
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.username.getText().toString().isEmpty()
                        && !binding.email.getText().toString().isEmpty()
                        && !binding.password.getText().toString().isEmpty()){

                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.email.getText().toString(),binding.password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){

                                        Users user = new Users(binding.username.getText().toString(),binding.email.getText().toString(),binding.password.getText().toString());
                                        String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                        database.getReference().child("Users").child(id).setValue(user);

                                        Intent i = new Intent(SignUpActivity.this,MainActivity.class);
                                        startActivity(i);
                                        finish();

                                        Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{
                    Toast.makeText(SignUpActivity.this,"Please Enter Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}