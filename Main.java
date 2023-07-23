import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final HotelBookingSystem bookingSystem = new HotelBookingSystem();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayOptions();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addRoom();
                case 2 -> addCustomer();
                case 3 -> bookRoom();
                case 4 -> saveSystemState();
                case 5 -> loadSystemState();
                case 6 -> running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Exiting the system.");
    }

    private static void displayOptions() {
        System.out.println("========== Hotel Booking System ==========");
        System.out.println("1. Add a room");
        System.out.println("2. Add a customer");
        System.out.println("3. Book a room for a customer");
        System.out.println("4. Save system state");
        System.out.println("5. Load system state");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }
private static RoomType checkRoomType(){
        RoomType roomType;
       while (true) {
           System.out.println("Enter the room type (SINGLE_ROOM, DOUBLE_ROOM, DELUXE_ROOM): ");
           String input = scanner.nextLine().trim().toUpperCase();

           try {
                roomType = RoomType.valueOf(input);
               break;
           } catch (IllegalArgumentException e) {
               System.out.println("Invalid room type. Please try again.");
           }

       }
       return roomType;
   }

    private static void addRoom() {
        bookingSystem.addRoom(checkRoomType());
        System.out.println("Room added successfully");
    }

        private static void addCustomer () {
            System.out.print("Enter customer name: ");
            String name = scanner.nextLine();
            System.out.print("Enter customer email: ");
            String email = scanner.nextLine();
            bookingSystem.addCustomer(name, email);
            System.out.println("Customer added successfully.");

        }

        private static void bookRoom () {
            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine();
            RoomType roomType = checkRoomType();
            System.out.print("Enter start date (yyyy-MM-dd): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter end date (yyyy-MM-dd): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());
            System.out.println("Save room bill in file system?");
            System.out.println("1: Yes");
            System.out.println("2: No");
            int choice = scanner.nextInt();
            bookingSystem.bookRoom(roomType, customerName, startDate, endDate, choice);

        }

        private static void saveSystemState () {
            System.out.print("Enter the full path of the file where you want to save the system state: ");
            String filePath = scanner.nextLine();
            bookingSystem.saveState(filePath);
        }

        private static void loadSystemState () {
            System.out.print("Enter the full path of the file from which you want to load the system state: ");
            String filePath = scanner.nextLine();
            bookingSystem.loadState(filePath);
        }

}