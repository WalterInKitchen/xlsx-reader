package com.test;

import io.github.walterinkitchen.xlsxreader.EntityIterator;
import io.github.walterinkitchen.xlsxreader.EntityIteratorFactory;
import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * MiniReader
 *
 * @author walter
 * @since 1.0
 */
public class MiniReader {
    public static void main(String[] args) throws URISyntaxException {
        File file = openFile("people.xlsx");
        try (EntityIterator<People> iterator = EntityIteratorFactory.buildXlsxEntityIterator(file, People.class)) {
            int counter = 0;
            StopWatch stopWatch = StopWatch.create();
            stopWatch.start();
            while (iterator.hasNext()) {
                People next = iterator.next();
                System.out.println(next);
                counter++;
            }
            long total = Runtime.getRuntime().totalMemory() / (1024 * 1024);
            long max = Runtime.getRuntime().maxMemory() / (1024 * 1024);
            stopWatch.stop();
            System.out.println("timeUsed:" + stopWatch.getTime(TimeUnit.SECONDS) + " seconds");
            System.out.println("counter:" + counter);
            System.out.println("total:" + total + "MB max:" + max + "MB");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static File openFile(String srcFile) throws URISyntaxException {
        URL resource = MiniReader.class.getClassLoader().getResource("people.xlsx");
        return new File(resource.toURI());
    }
}
