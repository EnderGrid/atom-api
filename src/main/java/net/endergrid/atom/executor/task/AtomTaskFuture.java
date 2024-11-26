package net.endergrid.atom.executor.task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface AtomTaskFuture<T> extends AtomTask, Future<T> {

    /**
     * Returns a {@link CompletableFuture} representation of this {@link AtomTaskFuture}.
     * This allows the task to be used in a CompletableFuture-based asynchronous programming model.
     *
     * @return a {@link CompletableFuture} representation of this {@link AtomTaskFuture}
     */
    CompletableFuture<T> asCompletableFuture();
}
