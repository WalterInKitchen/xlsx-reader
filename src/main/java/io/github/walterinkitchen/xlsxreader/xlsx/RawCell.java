package io.github.walterinkitchen.xlsxreader.xlsx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RawCell
 *
 * @author walter
 * @since 1.0
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RawCell {
    private String column;
    private String value;
    private String valueType;
}
