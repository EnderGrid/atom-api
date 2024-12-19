package net.endergrid.atom.event.group;

import lombok.NonNull;
import net.endergrid.atom.event.AtomEventContext;
import net.endergrid.atom.event.AtomEventObjectFactory;
import net.endergrid.atom.event.handler.AtomEventRegistration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

/**
 * Represents a hierarchical structure of related AtomEvents, where events can be grouped, filtered,
 * and organized into subgroups based on specific criteria.
 * <p>
 * This interface supports dynamic event grouping and contextual subgroup creation for effective event management.
 *
 * @param <E> the type of AtomEvent contained in this group
 */
public interface AtomEventGroup<E> {
    static <E> AtomEventGroup<E> eventClass(@NonNull Class<E> eventClass) {
        return AtomEventObjectFactory.get().createEventGroup(eventClass);
    }

    /**
     * Filters events based on their class type.
     *
     * @param eventClass the class of the event to filter
     * @return {@code true} if the class is accepted by the group, {@code false} otherwise
     */
    boolean acceptsEventClass(Class<?> eventClass);

    /**
     * Retrieves the class type of the events in this group.
     *
     * @return the class type of the events
     */
    Class<E> getAcceptedEventClass();

    /**
     * Retrieves the parent group of this group, if it exists.
     *
     * @return the parent {@link AtomEventGroup}, or {@code null} if this group has no parent
     */
    @Nullable
    AtomEventGroup<?> getParent();

    /**
     * Retrieves the path from the root group to this group.
     *
     * @return a {@link List} of {@link AtomEventGroup} objects representing the hierarchy from
     * the root to this group, including the current group
     */
    List<AtomEventGroup<?>> getHierarchyPath();

    /**
     * Creates a subgroup for the specified subclass.
     *
     * @param subClass the subclass of the events for the subgroup
     * @param <C>      the type of the subclass
     * @return the existing or newly created {@link AtomEventGroup} for the given subclass
     * @throws IllegalArgumentException if the subclass is not compatible with the group's event class
     */
    <C extends E> AtomEventGroup<C> subGroup(@NonNull Class<C> subClass);

    /**
     * Creates a subgroup by extracting a specific value from the event.
     *
     * @param extractor a {@link Function} to extract the relevant value from the event
     * @param equals    the value to match for inclusion in the subgroup
     * @param <T>       the type of the extracted value
     * @return a new {@link AtomEventGroup} representing the subgroup
     */
    <T> AtomEventGroup<E> extractedAndMatchedSubGroup(@NonNull Function<E, T> extractor, @NonNull T equals);

    /**
     * Creates a subgroup by filtering the event based on provided value by the caller
     *
     * @param clazz  the class type to filter by
     * @param equals the value to match for inclusion in the subgroup
     * @param <T>    the type of the provided value
     * @return a new {@link AtomEventGroup} representing the subgroup
     */
    <T> AtomEventGroup<E> providedAndMatchedSubGroup(@NonNull Class<T> clazz, @NotNull T equals);

    /**
     * Filters an event to determine whether it belongs to this group.
     *
     * @param context the event context to filter
     * @return {@code true} if the event is accepted by this group, {@code false} otherwise
     */
    boolean acceptsEvent(AtomEventContext<E> context);

    /**
     * Creates a new builder for registering an event handler with the {@link AtomEventGroup}.
     *
     * @return a new {@link AtomEventRegistration.Builder} instance
     */
    AtomEventRegistration.Builder<E> newRegistration();

    /**
     * Retrieves the root group in the hierarchy. If this group has no parent, it is the root.
     *
     * @return the root {@link AtomEventGroup}
     */
    default AtomEventGroup<?> getRootGroup() {
        AtomEventGroup<?> current = this;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        return current;
    }
}