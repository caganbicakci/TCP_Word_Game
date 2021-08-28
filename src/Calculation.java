import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Calculation {

    ArrayList <String> usedWords = new ArrayList<String>();

    public void addToUsedWords (String i){
    usedWords.add(i);
    }


    Timer timer = new Timer();

    TimerTask task = new TimerTask(){
        public void run(){
            timer.cancel();
        }

    };

    public void  setTimer(){
        timer.schedule(task,5000);
        System.out.println("TIMES UP!");
    }



}

