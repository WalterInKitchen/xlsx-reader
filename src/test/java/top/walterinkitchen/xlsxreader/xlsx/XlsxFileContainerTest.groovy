package top.walterinkitchen.xlsxreader.xlsx

import spock.lang.Specification
import top.walterinkitchen.xlsxreader.ResourceFileUtils

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
