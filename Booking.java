import java.io.Serializable;
import java.time.LocalDate;

public class Booking implements Serializable {
    private Room room;
    private final Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private final double TAX_PERCENTAGE = 0.2;
    private final double SERVICE_FEE_PERCENTAGE = 0.1;
    private double price;

    public Booking(Room room, Customer customer, LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalAmount(Room room) {
        getPrice(room);
        double taxes = this.price * TAX_PERCENTAGE;
        double serviceFee = (this.price + taxes) * SERVICE_FEE_PERCENTAGE;
        return this.price + taxes + serviceFee;
    }


    public double getPrice(Room room) {
        double  DELUXE_ROOM_PRICE = 55;
        double  DOUBLE_ROOM_PRICE = 35;
        double SINGLE_ROOM_PRICE = 20;
        switch (room.getRoomType()) {
            case SINGLE_ROOM -> this.price = SINGLE_ROOM_PRICE;

            case DOUBLE_ROOM -> this.price = DOUBLE_ROOM_PRICE;
            case DELUXE_ROOM -> this.price = DELUXE_ROOM_PRICE;
        }
        return price;
    }

    public double getTaxPercentage() {
        return TAX_PERCENTAGE;
    }

    public double getServiceFeePercentage() {
        return SERVICE_FEE_PERCENTAGE;
    }
}
