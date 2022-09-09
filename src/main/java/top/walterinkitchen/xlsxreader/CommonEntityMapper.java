package top.walterinkitchen.xlsxreader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * map to entity use json
 *
 * @author walter
 * @since 1.0
 */
public class CommonEntityMapper<T> implements EntityMapper<T> {
    private final Class<T> tClass;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * constructor
     *
     * @param tClass target class
     */
    CommonEntityMapper(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T map(Map<String, Object> source) {
        try {
            String str = mapper.writeValueAsString(source);
            return mapper.readValue(str, tClass);
        } catch (JsonProcessingException exc) {
            throw new ReaderException("map entity failed", exc);
        }
    }
}
