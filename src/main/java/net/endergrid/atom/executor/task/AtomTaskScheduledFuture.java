package net.endergrid.atom.executor.task;

import lombok.NonNull;

public interface AtomTaskScheduledFuture<T> extends AtomTaskScheduled, AtomTaskFuture<T> {
    /**
     * Completes the task with the given value.
     *
     * @param value the value to complete the task with
     * @return true if the task was successfully completed, false otherwise
     */
    boolean complete(@NonNull T value);
}
