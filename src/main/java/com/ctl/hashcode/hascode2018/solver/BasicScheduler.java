package com.ctl.hashcode.hascode2018.solver;

import com.ctl.hashcode.hascode2018.model.Quote;
import com.ctl.hashcode.hascode2018.model.Ride;
import com.ctl.hashcode.hascode2018.model.RideRequest;
import com.ctl.hashcode.hascode2018.model.State;

import java.util.Comparator;
import java.util.Optional;

public class BasicScheduler implements Scheduler {

    @Override
    public Optional<Ride> schedule(State state, RideRequest rideRequest) {
        //1 find the cars that can be here on time
        return state.getCars().values().stream()
                .map(car -> car.quote(rideRequest))
                .min(Comparator.comparing(Quote::getArriveAt)
                        .thenComparing(Quote::wasted))
                .map(quote -> Ride.builder()
                        .carId(quote.getCarId())
                        .rideRequest(rideRequest)
                        .start(quote.getArriveAt())
                        .end(rideRequest.distance() + quote.getArriveAt())
                        .build());
    }

}
