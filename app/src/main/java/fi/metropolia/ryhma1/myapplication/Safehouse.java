package fi.metropolia.ryhma1.myapplication;

import android.util.Log;

import java.util.HashMap;

public class Safehouse {
    private HashMap<String, Integer> balls;
    private static final Safehouse ourInstance = new Safehouse();

    public static Safehouse getInstance() {
        return ourInstance;
    }

    private Safehouse() {
        balls = new HashMap<String, Integer>();
    }
    public void safehouseSave(String mouth, int ass){
        balls.put(mouth, ass);
        Log.i("whats in my balls", mouth+" "+ass);
    }

    public HashMap getDrunk(){
        return balls;
        }
}
