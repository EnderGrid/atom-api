package net.endergrid.atom.event.bus;

/**
 * Represents an event bus that handles events of a specific type.
 *
 * @param <EVENT> the type of event this bus handles
 */
public interface AtomEventBus<EVENT> extends AtomEventRegistrable<EVENT> {
    /**
     * Gets the class of the event this bus handles.
     *
     * @return the event class
     */
    Class<EVENT> getEventClass();

    default boolean handlesEvent(Object event) {
        return this.getEventClass().isInstance(event);
    }

    default boolean handlesEventClass(Class<?> eventClass) {
        return this.getEventClass().isAssignableFrom(eventClass);
    }
}