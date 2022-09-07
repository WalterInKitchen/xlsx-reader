package top.walterinkitchen.xlsxreader.xlsx;

import org.apache.commons.io.IOUtils;
import top.walterinkitchen.xlsxreader.EntityIterator;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

/**
 * xlsx entity iterator
 *
 * @author walter
 * @since 1.0
 */
public class XlsxEntityIterator<T> implements EntityIterator<T> {
    private final XlsxFileContainer fileContainer;
    private final RawRowIterator rowIterator;
    private final RowToEntityMapper<T> entityMapper;

    private final Deque<T> deque = new LinkedList<>();

    /**
     * constructor
     *
     * @param fileContainer xlsx file container
     * @param rowIterator   row iterator
     * @param entityMapper  entity mapper
     */
    public XlsxEntityIterator(XlsxFileContainer fileContainer, RawRowIterator rowIterator, RowToEntityMapper<T> entityMapper) {
        this.fileContainer = fileContainer;
        this.rowIterator = rowIterator;
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
    public void close() {
        IOUtils.closeQuietly(this.fileContainer);
    }

    private void readNext() {
        while (this.rowIterator.hasNext()) {
            RawRow row = this.rowIterator.next();
            Optional<T> opt = this.entityMapper.map(row);
            if (!opt.isPresent()) {
                continue;
            }
            T entity = opt.get();
            this.deque.addLast(entity);
            break;
        }
    }
}
