package emulator;

import model.Bid;
import model.Product;
import model.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.TimerTask;

/**
 * Created by Cristina on 08.09.2014.
 */
public class BidEmulator extends TimerTask {

    private BidListener listener;
    private Random number = new Random();
    private static int uniqueBidId = 3;

    public void addListener(BidListener listener){
        this.listener = listener;
    }

    @Override
    public void run() {

        //create a random bid
            Bid bid = new Bid();
            bid.id = uniqueBidId++;

            Product product = new Product();
            int randomIdOfProduct = 1 + number.nextInt(2);
            product.id = randomIdOfProduct;

            User user = new User();
            int randomUserId = 1 + number.nextInt(4);
            user.id = randomUserId;
            int randomNotifications = number.nextInt(2); //0 or 1
            user.getOverbidNotifications = (randomNotifications == 0) ? false : true;

            bid.product = product;
            bid.user = user;

            BigDecimal randomAmount = BigDecimal.valueOf(100*number.nextDouble());
            bid.amount = randomAmount;

        //randomly generate second between 0 and 59
            int s = number.nextInt(60);
        //get system time
            String time = new Date().toString();
            String seconds = time.substring(17,19);
        //when seconds equals randomly generated number, generate bid event
        if (Integer.valueOf(seconds) == s) {
            System.out.println("Time: " + time);
            listener.bidEvent(bid);
        }

    }
}
