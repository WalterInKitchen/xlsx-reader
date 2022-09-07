package top.walterinkitchen.xlsxreader.xlsx;

import lombok.Builder;
import lombok.Getter;
import top.walterinkitchen.xlsxreader.EntityMapper;
import top.walterinkitchen.xlsxreader.annotaton.Column;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * annotation rao to entity mapper
 *
 * @author walter
 * @since 1.0
 */
public class AnnotationRowToEntityMapper<T> implements RowToEntityMapper<T> {
    private final Class<T> tClass;
    private final EntityMapper<T> entityMapper;
    private final SharedString sharedString;
    private final Map<String, String> headers = new HashMap<>();

    private Set<String> columns = null;

    /**
     * constructor
     *
     * @param tClass       class
     * @param entityMapper entity mapper
     * @param sharedString sharedString
     */
    public AnnotationRowToEntityMapper(Class<T> tClass, EntityMapper<T> entityMapper, SharedString sharedString) {
        this.tClass = tClass;
        this.entityMapper = entityMapper;
        this.sharedString = sharedString;
    }

    @Override
    public Optional<T> map(RawRow row) {
        if (row == null) {
            return Optional.empty();
        }
        if (this.headers.isEmpty()) {
            this.parseHeader(row);
            return Optional.empty();
        }
        return Optional.empty();
    }

    private void parseHeader(RawRow row) {
        List<RawCell> cells = row.getCells();
        if (cells == null || cells.size() == 0) {
            return;
        }
        List<CellValue> cellValues = cells.stream().map(this::buildCellValue).collect(Collectors.toList());
        Set<String> columns = parseColumns();
        List<CellValue> headerCells = cellValues.stream()
                .filter(cl -> columns.contains(cl.value))
                .collect(Collectors.toList());
        if (headerCells.size() == 0) {
            return;
        }
        cacheHeaders(headerCells);
    }

    private void cacheHeaders(List<CellValue> headerCells) {
        for (CellValue headerCell : headerCells) {
            this.headers.put(headerCell.column, headerCell.value);
        }
    }

    private CellValue buildCellValue(RawCell cell) {
        String value = cell.getValue();
        if ("s".equals(cell.getValueType())) {
            value = findValueInSharedString(value);
        }
        return CellValue.builder()
                .column(cell.getColumn())
                .value(value)
                .build();
    }

    private String findValueInSharedString(String indexStr) {
        int idx = Integer.parseInt(indexStr);
        return this.sharedString.getByIndex(idx);
    }

    private Set<String> parseColumns() {
        if (this.columns != null) {
            return this.columns;
        }
        Field[] fields = this.tClass.getDeclaredFields();
        this.columns = Collections.unmodifiableSet(
                Arrays.stream(fields).map(this::findColumn)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()));
        return this.columns;
    }

    private String findColumn(Field fd) {
        Column annotation = fd.getAnnotation(Column.class);
        if (annotation == null) {
            return null;
        }
        return annotation.name();
    }

    @Getter
    @Builder
    private static class CellValue {
        private String column;
        private String value;
    }
}
