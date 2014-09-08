import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Cristina on 08.09.2014.
 */
public class AuctionPrototype implements BidListener{

    private static BidEmulator bidEmulator;

    public AuctionPrototype(){
        bidEmulator = new BidEmulator();
        bidEmulator.addListener(this);
    }

    public static void main(String[] args){

        AuctionPrototype auctionPrototype = new AuctionPrototype();

        //users
        User mary = new User();
        User julia = new User();
        User james = new User();
        User me = new User();

        mary.id = 1;
        mary.name = "Mary";
        mary.email = "m.smith@xyz.com";
        mary.getOverbidNotifications = true;

        julia.id = 2;
        julia.name = "Julia";
        julia.email = "j.roberts@abc.org";
        julia.getOverbidNotifications = true;

        james.id = 3;
        james.name = "James";
        james.email = "james.bond@xyz.com";
        james.getOverbidNotifications = false;

        me.id = 4;
        me.name = "Cristina";
        me.email = "cristina.f@abc.org";
        me.getOverbidNotifications = true;

        //selling a bicycle and a ring
        Product bicycle = new Product();
        Product ring = new Product();

        bicycle.id = 1;
        bicycle.title = "Pegas Mountain Bike";
        bicycle.minimalPrice = BigDecimal.valueOf(100);
        bicycle.reservedPrice = BigDecimal.valueOf(3000);

        ring.id = 2;
        ring.title = "Engagement Ring";
        ring.minimalPrice = BigDecimal.valueOf(2500);
        ring.reservedPrice = BigDecimal.valueOf(7000);

        //some bids
        Bid bid1 = new Bid();
        bid1.id = 1;
        bid1.product = ring;
        bid1.amount = BigDecimal.valueOf(7000);
        bid1.user = james;

        Bid bid2 = new Bid();
        bid2.id = 2;
        bid2.product = ring;
        bid2.amount = BigDecimal.valueOf(7500);
        bid2.user = me;

        Bid bid3 = new Bid();
        bid3.id = 3;
        bid3.product = bicycle;
        bid3.amount = BigDecimal.valueOf(1000);
        bid3.user = me;

        //A collection of Bid objects represents current bids
        List<Bid> currentBids = new ArrayList<>();

        currentBids.add(bid1);
        currentBids.add(bid2);
        currentBids.add(bid3);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(bidEmulator, 0, 100);

    }

    @Override
    public void bidEvent() {
        System.out.println("Received a new bid!");
    }
}
