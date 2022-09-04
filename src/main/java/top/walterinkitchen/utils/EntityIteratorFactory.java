package top.walterinkitchen.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import top.walterinkitchen.utils.xlsx.XlsxEntityIterator;

import java.io.File;

/**
 * EntityIteratorFactory
 *
 * @author walter
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityIteratorFactory {
    /**
     * build xlsx entity iterator
     *
     * @param file   file
     * @param tClass class
     * @param <T>    type
     * @return iterator
     */
    static public <T> EntityIterator<T> buildXlsxEntityIterator(File file, Class<T> tClass) {
        return new XlsxEntityIterator<>(file, tClass);
    }
}
