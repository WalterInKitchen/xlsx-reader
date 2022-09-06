package top.walterinkitchen.xlsxreader.xlsx;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Xml based shared string
 *
 * @author walter
 * @since 1.0
 */
public class XmlSharedString implements SharedString {
    private final File xmlFile;
    private XMLEventReader eventReader = null;
    private final List<String> cached = new ArrayList<>();

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
        if (index >= this.cached.size()) {
            cacheStringToIndex(index);
        }
        if (index >= this.cached.size()) {
            return null;
        }
        return this.cached.get(index);
    }

    private void cacheStringToIndex(int readTo) {
        int curIdx = this.cached.size();
        while (this.eventReader.hasNext() && curIdx <= readTo) {
            try {
                XMLEvent evt = this.eventReader.nextEvent();
                if (isStartOfEvent(evt, "si")) {
                    readValueInSi();
                }
            } catch (XMLStreamException exc) {
                throw new RuntimeException(exc);
            }
        }
    }

    private void readValueInSi() throws XMLStreamException {
        while (this.eventReader.hasNext()) {
            XMLEvent evt = this.eventReader.nextEvent();
            if (isEndOfEvent(evt, "si")) {
                return;
            }
            if (isStartOfEvent(evt, "t")) {
                readValueInT();
                readToTillEnd("t");
                break;
            }
            if (isStartOfEvent(evt, "r")) {
                readValueInR();
                readToTillEnd("r");
                break;
            }
        }
        readToTillEnd("si");
    }

    private void readValueInR() throws XMLStreamException {
        while (this.eventReader.hasNext()) {
            XMLEvent evt = this.eventReader.nextEvent();
            if (isStartOfEvent(evt, "t")) {
                readValueInT();
                readToTillEnd("r");
                break;
            }
            if (isEndOfEvent(evt, "r")) {
                break;
            }
        }
    }

    private void readValueInT() throws XMLStreamException {
        if (!this.eventReader.hasNext()) {
            return;
        }
        XMLEvent next = this.eventReader.nextEvent();
        if (!next.isCharacters()) {
            return;
        }
        String str = next.asCharacters().getData();
        this.cached.add(str);
    }

    private void readToTillEnd(String type) throws XMLStreamException {
        while (this.eventReader.hasNext()) {
            XMLEvent evt = this.eventReader.nextEvent();
            if (isEndOfEvent(evt, type)) {
                break;
            }
        }
    }

    private boolean isStartOfEvent(XMLEvent evt, String type) {
        if (!evt.isStartElement()) {
            return false;
        }
        StartElement startElement = evt.asStartElement();
        return startElement.getName().getLocalPart().equals(type);
    }

    private boolean isEndOfEvent(XMLEvent evt, String type) {
        if (!evt.isEndElement()) {
            return false;
        }
        EndElement endElement = evt.asEndElement();
        return endElement.getName().getLocalPart().equals(type);
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
