package io.github.walterinkitchen.xlsxreader.xlsx;

import java.io.Closeable;

/**
 * shared string abstract
 *
 * @author walter
 * @since 1.0
 */
public interface SharedString extends Closeable {
    /**
     * get string by index
     *
     * @param index idx start from 0
     * @return string or null
     */
    String getByIndex(int index);
}
