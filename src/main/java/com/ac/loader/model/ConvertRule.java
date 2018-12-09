package com.ac.loader.model;

import com.ac.loader.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class that holds convert rules and its main functionality:
 * - convertHeader
 * - convertDataRow
 */
public class ConvertRule {

    private Set<Integer> indicies = new HashSet<>();
    private MappingData columnMapping;
    private MappingData rowMapping;

    /**
     * Construction for convert rule
     * @param columnMapping - column mapping object
     * @param rowMapping - row mapping object
     */
    public ConvertRule(MappingData columnMapping, MappingData rowMapping) {
        this.columnMapping = columnMapping;
        this.rowMapping = rowMapping;
    }

    /**
     * Method to convert the header with column mapping.
     * @param line - header line
     * @return
     */
    public List<String> convertHeader(String line) {
        String[] s = line.split(Constants.separator);
        // From the first line - create array of translating indicies
        List<String> newColumns = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            String originalValue = s[i];
            String newValue = columnMapping.convert(originalValue);
            if (newValue != null) {
                indicies.add(i);
                newColumns.add(newValue);
            }
        }
        return newColumns;
    }

    /**
     * Method to convert row data with row mapping.
     * @param dataRow - data row
     * @return
     */
    public List<String> convertDataRow(String dataRow) {
        String[] s = dataRow.split(Constants.separator);
        String key = s[0];
        // if ID matches
        String convertedKey = rowMapping.convert(key);
        if (convertedKey != null) {
            List<String> outLine = new ArrayList<>();
            outLine.add( convertedKey );
            indicies.stream().skip(1).forEach( idx -> outLine.add( s[idx] ) );
            return outLine;
        }
        return null;
    }
}
