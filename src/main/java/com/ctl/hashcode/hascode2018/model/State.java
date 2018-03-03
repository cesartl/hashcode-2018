package com.ctl.hashcode.hascode2018.model;

import com.google.common.collect.ListMultimap;
import lombok.*;

import java.util.*;
import java.util.function.Function;
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
    final static Random rn = new Random();


    static State getMutation(State s) {

        State copy = State.builder().
                rows(s.rows).
                cols(s.cols).
                numberOfCars(s.numberOfCars).
                rideRequests(s.rideRequests).
                bonus(s.bonus).
                steps(s.steps).build();

        return copy.mutate();
    }

    static public State getBetterMutation(State s) {

        Long current =  s.score();
        Long improved = 0L;
        State evolved = null;
        long counter = 0L;


        while (improved <= current) {
            evolved = s.mutate();
            improved = evolved.score();
            counter += 1;
        }

        return evolved;
    }

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
                .filter(r -> r.getEnd() <= steps)
                .map(r -> r.score(bonus))
                .mapToLong(s -> s)
                .sum();
    }

    public State mutate() {
        Car car1 = cars.get(rn.nextInt(cars.size()));
        Car car2 = cars.get(rn.nextInt(cars.size()));
        ArrayList<Ride> rides1 = new ArrayList<>(car1.getRides());
        ArrayList<Ride> rides2 = new ArrayList<>(car2.getRides());

        if (rides1.size() > 0) {
            Ride ride = rides1.get(rn.nextInt(rides1.size()));
            rides1.remove(ride);
            rides2.add(ride);
            car1.setRides(rides1);
            car2.setRides(rides2);
        }
        return this;
    }

    private Optional<Quote> bestQuote(RideRequest r) {
        return getCars().values().stream()
                .map(car -> car.quote(r))
                .min(Comparator.comparing(Quote::getArriveAt)
                        .thenComparing(Quote::wasted));
    }

    private long sort(RideRequest r) {
        return bestQuote(r).map(Quote::getWaitFor).orElse(Long.MAX_VALUE);
    }

    public void sortRides() {

//        this.rideRequests = rideRequests.stream()
//                .sorted(
//                        Comparator.comparing(this::sort)
//                                .thenComparing(RideRequest::getEarliest)
//                                .thenComparing(RideRequest::antiDistance))
//                .collect(Collectors.toCollection(LinkedList::new));
//
        this.rideRequests = rideRequests.stream()
                .sorted(Comparator.comparing(RideRequest::getEarliest)
                        .thenComparing(rideRequest -> -rideRequest.distance()))
                .collect(Collectors.toCollection(LinkedList::new));


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
