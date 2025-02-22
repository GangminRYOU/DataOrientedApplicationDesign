package com.org.gangmin.place;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Location {
    private String name;
    private List<String> attractions;
}
