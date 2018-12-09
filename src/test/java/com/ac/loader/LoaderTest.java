package com.ac.loader;

import com.ac.loader.model.MappingData;
import com.ac.loader.service.TransformDataService;
import com.ac.loader.service.TransformMappingService;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class LoaderTest {

    TransformDataService transformDataService;
    TransformMappingService transformMappingService;

    @Before
    public void setup() {
        transformMappingService = new TransformMappingService();
        transformDataService = new TransformDataService(transformMappingService);
    }

    @Test
    public void parseLine_test() {
        String d = "COL0\tCOL1\tCOL2\tCOL3";
        String[] cols = d.split("\t");
        assertEquals(cols.length, 4);
    }

    @Test
    public void transformLineToMap_test() throws IOException {
        String d = "COL0\tOURID";
        InputStream stream = new ByteArrayInputStream(d.getBytes(StandardCharsets.UTF_8));
        MappingData map = transformMappingService.transformMapping(stream);
        String result = map.convert("COL0");
        assertEquals(result, "OURID");
    }



}
