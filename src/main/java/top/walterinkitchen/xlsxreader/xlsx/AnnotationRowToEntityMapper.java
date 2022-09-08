package top.walterinkitchen.xlsxreader.xlsx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import top.walterinkitchen.xlsxreader.EntityMapper;
import top.walterinkitchen.xlsxreader.annotaton.Column;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    private HeaderColumns headerColumns = null;

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
        if (this.headerColumns == null || this.headerColumns.getHeaderSize() == 0) {
            this.parseHeader(row);
            return Optional.empty();
        }
        Map<String, Object> source = parseRow(row);
        if (source.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(entityMapper.map(source));
    }

    private Map<String, Object> parseRow(RawRow row) {
        List<RawCell> cells = row.getCells();
        Map<String, Object> res = new HashMap<>();
        for (RawCell cell : cells) {
            CellValue cv = buildCellValue(cell);
            String key = this.headerColumns.getFieldNameByColumn(cv.getColumn());
            if (key == null || key.equals("")) {
                continue;
            }
            res.put(key, cv.getValue());
        }
        return res;
    }

    private void parseHeader(RawRow row) {
        List<RawCell> cells = row.getCells();
        if (cells == null || cells.size() == 0) {
            return;
        }
        List<CellValue> cellValues = cells.stream().map(this::buildCellValue).collect(Collectors.toList());
        HeaderColumns columns = parseColumns();
        List<CellValue> headerCells = cellValues.stream()
                .filter(cl -> cl.getColumn() != null)
                .filter(cl -> columns.containsColumnName(cl.value))
                .collect(Collectors.toList());
        if (headerCells.size() == 0) {
            return;
        }
        cacheHeaders(headerCells, columns);
    }

    private void cacheHeaders(List<CellValue> headerCells, HeaderColumns columns) {
        for (CellValue headerCell : headerCells) {
            columns.registerColumn(headerCell.column, headerCell.value);
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

    private HeaderColumns parseColumns() {
        if (this.headerColumns != null) {
            return this.headerColumns;
        }
        Field[] fields = this.tClass.getDeclaredFields();
        List<ColumnField> columnFieldPairs = Arrays.stream(fields)
                .map(this::findColumn)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        this.headerColumns = HeaderColumns.build(columnFieldPairs);
        return this.headerColumns;
    }

    private ColumnField findColumn(Field fd) {
        Column annotation = fd.getAnnotation(Column.class);
        if (annotation == null) {
            return null;
        }
        String colName = annotation.name();
        String fieldName = fd.getName();
        return new ColumnField(colName, fieldName);
    }

    private static class HeaderColumns {
        private final Map<String, String> colNameVsFieldName;
        private final Map<String, String> colPosVsFieldName = new HashMap<>();

        private HeaderColumns(Map<String, String> colNameVsFieldName) {
            this.colNameVsFieldName = colNameVsFieldName;
        }

        public static HeaderColumns build(List<ColumnField> columnFields) {
            Map<String, String> colVsField = new HashMap<>();
            for (ColumnField colField : columnFields) {
                colVsField.put(colField.getColumnName(), colField.getFieldName());
            }
            return new HeaderColumns(colVsField);
        }

        public boolean containsColumnName(String columnName) {
            return this.colNameVsFieldName.containsKey(columnName);
        }

        public void registerColumn(String columnPos, String columnName) {
            String fieldName = this.colNameVsFieldName.get(columnName);
            this.colPosVsFieldName.put(columnPos, fieldName);
        }

        public String getFieldNameByColumn(String columnPos) {
            return colPosVsFieldName.getOrDefault(columnPos, null);
        }

        public int getHeaderSize() {
            return this.colPosVsFieldName.size();
        }
    }

    @Getter
    @AllArgsConstructor
    private static class ColumnField {
        private String columnName;
        private String fieldName;
    }

    @Getter
    @Builder
    private static class CellValue {
        private String column;
        private String value;
    }
}
