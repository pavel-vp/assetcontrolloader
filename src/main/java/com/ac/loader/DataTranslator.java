package com.ac.loader;

import com.ac.loader.service.TransformDataService;
import com.ac.loader.service.TransformMappingService;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Main class with entry point
 */
public class DataTranslator {

    public static void main(String[] args) {

        if (args == null || args.length < 3) {
            System.out.println("Program arguments: ");
            System.out.println("- /path/to/columnmapping.file ");
            System.out.println("- /path/to/rowmapping.file ");
            System.out.println("- /path/to/data.file ");
            System.exit(0);
        }

        String fileColumnMapping = args[0];
        String fileRowMapping = args[1];
        String fileData = args[2];

        TransformMappingService transformMappingService = new TransformMappingService();
        try {
            transformMappingService.transformMappingFromFiles(fileColumnMapping, fileRowMapping);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        TransformDataService transformDataService = new TransformDataService(transformMappingService);

        try(FileInputStream fileInputStream = new FileInputStream(fileData)) {

            // then, load the data file and in the same process do mapping and write output file
            transformDataService.doTransformAndWriteOutput(fileInputStream, System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
