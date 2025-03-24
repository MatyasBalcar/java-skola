import java.util.List;

public class Receipt {
    public List<Item> items;
    public String corp_name;
    public String itin;

    public Receipt(Item[] items, String corp_name, String itin) {
        this.items = List.of(items);
        this.corp_name = corp_name;
        this.itin = itin;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = List.of(items);
    }

    public String getCorp_name() {
        return corp_name;
    }

    public void setCorp_name(String corp_name) {
        this.corp_name = corp_name;
    }

    public String getItin() {
        return itin;
    }

    public void setItin(String itin) {
        this.itin = itin;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Item item : items) {
            totalPrice += item.getPrice() * item.getAmount();
        }
        return totalPrice;
    }

    public void printReceipt() {
        System.out.printf("Corp Name: %s\n", corp_name);
        System.out.printf("Itin: %s\n", itin);
        System.out.printf("Total: %s\n", getTotalPrice());
        for (Item item : items) {
            System.out.printf("Item: %s\n", item);
        }
        System.out.println();
    }


}
