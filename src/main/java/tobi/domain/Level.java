package tobi.domain;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Level {
    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

    private final int value;
    private final Level next;

    Level(int value, Level next) {
        this.value = value;
        this.next = next;
    }

    private static final Map<Integer, Level> convertMap = Stream.of(Level.values())
            .collect(Collectors.toMap(Level::getValue, Function.identity()));

    public static Level valueOf(int value) {
        if (convertMap.containsKey(value)) {
            return convertMap.get(value);
        }
        throw new AssertionError("Unknown value: " + value);
    }


    public int getValue() {
        return value;
    }

    public Level getNext() {
        return next;
    }
}
