package top.walterinkitchen.xlsxreader.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.walterinkitchen.xlsxreader.annotaton.Column;
import top.walterinkitchen.xlsxreader.annotaton.Sheet;

/**
 * book mapped to index1
 *
 * @author walter
 * @since 1.0
 */
@Data
@Sheet(useIndex = true, index = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookIndex1 {
    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "ISBN")
    private String isbn;
}
