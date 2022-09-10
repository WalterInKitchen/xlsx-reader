package io.github.walterinkitchen.xlsxreader;

import java.util.Map;

/**
 * EntityMapper
 *
 * @author walter
 * @since 1.0
 */
public interface EntityMapper<T> {
    /**
     * map source to entity
     *
     * @param source source
     * @return instance
     */
    T map(Map<String, Object> source);
}
