package top.walterinkitchen.xlsxreader.xlsx


import spock.lang.Specification
import top.walterinkitchen.xlsxreader.ResourceFileUtils

/**
 * RowIteratorTest
 * @author walter
 * @since 1.0
 */
class RawRowIteratorTest extends Specification {
    def 'read row from sheet.xml return row with correct cells'() {
        given:
        def file = ResourceFileUtils.getResources('xlsx/sheet1.xml')
        def iterator = RowIteratorFactory.buildRawRowIterator(file)

        when:
        List<RawRow> rows = readAllRows(iterator)

        then:
        stringfy(rows) == 'r:1,cs:[r:A,t:s,v:0;r:B,t:s,v:1;r:C,t:s,v:2;r:D,t:s,v:3;];' +
                'r:2,cs:[r:A,t:inlineStr,v:hello world;];' +
                'r:3,cs:[r:A,t:s,v:5;];' +
                'r:5,cs:[r:A,t:s,v:4;];'
    }

    List<RawRow> readAllRows(XmlRawRawRowIterator iterator) {
        List<RawRow> res = []
        while (iterator.hasNext()) {
            res.add(iterator.next())
        }
        return res
    }

    String stringfy(List<RawRow> rawRows) {
        StringBuilder builder = new StringBuilder()
        for (final def row in rawRows) {
            builder.append(stringfyRow(row))
                    .append(';')
        }
        return builder.toString()
    }

    String stringfyRow(RawRow row) {
        return 'r:' + row.getId() + ',cs:[' + stringfyCells(row.getCells()) + ']'
    }

    String stringfyCells(List<RawCell> cellList) {
        StringBuilder builder = new StringBuilder()
        for (final def cell in cellList) {
            builder.append('r:').append(cell.getColumn())
                    .append(',t:').append(cell.getValueType())
                    .append(',v:').append(cell.getValue())
                    .append(';')
        }
        return builder.toString()
    }
}
