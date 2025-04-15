import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DOMReceiptReaderWriter implements ReceiptReaderWriter {

    @Override
    public Receipt loadReceipt(InputStream input) throws Exception {
        //buildneme doc - stock kod
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(input);

        //root
        Element root = doc.getDocumentElement();

        //vybere ty dane items (mozna pridat total? ale ja to pocitam tak idk)
        String corpName = root.getElementsByTagName("name").item(0).getTextContent();
        String itin = root.getElementsByTagName("itin").item(0).getTextContent();

        List<Item> items = new ArrayList<>();

        //list itemu
        NodeList itemNodes = root.getElementsByTagName("item");
        for (int i = 0; i < itemNodes.getLength(); i++) {
            Element itemElement = (Element) itemNodes.item(i);
            //simplify - element goated
            String name = itemElement.getTextContent().trim();
            int amount = Integer.parseInt(itemElement.getAttribute("amount"));
            int unitPrice = Integer.parseInt(itemElement.getAttribute("unitPrice"));
            items.add(new Item(name, unitPrice, amount));
        }
        return new Receipt(items.toArray(new Item[0]), corpName, itin);
    }

    @Override
    public void storeReceipt(OutputStream output, Receipt receipt) throws Exception {
        //buildi factory and stuff - stock
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        //vytvori root
        Element root = doc.createElement("receipt");
        root.setAttribute("total", String.valueOf(receipt.getTotalPrice()));
        doc.appendChild(root);

        //attributy uctenky
        Element corpName = doc.createElement("name");
        corpName.appendChild(doc.createTextNode(receipt.getCorp_name()));
        root.appendChild(corpName);

        Element itin = doc.createElement("itin");
        itin.appendChild(doc.createTextNode(receipt.getItin()));
        root.appendChild(itin);

        Element itemsElement = doc.createElement("items");
        root.appendChild(itemsElement);

        //savne jednotlive items
        for (Item item : receipt.getItems()) {
            Element itemElement = doc.createElement("item");
            itemElement.setAttribute("amount", String.valueOf(item.getAmount()));
            itemElement.setAttribute("unitPrice", String.valueOf(item.getPrice()));
            itemElement.appendChild(doc.createTextNode(item.getName()));
            itemsElement.appendChild(itemElement);
        }

        //transformer
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        
        transformer.transform(source, result);
    }
}
