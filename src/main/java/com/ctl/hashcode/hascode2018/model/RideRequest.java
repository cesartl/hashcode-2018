package com.ctl.hashcode.hascode2018.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RideRequest {
    private final int id;
    private final Position from;
    private final Position to;
    private final long earliest;
    private final long latest;
}
