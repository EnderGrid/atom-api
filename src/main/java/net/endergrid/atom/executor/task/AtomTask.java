package net.endergrid.atom.executor.task;

import dev.oop778.bindings.type.Bindable;
import net.endergrid.atom.executor.AtomExecutor;
import net.endergrid.atom.executor.AtomExecutorFactory;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a task that can be executed by an {@link AtomExecutor}.
 * This interface provides methods to manage the lifecycle of the task, such as
 * checking if the task is running, completed, or cancelled, and cancelling the
 * task if needed.
 */
public interface AtomTask extends Bindable, Runnable {
    static AtomTaskBuilder.Selector builder() {
        return AtomExecutorFactory.get().createTaskBuilder();
    }

    static AtomTaskBuilder.ScheduledStage scheduled() {
        return builder().scheduled();
    }

    static AtomTaskBuilder.BuildStage immediate() {
        return builder().immediate();
    }

    /**
     * Gets the name of the task, if available.
     *
     * @return the name of the task, or {@code null} if the name is not available.
     */
    @Nullable
    String getName();

    /**
     * Checks if the task has been completed.
     *
     * @return {@code true} if the task has been completed, {@code false} otherwise.
     */
    boolean isDone();

    /**
     * Checks if the task is currently running.
     *
     * @return {@code true} if the task is currently running, {@code false} otherwise.
     */
    boolean isRunning();

    /**
     * Checks if the task is currently cancelled.
     * @return {@code true} if the task is currently cancelled, {@code false} otherwise.
     */
    boolean isCancelled();

    /**
     * Cancels the task, optionally interrupting it if it is currently running.
     *
     * @param mayInterruptIfRunning if {@code true}, the thread executing this
     *                              task should be interrupted; otherwise, in-flight tasks are allowed
     *                              to complete. NOTE: Not every executor supports interruption
     * @return {@code true} if this task is successfully cancelled
     */
    boolean cancel(boolean mayInterruptIfRunning);
}
