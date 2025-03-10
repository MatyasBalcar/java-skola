import java.util.List;

public class Receipt {
    private int id;
    private String name;
    private String itin;
    private int total;
    private List<Item> items;

    public Receipt() {
    }

    public Receipt(int id, String name, String itin, int total, List<Item> items) {
        this.id = id;
        this.name = name;
        this.itin = itin;
        this.total = total;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItin() {
        return itin;
    }

    public void setItin(String itin) {
        this.itin = itin;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Receipt #").append(id).append(" - ").append(name)
                .append(" (").append(itin).append("), total: ").append(total).append(" Kč\n");
        if (items != null) {
            for (Item item : items) {
                sb.append("  ").append(item.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
