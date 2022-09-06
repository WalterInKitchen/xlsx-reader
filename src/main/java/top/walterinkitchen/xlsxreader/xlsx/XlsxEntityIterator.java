package top.walterinkitchen.xlsxreader.xlsx;

import top.walterinkitchen.xlsxreader.EntityIterator;
import top.walterinkitchen.xlsxreader.EntityMapper;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

/**
 * xlsx entity iterator
 *
 * @author walter
 * @since 1.0
 */
public class XlsxEntityIterator<T> implements EntityIterator<T> {
    private final XlsxFileContainer fileContainer;
    private final RawRowIterator rowIterator;
    private final SharedString sharedString;
    private final EntityMapper<T> entityMapper;

    private final Deque<T> deque = new LinkedList<>();

    /**
     * constructor
     *
     * @param fileContainer xlsx file container
     * @param rowIterator   row iterator
     * @param sharedString  sharedString
     * @param entityMapper  entity mapper
     */
    public XlsxEntityIterator(XlsxFileContainer fileContainer, RawRowIterator rowIterator, SharedString sharedString, EntityMapper<T> entityMapper) {
        this.fileContainer = fileContainer;
        this.rowIterator = rowIterator;
        this.sharedString = sharedString;
        this.entityMapper = entityMapper;
        this.readNext();
    }

    @Override
    public boolean hasNext() {
        return !this.deque.isEmpty();
    }

    @Override
    public T next() {
        if (this.deque.isEmpty()) {
            throw new RuntimeException("no more to read");
        }
        T res = this.deque.removeLast();
        this.readNext();
        return res;
    }

    @Override
    public void close() throws IOException {
        this.fileContainer.close();
    }

    private void readNext() {

    }
}
