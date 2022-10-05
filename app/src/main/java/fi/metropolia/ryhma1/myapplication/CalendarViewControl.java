package fi.metropolia.ryhma1.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class CalendarViewControl extends AppCompatActivity {
private CalendarView calendarView;
private TabLayout tabLayout;
private Intent intentMain;
private ProgressBar progressBar;
private TextView progressText;
int vesiMaara = 10;

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
        //Progress pyörän toiminta,
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Asettaa Limitin veden määrälle ennen kun se on täynnä.
                // Lisätään juominen määrä keskelle.
                // Samalla haetaan int tieto vedestä.
                if (vesiMaara <= 100) {
                    progressText.setText("Juomien Määrä " + vesiMaara/10);
                    progressBar.setProgress(vesiMaara);
                    vesiMaara++;
                    handler.postDelayed(this, 200);
                } else {
                    handler.removeCallbacks(this);
                }
            }
        }, 200);

    }
}