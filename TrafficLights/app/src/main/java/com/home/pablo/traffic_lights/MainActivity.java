package com.home.pablo.traffic_lights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LinearLayout redLight;
    private LinearLayout yellowLight;
    private LinearLayout greenLight;
    private TextView countdownText;
    private AppCompatButton controlBtn;
    private int redDuration;
    private int yellowDuration;
    private int greenDuration;
    private long duration;
    private int nextLight;
    int previousLight;
    private boolean isTrafficLightsWork;
    private boolean isAppWorks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide action bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);

        redLight = findViewById(R.id.red_light);
        yellowLight = findViewById(R.id.yellow_light);
        greenLight = findViewById(R.id.green_light);

        countdownText = findViewById(R.id.countdown_text);
        controlBtn = findViewById(R.id.control_btn);

        countdownText.setVisibility(View.INVISIBLE);

        nextLight = 1;
        redDuration = 5;
        yellowDuration = 3;
        greenDuration = 7;

        isTrafficLightsWork = false;
        isAppWorks = true;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAppWorks = false;
    }

    public void onClickControlBtn(View view) {
        if (!isTrafficLightsWork) {
            isTrafficLightsWork = true;

            controlBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.button_stop));
            controlBtn.setText(getResources().getText(R.string.btn_stop_text));
            controlBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.text_stop, null));
            countdownText.setVisibility(View.VISIBLE);

            runTrafficLights();

            Toast.makeText(this, "Traffic Lights is ON", Toast.LENGTH_SHORT).show();
        } else {
            isTrafficLightsWork = false;
            controlBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.button_start));
            controlBtn.setText(getResources().getText(R.string.btn_start_text));
            controlBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.text_start, null));
            countdownText.setVisibility(View.INVISIBLE);

            stopLights();

            Toast.makeText(this, "Traffic Lights is OFF", Toast.LENGTH_SHORT).show();
        }
    }

    private void runTrafficLights() {

        new Thread(() -> {
            previousLight = 1;
            while (isTrafficLightsWork && isAppWorks) {
                switch (nextLight) {
                    case 1:
                        duration = redDuration;
                        nextLight = 2;
                        previousLight = 1;
                        countdownText.setText(String.valueOf(duration));
                        redLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_red));
                        yellowLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
                        greenLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
                        break;
                    case 2:
                        duration = yellowDuration;
                        nextLight = previousLight == 1 ? 3 : 1;
                        previousLight = 1;
                        countdownText.setText("");
                        redLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
                        yellowLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_yellow));
                        greenLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
                        break;
                    case 3:
                        duration = greenDuration;
                        nextLight = 2;
                        previousLight = 3;
                        countdownText.setText(String.valueOf(duration));
                        redLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
                        yellowLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
                        greenLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_green));
                        break;
                    default:
                        stopLights();
                }

                Log.d(TAG, "next light " + nextLight);
                try {
                    Thread.sleep(duration * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (previousLight == 3) {
                    greenLight.setBackground((ContextCompat.getDrawable(this, R.drawable.background_black)));
                    for (int i = 0; i < 3; i++) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        greenLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_green));

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        greenLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
                    }
                }

            }
        }).start();


    }

    private void stopLights() {
        nextLight = 1;
        redLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
        yellowLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
        greenLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
    }

    /*
    private void controlLights(int light, long duration) {
        resetLights();
        switch (light) {
            case 1:
                redLight.setBackgroundResource(R.drawable.background_red);
                countdownText.setVisibility(View.VISIBLE);
                break;
            case 2:
                yellowLight.setBackgroundResource(R.drawable.background_yellow);
                countdownText.setVisibility(View.INVISIBLE);
                break;
            case 3:
                greenLight.setBackgroundResource(R.drawable.background_green);
                countdownText.setVisibility(View.VISIBLE);
                break;
            default: resetLights();

        }

       /* new CountDownTimer(duration * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                runOnUiThread(() -> {
                    long t = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                    String time = String.format(Locale.getDefault(), "%d", t);
                    String text;
                    text = t < 100 ? time : "";
                    countdownText.setText(text);
                });
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    */
}