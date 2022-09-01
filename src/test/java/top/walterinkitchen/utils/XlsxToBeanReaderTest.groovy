package top.walterinkitchen.utils

import spock.lang.Specification
import top.walterinkitchen.utils.annotaton.EntityIterator
import top.walterinkitchen.utils.annotaton.EntityIteratorFactory
import top.walterinkitchen.utils.bean.Book

/**
 * test read xlsx to bean
 * @author walter
 * @since 1.0
 */
class XlsxToBeanReaderTest extends Specification {
    def 'read at sheet1 when sheet is in default configuration'() {
        given:
        File file = openResourceAsFile('xlsx/books.xlsx');
        EntityIterator<Book> entityIterator = EntityIteratorFactory.buildXlsxEntityIterator(file, Book.class);

        when:
        List<Book> books = readAllInIterator(entityIterator);

        then:
        books.size() == 10
    }

    File openResourceAsFile(String src) {
        URL resource = getClass().getClassLoader().getResource(src)
        return new File(resource.toURI());
    }

    <T> List<T> readAllInIterator(EntityIterator<T> iterator) {
        List<T> res = [];
        while (iterator.hasNext()) {
            def next = iterator.next();
            res.add(next);
        }
        return res;
    }
}
