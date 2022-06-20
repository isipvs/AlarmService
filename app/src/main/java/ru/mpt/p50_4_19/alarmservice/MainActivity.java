package ru.mpt.p50_4_19.alarmservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    TextView txtDataTime;
    TextView LastSeconds;
    Button btnStartTimer;
    Calendar dateTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDataTime = findViewById(R.id.txtDataTime);
        LastSeconds = findViewById(R.id.LastSeconds);
        btnStartTimer = findViewById(R.id.btnStartTimer);
        btnStartTimer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnStartTimer) {

            new TimePickerDialog(this, this, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();

            AlarmTimer timer = new AlarmTimer(dateTime.getTimeInMillis() - System.currentTimeMillis(), 1000);

            timer.start();

            Intent intent = new Intent(MainActivity.this, Alarm.class);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC, dateTime.getTimeInMillis(),
                    PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0));
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuts) {
        dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateTime.set(Calendar.MINUTE, minuts);
        txtDataTime.setText(DateUtils.formatDateTime(getApplicationContext(),
                dateTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
    }

    class AlarmTimer extends CountDownTimer{

        long Seconds;

        public AlarmTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            Seconds = millisInFuture / 1000;
        }

        @Override
        public void onTick(long l) {
            Seconds -= 1;
            LastSeconds.setText("" + Seconds);
        }

        @Override
        public void onFinish() {

        }
    }
}