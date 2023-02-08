package com.home.pablo.traffic_lights;

import static com.home.pablo.traffic_lights.R.id.control_btn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView countdownText;
    private AppCompatButton controlBtn;
    private int redDuration;
    private int greenDuration;
    private int duration = 90;
    private boolean isTrafficLightsWork = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);
        countdownText = findViewById(R.id.countdown_text);
        controlBtn = findViewById(control_btn);

    /*    controlBtn.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void onClickControlBtn(View view) {
        if (!isTrafficLightsWork) {
            controlBtn.setBackgroundResource(R.drawable.button_stop);
            controlBtn.setText(getResources().getText(R.string.btn_stop_text));
            controlBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.text_stop, null));
            isTrafficLightsWork = true;

            Toast.makeText(this, "Traffic Lights is ON", Toast.LENGTH_SHORT).show();
        } else {
            controlBtn.setBackgroundResource(R.drawable.button_start);
            controlBtn.setText(getResources().getText(R.string.btn_start_text));
            controlBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.text_start, null));
            isTrafficLightsWork = false;

            Toast.makeText(this, "Traffic Lights is OFF", Toast.LENGTH_SHORT).show();
        }
    }
}