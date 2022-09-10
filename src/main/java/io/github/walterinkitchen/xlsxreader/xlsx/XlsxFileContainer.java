package io.github.walterinkitchen.xlsxreader.xlsx;

import java.io.Closeable;
import java.io.File;
import java.util.Optional;

/**
 * Xlsx file container
 *
 * @author walter
 * @since 1.0
 */
public interface XlsxFileContainer extends Closeable {
    /**
     * get sheet by name
     *
     * @param sheetName sheet name
     * @return sheet file
     */
    Optional<File> getSheet(String sheetName);

    /**
     * get sheet by index
     *
     * @param sheetId sheet id(start from 1)
     * @return sheet file
     */
    Optional<File> getSheetById(int sheetId);

    /**
     * get sharedString file
     *
     * @return shardString file
     */
    Optional<File> getSharedStrings();
}
