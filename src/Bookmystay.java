/**
 * Book My Stay Application
 *
 * Use Case 4: Room Search & Availability Check
 *
 * This version introduces a read-only search service that allows users
 * to view available rooms without modifying inventory state.
 *
 * @author YourName
 * @version 4.1
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

// ===== Centralized Inventory (UC3) =====
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 3);
        availabilityMap.put("Suite Room", 0); // Example: unavailable
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    // No update used in UC4 (important concept)
}

// ===== Search Service (NEW IN UC4) =====
class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("===== AVAILABLE ROOMS =====\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Validation: show only available rooms
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available Rooms : " + available);
                System.out.println("--------------------------------------");
            }
        }
    }
}

// ===== Main Class =====
public class Bookmystay {

    public static void main(String[] args) {

        // ===== UC1: Welcome =====
        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v4.1");
        System.out.println("=====================================\n");

        // ===== UC2: Room Objects =====
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // ===== UC3: Inventory =====
        RoomInventory inventory = new RoomInventory();

        // ===== UC4: Search Service =====
        RoomSearchService searchService = new RoomSearchService();

        // Perform search (READ-ONLY)
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("\nApplication Terminated.");
    }
}
