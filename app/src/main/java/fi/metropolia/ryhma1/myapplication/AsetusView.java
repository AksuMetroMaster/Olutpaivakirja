package fi.metropolia.ryhma1.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

/**
 * Asetukset
 * @author Aleksis
 * @author Christian
 *
 */
public class AsetusView extends AppCompatActivity {
/**
*Asetukset hakee yläpalkin josta pääsee takaisin ja asetuksista löytyy reset nappula joka nolla KAIKEN.
 * @author Christian
 * @author Janina
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asetus_view);

        //hakee baarin
        ActionBar actionBar = getSupportActionBar();
        //esittelee baarin
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * pelkkä takaisin nappi.
     * @param item
     * @author Christian
     */

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    /**
     * Poistaaa kaikki tallennetut arvot
     * @author Aleksis
     * @author Christian
     */
    public void wantToDeleteEverything (View view){

        Safehouse.getInstance().delete();
        SharedPreferences pref = getSharedPreferences("Arvot", Activity.MODE_PRIVATE);
        SharedPreferences pref2= getSharedPreferences("HashSave", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        SharedPreferences.Editor editor2 = pref2.edit();
        editor.clear();
        editor.commit();
        editor2.clear();
        editor2.commit();

    }
}