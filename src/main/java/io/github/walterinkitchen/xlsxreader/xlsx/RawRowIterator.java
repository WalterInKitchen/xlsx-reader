package io.github.walterinkitchen.xlsxreader.xlsx;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Row iterator
 *
 * @author walter
 * @since 1.0
 */
public interface RawRowIterator extends Iterator<RawRow>, Closeable {
}
