package top.walterinkitchen.utils.annotaton

import org.apache.commons.io.IOUtils
import spock.lang.Specification
import top.walterinkitchen.utils.ResourceFileUtils
import top.walterinkitchen.utils.xlsx.DecompressedXlsxFile
import top.walterinkitchen.utils.xlsx.XlsxFileContainerFactory

/**
 * XlsxFileContainerFactoryTest
 * @author walter
 * @since 1.0
 */
class XlsxFileContainerFactoryTest extends Specification {
    def 'deCompressedXlsxFile is instance of DecompressedXlsxFile'() {
        given:
        def file = ResourceFileUtils.getResources('xlsx/books.xlsx')

        when:
        def container = XlsxFileContainerFactory.createDeCompressedXlsxFile(file)

        then:
        container instanceof DecompressedXlsxFile

        cleanup:
        IOUtils.closeQuietly(container)
    }
}
