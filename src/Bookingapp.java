import java.util.*;

// Reservation
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

// Inventory
class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation next() {
        return queue.poll();
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }
}

// Allocation Service
class RoomAllocationService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> assignedRoomsByType = new HashMap<>();
    private Map<String, Integer> counters = new HashMap<>();

    public void allocateRoom(Reservation r, RoomInventory inventory) {

        String type = r.getRoomType();

        if (inventory.getAvailable(type) <= 0) {
            System.out.println("No rooms available for " + r.getGuestName());
            return;
        }

        String roomId = generateRoomId(type);

        allocatedRoomIds.add(roomId);
        assignedRoomsByType
                .computeIfAbsent(type, k -> new HashSet<>())
                .add(roomId);

        inventory.decrement(type);

        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName()
                + ", Room ID: "
                + roomId);
    }

    private String generateRoomId(String type) {
        int count = counters.getOrDefault(type, 0) + 1;
        counters.put(type, count);
        return type + "-" + count;
    }
}

// MAIN CLASS
public class Bookingapp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService service = new RoomAllocationService();

        // Requests (same as PDF page 7 style)
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        System.out.println("Room Allocation Processing");

        while (queue.hasNext()) {
            service.allocateRoom(queue.next(), inventory);
        }
    }
}