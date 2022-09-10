package io.github.walterinkitchen.xlsxreader


import spock.lang.Specification
import io.github.walterinkitchen.xlsxreader.bean.Book

/**
 * EntityMapperTest
 *
 * @author walter
 * @since 1.0
 */
class EntityMapperTest extends Specification {
    def 'common mapper convert map to instance'() {
        given:
        def mapper = EntityMapperFactory.buildCommonEntityMapper(Book.class)

        when:
        def res = mapper.map(source as Map<String, Object>)

        then:
        stringfyBook(res) == expected

        where:
        source                                         | expected
        [name: 'abc', author: 'author1', isbn: 'isbn'] | 'name:abc,author:author1,isbn:isbn'
        [name: 'book2', author: 'aa', isbn: 123]       | 'name:book2,author:aa,isbn:123'
    }

    String stringfyBook(book) {
        return 'name:' + book.name + ',author:' + book.author + ',isbn:' + book.isbn;
    }
}
