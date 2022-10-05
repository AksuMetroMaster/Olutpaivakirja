package fi.metropolia.ryhma1.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarViewControl extends AppCompatActivity {
private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //when day is clicked on calendar, returns year,month and day
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                //displays selected day onscreen
                String date = dayOfMonth+"/"+(month+1)+"/"+year;
                Toast.makeText(getBaseContext(), "Selected date "+date,Toast.LENGTH_LONG).show();
                Log.i("CalendarView says date is", date);
            }
        });
    }
}