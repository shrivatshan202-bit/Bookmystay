/**
 * Book My Stay Application
 *
 * Use Case 8: Booking History & Reporting
 *
 * This version introduces booking history tracking and reporting
 * without modifying booking or inventory logic.
 *
 * @author YourName
 * @version 8.1
 */

import java.util.*;

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

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("--------------------------------------");
    }
}

// ===== Booking History (UC8) =====
class BookingHistory {

    private List<Reservation> historyList = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        historyList.add(reservation);
    }

    // Get all bookings
    public List<Reservation> getAllReservations() {
        return historyList;
    }
}

// ===== Reporting Service =====
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {

        System.out.println("\n===== BOOKING HISTORY =====\n");

        for (Reservation r : reservations) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {

        System.out.println("\n===== BOOKING SUMMARY REPORT =====\n");

        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : reservations) {
            summary.put(
                    r.getRoomType(),
                    summary.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + " -> Total Bookings: " + entry.getValue());
        }
    }
}

// ===== Main Class =====
public class Bookmystay {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v8.1");
        System.out.println("=====================================\n");

        // Simulated confirmed bookings (from UC6)
        Reservation r1 = new Reservation("RES-101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES-102", "Bob", "Double Room");
        Reservation r3 = new Reservation("RES-103", "Charlie", "Single Room");
        Reservation r4 = new Reservation("RES-104", "David", "Suite Room");

        // ===== UC8: Booking History =====
        BookingHistory history = new BookingHistory();

        // Store bookings (in order)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);
        history.addReservation(r4);

        // ===== Reporting =====
        BookingReportService reportService = new BookingReportService();

        // Display all bookings
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary
        reportService.generateSummary(history.getAllReservations());

        System.out.println("\nNote: Reporting does not modify booking data.");
        System.out.println("Application Terminated.");
    }
}