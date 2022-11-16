package com.prandroid.timepe.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.prandroid.timepe.R;

public class StartActivity extends AppCompatActivity {

    LottieAnimationView lottie1;
    LottieAnimationView lottie2;
    ImageView img;
    LottieAnimationView lottie;
    TextView txthello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        lottie = findViewById(R.id.lottie);
        lottie1 = findViewById(R.id.lottie1);
        lottie2 = findViewById(R.id.lottie2);
        img = findViewById(R.id.img);
        txthello = findViewById(R.id.txthello);

        img.animate().translationX(-1600).setDuration(1000).setStartDelay(3000);
        lottie.animate().translationX(1400).setDuration(1000).setStartDelay(3000);
        txthello.animate().translationX(1400).setDuration(1000).setStartDelay(3000);


        Intent i = new Intent(this, SignInActivity.class);

        new Handler().postDelayed(() -> {
            startActivity(i);
            finish();
        },5000);
    }
}