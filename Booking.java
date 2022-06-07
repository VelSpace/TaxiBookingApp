import javax.swing.*;
import java.util.*;

public class Booking {
    public static void main(String[] args) {
        // create 4 taxis
        List<Taxi> taxis = createTaxis(4);

        Scanner sc = new Scanner(System.in);
        int id = 1;

        while(true){
            System.out.println("0 - > Book Taxi");
            System.out.println("1 - > Print Taxi Details");
            int choice = sc.nextInt();
            switch (choice){
                case 0:{// get details from customer

                    int customerID = id;
                    System.out.println("Enter pickup point");
                    char pickUpPoint = sc.next().charAt(0);
                    System.out.println("Enter drop point");
                    char dropPoint = sc.next().charAt(0);
                    System.out.println("Enter pickup Time");
                    int pickUpTime = sc.nextInt();

                    // check if pickup and drop points are valid or not
                    if(pickUpPoint < 'A' || dropPoint < 'A' || pickUpPoint > 'F' || dropPoint > 'F'){
                        System.out.println("Valid pickup and drop are A,B,C,D,E,F. Exitting");
                        return;
                    }

                    // get all free taxis that can reach customer on or before pickup time

                    List<Taxi> freeTaxis = getFreeTaxis(taxis, pickUpTime,pickUpPoint);

                    // no free taxi so exit
                    if(freeTaxis.size() == 0){
                        System.out.println("No taxi can be alloted. Exitting");
                        return;
                    }

                    //sort taxis based on earnings
                    Collections.sort(freeTaxis,(a,b)->a.totalEarnings- b.totalEarnings);
                    //3,4,2 --> 2,3,4

                    //get freeTaxi nearest to us
                    bookTaxi(id,pickUpPoint,dropPoint,pickUpTime,freeTaxis);
                    id++;
                    break;
                }
                case 1:{
                    for(Taxi t : taxis)
                        t.printTaxiDetails();
                    for(Taxi t : taxis)
                        t.printDetail();
                    break;
                }
                default:
                    return;
            }
        }
    }

    private static void bookTaxi(int id, char pickUpPoint, char dropPoint, int pickUpTime, List<Taxi> freeTaxis) {
        // to find nearest
        int min = 999;

        // distance between pickup and drop
        int distanceBetweenPickUpAndDrop = 0;

        //this trip earning
        int earning = 0;

        // when taxi will be free next
        int nextFreeTime = 0;

        // where taxi is after trip is over
        char nextSpot = 'Z';

        //booked taxi
        Taxi bookedTaxi = null;

        //all details of current trip as string
        String tripDetail = "";

        for(Taxi t:freeTaxis){
            int distanceBetweenCustomerAndTaxi = Math.abs((t.currentSpot -'0') - (pickUpPoint - '0'))*15; //15 is distance between two stops
            if(distanceBetweenCustomerAndTaxi < min){
                bookedTaxi = t;
                // distance between  pickup and drop =(drop - pickup) *15 km
                distanceBetweenPickUpAndDrop = Math.abs((dropPoint - '0') - (pickUpPoint - '0')) * 15;;

                //trip earning = (distanceBetweenPickUpAndDrop-5)*10+100
                earning = (distanceBetweenPickUpAndDrop-5)*10+100;

                //drop time calculation
                int dropTime = pickUpTime+(distanceBetweenPickUpAndDrop/15);

                //when taxi will be free next
                nextFreeTime = dropTime;

                //taxi will be drop point after trip
                nextSpot = dropPoint;

                //creating trip detail
                tripDetail = "  "+id+"              "+id+"         "+pickUpPoint+"       "+dropPoint+"        "+pickUpTime+"           "+dropTime+"         "+earning;

                min = distanceBetweenCustomerAndTaxi;
            }
        }
        // setting corresponding details to allotted taxi
        bookedTaxi.setDetails(true,nextSpot,nextFreeTime,bookedTaxi.totalEarnings+earning,tripDetail);
        // Booked successfully
        System.out.println("Taxi "+bookedTaxi.id+" booked");
    }

    public static List<Taxi> getFreeTaxis(List<Taxi> taxis, int pickUpTime, char pickUpPoint) {
        List<Taxi> freeTaxis = new ArrayList<>();
        for(Taxi t: taxis){
            if(t.freeTime <= pickUpTime && (Math.abs((t.currentSpot-'0') - (pickUpPoint - '0')) <= pickUpTime - t.freeTime)){
                freeTaxis.add(t);
            }
        }
        return freeTaxis;
    }

    public static List<Taxi> createTaxis(int n) {
        List<Taxi> taxis = new ArrayList<>();
        // create taxis
        for(int i=1;i<=n;i++){
            Taxi t = new Taxi();
            taxis.add(t);
        }
        return taxis;
    }

}
