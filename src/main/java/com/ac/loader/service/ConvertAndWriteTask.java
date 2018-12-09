package com.ac.loader.service;

import com.ac.loader.Constants;
import com.ac.loader.model.ConvertRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Runnable task, to proceed data transformation and do output
 */
public class ConvertAndWriteTask implements Runnable {

    private List<String> buffer;
    private ConvertRule convertRule;
    private Consumer<String> outputFunction;

    /**
     * Constructor of ConvertAndWriteTask
     * @param buffer - buffer of data to proceed
     * @param convertRule - convert rule object
     * @param outputFunction - consumer function to proceed output
     */
    public ConvertAndWriteTask(List<String> buffer, ConvertRule convertRule, Consumer<String> outputFunction) {
        this.buffer = buffer;
        this.convertRule = convertRule;
        this.outputFunction = outputFunction;
    }

    @Override
    public void run() {
        buffer.forEach(dataRow -> {
            List<String> convertedRow = convertRule.convertDataRow(dataRow);
            if (convertedRow != null) {
                outputFunction.accept(String.join(Constants.separator, convertedRow));
            }
        });
    }
}
