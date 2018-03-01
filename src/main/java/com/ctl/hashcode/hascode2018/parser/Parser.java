package com.ctl.hashcode.hascode2018.parser;

import com.ctl.hashcode.hascode2018.model.Position;
import com.ctl.hashcode.hascode2018.model.RideRequest;
import com.ctl.hashcode.hascode2018.model.State;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;


public class Parser {
    public static State readFile(String filename) throws Exception {
        FileReader in = new FileReader(filename);
        List<String> lines = IOUtils.readLines(in);

        String header = lines.get(0);
        List<String> rides = lines.subList(1, lines.size());

        String[] headerValues = header.split(" ");
        int rows = Integer.parseInt(headerValues[0]);
        int cols = Integer.parseInt(headerValues[1]);
        int numCars = Integer.parseInt(headerValues[2]);
        int numRides = Integer.parseInt(headerValues[3]);
        int bonus = Integer.parseInt(headerValues[4]);
        long steps = Integer.parseInt(headerValues[5]);

        if (numRides != rides.size()) {
            throw new IllegalMonitorStateException("Number of lines does not match expected rides");
        }

        int id = 0;
        Deque<RideRequest> rideRequests = new ArrayDeque<>();
        for (String ride : rides) {
            String[] rideValues = ride.split(" ");
            int r1 = Integer.parseInt(rideValues[0]);
            int c1 = Integer.parseInt(rideValues[1]);
            int r2 = Integer.parseInt(rideValues[2]);
            int c2 = Integer.parseInt(rideValues[3]);
            int e = Integer.parseInt(rideValues[4]);
            int l = Integer.parseInt(rideValues[5]);

            Position start = Position.builder()
                    .x(c1)
                    .y(r1)
                    .build();
            Position end = Position.builder()
                    .x(c2)
                    .y(r2)
                    .build();

            RideRequest r = RideRequest.builder()
                    .id(id)
                    .from(start)
                    .to(end)
                    .earliest(e)
                    .latest(l)
                    .build();
            rideRequests.add(r);
            ++id;
        }

        State state = State.builder()
                .rows(rows)
                .cols(cols)
                .numberOfCars(numCars)
                .bonus(bonus)
                .steps(steps)
                .rideRequests(rideRequests)
                .build();
        return state;
    }
}
