package top.walterinkitchen.xlsxreader.xlsx

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import top.walterinkitchen.xlsxreader.bean.Book

/**
 * RowToEntityMapperTest
 *
 * @author walter
 * @since 1.0
 */
class RowToEntityMapperTest extends Specification {
    def 'if header row have not read, return empty'() {
        given:
        def entityMapper = RowToEntityMapperFactory.buildAnnotationMapper(Book.class, null, null)

        when:
        def row = buildRawRow([id: '001', cells: [[column: 'A1', value: '22']]])
        def res = entityMapper.map(row)

        then:
        res != null
        !res.isPresent()
    }

    def 'if header row have read, the header row return empty, the next none empty row return mapped entity'() {
        given:
        SharedString sharedString = Mock(SharedString)
        RowToEntityMapper entityMapper = RowToEntityMapperFactory.buildAnnotationMapper(Book.class, null, sharedString)

        when: 'map header row'
        def headerRow = buildRawRow([id: '001', cells: [
                [column: 'A2', value: '0', valueType: 's'],
                [column: 'B2', value: '1', valueType: 's'],
                [column: 'C2', value: '2', valueType: 's'],
                [column: 'G2', value: '3', valueType: 's']]])
        def res = entityMapper.map(headerRow)

        then: 'no entity return when given only header row'
        1 * sharedString.getByIndex(0) >> "ID"
        1 * sharedString.getByIndex(1) >> "NAME"
        1 * sharedString.getByIndex(2) >> "AUTHOR"
        1 * sharedString.getByIndex(3) >> "ISBN"
        !res.isPresent()

        when: 'given an entity row'
        def entityRow = buildRawRow([id: '002', cells: [
                [column: 'A2', value: '4', valueType: 's'],
                [column: 'B2', value: '5', valueType: 's'],
                [column: 'C2', value: '6', valueType: 's'],
                [column: 'G2', value: '7', valueType: 's']]])
        res = entityMapper.map(headerRow)

        then: 'entity not empty'
        1 * sharedString.getByIndex(4) >> "1"
        1 * sharedString.getByIndex(5) >> "红楼梦"
        1 * sharedString.getByIndex(6) >> "[清] 曹雪芹 著 / 高鹗 续"
        1 * sharedString.getByIndex(7) >> "9787020002207"
        res.isPresent()
    }

    RawRow buildRawRow(Map<String, Object> source) {
        ObjectMapper mapper = new ObjectMapper()
        def json = mapper.writeValueAsString(source)
        def row = mapper.readValue(json, RawRow.class)
        return row
    }

    String stringfyBook(book) {
        return 'name:' + book.name + ',author:' + book.author + ',isbn:' + book.isbn;
    }
}
