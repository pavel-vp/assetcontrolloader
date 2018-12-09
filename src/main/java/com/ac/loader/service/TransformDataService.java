package com.ac.loader.service;

import com.ac.loader.Constants;
import com.ac.loader.model.ConvertRule;
import com.ac.loader.model.RowType;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Main transormation service
 */
public class TransformDataService {

    private TransformMappingService transformMappingService;

    /**
     * Constructor oftransformation object
     * @param transformMappingService - transformation mapper service
     */
    public TransformDataService(TransformMappingService transformMappingService) {
        this.transformMappingService = transformMappingService;
    }

    /**
     * Main transformation method.
     * @param inputStream - input stream for data
     * @param consumerOutput - Consumer function to proceed the output string
     * @throws IOException
     * @throws InterruptedException
     */
    public void doTransformAndWriteOutput(InputStream inputStream, Consumer<String> consumerOutput) throws IOException, InterruptedException {
        // Load data and parse it in parallel
        ExecutorService executorService = Executors.newFixedThreadPool( Constants.NUM_THREADS);

        boolean isFirstLine = true;
        List<String> buffer = new ArrayList<>();
        Scanner sc = new Scanner(inputStream, Constants.CharacterSet);
        ConvertRule convertRule = null;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            RowType rowType = isFirstLine ? RowType.Header: RowType.Data;
            switch (rowType) {
                case Header:
                    convertRule = new ConvertRule(transformMappingService.getColumnMapping(), transformMappingService.getRowMapping());
                    consumerOutput.accept(String.join(Constants.separator, convertRule.convertHeader(line)));
                    break;
                case Data:
                    buffer.add(line);
                    if (buffer.size() >= Constants.BUFFER_RECORD_COUNT) {
                        executorService.execute(new ConvertAndWriteTask(buffer, convertRule, consumerOutput));
                        buffer = new ArrayList<>();
                    }
                    break;
            }
            isFirstLine = false;
        }
        // the remains of data
        if (buffer.size() > 0) {
            executorService.execute(new ConvertAndWriteTask(buffer, convertRule, consumerOutput));
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        if (sc.ioException() != null) {
            throw sc.ioException();
        }
    }

}
