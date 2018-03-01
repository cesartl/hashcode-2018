package com.ctl.hashcode.hascode2018;

import com.ctl.hashcode.hascode2018.model.RideRequest;
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

    @Test
    public void readRides() throws Exception {
        State state = Parser.readFile(filename);
        RideRequest r1 = state.getRideRequests().getFirst();
        assertEquals(0, r1.getFrom().getY());
        assertEquals(0, r1.getFrom().getX());
        assertEquals(1, r1.getTo().getY());
        assertEquals(3, r1.getTo().getX());
        assertEquals(2, r1.getEarliest());
        assertEquals(9, r1.getLatest());

    }
}