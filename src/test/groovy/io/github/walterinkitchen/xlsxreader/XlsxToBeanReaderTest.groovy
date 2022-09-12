package io.github.walterinkitchen.xlsxreader

import io.github.walterinkitchen.xlsxreader.bean.*
import spock.lang.Specification
/**
 * test read xlsx to bean
 * @author walter
 * @since 1.0
 */
class XlsxToBeanReaderTest extends Specification {
    def 'read at sheet1 when sheet is in default configuration'() {
        given:
        File file = ResourceFileUtils.getResources('xlsx/books.xlsx');
        EntityIterator<Book> entityIterator = EntityIteratorFactory.buildXlsxEntityIterator(file, Book.class);

        when:
        List<Book> books = readAllInIterator(entityIterator);

        then:
        books.size() == 10
        stringfyBook(books[0]) == 'name:红楼梦,author:[清] 曹雪芹 著 / 高鹗 续,isbn:9787020002207'
        stringfyBook(books[1]) == 'name:活着,author:余华,isbn:9787506365437'
        stringfyBook(books[2]) == 'name:百年孤独,author:[哥伦比亚] 加西亚·马尔克斯,isbn:9787544253994'
        stringfyBook(books[3]) == 'name:1984,author:[英] 乔治·奥威尔,isbn:9787530210291'
        stringfyBook(books[4]) == 'name:三体全集,author:刘慈欣,isbn:9787229042066'
        stringfyBook(books[5]) == 'name:哈利·波特,author:J.K.罗琳 (J.K.Rowling),isbn:9787020096695'
        stringfyBook(books[6]) == 'name:三国演义,author:[明] 罗贯中,isbn:9787020008728'
        stringfyBook(books[7]) == 'name:小王子,author:[法国] 安东尼·德·圣-埃克苏佩里,isbn:9787020042494'
        stringfyBook(books[8]) == 'name:呐喊,author:鲁迅,isbn:10019-1979'
        stringfyBook(books[9]) == 'name:人类简史,author:[以色列] 尤瓦尔·赫拉利,isbn:9787508647357'

        cleanup:
        entityIterator.close()
    }

    def 'read at sheet2 when sheet annotation config at sheet2'() {
        given:
        File file = ResourceFileUtils.getResources('xlsx/books.xlsx');
        EntityIterator<BookSheet2> entityIterator = EntityIteratorFactory.buildXlsxEntityIterator(file, BookSheet2.class);

        when:
        List<BookSheet2> books = readAllInIterator(entityIterator);

        then:
        books.size() == 10
        stringfyBook(books[0]) == 'name:红楼梦,author:曹雪芹 著,isbn:9787020002207'
        stringfyBook(books[1]) == 'name:活着,author:余华,isbn:9787506365437'
        stringfyBook(books[2]) == 'name:百年孤独,author:[哥伦比亚] 加西亚·马尔克斯,isbn:9787544253994'
        stringfyBook(books[3]) == 'name:1984,author:[英] 乔治·奥威尔,isbn:9787530210291'
        stringfyBook(books[4]) == 'name:三体全集,author:刘慈欣,isbn:9787229042066'
        stringfyBook(books[5]) == 'name:哈利·波特,author:J.K.罗琳,isbn:9787020096695'
        stringfyBook(books[6]) == 'name:三国演义,author:[明] 罗贯中,isbn:9787020008728'
        stringfyBook(books[7]) == 'name:小王子,author:[法国] 安东尼·德·圣-埃克苏佩里,isbn:9787020042494'
        stringfyBook(books[8]) == 'name:呐喊,author:鲁迅,isbn:10019-1979'
        stringfyBook(books[9]) == 'name:人类简史,author:[以色列] 尤瓦尔·赫拉利,isbn:9787508647357'

        cleanup:
        entityIterator.close()
    }

