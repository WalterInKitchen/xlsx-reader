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
            cacheStringAtIndex(index);
        }
        if (index >= this.cached.size()) {
            return null;
        }
        return this.cached.get(index);
    }

    private void cacheStringAtIndex(int readTo) {
        while (this.eventReader.hasNext() && this.cached.size() <= readTo) {
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
                readTillEnd("t");
                break;
            }
            if (isStartOfEvent(evt, "r")) {
                readAllRValues(evt, "si");
                return;
            }
        }
        readTillEnd("si");
    }

    private void readAllRValues(XMLEvent evt, String end) throws XMLStreamException {
        StringBuilder builder = new StringBuilder();
        while (this.eventReader.hasNext()) {
            if (isEndOfEvent(evt, end)) {
                break;
            }
            String val = readTInR(end);
            if (val == null) {
                break;
            }
            builder.append(val);
        }
        if (builder.length() > 0) {
            this.cached.add(builder.toString());
        }
    }

    private String readTInR(String end) throws XMLStreamException {
        String res = null;
        while (this.eventReader.hasNext()) {
            XMLEvent evt = this.eventReader.nextEvent();
            if (isEndOfEvent(evt, end)) {
                break;
            }
            if (isStartOfEvent(evt, "t")) {
                res = readStringValueInT();
                readTillEnd("r");
                break;
            }
            if (isEndOfEvent(evt, "r")) {
                break;
            }
        }
        return res;
    }

    private void readValueInT() throws XMLStreamException {
        String val = readStringValueInT();
        if (val == null) {
            return;
        }
        this.cached.add(val);
    }

    private String readStringValueInT() throws XMLStreamException {
        if (!this.eventReader.hasNext()) {
            return null;
        }
        XMLEvent next = this.eventReader.nextEvent();
        if (!next.isCharacters()) {
            return null;
        }
        return next.asCharacters().getData();
    }

    private void readTillEnd(String type) throws XMLStreamException {
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
