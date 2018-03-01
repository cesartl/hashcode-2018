package com.ctl.hashcode.hascode2018.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Car {
    private int id;

    @Singular
    private List<Ride> rides;

    public Ride getLastRide() {
        return rides.get(rides.size() -1);
    }

    public Quote quote(RideRequest rideRequest) {
        //TODO check
        final Ride lastRide = getLastRide();
        final int travelTime = Position.distance(lastRide.getRideRequest().getTo(), rideRequest.getFrom());
        final long arriveAt = lastRide.getEnd() + travelTime;
        return Quote.builder()
                .rideRequest(rideRequest)
                .arriveAt(Math.max(arriveAt, rideRequest.getEarliest()))
                .waitFor(Math.max(0, rideRequest.getEarliest() - arriveAt))
                .carId(id)
                .build();
    }

    public static Car init(int id) {
        final RideRequest rideRequest = RideRequest.builder()
                .to(Position.builder().x(0).y(0).build())
                .earliest(0)
                .latest(0)
                .build();
        final Ride ride = Ride.builder().rideRequest(rideRequest)
                .end(0)
                .carId(id)
                .build();
        return Car.builder().id(id)
                .ride(ride)
                .build();
    }
}
