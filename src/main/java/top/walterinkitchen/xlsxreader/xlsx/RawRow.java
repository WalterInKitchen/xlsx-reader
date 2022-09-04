package top.walterinkitchen.xlsxreader.xlsx;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * raw row
 *
 * @author walter
 * @since 1.0
 */
@Getter
@Builder
public class RawRow {
    private final int id;
    private final List<RawCell> cells;
}
