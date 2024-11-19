package net.endergrid.atom.event.handler;

import net.endergrid.atom.event.AtomEventResult;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface AtomEventHandler<EVENT> {
    @FunctionalInterface
    interface Async<T> extends AtomEventHandler<T> {
        CompletableFuture<AtomEventResult> handle(@NonNull T event, @NonNull AtomEventResult currentResult);
    }

    @FunctionalInterface
    interface Sync<T> extends AtomEventHandler<T> {
        AtomEventResult handle(@NonNull T event, @NonNull AtomEventResult currentResult);
    }
}
