package fi.metropolia.ryhma1.myapplication;

import android.util.Log;

/**
 * Laskin
 * @author Aleksis
 * @author Janina
 * @author Noora
 * @author Christian
 */
public class DrinkCounter {
    private int count;
    private String time;
    //creates counter
    public DrinkCounter(String date, int count){
        this.count = count;
        this.time = date;
        Log.d("Counter says", this.time);
    }
    //adds to counter
    public void plus(){
        count++;
    }
    //returns value of counter
    public int getCount(){
        return count;
    }
    //removes one unit from counter, unless its 0
    public void minus(){
        if(count > 0){
        count--;}
    }
    //returns the date counter was created
    public String getTime() {
        return time;
    }


}
