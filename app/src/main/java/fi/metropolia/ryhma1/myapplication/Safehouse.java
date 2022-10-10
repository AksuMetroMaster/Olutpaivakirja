package fi.metropolia.ryhma1.myapplication;

import android.util.Log;

import java.util.HashMap;
/**
 * Singleton joka tallentaa päivämäärät ja niitten juoma arvot
 * @author Aleksis
 * @author Noora
 */
public class Safehouse {
    private HashMap<String, Integer> values;
    private static final Safehouse ourInstance = new Safehouse();

    public static Safehouse getInstance() {
        return ourInstance;
    }

    /**
     * Tallentaa päivämäärän ja arvon Hashmapiin
     * @author Aleksis
     */
    private Safehouse() {
        this.values = new HashMap<String, Integer>();
    }

    /**
     * Tallentaa päivämäärän ja juotu arvon. Jos avain olemassa arvojen summa tallennetaan
     * @author Aleksis
     * @param key päivämäärä
     * @param num juotu arvo
     */
    public void safehouseSave(String key, int num){
        if(this.values.containsKey(key)){
            int newnum = this.values.get(key) + num;
            this.values.put(key,newnum);
        }
        this.values.putIfAbsent(key, num);
        Log.i("Code", "whats being saved "+key+" "+num);
    }

    /**
     * Tallentaa päivämäärän ja juotu arvon. Jos avain on olemassa uusi arvo korvaa
     * @param key päivämäärä
     * @param num juotu arvo
     */
    public void safehouseSaveNew(String key, int num){
        this.values.putIfAbsent(key,num);
        Log.i("Code", "whats being saved "+key+" "+num);
    }
    public Integer safehouseRetrieve(String key){
        if(this.values.containsKey(key)){
        return this.values.get(key);}else{
        return 0;}
    }
    public void delete(){
        Log.i("Code", "Deleting...");
        this.values.clear();
        Log.i("Code", "Whats left after delete "+this.values);
    }
    public HashMap getDrunk(){
        return this.values;}


}
