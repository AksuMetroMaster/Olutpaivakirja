package fi.metropolia.ryhma1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private DrinkCounter drinks = new DrinkCounter();
    private TextView txtDrinks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDrinks = findViewById(R.id.txtDrinkCounter);
        txtDrinks.setText(Integer.toString(drinks.getCount()));

    }
    public void openCalendar(View view) {
        Intent intent = new Intent(this, CalendarView.class);

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
}