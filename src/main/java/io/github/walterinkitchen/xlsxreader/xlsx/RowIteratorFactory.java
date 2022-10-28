package io.github.walterinkitchen.xlsxreader.xlsx;

import io.github.walterinkitchen.xlsxreader.ReaderException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
        try {
            FileInputStream ins = new FileInputStream(file);
            return new XmlRawRawRowIterator(ins);
        } catch (FileNotFoundException exc) {
            throw new ReaderException("init xml reader failed", exc);
        }
    }
}
