package top.walterinkitchen.utils.xlsx

import spock.lang.Specification
import top.walterinkitchen.utils.ResourceFileUtils

/**
 * SharedStringTest
 *
 * @author walter
 * @since 1.0
 */
class SharedStringTest extends Specification {
    def 'get string by index return string value if index exist'() {
        given:
        def file = ResourceFileUtils.getResources('xlsx/sharedStrings.xml')
        def sharedString = SharedStringFactory.createXmlSharedString(file)

        when:
        def res = sharedString.getByIndex(index)

        then:
        res == expected

        where:
        index | expected
        0     | 'Field1'
        1     | 'Field2'
        2     | 'Field3'
        3     | 'Field4'
        4     | 'AA'
        5     | 'AB'
    }

    def 'get string by index return null value if index not exist'() {
        given:
        def file = ResourceFileUtils.getResources('xlsx/sharedStrings.xml')
        def sharedString = SharedStringFactory.createXmlSharedString(file)

        when:
        def res = sharedString.getByIndex(index as int)

        then:
        res == null

        where:
        index << [-1, 20, 30, 33]
    }
}
