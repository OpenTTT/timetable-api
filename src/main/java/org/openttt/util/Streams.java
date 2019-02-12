package org.openttt.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Streams {
    private Streams() {}

    public static <T> Stream<T> stream(Iterable<T> iterable, boolean parallel) {
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return stream(iterable, true);
    }
}
