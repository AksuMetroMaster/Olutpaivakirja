package fi.metropolia.ryhma1.myapplication;

import android.util.Log;

import java.util.HashMap;

public class Safehouse {
    private HashMap<String, Integer> values;
    private static final Safehouse ourInstance = new Safehouse();

    public static Safehouse getInstance() {
        return ourInstance;
    }

    private Safehouse() {
        this.values = new HashMap<String, Integer>();
    }
    public void safehouseSave(String key, int num){
        if(this.values.containsKey(key)){
            int newnum = this.values.get(key) + num;
            this.values.put(key,newnum);
        }
        this.values.putIfAbsent(key, num);
        Log.i("whats being saved", key+" "+num);
    }
    public void safehouseSaveNew(String key, int num){
        this.values.putIfAbsent(key,num);
        Log.i("whats being saved", key+" "+num);
    }
    public Integer safehouseRetrieve(String key){
        if(this.values.containsKey(key)){
        return this.values.get(key);}else{
        return 0;}
    }

    public HashMap getDrunk(){
        return this.values;
        }
        public void delete(){
        this.values.clear();
        }

}
