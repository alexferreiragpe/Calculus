package com.example.alexf.calculus;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.alexf.mathkids.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MediaPlayer som = MediaPlayer.create(this, R.raw.fundo);

        som.start();


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent Identifica = new Intent();
                Identifica.setClass(SplashActivity.this, IdentificaActivity.class);
                startActivity(Identifica);
                finish();
                som.stop();
            }
        }, 4000);
    }

}
