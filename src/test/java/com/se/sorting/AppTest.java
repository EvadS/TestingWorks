package com.se.sorting;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;


public class AppTest {
    final Logger logger = Logger.getLogger(AppTest.class);
    final String fileName = "input_file.txt";
    final String sortedFilePath = "sorted_file.txt";
    String tmpDirectory;

    @Before
    public void init() throws IOException {
        tmpDirectory = App.createTemporaryDirectory();
        if (!App.isFileExists(fileName)) {
            create_big_file();
        }

        logger.debug("Created temporary directory  " + tmpDirectory);
    }

    @Test
    public void should_correct_sort() throws IOException {
        App.sort(fileName, tmpDirectory, sortedFilePath);
    }


    private void create_big_file() {

        Instant start = Instant.now();

        // bytes -> kb -> mb divide by Integer size
        int size = 1024 * 1024 ;//* 1024 ;/// Integer.SIZE * 2;
        System.out.println("iteration count " + size);


        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < size; ++i) {
                int rndVal = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                String val = String.valueOf(rndVal);
                out.write(val + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).getSeconds()); // prints PT1M3.553S
    }

    @After
    public void tearDown() {
        App.removeTmpDirectory(tmpDirectory);
        logger.debug("Removed temporary directory  " + tmpDirectory);
    }
}
