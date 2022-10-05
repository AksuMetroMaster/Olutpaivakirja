package fi.metropolia.ryhma1.myapplication;

import android.util.Log;


public class DrinkCounter {
    private int count;
    private String time;
    //creates counter
    public DrinkCounter(String date){
        this.count = 0;
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
