package fi.metropolia.ryhma1.myapplication;

public class DrinkCounter {
    private int count;

    public DrinkCounter(){
        this.count = 0;
    }
    public void plus(){
        count++;
    }
    public int getCount(){
        return count;
    }
    public void minus(){
        if(count > 0){
        count--;}
    }
}
