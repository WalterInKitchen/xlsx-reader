package top.walterinkitchen.utils.xlsx

import spock.lang.Specification
import top.walterinkitchen.utils.ResourceFileUtils

/**
 * XlsxFileContainerTest
 * @author walter
 * @since 1.0
 */
class XlsxFileContainerTest extends Specification {
    def 'get sheet by name return sheet file'() {
        given:
        ResourceFileUtils.getResources('xlsx/books.')

        XlsxFileContainerFactory.createDeCompressedXlsxFile()

    }

}
