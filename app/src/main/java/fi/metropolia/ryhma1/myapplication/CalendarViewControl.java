package fi.metropolia.ryhma1.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class CalendarViewControl extends AppCompatActivity {
private CalendarView calendarView;
private TabLayout tabLayout;
private Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                Toast.makeText(getBaseContext(), "Selected date "+dayOfMonth+"/"+month+"/"+year,Toast.LENGTH_LONG).show();
            }
        });

        tabLayout=(TabLayout) findViewById(R.id.mainPage);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 1) {
                    intentMain = new Intent(CalendarViewControl.this,
                            CalendarViewControl.class);


                }
                if (tab.getPosition() == 0) {
                    intentMain = new Intent(CalendarViewControl.this, MainActivity.class);
                    Log.e("TEST", "Painettiin toista");
                    CalendarViewControl.this.startActivity(intentMain);
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}