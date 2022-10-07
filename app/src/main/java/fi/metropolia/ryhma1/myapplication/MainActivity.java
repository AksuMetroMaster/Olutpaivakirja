package fi.metropolia.ryhma1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private TextView txtDrinks;
    private TextView txtWater;
    private TextView txtDrinksDay;
    private TextView txtWaterDay;
    private DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDateTime now = LocalDateTime.now();
    private DrinkCounter drinks;
    private DrinkCounter water;
    private TabLayout tabLayout;
    private Intent intentMain;
    private int waterCount = 0;
    private int drinkCount = 0;
    private int waterCountDay = 0;
    private int drinkCountDay = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefGet = getSharedPreferences("Arvot", Activity.MODE_PRIVATE);
        waterCount = prefGet.getInt("Waters", 0);
        drinkCount = prefGet.getInt("Drinks", 0);
        Log.i("Code", "I did it, I started onCreate");
        SharedPreferences pref= getSharedPreferences("HashSave", Activity.MODE_PRIVATE);
        HashMap<String, String> map= (HashMap<String, String>) pref.getAll();
        for (String s : map.keySet()) {
            String value=map.get(s);
            String key = s;
            Safehouse.getInstance().safehouseSave(key, Integer.valueOf(value));
            Log.i("Code","Retrieved "+key+" "+value);
            //Use Value
        }
        tabLayout=(TabLayout) findViewById(R.id.momTab);

        txtDrinks = findViewById(R.id.txtDrinkCounter);
        txtWater = findViewById(R.id.txtWaterCounter);
        txtDrinksDay = findViewById(R.id.txtDrinkCounterAllday);
        txtWaterDay = findViewById(R.id.txtWaterCounterAllday);

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

        refresh();
    }
    //resetoi näytön arvot takaisin nollaan ja aloittaa uuden laskun
    private void refresh(){
        now = LocalDateTime.now();
        drinks = new DrinkCounter(dtf.format(now), drinkCount);
        water = new DrinkCounter(dtf2.format(now), waterCount);
        waterCountDay = Safehouse.getInstance().safehouseRetrieve(dtf2.format(now));
        drinkCountDay = Safehouse.getInstance().safehouseRetrieve(dtf.format(now));
        drinkCount=0;
        waterCount=0;
        txtDrinks.setText(Integer.toString(drinks.getCount()));
        txtWater.setText((Integer.toString(water.getCount())));
        txtWaterDay.setText(Integer.toString(waterCountDay));
        txtDrinksDay.setText(Integer.toString(drinkCountDay));
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public void sendToSafehouse(View view) {
        now = LocalDateTime.now();
        Safehouse.getInstance().safehouseSave(drinks.getTime(), drinks.getCount());
        Safehouse.getInstance().safehouseSave(water.getTime(), water.getCount());
        for (Object i : Safehouse.getInstance().getDrunk().keySet()) {
            Log.i("Code",("Safehouse has key: " + i + " value: " + Safehouse.getInstance().getDrunk().get(i)));
        }
        refresh();

    }
    //button that moves from main activity to calendar activity
    public void openCalendar(View view) {
        Intent intent = new Intent(this, CalendarViewControl.class);

        startActivity(intent);
    }
    //button that adds to beer counter
    public void addDrink(View view){
        drinks.plus();
        //shows counter result onscreen
        txtDrinks.setText(Integer.toString(drinks.getCount()));
    }
    //button that adds to water counter
    public void addWater(View view){
        water.plus();
        txtWater.setText((Integer.toString(water.getCount())));
    }
    //button that removes from beer counter
    public void undoDrink(View view){
        drinks.minus();
        //shows counter result onscreen
        txtDrinks.setText(Integer.toString(drinks.getCount()));
    }
    public void undoWater(View view){
        water.minus();
        //shows counter result onscreen
        txtWater.setText(Integer.toString(water.getCount()));
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.actionSettings:
                Intent intentMain = new Intent(MainActivity.this ,
                    AsetusView.class);
                MainActivity.this.startActivity(intentMain);

        }
        return false;
    } protected void onPause() {
        super.onPause();
        //tallentaa nykyiset juoma arvot että voi siirtyä kalenterin ja main activityn välillä, sekä sulkea appin
        SharedPreferences prefPut = getSharedPreferences("Arvot", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt("Drinks", drinks.getCount());
        prefEditor.putInt("Waters", water.getCount());
        prefEditor.commit();

        //tallentaa Hashmapin kun ohjelma suljetaan
        SharedPreferences pref= getSharedPreferences("HashSave", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        HashMap map = Safehouse.getInstance().getDrunk();
        for (Object s : map.keySet()) {
            editor.putString((String) s, String.valueOf(map.get(s)));
            Log.i("Code", "Saving "+s+" "+map.get(s));
            editor.commit();
        }


    }

}