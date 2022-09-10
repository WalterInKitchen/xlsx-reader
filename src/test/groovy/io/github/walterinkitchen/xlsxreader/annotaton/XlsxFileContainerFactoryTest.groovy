package io.github.walterinkitchen.xlsxreader.annotaton


import org.apache.commons.io.IOUtils
import spock.lang.Specification

/**
 * XlsxFileContainerFactoryTest
 * @author walter
 * @since 1.0
 */
class XlsxFileContainerFactoryTest extends Specification {
    def 'deCompressedXlsxFile is instance of DecompressedXlsxFile'() {
        given:
        def file = io.github.walterinkitchen.xlsxreader.ResourceFileUtils.getResources('xlsx/books.xlsx')

        when:
        def container = io.github.walterinkitchen.xlsxreader.xlsx.XlsxFileContainerFactory.createDeCompressedXlsxFile(file)

        then:
        container instanceof io.github.walterinkitchen.xlsxreader.xlsx.DecompressedXlsxFile

        cleanup:
        IOUtils.closeQuietly(container)
    }
}
