package com.home.pablo.traffic_lights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView countdown;
    private AppCompatButton startBtn;
    private int redDuration;
    private int greenDuration;
    private int duration = 90;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countdown = findViewById(R.id.countdown);
        startBtn = findViewById(R.id.start_btn);

    /*    startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isTimerRunning) {
                    isTimerRunning = true;
                    countdown.setVisibility(View.VISIBLE);
                    new CountDownTimer(duration * 1000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String time = String.format(Locale.getDefault(), "%d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
                                    countdown.setText(time);
                                }
                            });
                        }

                        @Override
                        public void onFinish() {
                            duration = 90;
                            isTimerRunning = false;
                        }
                    }.start();
                } else {
                    Toast.makeText(MainActivity.this, "Timer is already running", Toast.LENGTH_SHORT).show();
                }
            }
        }); */
    }



}