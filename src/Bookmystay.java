/**
 * Book My Stay App
 *
 * Use Case 9: Error Handling & Validation
 *
 * Strengthens reliability by introducing input validation and custom exceptions.
 *
 * @author YourName
 * @version 9.1
 */

import java.util.*;

// ===== Custom Exception =====
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ===== Reservation Model =====
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    public void display() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("--------------------------------------");
    }
}

// ===== Inventory Service =====
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 2);
        availability.put("Double Room", 2);
        availability.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        return availability.get(roomType);
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        int count = availability.get(roomType);
        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
        availability.put(roomType, count - 1);
    }

    private void validateRoomType(String roomType) throws InvalidBookingException {
        if (!availability.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void displayAvailability() {
        System.out.println("\nCurrent Room Availability:");
        availability.forEach((type, count) -> System.out.println(type + " : " + count));
    }
}

// ===== Booking History =====
class BookingHistory {
    private List<Reservation> historyList = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        historyList.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return historyList;
    }
}

// ===== Reporting Service =====
class BookingReportService {
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n===== BOOKING HISTORY =====\n");
        for (Reservation r : reservations) {
            r.display();
        }
    }

    public void generateSummary(List<Reservation> reservations) {
        System.out.println("\n===== BOOKING SUMMARY REPORT =====\n");
        Map<String, Integer> summary = new HashMap<>();
        for (Reservation r : reservations) {
            summary.put(r.getRoomType(), summary.getOrDefault(r.getRoomType(), 0) + 1);
        }
        summary.forEach((type, count) -> System.out.println(type + " -> Total Bookings: " + count));
    }
}

// ===== Main Class =====
public class Bookmystay {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v9.1");
        System.out.println("=====================================\n");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Sample booking inputs
        List<String[]> bookingRequests = Arrays.asList(
                new String[]{"RES-201", "Alice", "Single Room"},
                new String[]{"RES-202", "Bob", "Double Room"},
                new String[]{"RES-203", "Charlie", "Presidential Suite"}, // Invalid type
                new String[]{"RES-204", "David", "Suite Room"},
                new String[]{"RES-205", "Eve", "Suite Room"} // Overbooking
        );

        for (String[] req : bookingRequests) {
            try {
                String resId = req[0];
                String guestName = req[1];
                String roomType = req[2];

                // Validate and allocate
                inventory.allocateRoom(roomType);

                // Create reservation and store
                Reservation reservation = new Reservation(resId, guestName, roomType);
                history.addReservation(reservation);

                System.out.println("Booking Confirmed: " + resId + " for " + guestName + " (" + roomType + ")");
            } catch (InvalidBookingException e) {
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        // Display remaining inventory
        inventory.displayAvailability();

        // Display booking history and summary
        reportService.displayAllBookings(history.getAllReservations());
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nApplication Terminated.");
    }
}