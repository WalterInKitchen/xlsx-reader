package top.walterinkitchen.utils.annotaton;

import java.io.File;
import java.io.IOException;

/**
 * xlsx entity iterator
 *
 * @author walter
 * @since 1.0
 */
public class XlsxEntityIterator<T> implements EntityIterator<T> {
    private final File file;
    private final Class<T> tClass;

    /**
     * constructor
     *
     * @param tClass class
     * @param file   file
     */
    XlsxEntityIterator(File file, Class<T> tClass) {
        this.file = file;
        this.tClass = tClass;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
