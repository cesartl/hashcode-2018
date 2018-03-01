package com.ctl.hashcode.hascode2018;

import com.ctl.hashcode.hascode2018.model.State;
import com.ctl.hashcode.hascode2018.parser.Parser;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class TestParser {
    private final static String input = "/inputs/a_example.in";

    private String filename;

    @Before
    public void setup() throws Exception {
        URL path = TestParser.class.getResource(input);
        filename = Paths.get(path.toURI()).toFile().getAbsolutePath();
    }

    @Test
    public void readHeader() throws Exception {
        State state = Parser.readFile(filename);
        assertEquals(3, state.getRows());
        assertEquals(4, state.getCols());
        assertEquals(2, state.getNumberOfCars());
        assertEquals(2, state.getBonus());
        assertEquals(10, state.getSteps());
        assertEquals(3, state.getRideRequests().size());
    }
}