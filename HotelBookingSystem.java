import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HotelBookingSystem {
    private final List<Customer> CUSTOMERS = new ArrayList<>();
    private final List<Booking> BOOKINGS = new ArrayList<>();
    private final List<Room> ROOMS = new ArrayList<>();


    void addRoom(RoomType r) {
        Room room = new Room(r);
        ROOMS.add(room);
    }

    void addCustomer(String name, String email) {
        Customer customer = new Customer(name, email);
        CUSTOMERS.add(customer);
    }

    public void bookRoom(RoomType roomType, String customerName, LocalDate startDate, LocalDate endDate, int choice) {
        Room room = findAvailableRoomByType(roomType, startDate, endDate);
        Customer customer = findCustomerByName(customerName);

        if (room == null || customer == null) {
            System.out.println("Invalid room or customer details.");
            return;
        }

        if (!isAvailable(room, startDate, endDate)) {
            System.out.println("Room is not available for the selected period.");
            return;
        }


        Booking booking = new Booking(room, customer, startDate, endDate);
        BOOKINGS.add(booking);
        if (choice == 1) {
            String fileName = customer.getName() + "_" + room.getRoomID() + "_bill.txt";
            saveBillToFile(fileName, booking);
            System.out.println("The bill has been saved as " + fileName);
        } else {
            System.out.println("Your Bill ");
            System.out.println(printBill(booking));
            System.out.println("Room booking successfully!" + "\n");
        }
    }

    private Customer findCustomerByName(String customerName) {
        for (Customer c : CUSTOMERS) {
            if (c.getName().equals(customerName)) {
                return c;
            }
        }
        return null;
    }

    public boolean isAvailable(Room room, LocalDate startDate, LocalDate endDate) {
        for (Booking booking : BOOKINGS) {
            if (booking.getRoom().equals(room) && !endDate.isBefore(booking.getStartDate()) && !startDate.isAfter(booking.getEndDate())) {
                return false;
            }
        }
        return true;
    }

    private Room findAvailableRoomByType(RoomType roomType, LocalDate startDate, LocalDate endDate) {
        for (Room room : ROOMS) {
            if (room.getRoomType().equals(roomType) && isAvailable(room, startDate, endDate)) {
                return room;
            }
        }
        return null;
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    String printBill(Booking booking) {
        return "Room ID: " + booking.getRoom().getRoomID() + "\n"
                + "Room Type: " + booking.getRoom().getRoomType() + "\n"
                + "Customer Name: " + booking.getCustomer().getName() + "\n"
                + "Customer Email: " + booking.getCustomer().getEmail() + "\n"
                + "Booking Period: " + formatDate(booking.getStartDate()) + " to " + formatDate(booking.getEndDate()) + "\n"
                + "Taxes (20%): $" + (booking.getPrice(booking.getRoom()) * booking.getTaxPercentage()) + "\n"
                + "Service Fee (10%): $" + (booking.getPrice(booking.getRoom()) * booking.getServiceFeePercentage()) + "\n"
                + "Total Amount: $" + booking.getTotalAmount(booking.getRoom()) + "\n";
    }

    private void saveBillToFile(String fileName, Booking booking) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println(printBill(booking));
            System.out.println("\n" + printBill(booking));
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while saving the bill: " + e.getMessage());
        }
    }

    public void saveState(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(ROOMS);
            oos.writeObject(CUSTOMERS);
            oos.writeObject(BOOKINGS);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while saving the system state: " + e.getMessage());
        }

    }

    public void loadState(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Room> loadedRooms = (List<Room>) ois.readObject();
            List<Customer> loadedCustomers = (List<Customer>) ois.readObject();
            List<Booking> loadedBookings = (List<Booking>) ois.readObject();

            this.ROOMS.addAll(loadedRooms);

            this.CUSTOMERS.addAll(loadedCustomers);

            this.BOOKINGS.addAll(loadedBookings);


            System.out.println("System state loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error occurred while loading the system state: " + e.getMessage());
        }

    }
}