package fi.metropolia.ryhma1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity {
    private DrinkCounter drinks = new DrinkCounter();
    private TextView txtDrinks;
    private Object Menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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