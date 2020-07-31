package com.example.randhir.timer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText minutes ;
    TextView countDown;
    ImageButton start;
    ImageButton reset;
    Button set;

    MediaPlayer itsTime ;

    CountDownTimer timer ;

    boolean timerrunning ;
    long startTime;
    long remainingTime;
    long endTime;

    private void setTime(long milliseconds) {
        startTime = milliseconds;
        resetTimer();
        closeKeyboard();

    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + remainingTime;
        timer = new CountDownTimer(remainingTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                timerrunning = false;

                itsTime.start();

            }
        }.start();
        timerrunning = true;
        
    }

    private void resetTimer() {
        remainingTime = startTime;
        updateCountDownText();
    }

    private void updateCountDownText() {
        int hours = (int) (remainingTime / 1000) / 3600;
        int minutes = (int) ((remainingTime / 1000) % 3600) / 60;
        int seconds = (int) (remainingTime / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        countDown.setText(timeLeftFormatted);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itsTime = MediaPlayer.create(this,R.raw.timerend);

        minutes = findViewById(R.id.editText);
        countDown = findViewById(R.id.textView2);
        set = (Button) findViewById(R.id.button);
        start = (ImageButton) findViewById(R.id.imageButton1);
        reset = (ImageButton) findViewById(R.id.imageButton2);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = minutes.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                minutes.setText("");
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startTimer();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }




}


