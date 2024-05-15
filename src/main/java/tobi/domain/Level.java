package tobi.domain;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Level {
    BASIC(1), SILVER(2), GOLD(3);

    private final int value;

    private static final Map<Integer, Level> convertMap = Stream.of(Level.values())
            .collect(Collectors.toMap(Level::getValue, Function.identity()));

    Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Level valueOf(int value) {
        if(convertMap.containsKey(value)) {
            return convertMap.get(value);
        }
        throw new AssertionError("Unknown value: " + value);
    }
}
