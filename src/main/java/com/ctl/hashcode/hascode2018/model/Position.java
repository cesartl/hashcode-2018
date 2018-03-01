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
}
