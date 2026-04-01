/**
 * Book My Stay Application
 *
 * Use Case 5: Booking Request Queue (FIFO)
 *
 * This version introduces a queue-based booking request system to ensure
 * fair handling of multiple booking requests using FIFO principle.
 *
 * NOTE: No inventory update happens in this use case.
 *
 * @author YourName
 * @version 5.1
 */

import java.util.*;

// ===== Room Domain Model =====
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

    public void displayRoomDetails() {
        System.out.println("Room Type       : " + roomType);
        System.out.println("Beds            : " + numberOfBeds);
        System.out.println("Size (sq ft)    : " + size);
        System.out.println("Price/Night     : ₹" + pricePerNight);
    }
}

// ===== Concrete Rooms =====
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 120.0, 2000.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 200.0, 3500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 350.0, 6000.0);
    }
}

// ===== Inventory (UC3) =====
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 3);
        availabilityMap.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }
}

// ===== Reservation Model (NEW) =====
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
        System.out.println("--------------------------------------");
    }
}

// ===== Booking Queue Service (UC5) =====
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // View all requests (FIFO order)
    public void displayQueue() {
        System.out.println("\n===== BOOKING REQUEST QUEUE (FIFO) =====\n");

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }
}

// ===== Main Class =====
public class Bookmystay {

    public static void main(String[] args) {

        // ===== UC1: Welcome =====
        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v5.1");
        System.out.println("=====================================\n");

        // ===== UC3: Inventory (read-only here) =====
        RoomInventory inventory = new RoomInventory();

        // ===== UC5: Booking Queue =====
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulated booking requests (arrival order matters)
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("David", "Single Room"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();

        System.out.println("\nNote: No rooms allocated yet (inventory unchanged).");
        System.out.println("Application Terminated.");
    }
}
