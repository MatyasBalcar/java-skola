import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class DOMReceiptReaderWriterTest {

    @Test
    void testLoadReceipt() throws Exception {
        /*
        * Metoda na test read
        * */
        ByteArrayInputStream input = getByteArrayInputStream();
        DOMReceiptReaderWriter readerWriter = new DOMReceiptReaderWriter();
        Receipt receipt = readerWriter.loadReceipt(input);

        assertEquals("Test Corp", receipt.getCorp_name());
        assertEquals("123456789", receipt.getItin());
        assertEquals("Item1", receipt.getItems().getFirst().getName());
        assertEquals(100, receipt.getItems().getFirst().getPrice());
        assertEquals(2, receipt.getItems().getFirst().getAmount());
    }

    private static ByteArrayInputStream getByteArrayInputStream() {
        /*
        * Pomocna metoda na data
        * */
        String xml = """
                    <receipt total="500">
                        <name>Test Corp</name>
                        <itin>123456789</itin>
                        <items>
                            <item amount="2" unitPrice="100">Item1</item>
                            <item amount="3" unitPrice="100">Item2</item>
                        </items>
                    </receipt>
                """;
        return new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void testStoreReceipt() throws Exception {
        /*
        * Metoda na test store
        * */
        Item[] items = new Item[]{
                new Item("Item1", 100, 2),
                new Item("Item2", 100, 3)
        };
        Receipt receipt = new Receipt(items, "Test Corp", "123456789");
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        DOMReceiptReaderWriter readerWriter = new DOMReceiptReaderWriter();
        readerWriter.storeReceipt(output, receipt);

        String xmlOutput = output.toString(StandardCharsets.UTF_8);
        System.out.printf("Receipt: %s\n", xmlOutput);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlOutput.getBytes(StandardCharsets.UTF_8)));

        assertEquals("receipt", doc.getDocumentElement().getNodeName());
        assertEquals("Test Corp", doc.getElementsByTagName("name").item(0).getTextContent());
        assertEquals("123456789", doc.getElementsByTagName("itin").item(0).getTextContent());
        assertEquals(2, doc.getElementsByTagName("item").getLength());
    }
}
