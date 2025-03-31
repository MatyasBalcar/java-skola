import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SAXReceiptReaderWriter implements ReceiptReaderWriter {
    @Override
    public Receipt loadReceipt(InputStream input) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        //promene
        List<Item> items = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        String[] corpName = new String[1];
        String[] itin = new String[1];
        String[] currentItemName = new String[1];
        int[] currentItemPrice = new int[1];
        int[] currentItemAmount = new int[1];

        DefaultHandler handler = new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                currentValue.setLength(0);
                if (qName.equals("item")) {
                    currentItemAmount[0] = Integer.parseInt(attributes.getValue("amount"));
                    currentItemPrice[0] = Integer.parseInt(attributes.getValue("unitPrice"));
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                switch (qName) {
                    case "name" -> corpName[0] = currentValue.toString();
                    case "itin" -> itin[0] = currentValue.toString();
                    case "item" -> {
                        currentItemName[0] = currentValue.toString().trim();
                        items.add(new Item(currentItemName[0], currentItemPrice[0], currentItemAmount[0]));
                    }
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                currentValue.append(ch, start, length);
            }
        };

        saxParser.parse(input, handler);
        return new Receipt(items.toArray(new Item[0]), corpName[0], itin[0]);
    }

    @Override
    public void storeReceipt(OutputStream output, Receipt receipt) throws Exception {
        throw new UnsupportedOperationException("SAX API nepodporuje zápis XML.");
    }
}
