package com.prandroid.timepe.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.prandroid.timepe.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Intent i = new Intent(this, MainActivity.class);

        new Handler().postDelayed(() -> {
            startActivity(i);
            finish();
        },4000);
    }
}