package top.walterinkitchen.utils.utils

import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import spock.lang.Specification
import top.walterinkitchen.utils.ResourceFileUtils

/**
 * DeCompressorFactoryTest
 * @author walter
 * @since 1.0
 */
class DeCompressorFactoryTest extends Specification {
    def 'get instance return CommonsDeCompressor instance'() {
        when:
        def deCompressor = DeCompressorFactory.getInstance()

        then:
        deCompressor instanceof CommonDeCompressor
    }

    def 'unzip an zip file return an folder contains all content files'() {
        given:
        def deCompressor = DeCompressorFactory.getInstance()
        def zip = ResourceFileUtils.getResources('xlsx/docs.xlsx')

        when:
        def folder = deCompressor.decompressZip(zip)

        then:
        folder.isDirectory()
        listFilesByTree(folder, null) == ['[Content_Types].xml', '_rels/.rels', 'docProps/app.xml', 'docProps/core.xml',
                                          'xl/_rels/workbook.xml.rels', 'xl/sharedStrings.xml', 'xl/styles.xml',
                                          'xl/theme/theme1.xml', 'xl/workbook.xml', 'xl/worksheets/sheet1.xml', 'xl/worksheets/sheet2.xml']

        cleanup:
        FileUtils.deleteQuietly(folder)
    }

    List<String> listFilesByTree(File file, String parent) {
        List<String> result = []
        def files = file.listFiles()
        Arrays.sort(files)

        String path = parent == null ? "" : parent + "/"
        for (final def fl in files) {
            def name = FilenameUtils.getName(fl.getName())
            if (fl.isFile()) {
                result.add(path + name)
                continue
            }
            if (fl.isDirectory()) {
                def res = listFilesByTree(fl, path + name)
                result.addAll(res)
            }
        }
        return result
    }
}
