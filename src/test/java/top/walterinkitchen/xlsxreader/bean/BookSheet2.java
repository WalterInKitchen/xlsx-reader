package top.walterinkitchen.xlsxreader.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.walterinkitchen.xlsxreader.annotaton.Column;
import top.walterinkitchen.xlsxreader.annotaton.Sheet;

/**
 * book in sheet2
 *
 * @author walter
 * @since 1.0
 */
@Data
@Sheet(name = "Sheet2")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSheet2 {
    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "ISBN")
    private String isbn;
}
