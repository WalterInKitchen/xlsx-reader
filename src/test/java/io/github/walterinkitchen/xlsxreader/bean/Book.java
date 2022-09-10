package io.github.walterinkitchen.xlsxreader.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.github.walterinkitchen.xlsxreader.annotaton.Column;
import io.github.walterinkitchen.xlsxreader.annotaton.Sheet;

/**
 * book
 *
 * @author walter
 * @since 1.0
 */
@Data
@Sheet
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Column(name = "NAME")
    private String name;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "ISBN")
    private String isbn;
}
