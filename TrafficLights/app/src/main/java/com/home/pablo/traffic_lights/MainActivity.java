package com.home.pablo.traffic_lights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private int duration;
    private int nextLight;
    int previousLight;
    private volatile boolean isTrafficLightWork;
    private boolean isAppWork;


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
        redDuration = 15;
        yellowDuration = 3;
        greenDuration = 7;

        isTrafficLightWork = false;
        isAppWork = true;
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
        isAppWork = false;
    }

    public void onClickControlBtn(View view) {
        if (!isTrafficLightWork) {
            isTrafficLightWork = true;

            controlBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.button_stop));
            controlBtn.setText(getResources().getText(R.string.btn_stop_text));
            controlBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.text_stop, null));
            countdownText.setVisibility(View.VISIBLE);

            runTrafficLights();

        } else {
            isTrafficLightWork = false;
            controlBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.button_start));
            controlBtn.setText(getResources().getText(R.string.btn_start_text));
            controlBtn.setTextColor(ResourcesCompat.getColor(getResources(), R.color.text_start, null));
            countdownText.setVisibility(View.INVISIBLE);

            stopLights();
        }
    }

    private void runTrafficLights() {
        new Thread(() -> {
            previousLight = 1;
            while (isTrafficLightWork && isAppWork) {
                switch (nextLight) {
                    case 1:
                        duration = redDuration;
                        nextLight = 2;
                        previousLight = 1;
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
                        redLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
                        yellowLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_black));
                        greenLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_green));
                        break;
                    default:
                        stopLights();
                }

                for (int i = duration; i > 0; i--) {
                    if (!isTrafficLightWork) {
                        return;
                    }
                    if (nextLight == 2 && i < 100) {
                        countdownText.setText(String.valueOf(i));
                    } else {
                        countdownText.setText("");
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (!isTrafficLightWork) {
                        return;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                countdownText.setText("");

                if (previousLight == 3) {
                    greenLight.setBackground((ContextCompat.getDrawable(this, R.drawable.background_black)));
                    for (int i = 0; i < 3; i++) {
                        if (!isTrafficLightWork) {
                            return;
                        }
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        greenLight.setBackground(ContextCompat.getDrawable(this, R.drawable.background_green));
                        if (!isTrafficLightWork) {
                            return;
                        }
                        try {
                            Thread.sleep(800);
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
}