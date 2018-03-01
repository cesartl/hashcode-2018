package com.ctl.hashcode.hascode2018.solver;

import com.ctl.hashcode.hascode2018.model.Ride;
import com.ctl.hashcode.hascode2018.model.RideRequest;
import com.ctl.hashcode.hascode2018.model.State;

import java.util.Optional;

public interface Scheduler {
    Optional<Ride> schedule(State state, RideRequest rideRequest);
}
