package top.walterinkitchen.utils.annotaton.xlsx;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * represent an decompressed xlsx file
 *
 * @author walter
 * @since 1.0
 */
public class DecompressedXlsxFile implements XlsxFileContainer {
    private final File decompressed;
    private final Map<String, Integer> sheetNameIdCache = new HashMap<>();
    private boolean initialized = false;

    /**
     * constructr
     *
     * @param decompressed decompressed folder
     */
    DecompressedXlsxFile(File decompressed) {
        this.decompressed = decompressed;
    }

    @Override
    public Optional<File> getSheet(String sheetName) {
        if (!this.initialized) {
            this.loadSheetName();
            this.initialized = true;
        }
        Integer id = this.sheetNameIdCache.getOrDefault(sheetName, -1);
        return this.getSheetById(id);
    }

    @Override
    public Optional<File> getSheetById(int sheetId) {
        String sheet = "sheet" + sheetId + ".xml";
        String fullPath = this.decompressed.getAbsolutePath();
        return getFileInPath(Paths.get(fullPath, "xl", "worksheets", sheet));
    }

    @Override
    public Optional<File> getSharedStrings() {
        String fullPath = this.decompressed.getAbsolutePath();
        return getFileInPath(Paths.get(fullPath, "xl", "sharedStrings.xml"));
    }

    private static Optional<File> getFileInPath(Path fullPath) {
        File file = fullPath.toFile();
        if (!file.exists() || file.isDirectory()) {
            return Optional.empty();
        }
        return Optional.of(file);
    }

    @Override
    public void close() throws IOException {
        FileUtils.deleteQuietly(decompressed);
    }

    private void loadSheetName() {
        String fullPath = this.decompressed.getAbsolutePath();
        Optional<File> workbook = getFileInPath(Paths.get(fullPath, "xl", "workbook.xml"));
        if (!workbook.isPresent()) {
            return;
        }
        Map<String, Integer> cache = loadSheetNameInFile(workbook.get());
        this.sheetNameIdCache.putAll(cache);
    }

    private Map<String, Integer> loadSheetNameInFile(File file) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList workbook = doc.getElementsByTagName("workbook");
            Node item = workbook.item(0);
            return getSheetNameInWorkBook(item);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Integer> getSheetNameInWorkBook(Node wb) {
        NodeList childNodes = wb.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if ("sheets".equals(item.getNodeName())) {
                return getSheetNameInSheets(item);
            }
        }
        return Collections.emptyMap();
    }

    private Map<String, Integer> getSheetNameInSheets(Node sheets) {
        Map<String, Integer> result = new HashMap<>();
        NodeList childNodes = sheets.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node sheet = childNodes.item(i);
            if (!"sheet".equals(sheet.getNodeName())) {
                continue;
            }
            NamedNodeMap attributes = sheet.getAttributes();
            String idStr = attributes.getNamedItem("sheetId").getNodeValue();
            result.put(attributes.getNamedItem("name").getNodeValue(), Integer.parseInt(idStr));
        }
        return result;
    }
}
