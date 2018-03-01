package com.ctl.hashcode.hascode2018.solver;

import com.ctl.hashcode.hascode2018.model.Ride;
import com.ctl.hashcode.hascode2018.model.RideRequest;
import com.ctl.hashcode.hascode2018.model.State;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Solver {

    private final Scheduler scheduler;

    public void solve(State state) {
        while (state.getRideRequests().isEmpty()) {
            final RideRequest rideRequest = state.getRideRequests().pop();
            final Ride ride = scheduler.schedule(state, rideRequest);
            state.addRide(ride);
        }
    }

}
