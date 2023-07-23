import java.io.Serializable;

public class Property implements Serializable {
   private int quantity;
   private final String nameProperty;

    public Property(int quantity, String nameProperty) {
        this.quantity = quantity;
        this.nameProperty = nameProperty;
    }

    public int getCount() {
        return quantity;
    }

    public String getNameProperty() {
        return nameProperty;
    }

    public void setCount(int count) {
        this.quantity = count;
    }

}
