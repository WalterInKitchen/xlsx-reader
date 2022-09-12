package com.test;

import io.github.walterinkitchen.xlsxreader.EntityIterator;
import io.github.walterinkitchen.xlsxreader.EntityIteratorFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * MiniReader
 *
 * @author walter
 * @since 1.0
 */
public class MiniReader {
    public static void main(String[] args) throws URISyntaxException {
        File file = openFile("people.xlsx");
        EntityIterator<People> iterator = EntityIteratorFactory.buildXlsxEntityIterator(file, People.class);
        while (iterator.hasNext()) {
            People next = iterator.next();
            System.out.println(next);
        }
        long total = Runtime.getRuntime().totalMemory();
        long max = Runtime.getRuntime().maxMemory();
        System.out.println("total:" + total + " max:" + max);
    }

    private static File openFile(String srcFile) throws URISyntaxException {
        URL resource = MiniReader.class.getClassLoader().getResource("people.xlsx");
        return new File(resource.toURI());
    }
}
