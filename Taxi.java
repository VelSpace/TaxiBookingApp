import java.util.*;

public class Taxi {

    static int taxiCount = 0;
    int id;
    boolean booked;
    char currentSpot;
    int freeTime;
    int totalEarnings;
    List<String> trips;


    public Taxi(){
        booked = false;
        currentSpot = 'A'; // start point A
        freeTime = 6; // example 6AM
        totalEarnings = 0;
        trips = new ArrayList<>();
        taxiCount++; // everyTime new taxi is created a new id will be assigned
        id = taxiCount;
    }

    public void setDetails(boolean booked, char currentSpot, int freeTime, int totalEarnings,String tripDetail){
        this.booked = booked;
        this.currentSpot = currentSpot;
        this.freeTime = freeTime;
        this.totalEarnings = totalEarnings;
        this.trips.add(tripDetail);
    }

    public void printDetail(){
        System.out.println("Taxi - "+ this.id + " Total Earnings - " + this.totalEarnings);
        System.out.println("TaxiID    BookingID    CustomerID    From    To    PickupTime    DropTime      Amount");
        for(String trip : trips)
        {
            System.out.println(id + "          " + trip);
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }

    public void printTaxiDetails()
    {
        //print total earningand taxi details like current location and free time
        System.out.println("Taxi - "+ this.id + " Total Earnings - " + this.totalEarnings + " Current spot - " + this.currentSpot +" Free Time - " + this.freeTime);
    }
}
