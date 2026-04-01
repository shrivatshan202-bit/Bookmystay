/**
 * Book My Stay Application
 *
 * Use Case 7: Add-On Service Selection
 *
 * This version allows attaching optional services to reservations
 * without modifying booking or inventory logic.
 *
 * @author YourName
 * @version 7.1
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
}

// ===== Add-On Service =====
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}

// ===== Add-On Service Manager (UC7) =====
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added: " + service.getServiceName() +
                " for Reservation ID: " + reservationId);
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        System.out.println("\nServices for Reservation ID: " + reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        double totalCost = 0;

        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " : ₹" + s.getPrice());
            totalCost += s.getPrice();
        }

        System.out.println("Total Add-On Cost: ₹" + totalCost);
    }
}

// ===== Main Class =====
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Welcome to Book My Stay App");
        System.out.println("   Hotel Booking System v7.1");
        System.out.println("=====================================\n");

        // Simulated confirmed reservations (from UC6)
        Reservation r1 = new Reservation("RES-101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES-102", "Bob", "Double Room");

        // Add-On Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Guest selects services
        serviceManager.addService(r1.getReservationId(), new AddOnService("Breakfast", 500));
        serviceManager.addService(r1.getReservationId(), new AddOnService("WiFi", 200));

        serviceManager.addService(r2.getReservationId(), new AddOnService("Spa", 1500));

        // Display services + cost
        serviceManager.displayServices(r1.getReservationId());
        serviceManager.displayServices(r2.getReservationId());

        System.out.println("\nNote: Booking & Inventory remain unchanged.");
        System.out.println("Application Terminated.");
    }
}