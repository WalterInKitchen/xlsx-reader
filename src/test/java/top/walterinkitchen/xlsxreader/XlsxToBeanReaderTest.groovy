package top.walterinkitchen.xlsxreader

import spock.lang.Specification
import top.walterinkitchen.xlsxreader.bean.Book
import top.walterinkitchen.xlsxreader.bean.BookIndex1
import top.walterinkitchen.xlsxreader.bean.BookIndex2
import top.walterinkitchen.xlsxreader.bean.BookSheet2

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
    }

    def 'read at sheet at index1 when sheet annotation config as index with index1'() {
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
    }

    String stringfyBook(book) {
        return 'name:' + book.name + ',author:' + book.author + ',isbn:' + book.isbn;
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
