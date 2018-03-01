package com.ctl.hashcode.hascode2018.model;

import com.google.common.collect.ListMultimap;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.ArrayList;
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
    @Singular
    private Map<Integer, Car> cars;

    public ListMultimap<Integer, Integer> computeRides() {
        return null;
    }

    public void addRide(Ride ride) {
        final Car car = cars.get(ride.getCarId());
        final ArrayList<Ride> newRides = new ArrayList<>(car.getRides());
        newRides.add(ride);
        car.setRides(newRides);
    }

    public long score(){
        return 0;
    }
}
