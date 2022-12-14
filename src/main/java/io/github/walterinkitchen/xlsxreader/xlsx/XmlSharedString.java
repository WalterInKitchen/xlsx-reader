package io.github.walterinkitchen.xlsxreader.xlsx;

import io.github.walterinkitchen.xlsxreader.ReaderException;
import org.apache.commons.io.IOUtils;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Xml based shared string
 *
 * @author walter
 * @since 1.0
 */
public class XmlSharedString implements SharedString {
    private final InputStream xmlIns;
    private XMLEventReader eventReader = null;
    private final List<String> cached = new ArrayList<>();

    /**
     * constructor
     *
     * @param ins ins
     */
    XmlSharedString(InputStream ins) {
        this.xmlIns = ins;
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
                throw new ReaderException("read xml failed", exc);
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
                boolean isEnd = readValueInT();
                if (!isEnd) {
                    readTillEnd("t");
                }
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
                EleResult ele = readStringValueInT();
                res = ele.value;
                readTillEnd("r");
                break;
            }
            if (isEndOfEvent(evt, "r")) {
                break;
            }
        }
        return res;
    }

    private boolean readValueInT() throws XMLStreamException {
        EleResult res = readStringValueInT();
        this.cached.add(res.value);
        return res.end;
    }

    private EleResult readStringValueInT() throws XMLStreamException {
        if (!this.eventReader.hasNext()) {
            return new EleResult(null, false);
        }
        XMLEvent next = this.eventReader.nextEvent();
        boolean isEnd = next.isEndElement();
        if (!next.isCharacters()) {
            return new EleResult(null, isEnd);
        }
        String val = next.asCharacters().getData();
        return new EleResult(val, isEnd);
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
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
            this.eventReader = factory.createXMLEventReader(xmlIns);
        } catch (XMLStreamException exc) {
            throw new ReaderException("create event reader failed", exc);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.eventReader != null) {
            try {
                this.eventReader.close();
            } catch (XMLStreamException exc) {
                throw new RuntimeException(exc);
            }
        }
        IOUtils.closeQuietly(this.xmlIns);
    }

    private static class EleResult {
        private final String value;
        private final boolean end;

        private EleResult(String value, boolean end) {
            this.value = value;
            this.end = end;
        }
    }
}
