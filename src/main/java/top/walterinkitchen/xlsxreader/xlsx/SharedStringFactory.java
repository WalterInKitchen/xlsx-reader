package top.walterinkitchen.xlsxreader.xlsx;

import java.io.File;

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
        return new XmlSharedString(file);
    }
}
