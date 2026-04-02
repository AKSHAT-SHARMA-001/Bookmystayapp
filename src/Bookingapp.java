/**
 * Use Case 1: Application Entry & Welcome Message
 *
 * Description:
 * This class represents the entry point of the Hotel Booking Management System.
 *
 * At this stage, the application:
 * - Starts execution from the main() method
 * - Displays a welcome message to the user
 * - Confirms system startup
 *
 * No business logic or data structures are implemented here.
 *
 * @author Developer
 * @version 1.0
 */
public class Bookingapp {

    /**
     * Application entry point.
     * This is the first method executed when the program starts.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        // Welcome message
        System.out.println("=== Book My Stay App ===");

        // Application info
        System.out.println("Hotel Booking Management System v1.0");

        // Startup confirmation
        System.out.println("Application started successfully.");
    }
}