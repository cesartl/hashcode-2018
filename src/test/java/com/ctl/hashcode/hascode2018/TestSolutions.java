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
import java.text.MessageFormat;

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
    public void solveC_forever() throws Exception {
        State c = solve(getC(), "c.solution", true);
        for (;;) c = State.getBetterMutation(c,"c.working.solution");
    }

    @Test
    public void solveD_forever() throws Exception {
        State d = solve(getD(), "d.solution", true);
        for (;;) d = State.getBetterMutation(d,"d.working.solution");
    }


    @Test
    public void solveAll() throws Exception {
        boolean write = true;
        long total = 0;
        long total2 = 0;



        System.out.println("A");
        State a = solve(getA(), "a.solution", write);
        System.out.println("B");
        State b = solve(getB(), "b.solution", write);
        System.out.println("C");
        State c = solve(getC(), "c.solution", write);
        System.out.println("D");
        State d = solve(getD(), "d.solution", write);
        System.out.println("E");
        State e = solve(getE(), "e.solution", write);

        total += b.score();
        total += c.score();
        total += d.score();
        total += e.score();
        System.out.println(MessageFormat.format("{0}", total));
        System.out.println("----evolve----");

        System.out.println("----evolving a---- trivial");
        for (int i = 0; i < 10; i++) a = State.getBetterMutation(a, "a.working.solution");
        System.out.println("----evolving b---- not needed");
        for (int i = 0; i <= 100_000; i++) b = State.getBetterMutation(b, "b.working.solution");
        System.out.println("----evolving c----");
        for (int i = 0; i <= 1_000_000; i++) c = State.getBetterMutation(c,"c.working.solution");
        System.out.println("----evolving d----");
        for (int i = 0; i <= 1_000_000; i++) d = State.getBetterMutation(d, "d.working.solution");
        System.out.println("----evolving e----");
        for (int i = 0; i <= 1_000_000; i++) e = State.getBetterMutation(e,"e.working.solution");
        System.out.println("----evolved----");
        total2 += b.score();
        total2 += c.score();
        total2 += d.score();
        total2 += e.score();

        FileUtils.write(new File("a_evolved.solution"), a.outputRides(), Charset.defaultCharset());
        FileUtils.write(new File("b_evolved.solution"), b.outputRides(), Charset.defaultCharset());
        FileUtils.write(new File("c_evolved.solution"), c.outputRides(), Charset.defaultCharset());
        FileUtils.write(new File("d_evolved.solution"), d.outputRides(), Charset.defaultCharset());
        FileUtils.write(new File("e_evolved.solution"), e.outputRides(), Charset.defaultCharset());

        System.out.println(MessageFormat.format("{0}", a.score()));
        System.out.println(MessageFormat.format("{0}", b.score()));
        System.out.println(MessageFormat.format("{0}", c.score()));
        System.out.println(MessageFormat.format("{0}", d.score()));
        System.out.println(MessageFormat.format("{0}", e.score()));
        System.out.println(MessageFormat.format("{0}", total2));
    }


    private State solve(State state, String fileName, boolean write) throws IOException {
        final Solver solver = new Solver(new BasicScheduler());
        solver.solve(state);
        if (write) {
            FileUtils.write(new File(fileName), state.outputRides(), Charset.defaultCharset());
        }
//        System.out.println("-----------");
//        state.getCars().values().stream()
//                .map(c -> c.getRides().size())
//                .forEach(c -> System.out.println("Rides: " + c));
//        System.out.println("-----------");
        final long score = state.score();
        System.out.println(MessageFormat.format("{0}", score));
        return state;
    }
}
