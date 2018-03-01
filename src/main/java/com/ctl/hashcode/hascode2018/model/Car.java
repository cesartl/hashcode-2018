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
}
