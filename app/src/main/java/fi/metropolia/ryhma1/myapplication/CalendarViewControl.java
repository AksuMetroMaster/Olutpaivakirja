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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CalendarViewControl extends AppCompatActivity {
private CalendarView calendarView;
private TabLayout tabLayout;
private Intent intentMain;
private ProgressBar progressBar;
private TextView progressText;
private TextView txtDrinksAmmount;
private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
private DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
LocalDateTime now = LocalDateTime.now();
private int waterAmmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        txtDrinksAmmount = (TextView) findViewById(R.id.olutAmount);
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
        //set the drink textview
        showValues(now.format(dtf));
        //activates water progressbar
        doProgressBar(now.format(dtf2));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //when day is clicked on calendar, returns year,month and day
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                month++;
                //displays selected day onscreen
                String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(month+"/"+dayOfMonth+"/"+year));
                String date2 = new SimpleDateFormat("yyyy/MM/dd").format(new Date(year+"/"+month+"/"+dayOfMonth));
                showValues(date);
                doProgressBar(date2);
                Toast.makeText(getBaseContext(), "Selected date "+date+" "+date2,Toast.LENGTH_LONG).show();
                Log.i("CalendarView says date is", date+" "+date2+" "+now.format(dtf));

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
        private void doProgressBar(String dayToday){
            //Progress pyörän toiminta,

            waterAmmount = Safehouse.getInstance().safehouseRetrieve(dayToday);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    // Asettaa Limitin veden määrälle ennen kun se on täynnä.
                    // Lisätään juominen määrä keskelle.
                    // Samalla haetaan int tieto vedestä.
                    int loop = 0;
                    if (loop <= waterAmmount*10) {

                        progressText.setText("Veden Määrä " + waterAmmount);
                        progressBar.setProgress(loop);
                        loop++;
                        handler.postDelayed(this, 50);
                    } else {
                        handler.removeCallbacks(this);
                    }
                }
            }, 50);
        }
    //updates ui textfields with corresponding values of dates
    private void showValues(String dayToday){

        Log.i("Update",dayToday+" "+Safehouse.getInstance().getDrunk());
        int daysDrink = Safehouse.getInstance().safehouseRetrieve(dayToday);
        Log.i("Mr Hacker", String.valueOf(daysDrink));
        txtDrinksAmmount.setText(String.valueOf(daysDrink));
    }
}