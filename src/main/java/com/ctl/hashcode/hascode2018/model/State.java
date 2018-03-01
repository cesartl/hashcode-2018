package com.ctl.hashcode.hascode2018.model;

import com.google.common.collect.ListMultimap;
import javafx.geometry.Pos;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@ToString
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

    public long score() {
        return cars.values().stream()
                .flatMap(c -> c.getRides().stream())
                .map(r -> r.score(bonus))
                .mapToLong(s -> s)
                .sum();
    }

    public void sortRides() {

        this.rideRequests = rideRequests.stream()
                .sorted(Comparator.comparing(RideRequest::getEarliest).thenComparing(RideRequest::antiDistance))
                .collect(Collectors.toCollection(LinkedList::new));
//
//        this.rideRequests = rideRequests.stream()
//                .sorted(Comparator.comparing(RideRequest::getEarliest)
//                        .thenComparing(rideRequest -> -rideRequest.distance()))
//                .collect(Collectors.toCollection(LinkedList::new));
    }

    public String outputRides() {
        StringBuilder sb = new StringBuilder();
        for (Car car : cars.values()) {
            sb.append(car.getRides().size());
            sb.append(" ");
            for (Ride ride : car.getRides()) {
                sb.append(ride.getRideRequest().getId());
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
