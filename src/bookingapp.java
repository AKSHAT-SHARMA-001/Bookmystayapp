import java.util.*;

// Room class
class Room {
    String type;
    int beds;
    int size;
    double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails(int available) {
        System.out.println(type + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available: " + available);
        System.out.println();
    }
}

// Inventory class
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

// Search service
class RoomSearchService {
    public void searchAvailableRooms(
            RoomInventory inventory,
            Room single,
            Room dbl,
            Room suite) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get("Single") > 0) {
            single.displayDetails(availability.get("Single"));
        }

        if (availability.get("Double") > 0) {
            dbl.displayDetails(availability.get("Double"));
        }

        if (availability.get("Suite") > 0) {
            suite.displayDetails(availability.get("Suite"));
        }
    }
}

// MAIN CLASS (lowercase)
public class bookingapp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Room single = new Room("Single", 1, 250, 1500.0);
        Room dbl = new Room("Double", 2, 400, 2500.0);
        Room suite = new Room("Suite", 3, 750, 5000.0);

        RoomSearchService service = new RoomSearchService();

        service.searchAvailableRooms(inventory, single, dbl, suite);
    }
}