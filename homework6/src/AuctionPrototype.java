import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Cristina on 08.09.2014.
 */
public class AuctionPrototype implements BidListener{

    //Bid emulator
    private static BidEmulator bidEmulator;

    //A collection of Bid objects representing current bids
    private static List<Bid> currentBids;

    //Constructor initializes bid emulator, registers this class as a listener and creates list of bids
    public AuctionPrototype(){
        bidEmulator = new BidEmulator();
        bidEmulator.addListener(this);
        currentBids = new ArrayList<>();
    }

    public static void main(String[] args){

        //Create auction prototype
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

        currentBids.add(bid1);
        currentBids.add(bid2);
        currentBids.add(bid3);

        //print current bids
        currentBids.forEach(bid -> {
            System.out.println(bid.user.name + " bids for " + bid.product.title);
        });

        //Use Streams API with the collection of Bid objects.
        //Use Comparator and sort the stream.

        // Filter, map, reduce
        int numberOfBidsOnRing = currentBids.stream()
                .filter(b -> b.product.id == 2)
                .mapToInt(b -> 1)
                .sum();        // Combining the results (reducing)

        System.out.println("Number of bids on ring is " + numberOfBidsOnRing);

        System.out.println("\n Bids on ring, in descending order of bid amount:");
        Comparator<Bid> comparator = Comparator.comparing(b -> b.amount);
        currentBids.stream()
                .filter(b -> b.product.id == 2)
                .sorted(comparator.reversed())
                .forEach(b -> {
                    System.out.println(b.user.name + " bids " + b.amount + " for " + b.product.title);
                });


        System.out.println();

        //start bidding process emulation
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(bidEmulator, 0, 100);

    }

    @Override
    public void bidEvent(Bid bid) {
        System.out.println("Received a new bid!");
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Bid id: " + bid.id + " Product id: " + bid.product.id +
            " User id: " + bid.user.id + " Amount: " + df.format(bid.amount));
        //add the new bid to the collection of bids
        currentBids.add(bid);
        //announce all bidders who opted for receiving overbid emails
        currentBids.stream()
                .filter(b -> b.product.id == bid.product.id)
                .filter(b -> b.user.getOverbidNotifications == true)
                .map(b -> b.user.id)
                .distinct()
                .forEach(i -> {
                    switch(i){
                        case 1: System.out.println("Mail sent to Mary: m.smith@xyz.com"); break;
                        case 2: System.out.println("Mail sent to Julia: j.roberts@abc.org"); break;
                        case 3: System.out.println("Mail sent to James: james.bond@xyz.com"); break;
                        case 4: System.out.println("Mail sent to Cristina: cristina.f@abc.org"); break;
                        default:;
                    }
                });
        //if the bid is less than a min Product price, send the bidder a sorry email
        //ff the bid is greater or equal to the Product reserved price, send the bidder a winning email
        System.out.println();
    }
}
