package com.test;

import io.github.walterinkitchen.xlsxreader.annotaton.Column;
import io.github.walterinkitchen.xlsxreader.annotaton.Sheet;
import lombok.Data;

/**
 * People
 *
 * @author walter
 * @since 1.0
 */
@Data
@Sheet
public class People {
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "gender")
    private String gender;
}
