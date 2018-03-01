package com.ctl.hashcode.hascode2018.model;

import com.google.common.collect.ListMultimap;
import lombok.Builder;
import lombok.Value;

import java.util.Deque;
import java.util.Map;

@Value
@Builder
public class State {
    private int rows;
    private int cols;
    private int numberOfCars;
    private Deque<RideRequest> rideRequests;
    private int bonus;
    private long steps;
    /**
     * Cars by ID
     */
    private Map<Integer, Car> cars;

    public ListMultimap<Integer, Integer> computeRides() {
        return null;
    }

    public void addRide(Ride ride) {
        cars.get(ride.getCarId()).getRides().add(ride);
    }
}