    def 'read at sheet at index2 when sheet annotation config as index with index2'() {
        given:
        File file = ResourceFileUtils.getResources('xlsx/books.xlsx');
        EntityIterator<BookIndex2> entityIterator = EntityIteratorFactory.buildXlsxEntityIterator(file, BookIndex2.class);

        when:
        List<BookIndex2> books = readAllInIterator(entityIterator);

        then:
        books.size() == 10
        stringfyBook(books[0]) == 'name:红楼梦,author:曹雪芹 著,isbn:9787020002207'
        stringfyBook(books[1]) == 'name:活着,author:余华,isbn:9787506365437'
        stringfyBook(books[2]) == 'name:百年孤独,author:[哥伦比亚] 加西亚·马尔克斯,isbn:9787544253994'
        stringfyBook(books[3]) == 'name:1984,author:[英] 乔治·奥威尔,isbn:9787530210291'
        stringfyBook(books[4]) == 'name:三体全集,author:刘慈欣,isbn:9787229042066'
        stringfyBook(books[5]) == 'name:哈利·波特,author:J.K.罗琳,isbn:9787020096695'
        stringfyBook(books[6]) == 'name:三国演义,author:[明] 罗贯中,isbn:9787020008728'
        stringfyBook(books[7]) == 'name:小王子,author:[法国] 安东尼·德·圣-埃克苏佩里,isbn:9787020042494'
        stringfyBook(books[8]) == 'name:呐喊,author:鲁迅,isbn:10019-1979'
        stringfyBook(books[9]) == 'name:人类简史,author:[以色列] 尤瓦尔·赫拉利,isbn:9787508647357'

        cleanup:
        entityIterator.close()
    }

    def 'read sheet at index1 when sheet annotation config as index with index1'() {
        given:
        File file = ResourceFileUtils.getResources('xlsx/books.xlsx');
        EntityIterator<BookIndex1> entityIterator = EntityIteratorFactory.buildXlsxEntityIterator(file, BookIndex1.class);

        when:
        List<BookIndex1> books = readAllInIterator(entityIterator);

        then:
        books.size() == 10
        stringfyBook(books[0]) == 'name:红楼梦,author:[清] 曹雪芹 著 / 高鹗 续,isbn:9787020002207'
        stringfyBook(books[1]) == 'name:活着,author:余华,isbn:9787506365437'
        stringfyBook(books[2]) == 'name:百年孤独,author:[哥伦比亚] 加西亚·马尔克斯,isbn:9787544253994'
        stringfyBook(books[3]) == 'name:1984,author:[英] 乔治·奥威尔,isbn:9787530210291'
        stringfyBook(books[4]) == 'name:三体全集,author:刘慈欣,isbn:9787229042066'
        stringfyBook(books[5]) == 'name:哈利·波特,author:J.K.罗琳 (J.K.Rowling),isbn:9787020096695'
        stringfyBook(books[6]) == 'name:三国演义,author:[明] 罗贯中,isbn:9787020008728'
        stringfyBook(books[7]) == 'name:小王子,author:[法国] 安东尼·德·圣-埃克苏佩里,isbn:9787020042494'
        stringfyBook(books[8]) == 'name:呐喊,author:鲁迅,isbn:10019-1979'
        stringfyBook(books[9]) == 'name:人类简史,author:[以色列] 尤瓦尔·赫拉利,isbn:9787508647357'

        cleanup:
        entityIterator.close()
    }

    def 'read sheet at sheet3 when cell is formula calculated then should read cellValue not formula'() {
        given:
        File file = ResourceFileUtils.getResources('xlsx/books.xlsx');
        EntityIterator<RandomBean> entityIterator = EntityIteratorFactory.buildXlsxEntityIterator(file, RandomBean.class);

        when:
        List<RandomBean> instances = readAllInIterator(entityIterator);

        then:
        instances.size() == 8
        stringfyRandBean(instances[0]) == 'id:1,f1:a1,f2:b1,f3:c1,f4:d1,f5:e1'
        stringfyRandBean(instances[1]) == 'id:2,f1:a12,f2:b12,f3:c12,f4:d12,f5:e12'
        stringfyRandBean(instances[2]) == 'id:3,f1:a123,f2:b123,f3:c123,f4:d123,f5:e123'
        stringfyRandBean(instances[3]) == 'id:4,f1:a1234,f2:b1234,f3:c1234,f4:d1234,f5:e1234'
        stringfyRandBean(instances[4]) == 'id:5,f1:a12345,f2:b12345,f3:c12345,f4:d12345,f5:e12345'
        stringfyRandBean(instances[5]) == 'id:6,f1:a123456,f2:b123456,f3:c123456,f4:d123456,f5:e123456'
        stringfyRandBean(instances[6]) == 'id:7,f1:a1234567,f2:b1234567,f3:c1234567,f4:d1234567,f5:e1234567'
        stringfyRandBean(instances[7]) == 'id:8,f1:a12345678,f2:b12345678,f3:c12345678,f4:d12345678,f5:e12345678'

        cleanup:
        entityIterator.close()
    }

    String stringfyBook(book) {
        return 'name:' + book.name + ',author:' + book.author + ',isbn:' + book.isbn;
    }

    String stringfyRandBean(RandomBean bean) {
        return 'id:' + bean.id + ',f1:' + bean.f1 + ',f2:' + bean.f2 + ',f3:' + bean.f3 + ',f4:' + bean.f4 + ',f5:' + bean.f5;
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
