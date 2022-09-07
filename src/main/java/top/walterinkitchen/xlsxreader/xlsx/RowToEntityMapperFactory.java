package top.walterinkitchen.xlsxreader.xlsx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import top.walterinkitchen.xlsxreader.EntityMapper;

/**
 * RowToEntityMapperFactory
 *
 * @author walter
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RowToEntityMapperFactory {
    /**
     * build annotation row to entity mapper
     *
     * @param tClass       class
     * @param mapper       entity mapper
     * @param sharedString shared strings
     * @param <T>          type
     * @return instance
     */
    public static <T> RowToEntityMapper<T> buildAnnotationMapper(Class<T> tClass, EntityMapper<T> mapper, SharedString sharedString) {
        return new AnnotationRowToEntityMapper<>(tClass, mapper, sharedString);
    }
}
