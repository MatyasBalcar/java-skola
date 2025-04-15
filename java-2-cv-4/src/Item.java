public class Item {
    private int id;
    private int receiptId;
    private String description;
    private int amount;
    private int unitPrice;

    public Item() {
    }

    public Item(int id, int receiptId, String description, int amount, int unitPrice) {
        this.id = id;
        this.receiptId = receiptId;
        this.description = description;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return amount + " × " + unitPrice + " Kč: " + description;
    }
}
