package net.endergrid.atom.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the priority levels for events
 * The priority determines the order in which event handlers are executed.
 * The {@link Default} enum provides some common priority levels.
 * <p>
 * You can create your own priority levels by implementing the {@link AtomEventPriority} interface.
 */
public interface AtomEventPriority {
    public long getPriority();

    @RequiredArgsConstructor
    @Getter
    public enum Default implements AtomEventPriority {
        FIRST(Long.MIN_VALUE),
        NORMAL(Long.MAX_VALUE / 2),
        LAST(Long.MAX_VALUE);

        private final long priority;
    }
}
