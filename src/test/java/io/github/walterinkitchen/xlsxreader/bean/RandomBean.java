package io.github.walterinkitchen.xlsxreader.bean;

import io.github.walterinkitchen.xlsxreader.annotaton.Column;
import io.github.walterinkitchen.xlsxreader.annotaton.Sheet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author walter
 * @since 1.0
 */
@Data
@Sheet(name = "rand")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomBean {
    @Column(name = "id")
    private String id;
    @Column(name = "a")
    private String f1;
    @Column(name = "b")
    private String f2;
    @Column(name = "c")
    private String f3;
    @Column(name = "d")
    private String f4;
    @Column(name = "e")
    private String f5;
}
