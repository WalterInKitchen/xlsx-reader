package top.walterinkitchen.xlsxreader.xlsx;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Raw row iterator
 *
 * @author walter
 * @since 1.0
 */
public class XmlRawRawRowIterator implements RawRowIterator {
    private final File xmlSheet;
    private RawRow next;
    private XMLEventReader reader;

    /**
     * constructor
     *
     * @param xmlSheet sheet file
     */
    XmlRawRawRowIterator(File xmlSheet) {
        this.xmlSheet = xmlSheet;
        this.next = this.readNextRow();
    }

    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public RawRow next() {
        RawRow res = this.next;
        this.next = this.readNextRow();
        return res;
    }

    private RawRow readNextRow() {
        this.initIfNecessary();
        while (this.reader.hasNext()) {
            try {
                XMLEvent evt = this.reader.nextEvent();
                if (!evt.isStartElement()) {
                    continue;
                }
                if (!isTypeStartEvt(evt, "row")) {
                    continue;
                }
                StartElement startElement = evt.asStartElement();
                Integer rowId = getRowId(startElement);
                if (rowId == null) {
                    continue;
                }
                List<RawCell> cells = readCells(reader);
                return RawRow.builder().id(rowId).cells(cells).build();
            } catch (XMLStreamException exc) {
                throw new RuntimeException(exc);
            }
        }
        return null;
    }

    private List<RawCell> readCells(XMLEventReader reader) {
        List<RawCell> result = new ArrayList<>();
        while (reader.hasNext()) {
            try {
                XMLEvent evt = reader.nextEvent();
                if (isTypeEndEvt(evt, "row")) {
                    break;
                }
                if (!isTypeStartEvt(evt, "c")) {
                    continue;
                }
                StartElement startElement = evt.asStartElement();
                RawCell cell = readCell(startElement, reader);
                if (cell == null) {
                    continue;
                }
                result.add(cell);
            } catch (XMLStreamException exc) {
                throw new RuntimeException(exc);
            }
        }
        return result;
    }

    private RawCell readCell(StartElement cellNode, XMLEventReader reader) {
        Attribute rAttr = cellNode.getAttributeByName(new QName("r"));
        Attribute tAttr = cellNode.getAttributeByName(new QName("t"));
        if (rAttr == null) {
            return null;
        }
        String valType = tAttr == null ? "" : tAttr.getValue();
        String value = readOneCellValue(reader);
        return RawCell.builder().column(rAttr.getValue())
                .valueType(valType)
                .value(value)
                .build();
    }

    private String readOneCellValue(XMLEventReader reader) {
        while (reader.hasNext()) {
            try {
                XMLEvent evt = reader.nextEvent();
                if (isTypeEndEvt(evt, "c") || isTypeEndEvt(evt, "row")) {
                    return null;
                }
                if (!isTypeStartEvt(evt, "v") && !isTypeStartEvt(evt, "t")) {
                    continue;
                }
                XMLEvent nextEvt = reader.nextEvent();
                return nextEvt.asCharacters().getData();
            } catch (XMLStreamException exc) {
                throw new RuntimeException(exc);
            }
        }
        return null;
    }

    private boolean isTypeStartEvt(XMLEvent evt, String type) {
        if (!evt.isStartElement()) {
            return false;
        }
        StartElement startEvt = evt.asStartElement();
        String typeName = startEvt.getName().getLocalPart();
        return typeName.equals(type);
    }

    private boolean isTypeEndEvt(XMLEvent evt, String type) {
        if (!evt.isEndElement()) {
            return false;
        }
        EndElement endElement = evt.asEndElement();
        String endName = endElement.getName().getLocalPart();
        return endName.equals(type);
    }

    private Integer getRowId(StartElement startElement) {
        Attribute rowIdx = startElement.getAttributeByName(new QName("r"));
        if (rowIdx == null) {
            return null;
        }
        String value = rowIdx.getValue();
        return Integer.parseInt(value);
    }

    private void initIfNecessary() {
        if (this.reader != null) {
            return;
        }

        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            this.reader = factory.createXMLEventReader(new FileInputStream(this.xmlSheet));
        } catch (XMLStreamException | FileNotFoundException exc) {
            throw new RuntimeException(exc);
        }
    }
}
