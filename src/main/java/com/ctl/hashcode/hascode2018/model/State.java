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
        Map<Integer, Car> ncars = new HashMap<>();

        s.cars.forEach((k, car) -> {
                    final ArrayList<Ride> newRides = new ArrayList<>(car.getRides());
                    Car cc = Car.builder()
                            .id(car.getId())
                            .rides(newRides)
                            .build();
                    ncars.put(k,cc);
                }
        );

        State copy = State.builder().
                rows(s.rows).
                cols(s.cols).
                numberOfCars(s.numberOfCars).
                rideRequests(s.rideRequests).
                bonus(s.bonus).
                cars(ncars).
                steps(s.steps).build();

        return copy.mutate();
    }

    static public State getBetterMutation(State s) {
        final Long current = s.score();
        Long improved = 0L;
        State evolved = null;
        int count = 0;

        while (improved <= current && count < 2) {
            evolved = State.getMutation(s).mutate();
            evolved.recalc_rides();
            improved = evolved.score();
            //System.out.println(evolved.outputRides());
            count = count + 1;
            //System.out.println(improved + " <= " + current);
        }

        ///System.out.println("!!!!" + count);
        if (count >= 2) {
            System.out.println("!!!!" + s.score());
            return s;
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

    public void replaceRide(Ride ride1, Ride ride2) {
        final Car car = cars.get(ride2.getCarId());
        final ArrayList<Ride> newRides = new ArrayList<>(car.getRides());
        newRides.remove(ride1);
        newRides.add(ride2);
        car.setRides(newRides);
    }

    public void removeRide(Ride ride) {
        final Car car = cars.get(ride.getCarId());
        final ArrayList<Ride> newRides = new ArrayList<>(car.getRides());
        newRides.remove(ride);
        car.setRides(newRides);
    }


    public void recalc_rides() {
        for (Integer key : cars.keySet()) {
            Car car = cars.get(key);
            Car new_car = Car.init(key);
            List<Ride> old_rides = car.getRides();

            for (Ride r : old_rides) {
                RideRequest rideRequest = r.getRideRequest();
                Quote quote = new_car.quote(r.getRideRequest());
                Ride new_ride = Ride.builder()
                        .carId(quote.getCarId())
                        .rideRequest(rideRequest)
                        .start(quote.getArriveAt())
                        .end(rideRequest.distance() + quote.getArriveAt())
                        .build();
                addRideToCar(new_car, new_ride);
                this.replaceRide(r, new_ride);
            }

        }

    }

    public void addRideToCar(Car car, Ride ride) {
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
        if (car1.getId() == car2.getId()) return this;
        ArrayList<Ride> rides1 = new ArrayList<>(car1.getRides());
        ArrayList<Ride> rides2 = new ArrayList<>(car2.getRides());

        if (rides1.size() > 0 && rides2.size() > 0) {
            Ride ride = rides1.get(rn.nextInt(rides1.size()));
            rides1.remove(ride);
            rides2.add(rn.nextInt(rides2.size()),ride);
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
