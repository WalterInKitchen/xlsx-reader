package top.walterinkitchen.xlsxreader.xlsx;

import top.walterinkitchen.xlsxreader.EntityIterator;
import top.walterinkitchen.xlsxreader.EntityMapper;

import java.io.IOException;

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
