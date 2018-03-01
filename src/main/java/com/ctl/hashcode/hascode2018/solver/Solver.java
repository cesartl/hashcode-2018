package com.ctl.hashcode.hascode2018.solver;

import com.ctl.hashcode.hascode2018.model.Ride;
import com.ctl.hashcode.hascode2018.model.RideRequest;
import com.ctl.hashcode.hascode2018.model.State;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class Solver {

    private final Scheduler scheduler;

    public void solve(State state) {
        state.sortRides();
        while (!state.getRideRequests().isEmpty()) {
            final RideRequest rideRequest = state.getRideRequests().pop();
            Optional<Ride> ride = scheduler.schedule(state, rideRequest);
            if (ride.isPresent()) {
                state.addRide(ride.get());
            } else {
                System.out.println("No ride!!!!!!");
                break;
            }
        }
    }

}
