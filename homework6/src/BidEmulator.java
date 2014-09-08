import java.util.Date;
import java.util.Random;
import java.util.TimerTask;

/**
 * Created by Cristina on 08.09.2014.
 */
public class BidEmulator extends TimerTask {

    private BidListener listener;
    private Random number = new Random();

    public void addListener(BidListener listener){
        this.listener = listener;
    }

    @Override
    public void run() {

        //randomly generate second between 0 and 59
            int s = number.nextInt(60);
        //get system time
            String time = new Date().toString();
            String seconds = time.substring(17,19);
        //when seconds equals randomly generated number, generate bid event
        if (Integer.valueOf(seconds) == s) {
            System.out.println("Time: " + time);
            listener.bidEvent();
        }

    }
}
