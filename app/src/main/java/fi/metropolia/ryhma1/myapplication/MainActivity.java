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
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;

import com.google.android.material.tabs.TabLayout;

/**
 * Ohjelman päänäkymä koodi
 * @author Aleksis
 * @author Janina
 * @author Noora
 * @author Christian
 */
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

        tabLayout=(TabLayout) findViewById(R.id.momTab);

        txtDrinks = findViewById(R.id.txtDrinkCounter);
        txtWater = findViewById(R.id.txtWaterCounter);
        txtDrinksDay = findViewById(R.id.txtDrinkCounterAllday);
        txtWaterDay = findViewById(R.id.txtWaterCounterAllday);

        recover();
        /**
         * nappi vaihtaa main activitystä kalenteriin.
         * tab toiminto valitsee aina defoultina tab 0 eli ensimäinen joten sen voi säilyttää muutamatta.
         * kun tab siirtyy positioon 1 vaihtuu näkymäksi kalenteri activiyt.
         * @author Janina
         * @author Christian
         */
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    intentMain = new Intent(MainActivity.this, MainActivity.class);
                }
                if (tab.getPosition() == 1) {
                    intentMain = new Intent(MainActivity.this,
                            CalendarViewControl.class);
                    MainActivity.this.startActivity(intentMain);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        refresh();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recover();
        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
    /**
     * Palauttaa hauskan kommentin kun painaa joka vesi tai olut nappulaa.
     * @author Aleksis
     * @author Christian
     */
    private String hasuCommentti(String whatKind){
        Random r = new Random();
        switch (whatKind) {
            case "plusWater":
                String[] waterlist = {"\"Vesi On Märkää\"","lasi on tyhjä ? hyvää työtä harri","vesi ei ole uusiutuva resurrsi palauta se heti!","Älä koske sähkölaitteisiin märkänä"};
                return waterlist[r.nextInt(waterlist.length)];

            case "plusDrink":
                String[] drinklist = {"Daa dirlan dirlan daa","Olet liekeissä kastele itsesi","kurkkusi on märkänä","makkara perunoiden tarpeessa ?","muista käydä saniteetti tilassa"};
                return drinklist[r.nextInt(drinklist.length)];
            default:
                return "perkele";
        }
    }
    /**
     * Palauttaa arvot jotka on tallennettu Sharedpreferenciin tallentaa Safehousiin käyttöä varten
     * @author Aleksis
     */
    private void recover(){
        SharedPreferences prefGet = getSharedPreferences("Arvot", Activity.MODE_PRIVATE);
        waterCount = prefGet.getInt("Waters", 0);
        drinkCount = prefGet.getInt("Drinks", 0);
        SharedPreferences pref= getSharedPreferences("HashSave", Activity.MODE_PRIVATE);
        HashMap<String, String> map= (HashMap<String, String>) pref.getAll();
        for (String s : map.keySet()) {
            String value=map.get(s);
            String key = s;
            Safehouse.getInstance().safehouseSaveNew(key, Integer.valueOf(value));
            Log.i("Code","Retrieved "+key+" "+value);
            //Use Value
        }
    }

    //resetoi näytön arvot takaisin nollaan ja aloittaa uuden laskun

    /**
     * Päivittää näkymään uudet Counter arvot
     * @author Aleksis
     */
    private void refresh(){
        now = LocalDateTime.now();
        drinks = new DrinkCounter(dtf.format(now), drinkCount);
        water = new DrinkCounter(dtf2.format(now), waterCount);
        waterCountDay = Safehouse.getInstance().safehouseRetrieve(dtf2.format(now));
        drinkCountDay = Safehouse.getInstance().safehouseRetrieve(dtf.format(now));
        txtDrinks.setText(Integer.toString(drinks.getCount()));
        txtWater.setText((Integer.toString(water.getCount())));
        txtWaterDay.setText(Integer.toString(waterCountDay));
        txtDrinksDay.setText(Integer.toString(drinkCountDay));
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     *Tallentaa Counter arvot Safehousiin jotta kalenteri näyttää samat arvot
     * @author Aleksis
     */
    public void sendToSafehouse(View view) {
        now = LocalDateTime.now();
        Safehouse.getInstance().safehouseSave(drinks.getTime(), drinks.getCount());
        Safehouse.getInstance().safehouseSave(water.getTime(), water.getCount());
        for (Object i : Safehouse.getInstance().getDrunk().keySet()) {
            Log.i("Code",("Safehouse has key: " + i + " value: " + Safehouse.getInstance().getDrunk().get(i)));
        }
        drinkCount=0;
        waterCount=0;
        refresh();

    }

    /**
     * Avaa kalenterin
     * @author Aleksis
     */
    public void openCalendar(View view) {
        Intent intent = new Intent(this, CalendarViewControl.class);

        startActivity(intent);
    }

    /**
     * Lisää juoman laskuriin ja näytää hauskan kommentin
     * @author Aleksis
     * @author Janina
     */
    public void addDrink(View view){
        drinks.plus();
        Toast.makeText(getBaseContext(), hasuCommentti("plusDrink"),Toast.LENGTH_LONG).show();
        //shows counter result onscreen
        txtDrinks.setText(Integer.toString(drinks.getCount()));
    }
    /**
     * Lisää veden laskuriin ja näytää hauskan kommentin
     * @author Aleksis
     */
    public void addWater(View view){
        water.plus();
        Toast.makeText(getBaseContext(), hasuCommentti("plusWater"),Toast.LENGTH_LONG).show();
        txtWater.setText((Integer.toString(water.getCount())));
    }

    /**
     * poistaa juoman laskurista
     * @author Aleksis
     * @author Janina
     */
    public void undoDrink(View view){
        drinks.minus();
        //shows counter result onscreen
        txtDrinks.setText(Integer.toString(drinks.getCount()));
    }
    /**
     * poistaa veden laskurista
     * @author Aleksis
     * @author Janina
     */
    public void undoWater(View view){
        water.minus();
        //shows counter result onscreen
        txtWater.setText(Integer.toString(water.getCount()));
    }
    /**
     * avaa asetukset siirtyy AsetusViewiin.
     * @author Aleksis
     * @author Christian
     */
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.actionSettings:
                Intent intentMain = new Intent(MainActivity.this ,
                    AsetusView.class);
                MainActivity.this.startActivity(intentMain);

        }
        return false;
    }

    /**
     * Kun appi näkymö vaihtuu tai sulkeutuu kaytää saveAll() tallentaa kaiken
     * @author Aleksis
     */
    protected void onPause() {
        super.onPause();
        saveAll();
    }

    /**
     * Tallentaa kaikki arvot SharedPrefernceiin Hashmap, vesi laskuri ja juoma lasurin
     * @author Aleksis
     */
    private void saveAll(){
        SharedPreferences prefLiquid = getSharedPreferences("Arvot", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editorLiquid= prefLiquid.edit();
        editorLiquid.putInt("Waters", water.getCount());
        editorLiquid.putInt("Drinks",drinks.getCount());
        editorLiquid.commit();
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