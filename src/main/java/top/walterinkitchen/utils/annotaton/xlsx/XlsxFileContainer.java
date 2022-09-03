package top.walterinkitchen.utils.annotaton.xlsx;

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
     * @param index index
     * @return sheet file
     */
    Optional<File> getSheetAt(int index);

    /**
     * get sharedString file
     *
     * @return shardString file
     */
    Optional<File> getSharedStrings();
}
