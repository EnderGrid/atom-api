package net.endergrid.atom.event;

import lombok.NonNull;
import net.endergrid.atom.event.handler.AtomEventHandler;
import org.jetbrains.annotations.Blocking;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface AtomEventContext<EVENT> {
    EVENT getEvent();

    /**
     * Returns the latest result of the event
     */
    @NonNull
    AtomEventResult getResult();

    /**
     * Await the completion of the chain
     */
    @Blocking
    AtomEventResult await();

    /**
     * Advance the chain with the previous result.
     */
    AtomEventHandler.Continuation advance();

    /**
     * Advance the chain with a given result, passing it on to the next handler.
     */
    AtomEventHandler.Continuation advanceWith(@NonNull AtomEventResult result);

    /**
     * Advance the chain due to an encountered exception.
     */
    AtomEventHandler.Continuation advanceWithError(Exception exception);

    /**
     * Advance the chain once the given future completes.
     */
    AtomEventHandler.Continuation advanceOnCompletion(CompletableFuture<Void> future);

    /**
     * Advance the chain once the given future completes with an AtomEventResult.
     */
    AtomEventHandler.Continuation advanceOnResult(CompletableFuture<AtomEventResult> future);

    /**
     * Registers a callback once the chain finishes successfully.
     */
    void onSuccess(Consumer<AtomEventResult> action);

    /**
     * When the event chain fails to finish due to errors.
     * <p>
     * {@code NOTE}: The caller decides weather error cancels the chain or continues, if the caller of the event allows errors to not disturb the chain, this method will never be called.
     */
    void onFailure(BiConsumer<AtomEventResult, Throwable> action);

    boolean isCompleted();
}
