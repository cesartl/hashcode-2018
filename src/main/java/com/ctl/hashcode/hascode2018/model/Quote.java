package com.ctl.hashcode.hascode2018.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Quote {
    private long arriveAt;
    private long waitFor;
    private int carId;
    private RideRequest rideRequest;
    private long preTravel;

    public long wasted() {
        return waitFor + preTravel;
    }
}
