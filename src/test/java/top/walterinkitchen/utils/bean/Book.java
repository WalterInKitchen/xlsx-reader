package top.walterinkitchen.utils.bean;

import lombok.Data;
import top.walterinkitchen.utils.annotaton.Column;
import top.walterinkitchen.utils.annotaton.Sheet;

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
