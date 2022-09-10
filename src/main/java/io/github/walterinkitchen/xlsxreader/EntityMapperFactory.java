package io.github.walterinkitchen.xlsxreader;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * EntityMapperFactory
 *
 * @author walter
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityMapperFactory {
    /**
     * build common mapper
     *
     * @param tClass target type class
     * @param <T>    type
     * @return mapper
     */
    static public <T> EntityMapper<T> buildCommonEntityMapper(Class<T> tClass) {
        return new CommonEntityMapper<>(tClass);
    }
}
