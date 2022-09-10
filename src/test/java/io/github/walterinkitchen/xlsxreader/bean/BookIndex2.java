package io.github.walterinkitchen.xlsxreader.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.walterinkitchen.xlsxreader.annotaton.Column;
import io.github.walterinkitchen.xlsxreader.annotaton.Sheet;

/**
 * book mapped to index2
 *
 * @author walter
 * @since 1.0
 */
@Data
@Sheet(useIndex = true, index = 2)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookIndex2 {
    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "ISBN")
    private String isbn;
}
