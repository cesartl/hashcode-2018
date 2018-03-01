package com.ctl.hashcode.hascode2018;

import com.ctl.hashcode.hascode2018.model.State;
import com.ctl.hashcode.hascode2018.parser.Parser;
import com.ctl.hashcode.hascode2018.solver.BasicScheduler;
import com.ctl.hashcode.hascode2018.solver.Solver;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Paths;

public class TestSolutions {

    public State getA() throws Exception {
        String file = "/inputs/a_example.in";
        URL path = TestParser.class.getResource(file);
        final String fileName = Paths.get(path.toURI()).toFile().getAbsolutePath();
        return Parser.readFile(fileName);
    }

    @Test
    public void testA() throws Exception {
        final State a = getA();
        final Solver solver = new Solver(new BasicScheduler());
        solver.solve(a);

        System.out.println();
        System.out.println(a.score());
        System.out.println(a.outputRides());
    }
}
