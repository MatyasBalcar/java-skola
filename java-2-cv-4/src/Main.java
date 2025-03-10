import java.util.ArrayList;
import java.util.List;

//*
// KONFIGURACNI SQL PRIKAZY
//
//`CREATE TABLE receipt (
//        id SERIAL PRIMARY KEY,
//        name TEXT NOT NULL,
//        itin TEXT,
//        total INT NOT NULL
//);
//
//        CREATE TABLE item (
//        id SERIAL PRIMARY KEY,
//        receipt_id INT REFERENCES receipt(id) ON DELETE CASCADE,
//        description TEXT NOT NULL,
//        amount INT NOT NULL,
//        unit_price INT NOT NULL
//);
//
//
// *//
public class Main {
    public static void main(String[] args) {
        Receipt receipt = new Receipt();
        receipt.setName("ACME corp.");
        receipt.setItin("CZ12345678");
        receipt.setTotal(1642);

        List<Item> items = new ArrayList<>();
        items.add(new Item(1, receipt.getId(), "Nitroglycerin", 50, 24));
        items.add(new Item(2, receipt.getId(), "Jet Propelled Pogo Stick", 4, 100));
        items.add(new Item(3, receipt.getId(), "Hen Grenade", 1, 42));
        receipt.setItems(items);

        ReceiptDAO dao = new ReceiptDAO();
        dao.createReceipt(receipt);

        Receipt loaded = dao.getReceipt(receipt.getId());
        System.out.println("Načtená účtenka: " + loaded.getName());
        for (Item item : loaded.getItems()) {
            System.out.println(" - " + item.getDescription() + ": " + item.getAmount() + " × " + item.getUnitPrice());
        }
    }
}
