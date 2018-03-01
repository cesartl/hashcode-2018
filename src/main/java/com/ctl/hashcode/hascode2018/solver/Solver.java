package com.ctl.hashcode.hascode2018.solver;

import com.ctl.hashcode.hascode2018.model.State;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Solver {

    private final Scheduler scheduler;

    public State solve(State state) {
        while (state.getRideRequests().isEmpty() && state.getCurrentStep() <= state.getSteps()) {

        }
        return null;
    }

}
