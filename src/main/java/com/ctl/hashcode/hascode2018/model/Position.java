package com.ctl.hashcode.hascode2018.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Position {
    private final int x;
    private final int y;

    public int distanceWith(Position other){
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    public static int distance(Position p1, Position p2){
        return p1.distanceWith(p2);
    }
}
