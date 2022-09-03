package top.walterinkitchen.utils.annotaton;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Entity iterator
 *
 * @author walter
 * @since 1.0
 */
public interface EntityIterator<T> extends Iterator<T>, Closeable {
}
