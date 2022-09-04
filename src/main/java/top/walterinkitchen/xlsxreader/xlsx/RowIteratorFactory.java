package top.walterinkitchen.xlsxreader.xlsx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * RowIteratorFactory
 *
 * @author walter
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RowIteratorFactory {
    /**
     * build rawRow iterator
     *
     * @param file file
     * @return iterator
     */
    public static XmlRawRawRowIterator buildRawRowIterator(File file) {
        return new XmlRawRawRowIterator(file);
    }
}
