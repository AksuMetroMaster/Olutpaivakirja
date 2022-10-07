package fi.metropolia.ryhma1.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

public class AsetusView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asetus_view);

        //hakee baarin
        ActionBar actionBar = getSupportActionBar();
        //esittelee baarin
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
    public void wantToDeleteEverything (View view){
        
    }
}