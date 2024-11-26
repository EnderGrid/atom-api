package net.endergrid.atom.executor;

import dev.oop778.bindings.type.Bindable;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Represents an executor that can execute tasks asynchronously. It extends the {@link Executor} interface
 * and the {@link Bindable} interface, allowing it to be used in a variety of contexts.
 */
public interface AtomExecutor extends Executor, Bindable {

    /**
     * Checks if the current thread is part of the AtomExecutor.
     *
     * @return {@code true} if the current thread is part of the AtomExecutor, {@code false} otherwise.
     */
    boolean isCurrentThreadPartOfExecutor();

    /**
     * Initiates an orderly shutdown of the executor. Previously submitted tasks are executed,
     * but no new tasks will be accepted.
     */
    void shutdown();

    /**
     * Attempts to stop all actively executing tasks, halts the processing of waiting tasks,
     * and returns a list of the tasks that were waiting to be executed.
     *
     * @return a list of tasks that never commenced execution.
     */
    List<Runnable> shutdownNow();

    /**
     * Returns {@code true} if this executor has been shut down.
     *
     * @return {@code true} if this executor has been shut down, {@code false} otherwise.
     */
    boolean isShutdown();

    /**
     * Returns {@code true} if all tasks have completed following shut down.
     * Note that {@code isTerminated} is never {@code true} unless either {@code shutdown} or
     * {@code shutdownNow} was called first.
     *
     * @return {@code true} if all tasks have completed following shut down, {@code false} otherwise.
     */
    boolean isTerminated();

    /**
     * Blocks until all tasks have completed execution after a shutdown request, or the timeout
     * occurs, or the current thread is interrupted, whichever happens first.
     *
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return {@code true} if this executor terminated and {@code false} if the timeout elapsed before termination
     * @throws InterruptedException if interrupted while waiting
     */
    boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
}