package com.ctl.hashcode.hascode2018;

import com.ctl.hashcode.hascode2018.model.State;
import com.ctl.hashcode.hascode2018.parser.Parser;
import com.ctl.hashcode.hascode2018.solver.BasicScheduler;
import com.ctl.hashcode.hascode2018.solver.Solver;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class TestSolutions {

    public State getA() throws Exception {
        String file = "/inputs/a_example.in";
        URL path = TestParser.class.getResource(file);
        final String fileName = Paths.get(path.toURI()).toFile().getAbsolutePath();
        return Parser.readFile(fileName);
    }

    public State getB() throws Exception {
        String file = "/inputs/b_should_be_easy.in";
        URL path = TestParser.class.getResource(file);
        final String fileName = Paths.get(path.toURI()).toFile().getAbsolutePath();
        return Parser.readFile(fileName);
    }

    public State getC() throws Exception {
        String file = "/inputs/c_no_hurry.in";
        URL path = TestParser.class.getResource(file);
        final String fileName = Paths.get(path.toURI()).toFile().getAbsolutePath();
        return Parser.readFile(fileName);
    }

    public State getD() throws Exception {
        String file = "/inputs/d_metropolis.in";
        URL path = TestParser.class.getResource(file);
        final String fileName = Paths.get(path.toURI()).toFile().getAbsolutePath();
        return Parser.readFile(fileName);
    }

    public State getE() throws Exception {
        String file = "/inputs/e_high_bonus.in";
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
        System.out.println();
        System.out.println(a.outputRides());
    }

    @Test
    public void solveAll() throws Exception {
        solve(getB(), "b.solution");
        solve(getC(), "c.solution");
        solve(getD(), "d.solution");
        solve(getE(), "e.solution");
    }



    private void solve(State state, String fileName) throws IOException {
        final Solver solver = new Solver(new BasicScheduler());
        solver.solve(state);
        FileUtils.write(new File(fileName), state.outputRides(), Charset.defaultCharset());
    }
}
