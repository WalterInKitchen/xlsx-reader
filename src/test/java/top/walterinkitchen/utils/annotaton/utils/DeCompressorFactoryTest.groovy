package top.walterinkitchen.utils.annotaton.utils

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
        def zip = ResourceFileUtils.getResources('zip/cities.zip')

        when:
        def folder = deCompressor.decompressZip(zip)

        then:
        folder.isDirectory()
        FilenameUtils.getBaseName(folder.getName()).startsWith("cities")
        listFiles(folder) == ['beijing', 'guangzhou', 'shanghai.txt', 'tianjing']

        cleanup:
        FileUtils.deleteQuietly(folder)
    }

    ArrayList<String> listFiles(File file) {
        if (!file.isDirectory()) {
            return []
        }
        File folder = file.listFiles()[0];
        def list = folder.listFiles().collect { fl -> FilenameUtils.getName(fl.getName()) }
                .sort()
                .toList()
        return list
    }
}
