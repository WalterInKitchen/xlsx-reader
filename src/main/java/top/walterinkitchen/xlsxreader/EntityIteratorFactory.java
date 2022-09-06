package top.walterinkitchen.xlsxreader;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import top.walterinkitchen.xlsxreader.annotaton.Sheet;
import top.walterinkitchen.xlsxreader.xlsx.RowIteratorFactory;
import top.walterinkitchen.xlsxreader.xlsx.SharedString;
import top.walterinkitchen.xlsxreader.xlsx.SharedStringFactory;
import top.walterinkitchen.xlsxreader.xlsx.XlsxEntityIterator;
import top.walterinkitchen.xlsxreader.xlsx.XlsxFileContainer;
import top.walterinkitchen.xlsxreader.xlsx.XlsxFileContainerFactory;
import top.walterinkitchen.xlsxreader.xlsx.XmlRawRawRowIterator;

import java.io.File;
import java.util.Optional;

/**
 * EntityIteratorFactory
 *
 * @author walter
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityIteratorFactory {
    /**
     * build xlsx entity iterator
     *
     * @param file   file
     * @param tClass class
     * @param <T>    type
     * @return iterator
     */
    static public <T> EntityIterator<T> buildXlsxEntityIterator(File file, Class<T> tClass) {
        XlsxFileContainer container = XlsxFileContainerFactory.createDeCompressedXlsxFile(file);
        Optional<File> sharedStrings = container.getSharedStrings();
        if (!sharedStrings.isPresent()) {
            throw new RuntimeException("sharedStrings not exist");
        }
        XmlRawRawRowIterator rowIterator = RowIteratorFactory.buildRawRowIterator(getXmlSheetFile(container, tClass));
        SharedString xmlSharedString = SharedStringFactory.createXmlSharedString(sharedStrings.get());
        EntityMapper<T> mapper = EntityMapperFactory.buildCommonEntityMapper(tClass);
        return new XlsxEntityIterator<>(container, rowIterator, xmlSharedString, mapper);
    }

    private static <T> File getXmlSheetFile(XlsxFileContainer container, Class<T> tClass) {
        Sheet sheet = tClass.getAnnotation(Sheet.class);
        if (sheet == null) {
            return getSheetByIndex(container, 1);
        }
        if (sheet.useIndex()) {
            return getSheetByIndex(container, sheet.index());
        }
        return getSheetByName(container, sheet.name());
    }

    private static File getSheetByName(XlsxFileContainer container, String name) {
        Optional<File> sheet = container.getSheet(name);
        if (!sheet.isPresent()) {
            throw new RuntimeException(String.format("%s not exist", name));
        }
        return sheet.get();
    }

    private static File getSheetByIndex(XlsxFileContainer container, int index) {
        Optional<File> sheetFile = container.getSheetById(index);
        if (!sheetFile.isPresent()) {
            throw new RuntimeException(String.format("sheet%s not exist", index));
        }
        return sheetFile.get();
    }
}
