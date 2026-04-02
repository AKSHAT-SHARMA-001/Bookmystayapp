import java.util.*;

// Reservation
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Queue
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void add(Reservation r) {
        queue.offer(r);
    }

    public Reservation get() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 3);
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }

    public boolean allocate(String type) {
        if (availability.get(type) > 0) {
            availability.put(type, availability.get(type) - 1);
            return true;
        }
        return false;
    }

    public Map<String, Integer> getAll() {
        return availability;
    }
}

// Allocation Service
class RoomAllocationService {
    private Map<String, Integer> counters = new HashMap<>();

    public String allocateRoom(Reservation r, RoomInventory inventory) {
        if (!inventory.allocate(r.roomType)) {
            return null;
        }

        int count = counters.getOrDefault(r.roomType, 0) + 1;
        counters.put(r.roomType, count);

        return r.roomType + "-" + count;
    }
}

// Concurrent Processor
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue queue;
    private RoomInventory inventory;
    private RoomAllocationService service;

    public ConcurrentBookingProcessor(
            BookingRequestQueue queue,
            RoomInventory inventory,
            RoomAllocationService service) {

        this.queue = queue;
        this.inventory = inventory;
        this.service = service;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // Synchronize queue access
            synchronized (queue) {
                if (!queue.hasRequests()) break;
                r = queue.get();
            }

            // Synchronize allocation
            synchronized (inventory) {
                String roomId = service.allocateRoom(r, inventory);

                if (roomId != null) {
                    System.out.println("Booking confirmed for Guest: "
                            + r.guestName + ", Room ID: " + roomId);
                } else {
                    System.out.println("Booking failed for " + r.guestName);
                }
            }
        }
    }
}

// MAIN CLASS
public class Bookingapp {

    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Add requests
        queue.add(new Reservation("Abhi", "Single"));
        queue.add(new Reservation("Vanmathi", "Double"));
        queue.add(new Reservation("Kural", "Suite"));
        queue.add(new Reservation("Subha", "Single"));

        System.out.println("Concurrent Booking Simulation\n");

        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(queue, inventory, service));

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(queue, inventory, service));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        // Final inventory
        System.out.println("\nRemaining Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}