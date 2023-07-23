import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private static int ID;
    private final RoomType roomType;
    private List<Property> properties;
    private final int roomID;

    public Room(RoomType roomType) {
        this.roomType = roomType;
        roomID = ID++;
    }

    public int getRoomID() {
        return roomID;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public List<Property> getProperties() {
        return properties;
    }
}

