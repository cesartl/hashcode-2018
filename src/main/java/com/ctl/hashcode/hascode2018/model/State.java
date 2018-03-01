package com.ctl.hashcode.hascode2018.model;

import com.google.common.collect.ListMultimap;
import lombok.Builder;
import lombok.Value;

import java.util.Deque;
import java.util.List;

@Value
@Builder
public class State {
    private int rows;
    private int cols;
    private int numberOfCars;
    private Deque<RideRequest> rideRequests;
    private int bonus;
    private long steps;
    private long currentStep;
    private List<Car> cars;

    public ListMultimap<Integer, Integer> computeRides() {
        return null;
    }
}
