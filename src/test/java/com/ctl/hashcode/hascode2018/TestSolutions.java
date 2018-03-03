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
import java.util.concurrent.*;

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
        boolean write = false;
        long total = 0;
        long total2 = 0;


        State b = solve(getB(), "b.solution", write);
        State c = solve(getC(), "c.solution", write);
        State d = solve(getD(), "d.solution", write);
        State e = solve(getE(), "e.solution", write);

        total += b.score();
        total += c.score();
        total += d.score();
        total += e.score();
        System.out.println(MessageFormat.format("{0}", total));
        System.out.println("----evolve----");

        final ExecutorService executorService = Executors.newFixedThreadPool(2);

        final Future<State> evolveC = evolve(c, executorService, 1000);
        final Future<State> evolveD = evolve(d, executorService, 1000);

        c = evolveC.get();
        d = evolveD.get();

        total2 += b.score();
        total2 += c.score();
        total2 += d.score();
        total2 += e.score();

        System.out.println(MessageFormat.format("{0}", b.score()));
        System.out.println(MessageFormat.format("{0}", c.score()));
        System.out.println(MessageFormat.format("{0}", d.score()));
        System.out.println(MessageFormat.format("{0}", e.score()));
        System.out.println(MessageFormat.format("{0}", total2));
    }

    private Future<State> evolve(State state, ExecutorService executor, int iterations) {
        return executor.submit(() -> {
            State s = state;
            for (int i = 0; i <= iterations; i++) s = State.getBetterMutation(s);
            return s;
        });
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
