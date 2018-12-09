package com.ac.loader.service;

import com.ac.loader.Constants;
import com.ac.loader.model.MappingData;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Service to help parse mapping files
 *  - column mapping
 *  - row mapping
 */
public class TransformMappingService {

    private MappingData columnMapping;
    private MappingData rowMapping;

    /**
     * Method that fill mapping object from files
     * @param fileColumnMapping - file with column mapping data
     * @param fileRowMapping - file with row mapping data
     * @throws IOException
     */
    public void transformMappingFromFiles(String fileColumnMapping, String fileRowMapping) throws IOException {
        columnMapping = loadFromFiles(fileColumnMapping);
        rowMapping = loadFromFiles(fileRowMapping);
    }

    private MappingData loadFromFiles(String fileColumnMapping) throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream(fileColumnMapping)) {
            return transformMapping(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public MappingData transformMapping(InputStream dataInputStream) throws IOException {

        MappingData result = new MappingData();
        Scanner sc = new Scanner(dataInputStream, "UTF-8");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] s = line.split(Constants.separator);
            result.putMapping(s[0], s[1]);
        }

        if (sc.ioException() != null) {
            throw sc.ioException();
        }
        return result;
    }

    public MappingData getColumnMapping() {
        return columnMapping;
    }

    public MappingData getRowMapping() {
        return rowMapping;
    }
}
