package net.endergrid.atom.executor.task;

import lombok.NonNull;

import java.util.concurrent.TimeUnit;

public interface AtomTaskScheduled extends AtomTask {
    /**
     * Updates the interval for the scheduled task.
     *
     * @param interval the new interval value
     * @param unit the time unit of the interval
     * @return true if the interval was successfully updated, false otherwise
     */
    boolean updateInterval(long interval, @NonNull TimeUnit unit);
}
