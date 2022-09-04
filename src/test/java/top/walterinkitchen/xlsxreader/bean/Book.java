package top.walterinkitchen.xlsxreader.bean;

import lombok.Data;
import top.walterinkitchen.xlsxreader.annotaton.Column;
import top.walterinkitchen.xlsxreader.annotaton.Sheet;

/**
 * book
 *
 * @author walter
 * @since 1.0
 */
@Data
@Sheet
public class Book {
    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "ISBN")
    private String isbn;
}
