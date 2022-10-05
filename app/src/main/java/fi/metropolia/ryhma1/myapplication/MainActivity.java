package fi.metropolia.ryhma1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private TextView txtDrinks;
    private TextView txtWater;
    private Object Menu;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private DrinkCounter drinks;
    private DrinkCounter water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDrinks = findViewById(R.id.txtDrinkCounter);
        txtWater = findViewById(R.id.txtWaterCounter);

        refresh();
    }
    private void refresh(){
        drinks = new DrinkCounter(sdf.format(c.getTime()));
        water = new DrinkCounter(sdf2.format(c.getTime()));
        txtDrinks.setText(Integer.toString(drinks.getCount()));
        txtWater.setText((Integer.toString(water.getCount())));
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public void sendToSafehouse(View view) {
        Safehouse.getInstance().safehouseSave(drinks.getTime(), drinks.getCount());
        Safehouse.getInstance().safehouseSave(water.getTime(), water.getCount());

        for (Object i : Safehouse.getInstance().getDrunk().keySet()) {
            Log.i("Safehouse has ",("key: " + i + " value: " + Safehouse.getInstance().getDrunk().get(i)));
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
    }

}