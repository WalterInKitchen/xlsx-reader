package io.github.walterinkitchen.xlsxreader.xlsx;

import java.io.Closeable;
import java.util.Optional;

/**
 * Map RawRow to entity
 *
 * @author walter
 * @since 1.0
 */
public interface RowToEntityMapper<T> extends Closeable {
    /**
     * map rawRow to entity
     *
     * @param row rawRow
     * @return optional entity
     */
    Optional<T> map(RawRow row);
}
