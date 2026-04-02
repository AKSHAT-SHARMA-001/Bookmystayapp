import java.util.HashMap;
import java.util.Map;

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public int getAvailableRooms(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

public class Bookingapp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        System.out.println("Single Room Available: " + inventory.getAvailableRooms("Single"));
        System.out.println("Double Room Available: " + inventory.getAvailableRooms("Double"));
        System.out.println("Suite Room Available: " + inventory.getAvailableRooms("Suite"));
    }
}