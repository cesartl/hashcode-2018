package com.ctl.hashcode.hascode2018.model;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class Car {
    private int id;
    private List<Ride> rides;

    public static Car empty(int id) {
        return new Car(id, new ArrayList<>());
    }

    public Ride getLastRide() {
        return rides.get(rides.size());
    }

    public Quote quote(RideRequest rideRequest) {
        //TODO check
        final Ride lastRide = getLastRide();
        final int travelTime = Position.distance(lastRide.getRideRequest().getTo(), rideRequest.getFrom());
        final long arriveAt = lastRide.getEnd() + travelTime;
        return Quote.builder()
                .arriveAt(Math.max(arriveAt, rideRequest.getEarliest()))
                .waitFor(Math.min(0, rideRequest.getEarliest() - arriveAt))
                .carId(id)
                .build();
    }
}
