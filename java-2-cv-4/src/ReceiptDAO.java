import java.sql.*;
import java.util.*;

public class ReceiptDAO {

    public void createReceipt(Receipt receipt) {
        String insertReceipt = "INSERT INTO receipt (name, itin, total) VALUES (?, ?, ?) RETURNING id";
        String insertItem = "INSERT INTO item (receipt_id, description, amount, unit_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psReceipt = conn.prepareStatement(insertReceipt);
                 PreparedStatement psItem = conn.prepareStatement(insertItem)) {

                psReceipt.setString(1, receipt.getName());
                psReceipt.setString(2, receipt.getItin());
                psReceipt.setInt(3, receipt.getTotal());

                ResultSet rs = psReceipt.executeQuery();
                if (rs.next()) {
                    int receiptId = rs.getInt(1);
                    receipt.setId(receiptId);

                    for (Item item : receipt.getItems()) {
                        psItem.setInt(1, receiptId);
                        psItem.setString(2, item.getDescription());
                        psItem.setInt(3, item.getAmount());
                        psItem.setInt(4, item.getUnitPrice());
                        psItem.addBatch();
                    }
                    psItem.executeBatch();
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Receipt getReceipt(int id) {
        String getReceipt = "SELECT * FROM receipt WHERE id = ?";
        String getItems = "SELECT * FROM item WHERE receipt_id = ?";

        try (Connection conn = DB.connect();
             PreparedStatement psReceipt = conn.prepareStatement(getReceipt);
             PreparedStatement psItems = conn.prepareStatement(getItems)) {

            psReceipt.setInt(1, id);
            ResultSet rsReceipt = psReceipt.executeQuery();

            if (rsReceipt.next()) {
                Receipt r = new Receipt();
                r.setId(id);
                r.setName(rsReceipt.getString("name"));
                r.setItin(rsReceipt.getString("itin"));
                r.setTotal(rsReceipt.getInt("total"));

                psItems.setInt(1, id);
                ResultSet rsItems = psItems.executeQuery();
                List<Item> items = new ArrayList<>();

                while (rsItems.next()) {
                    Item item = new Item();
                    item.setId(rsItems.getInt("id"));
                    item.setReceiptId(id);
                    item.setDescription(rsItems.getString("description"));
                    item.setAmount(rsItems.getInt("amount"));
                    item.setUnitPrice(rsItems.getInt("unit_price"));
                    items.add(item);
                }

                r.setItems(items);
                return r;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
