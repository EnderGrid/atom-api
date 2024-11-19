package net.endergrid.atom.event.bus;

import net.endergrid.atom.Atom;
import net.endergrid.atom.event.AtomEventResult;
import net.endergrid.atom.event.handler.AtomEventHandlerRegistration;
import lombok.NonNull;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.UnaryOperator;

/**
 * Provides an interface for registering and managing event handlers in an event bus.
 *
 * @param <EVENT> the type of events handled by this registration holder
 */
public interface AtomEventRegistrable<EVENT> {
    /**
     * Posts an event synchronously, blocking until all handlers have been executed.
     *
     * @param event the event to post
     * @return the result of the event processing
     */
    @Blocking
    AtomEventResult postSync(EVENT event);

    /**
     * Posts an event asynchronously, executing handlers in a separate thread.
     *
     * @param event    the event to post
     * @param executor the executor to run the handlers, or null to use the default
     * @return a CompletableFuture representing the result of the event processing
     */
    CompletableFuture<AtomEventResult> postAsync(EVENT event, @Nullable Executor executor);

    /**
     * Registers an event handler with the bus.
     *
     * @param <E>          the type of event the handler processes
     * @param registration the handler registration
     * @return a registration object for the handler
     */
    <E extends EVENT> AtomEventHandlerRegistration<E> registerHandler(@NonNull AtomEventHandlerRegistration<E> registration);

    /**
     * Registers an event handler with the event bus.
     *
     * @param <E>     the type of event the handler processes
     * @param builder a function that configures the event handler registration
     * @return a registration object for the handler
     */
    default <E> AtomEventHandlerRegistration<E> registerHandler(@NonNull UnaryOperator<AtomEventHandlerRegistration.Builder<E>> builder) {
        return this.registerHandler(builder.apply(Atom.get().getObjectFactory().create(AtomEventHandlerRegistration.Builder.class)).build());
    }
}
