package io.github.walterinkitchen.xlsxreader.annotaton

import io.github.walterinkitchen.xlsxreader.ResourceFileUtils
import io.github.walterinkitchen.xlsxreader.xlsx.DecompressedXlsxFile
import io.github.walterinkitchen.xlsxreader.xlsx.XlsxFileContainerFactory
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
        def file = ResourceFileUtils.getResources('xlsx/books.xlsx')

        when:
        def container = XlsxFileContainerFactory.createDeCompressedXlsxFile(file)

        then:
        container instanceof DecompressedXlsxFile

        cleanup:
        IOUtils.closeQuietly(container)
    }
}
