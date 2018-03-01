package com.ctl.hashcode.hascode2018.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Ride {
    private int carId;
    private RideRequest rideRequest;
    private long start;
    private long end;

}
