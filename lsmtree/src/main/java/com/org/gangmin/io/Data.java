package com.org.gangmin.io;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Data<T> {
    private Long id;
    private T object;
}
