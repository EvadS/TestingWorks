package com.se.sorting;

import com.google.code.externalsorting.ExternalSort;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;


public class App {

    static final Logger logger = Logger.getLogger(App.class);


    private static final String TEST_FILE1_TXT = "numbers";
    private static final String TEST_FILE1_OUT = "numbers_Res.txt";
    private static int MAX_MEMORY_SIZE = 100 * 1024 * 1024;
    private static int maxNumberTempFiles = 1000;

    private static String TEMPORARY_SORTING_DIRECTORY  = "tmp_sorting";

    static boolean isFileExists(String filePathString ){
        boolean result = false;

        File f = new File(filePathString);
        if(f.exists() && !f.isDirectory()) {
            result = true;
        }
        return result;
    }

    static String  createTemporaryDirectory() throws IOException {
        File temp = File.createTempFile(TEMPORARY_SORTING_DIRECTORY,"");
        temp.delete();
        temp.mkdir();

        return temp.getAbsolutePath();
    }

    static void removeTmpDirectory (String tmpFolderPath){
        File tmpDir = new File(tmpFolderPath);
        if(tmpDir.exists())
            tmpDir.delete();
    }

    static void sort(String inputFilePath, String temporaryFolderPath,String sortedFilePath ) throws IOException {

        logger.debug("before sorting ");

        Comparator<String> comparator = (s, t1) -> {
            Integer a = Integer.parseInt(s);
            Integer b = Integer.parseInt(t1);
            return a.compareTo(b);
        };

        Charset defaultCharset = Charset.defaultCharset();
         File tmpFolder = new File(temporaryFolderPath);
        

        List<File> sortInBatch = new ArrayList();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath))) {

            sortInBatch   =  ExternalSort.sortInBatch(bufferedReader, 0, comparator, maxNumberTempFiles,
                    MAX_MEMORY_SIZE, defaultCharset,
                    tmpFolder, false, 0, true, true);
        }

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sortedFilePath))) {

            ExternalSort.mergeSortedFiles(sortInBatch, bufferedWriter, comparator, Charset.defaultCharset(), false, true);
        }
    }

    public static void main(String[] args) throws IOException {

        String temporaryPath = createTemporaryDirectory();

        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter file path for sort ");

        //TODO: for test
        String filePath = scanner.nextLine();  // Read user input

        if(!isFileExists(filePath)){
            logger.error("File " + filePath + " does not exist.");
            System.exit(1);
        }

        try {
            sort(filePath,temporaryPath,TEST_FILE1_OUT);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
