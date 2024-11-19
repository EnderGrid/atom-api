package net.endergrid.atom.event.handler;

import net.endergrid.atom.event.AtomEventPriority;
import dev.oop778.bindings.type.Bindable;
import lombok.NonNull;
import org.jetbrains.annotations.CheckReturnValue;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface for parameters required to register an event handler.
 *
 * @param <EVENT> the type of event the handler processes
 */
public interface AtomEventHandlerRegistration<EVENT> extends Bindable {
    /**
     * Gets the class of the event the handler processes.
     *
     * @return the event class
     */
    Class<? extends EVENT> getEventClass();

    /**
     * Gets the priority of the event handler.
     *
     * @return the priority
     */
    AtomEventPriority getPriority();

    /**
     * Gets the event handler.
     *
     * @return the handler
     */
    AtomEventHandler<? super EVENT> getHandler();

    /**
     * Gets the filter predicate for the event handler.
     *
     * @return the filter predicate
     */
    Predicate<? super EVENT> getFilter();

    /**
     * Gets the creation stack for the event handler.
     *
     * @return the creation stack
     */
    List<String> getCreationStack();

    /**
     * Gets the creation time of the event handler registration in nanoseconds.
     *
     * @return the creation time in nanoseconds
     */
    Long getCreationTimeNs();

    /**
     * Builder interface for creating handler registration parameters.
     *
     * @param <EVENT> the type of event the handler processes
     */
    interface Builder<EVENT> {
        /**
         * Sets the event class for the handler.
         *
         * @param eventClass the event class
         * @return new builder instance
         */
        @CheckReturnValue
        Builder<EVENT> withEvent(@NonNull Class<? extends EVENT> eventClass);

        /**
         * Sets the priority for the handler.
         *
         * @param priority the priority
         * @return new builder instance
         */
        @CheckReturnValue
        Builder<EVENT> withPriority(@NonNull AtomEventPriority priority);

        /**
         * Sets a filter predicate for the handler.
         *
         * @param filter the filter predicate
         * @return new builder instance
         */
        @CheckReturnValue
        Builder<EVENT> withFilter(@NonNull Predicate<? super EVENT> filter);

        /**
         * Sets an asynchronous event handler for the registration.
         *
         * @param handler the asynchronous event handler to be registered
         * @return new builder instance
         */
        @CheckReturnValue
        Builder<EVENT> withAsyncHandler(@NonNull AtomEventHandler.Async<? super EVENT> handler);

        /**
         * Sets a synchronous event handler for the registration.
         *
         * @param handler the synchronous event handler to be registered
         * @return new builder instance
         */
        @CheckReturnValue
        Builder<EVENT> withSyncHandler(@NonNull AtomEventHandler.Sync<? super EVENT> handler);

        /**
         * Builds the registration for this handler
         */
        AtomEventHandlerRegistration<EVENT> build();
    }
}
