import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StAXReceiptReaderWriterTest {
    @Test
    public void testLoadReceipt() throws Exception {
        Receipt receipt = getReceipt();

        assertNotNull(receipt);
        assertEquals("ACME corp.", receipt.getCorp_name());
        assertEquals("CZ12345678", receipt.getItin());

        List<Item> items = receipt.getItems();
        assertEquals(3, items.size());

        assertEquals("Nitroglycerin", items.getFirst().getName());
        assertEquals(24, items.get(0).getPrice());
        assertEquals(50, items.get(0).getAmount());

        assertEquals("Jet Propelled Pogo Stick", items.get(1).getName());
        assertEquals(100, items.get(1).getPrice());
        assertEquals(4, items.get(1).getAmount());

        assertEquals("Hen Grenade", items.get(2).getName());
        assertEquals(42, items.get(2).getPrice());
        assertEquals(1, items.get(2).getAmount());
        receipt.printReceipt();
    }

    private static Receipt getReceipt() throws Exception {
        String xml = """
                <?xml version="1.0" ?>
                <receipt total="1642">
                    <name>ACME corp.</name>
                    <itin>CZ12345678</itin>
                    <items>
                        <item amount="50" unitPrice="24">Nitroglycerin</item>
                        <item amount="4" unitPrice="100">Jet Propelled Pogo Stick</item>
                        <item amount="1" unitPrice="42">Hen Grenade</item>
                    </items>
                </receipt>
                """;

        InputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        StAXReceiptReaderWriter readerWriter = new StAXReceiptReaderWriter();
        return readerWriter.loadReceipt(input);
    }


}
