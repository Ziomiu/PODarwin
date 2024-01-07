package agh.ics.oop.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record Pair<T>(T first, T second) {
    public List<T> getAsList() {
        return Stream.of(first, second).filter(Objects::nonNull).toList();
    }
}
