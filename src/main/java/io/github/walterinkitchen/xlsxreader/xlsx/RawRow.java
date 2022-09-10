package io.github.walterinkitchen.xlsxreader.xlsx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * raw row
 *
 * @author walter
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RawRow {
    private int id;
    private List<RawCell> cells;
}
