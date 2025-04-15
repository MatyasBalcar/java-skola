import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StAXReceiptReaderWriter {
    public Receipt loadReceipt(InputStream input) throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(input);
        //promene
        List<Item> items = new ArrayList<>();
        String corpName = null;
        String itin = null;
        String currentElement = "";
        String currentItemName = null;
        int currentItemPrice = 0;
        int currentItemAmount = 0;
        //extremni cyklus switche
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                currentElement = event.asStartElement().getName().getLocalPart();
                if (currentElement.equals("item")) {
                    currentItemAmount = Integer.parseInt(event.asStartElement().getAttributeByName(QName.valueOf("amount")).getValue());
                    currentItemPrice = Integer.parseInt(event.asStartElement().getAttributeByName(QName.valueOf("unitPrice")).getValue());
                }
            } else if (event.isCharacters()) {
                String data = event.asCharacters().getData().trim();
                if (!data.isEmpty()) {
                    switch (currentElement) {
                        case "name": corpName = data; break;
                        case "itin": itin = data; break;
                        case "item": currentItemName = data; break;
                    }
                }
            } else if (event.isEndElement()) {
                if (event.asEndElement().getName().getLocalPart().equals("item")) {
                    items.add(new Item(currentItemName, currentItemPrice, currentItemAmount));
                }
            }
        }
        return new Receipt(items.toArray(new Item[0]), corpName, itin);
    }

    public void storeReceipt(OutputStream output, Receipt receipt) throws Exception {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(output, "UTF-8");

        writer.writeStartDocument("UTF-8", "1.0");
        writer.writeStartElement("receipt");
        writer.writeAttribute("total", String.valueOf(receipt.getTotalPrice()));

        writer.writeStartElement("name");
        writer.writeCharacters(receipt.getCorp_name());
        writer.writeEndElement();

        writer.writeStartElement("itin");
        writer.writeCharacters(receipt.getItin());
        writer.writeEndElement();

        writer.writeStartElement("items");
        for (Item item : receipt.getItems()) {
            writer.writeStartElement("item");
            writer.writeAttribute("amount", String.valueOf(item.getAmount()));
            writer.writeAttribute("unitPrice", String.valueOf(item.getPrice()));
            writer.writeCharacters(item.getName());
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }
}

