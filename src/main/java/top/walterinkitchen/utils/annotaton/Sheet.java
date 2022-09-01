package top.walterinkitchen.utils.annotaton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mark a bean is mapped to a sheet in xlsx
 *
 * @author walter
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Sheet {
    /**
     * Sheet name
     *
     * @return sheet name
     */
    String name() default "sheet1";

    /**
     * if useIndex is true,use index to location sheet.
     * or use name to locate sheet.
     *
     * @return true to enable index location
     */
    boolean useIndex() default false;

    /**
     * sheet's index
     *
     * @return index
     */
    int index() default 0;
}