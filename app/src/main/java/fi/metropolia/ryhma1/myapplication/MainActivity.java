package fi.metropolia.ryhma1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private DrinkCounter drinks = new DrinkCounter();
    private TextView txtDrinks;
    private Object Menu;
    private TabLayout tabLayout;
    private Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout=(TabLayout) findViewById(R.id.momTab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    intentMain = new Intent(MainActivity.this, MainActivity.class);

                }
                if (tab.getPosition() == 1) {
                    intentMain = new Intent(MainActivity.this,
                            CalendarViewControl.class);
                    Log.e("TEST", "Painettiin toista");
                    MainActivity.this.startActivity(intentMain);
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        txtDrinks = findViewById(R.id.txtDrinkCounter);
        txtDrinks.setText(Integer.toString(drinks.getCount()));
    }






    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(this, CalendarViewControl.class);

        startActivity(intent);
    }
    public void addDrink(View view){
        drinks.plus();
        txtDrinks.setText(Integer.toString(drinks.getCount()));
    }
    public void undoDrink(View view){
        drinks.minus();
        txtDrinks.setText(Integer.toString(drinks.getCount()));
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.actionSettings:
                Intent intentMain = new Intent(MainActivity.this ,
                    AsetusView.class);
                MainActivity.this.startActivity(intentMain);

        }
        return false;
    }

}