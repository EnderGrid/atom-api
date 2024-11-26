package net.endergrid.atom.executor.grouped;

import net.endergrid.atom.executor.AtomExecutor;
import org.jetbrains.annotations.NotNull;

/**
 * Executes a grouped task using the AtomExecutor.
 * The point of grouped tasks is that, it'll only execute that task if no other task with the same group id is currently running.
 *
 * @param <T> the type of the task
 */
public interface AtomGroupedExecutor<T> extends AtomExecutor {
    /**
     * Executes the specified grouped task using the AtomExecutor.
     *
     * @param task the grouped task to execute
     */
    void execute(@NotNull GroupedRunnable<T> task);
}
