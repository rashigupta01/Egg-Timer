package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timeLeft;
    SeekBar seekBar;
    Button timerActionBtn;

    boolean timerGoingOn = false;
    CountDownTimer timer;

    public void btnOnClick (View view) {

        if(timerGoingOn) {
            timerActionBtn.setText("START");
            if(timer != null) {
                timer.cancel();
                timer = null;
            }
            seekBar.setEnabled(true);
        } else {
            timerActionBtn.setText("STOP");

            seekBar.setEnabled(false);

            timer = new CountDownTimer(seekBar.getProgress()*1000 + 100, 1000) {
                public void onTick (long millisecondsLeft) {
                    updateTimeLeft((int) millisecondsLeft/1000);
                    seekBar.setProgress( (int) millisecondsLeft/1000);
                }

                public void onFinish() {
                    MediaPlayer hornSound = MediaPlayer.create(getApplicationContext(), R.raw.radiointerruption);
                    hornSound.start();

                    seekBar.setEnabled(true);
                    timerActionBtn.setText("START");

                    timerGoingOn = false;

                    updateTimeLeft(0);
                    seekBar.setProgress(0);
                }
            }.start();
        }
        timerGoingOn = !timerGoingOn;
    }

    public void updateTimeLeft (int timeLeftValue) {
        String minutes, seconds;
        minutes = Integer.toString(timeLeftValue/60);
        seconds = Integer.toString(timeLeftValue%60);
        if(minutes.length()==1) {
            minutes = "0" + minutes;
        }
        if(seconds.length()==1) {
            seconds = "0" + seconds;
        }
        timeLeft.setText(minutes + " : " + seconds);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        timeLeft = findViewById(R.id.timeLeft);
        timerActionBtn = findViewById(R.id.timerActionBtn);

        seekBar.setMax(10*60);      // maximum timer can be of 10 minutes
        seekBar.setProgress(30);
        timeLeft.setText("00 : 30");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimeLeft(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}