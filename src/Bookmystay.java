/**
 * Book My Stay Application
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * This version processes booking requests from a queue, assigns unique room IDs,
 * updates inventory, and prevents double-booking using Set and HashMap.
 *
 * @author YourName
 * @version 6.1
 */

import java.util.*;

// ===== Room Domain =====
abstract class Room {
    private String roomType;

    public Room(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }
}

// ===== Concrete Rooms =====
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room");
    }
}

// ===== Inventory Service =====
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 2);
        availabilityMap.put("Double Room", 1);
        availabilityMap.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) {
        availabilityMap.put(roomType, getAvailability(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\n===== CURRENT INVENTORY =====");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ===== Reservation =====
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
}

// ===== Booking Queue =====
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// ===== Booking Service (CORE UC6) =====
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type → allocated room IDs
    private Map<String, Set<String>> roomAllocationMap = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Process queue
    public void processBookings(BookingRequestQueue queue) {

        System.out.println("\n===== PROCESSING BOOKINGS =====\n");

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();
            String roomType = request.getRoomType();

            System.out.println("Processing request for: " + request.getGuestName());

            // Check availability
            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness (extra safety)
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                // Store ID in global set
                allocatedRoomIds.add(roomId);

                // Map room type → allocated IDs
                roomAllocationMap
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                // Update inventory (IMPORTANT)
                inventory.decrementAvailability(roomType);

                // Confirm booking
                System.out.println("✅ Booking Confirmed!");
                System.out.println("Guest: " + request.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);
                System.out.println("--------------------------------------");

            } else {
                System.out.println("❌ Booking Failed (No Availability)");
                System.out.println("Guest: " + request.getGuestName());
                System.out.println("--------------------------------------");
            }
        }
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
    }

    // Display allocations
    public void displayAllocations() {
        System.out.println("\n===== ROOM ALLOCATIONS =====");

        for (Map.Entry<String, Set<String>> entry : roomAllocationMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ===== Main Class =====
public class Bookmystay {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v6.1");
        System.out.println("=====================================");

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Add requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Double Room"));

        // Booking Service
        BookingService bookingService = new BookingService(inventory);

        // Process bookings
        bookingService.processBookings(queue);

        // Show allocations
        bookingService.displayAllocations();

        // Show remaining inventory
        inventory.displayInventory();

        System.out.println("\nApplication Terminated.");
    }
}