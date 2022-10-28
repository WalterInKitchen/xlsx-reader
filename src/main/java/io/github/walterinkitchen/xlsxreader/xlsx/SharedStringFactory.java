package io.github.walterinkitchen.xlsxreader.xlsx;

import io.github.walterinkitchen.xlsxreader.ReaderException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * SharedStringFactory
 *
 * @author walter
 * @since 1.0
 */
public class SharedStringFactory {
    /**
     * create a xml powered sharedString
     *
     * @param file xml shared string file
     * @return instance
     */
    static public SharedString createXmlSharedString(File file) {
        try {
            FileInputStream ins = new FileInputStream(file);
            return new XmlSharedString(ins);
        } catch (FileNotFoundException exc) {
            throw new ReaderException("init xml reader failed", exc);
        }
    }
}
