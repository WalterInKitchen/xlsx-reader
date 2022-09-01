package top.walterinkitchen.utils.annotaton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mark a field is mapped to a column in sheet
 *
 * @author walter
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    /**
     * column name
     *
     * @return column name;not null
     */
    String name();
}
