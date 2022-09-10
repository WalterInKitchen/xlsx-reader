package io.github.walterinkitchen.xlsxreader;

import io.github.walterinkitchen.xlsxreader.xlsx.RowIteratorFactory;
import io.github.walterinkitchen.xlsxreader.xlsx.RowToEntityMapper;
import io.github.walterinkitchen.xlsxreader.xlsx.RowToEntityMapperFactory;
import io.github.walterinkitchen.xlsxreader.xlsx.SharedString;
import io.github.walterinkitchen.xlsxreader.xlsx.SharedStringFactory;
import io.github.walterinkitchen.xlsxreader.xlsx.XlsxEntityIterator;
import io.github.walterinkitchen.xlsxreader.xlsx.XlsxFileContainerFactory;
import io.github.walterinkitchen.xlsxreader.xlsx.XmlRawRawRowIterator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import io.github.walterinkitchen.xlsxreader.annotaton.Sheet;
import io.github.walterinkitchen.xlsxreader.xlsx.XlsxFileContainer;

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
        Optional<File> sharedStringsFile = container.getSharedStrings();
        if (!sharedStringsFile.isPresent()) {
            throw new ReaderException("sharedStrings not exist", null);
        }
        XmlRawRawRowIterator rowIterator = RowIteratorFactory.buildRawRowIterator(getXmlSheetFile(container, tClass));
        SharedString xmlSharedString = SharedStringFactory.createXmlSharedString(sharedStringsFile.get());
        EntityMapper<T> mapper = EntityMapperFactory.buildCommonEntityMapper(tClass);
        RowToEntityMapper<T> entityMapper = RowToEntityMapperFactory.buildAnnotationMapper(tClass, mapper, xmlSharedString);
        return new XlsxEntityIterator<>(container, rowIterator, entityMapper);
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
            throw new ReaderException(String.format("%s not exist", name), null);
        }
        return sheet.get();
    }

    private static File getSheetByIndex(XlsxFileContainer container, int index) {
        Optional<File> sheetFile = container.getSheetById(index);
        if (!sheetFile.isPresent()) {
            throw new ReaderException(String.format("sheet%s not exist", index), null);
        }
        return sheetFile.get();
    }
}
