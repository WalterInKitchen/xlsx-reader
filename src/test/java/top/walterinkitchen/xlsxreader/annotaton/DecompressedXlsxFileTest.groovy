package top.walterinkitchen.xlsxreader.annotaton

import org.apache.commons.io.IOUtils
import spock.lang.Specification
import top.walterinkitchen.xlsxreader.ResourceFileUtils
import top.walterinkitchen.xlsxreader.xlsx.XlsxFileContainerFactory

/**
 * DecompressedXlsxFileTest
 * @author walter
 * @since 1.0
 */
class DecompressedXlsxFileTest extends Specification {
    def 'get sheet by id return sheet file if sheet exist'() {
        given:
        def file = ResourceFileUtils.getResources('xlsx/docs.xlsx')
        def deCompressed = XlsxFileContainerFactory.createDeCompressedXlsxFile(file)

        when:
        def sheet = deCompressed.getSheetById(sheetId)

        then:
        sheet.isPresent()
        sheet.get().isFile()
        sheet.get().exists()
        sheet.get().getAbsolutePath().endsWith(sheetFileSuffix)

        cleanup:
        deCompressed.close()

        where:
        sheetId | sheetFileSuffix
        1       | 'xl/worksheets/sheet1.xml'
        2       | 'xl/worksheets/sheet2.xml'
    }

    def 'get sheet by id return empty if sheet not exist'() {
        given:
        def file = ResourceFileUtils.getResources('xlsx/docs.xlsx')
        def deCompressed = XlsxFileContainerFactory.createDeCompressedXlsxFile(file)

        when:
        def sheet = deCompressed.getSheetById(3)
        !sheet.isPresent()

        then:
        sheet != null

        cleanup:
        deCompressed.close()
    }

    def 'get sheet by name return sheet file if sheet exist'() {
        given:
        def file = ResourceFileUtils.getResources('xlsx/docs.xlsx')
        def deCompressed = XlsxFileContainerFactory.createDeCompressedXlsxFile(file)

        when:
        def sheet = deCompressed.getSheet(sheetName)

        then:
        sheet.isPresent()
        sheet.get().isFile()
        sheet.get().exists()
        sheet.get().getAbsolutePath().endsWith(sheetFileSuffix)

        cleanup:
        deCompressed.close()

        where:
        sheetName | sheetFileSuffix
        'name1'   | 'xl/worksheets/sheet2.xml'
        'name2'   | 'xl/worksheets/sheet1.xml'
    }

    def 'get sheet by name return empty file if sheet not exist'() {
        given:
        def file = ResourceFileUtils.getResources('xlsx/docs.xlsx')
        def deCompressed = XlsxFileContainerFactory.createDeCompressedXlsxFile(file)

        when:
        def sheet = deCompressed.getSheet('sheet3')

        then:
        !sheet.isPresent()

        cleanup:
        deCompressed.close()
    }

    def 'get sharedStrings return sharedString file'() {
        given:
        def file = ResourceFileUtils.getResources('xlsx/docs.xlsx')
        def deCompressedXlsxFile = XlsxFileContainerFactory.createDeCompressedXlsxFile(file)

        when:
        def sharedStrings = deCompressedXlsxFile.getSharedStrings()

        then:
        sharedStrings.isPresent()
        sharedStrings.get().isFile()
        sharedStrings.get().exists()
        sharedStrings.get().getAbsolutePath().endsWith('xl/sharedStrings.xml')

        cleanup:
        IOUtils.closeQuietly(deCompressedXlsxFile)
    }
}
