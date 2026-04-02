import java.util.*;

// Inventory
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public void increment(String type) {
        availability.put(type, availability.get(type) + 1);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }
}

// Cancellation Service
class CancellationService {

    private Map<String, String> reservationRoomTypeMap;
    private Stack<String> rollbackStack;

    public CancellationService() {
        reservationRoomTypeMap = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    // Register confirmed booking
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    // Cancel booking
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation. Booking not found.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        // Restore inventory
        inventory.increment(roomType);

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Remove booking
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    // Show rollback history (LIFO)
    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");

        for (int i = rollbackStack.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + rollbackStack.get(i));
        }
    }
}

// MAIN CLASS
public class Bookingapp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        // Simulate confirmed booking
        String reservationId = "Single-1";
        service.registerBooking(reservationId, "Single");

        System.out.println("Booking Cancellation");

        // Cancel booking
        service.cancelBooking(reservationId, inventory);

        // Show rollback
        service.showRollbackHistory();

        // Show updated inventory
        System.out.println("\nUpdated Single Room Availability: "
                + inventory.getAvailable("Single"));
    }
}