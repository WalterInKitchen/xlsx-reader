package top.walterinkitchen.xlsxreader.xlsx;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Xml based shared string
 *
 * @author walter
 * @since 1.0
 */
public class XmlSharedString implements SharedString {
    private final File xmlFile;
    private XMLEventReader eventReader = null;
    private int cachedIndex = 0;
    private final Map<Integer, String> cached = new HashMap<>();

    /**
     * constructor
     *
     * @param xmlFile xml file
     */
    XmlSharedString(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    @Override
    public String getByIndex(int index) {
        this.initIfNecessary();
        if (index < 0) {
            return null;
        }
        if (index >= cachedIndex) {
            cacheStringToIndex(index);
        }
        return this.cached.get(index);
    }

    private void cacheStringToIndex(int index) {
        int curIdx = this.cachedIndex;
        while (this.eventReader.hasNext() && curIdx <= index) {
            try {
                XMLEvent evt = this.eventReader.nextEvent();
                if (!evt.isStartElement()) {
                    continue;
                }
                StartElement startElement = evt.asStartElement();
                if (startElement.getName().getLocalPart().equals("t")) {
                    XMLEvent next = this.eventReader.nextEvent();
                    String str = next.asCharacters().getData();
                    this.cached.put(curIdx++, str);
                }
            } catch (XMLStreamException exc) {
                throw new RuntimeException(exc);
            }
        }
        this.cachedIndex = Math.max(curIdx, this.cachedIndex);
    }

    private void initIfNecessary() {
        if (this.eventReader != null) {
            return;
        }
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            this.eventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlFile));
        } catch (XMLStreamException | FileNotFoundException exc) {
            throw new RuntimeException(exc);
        }
    }
}
