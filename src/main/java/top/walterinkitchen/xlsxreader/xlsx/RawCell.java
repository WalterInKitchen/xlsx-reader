package top.walterinkitchen.xlsxreader.xlsx;

import lombok.Builder;
import lombok.Getter;

/**
 * RawCell
 *
 * @author walter
 * @since 1.0
 */
@Builder
@Getter
public class RawCell {
    private final String column;
    private final String value;
    private final String valueType;
}
