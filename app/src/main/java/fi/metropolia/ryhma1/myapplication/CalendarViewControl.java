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

/**
 * Kalenteri näkymä
 * @author Aleksis
 */
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
        /**
         * Palauttaa kalenterin valittu päivämäärän
         * @author Aleksis
         * @author Noora
         */
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                month++;
                //displays selected day onscreen
                String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(month+"/"+dayOfMonth+"/"+year));
                String date2 = new SimpleDateFormat("yyyy/MM/dd").format(new Date(year+"/"+month+"/"+dayOfMonth));
                showValues(date);
                doProgressBar(date2);
                //Toast.makeText(getBaseContext(), "Selected date "+date+" "+date2,Toast.LENGTH_LONG).show();
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

    /**
     * päivän avulla hakee safehousein hashmapiin tallennetun veden määrän ja näytää progressi baarin avulla
     * @param dayToday Kalentrin palauttama päivä
     * @author Aleksis
     * @author Noora
     */
    private void doProgressBar(String dayToday){
            //Progress pyörän toiminta,

            waterAmmount = Safehouse.getInstance().safehouseRetrieve(dayToday);
            int max = 100;
            int water= waterAmmount*10;
            if(water <max){
                max = water;
            }
            final Handler handler = new Handler();
            int finalMax = max;
            final int[] loop = {0};
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    // Asettaa Limitin veden määrälle ennen kun se on täynnä.
                    // Lisätään juominen määrä keskelle.
                    // Samalla haetaan int tieto vedestä.


                    if (loop[0] <= finalMax) {

                        progressText.setText("Vettä juotu " + waterAmmount);
                        progressBar.setProgress(loop[0]);
                        loop[0]++;
                        handler.postDelayed(this, 20);
                    } else {
                        handler.removeCallbacks(this);
                    }
                }
            }, 20);
        }
    //updates ui textfields with corresponding values of dates

    /**
     * päivän mukaan palauttaa olueen arvon
     * @param dayToday Kalenterin palauttama päivä
     * @author Aleksis
     * @author Noora
     */
    private void showValues(String dayToday){

        Log.i("Code","Drinks "+dayToday+" "+Safehouse.getInstance().getDrunk());
        int daysDrink = Safehouse.getInstance().safehouseRetrieve(dayToday);
        Log.i("Code", "Ammount "+String.valueOf(daysDrink));
        txtDrinksAmmount.setText(String.valueOf(daysDrink));
    }
}