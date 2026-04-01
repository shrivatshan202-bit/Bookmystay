/**
 * Book My Stay Application
 *
 * This application demonstrates:
 * UC1 - Application Entry & Welcome Message
 * UC2 - Room Initialization & Static Availability
 *
 * @author YourName
 * @version 2.1
 */

// Abstract Room class
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double size;
    private double pricePerNight;

    public Room(String roomType, int numberOfBeds, double size, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getSize() {
        return size;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type       : " + roomType);
        System.out.println("Beds            : " + numberOfBeds);
        System.out.println("Size (sq ft)    : " + size);
        System.out.println("Price/Night     : ₹" + pricePerNight);
    }
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 120.0, 2000.0);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 200.0, 3500.0);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 350.0, 6000.0);
    }
}

// Main Application Class (Single Entry Point)
public class Bookmystay {

    public static void main(String[] args) {

        // ===== UC1: Welcome Message =====
        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v2.1");
        System.out.println("=====================================\n");


        // ===== UC2: Room Initialization =====

        // Polymorphism
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        System.out.println("===== ROOM DETAILS & AVAILABILITY =====\n");

        // Single Room
        single.displayRoomDetails();
        System.out.println("Available Rooms : " + singleAvailability);
        System.out.println("--------------------------------------");

        // Double Room
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + doubleAvailability);
        System.out.println("--------------------------------------");

        // Suite Room
        suite.displayRoomDetails();
        System.out.println("Available Rooms : " + suiteAvailability);
        System.out.println("--------------------------------------");

        // End
        System.out.println("\nApplication Terminated.");
    }
}
