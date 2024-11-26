package net.endergrid.atom.executor.task;

import io.vavr.CheckedConsumer;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import lombok.NonNull;
import net.endergrid.atom.executor.AtomExecutor;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Builder interface for creating atomic tasks with various execution patterns.
 * Provides fluent API for both immediate and scheduled task creation.
 */
public interface AtomTaskBuilder {

    /**
     * Initial selector interface to choose between immediate or scheduled task execution.
     */
    interface Selector {
        /**
         * Creates a scheduled task with timing controls.
         * @return ScheduledStage for configuring task timing
         */
        ScheduledStage scheduled();

        /**
         * Creates a task for immediate execution.
         * @return BuildStage for configuring immediate task
         */
        BuildStage immediate();
    }

    /**
     * Configuration stage for scheduled task timing parameters.
     */
    interface ScheduledStage {
        /**
         * Configures a one-time delayed task execution.
         * @param delay time to wait before execution
         * @param unit time unit for the delay
         * @return BuildStage for further task configuration
         */
        ScheduledBuildStage withDelay(long delay, TimeUnit unit);

        /**
         * Configures a recurring task execution at fixed intervals.
         * @param initialDelay time to wait before first execution
         * @param interval time between subsequent executions
         * @param unit time unit for delay and period
         * @return ScheduledStage for further scheduling configuration
         */
        ScheduledBuildStage withFixedRate(long initialDelay, long interval, TimeUnit unit);
    }

    /**
     * Base building stage with common task configuration options.
     * @param <BUILD> type parameter for builder implementation
     */
    interface BaseBuildStage<TASK, BUILD extends BaseBuildStage<TASK, BUILD>> {
        /**
         * Sets a name for the task.
         * @param name task identifier
         * @return builder instance for method chaining
         */
        BUILD withName(@NonNull String name);

        /**
         * Builds a task with standard Runnable.
         * @param task the task to execute
         * @return configured AtomTask
         */
        TASK build(@NonNull Runnable task);

        /**
         * Builds a task with exception handling support.
         * @param task the task to execute that may throw checked exceptions
         * @return configured AtomTask
         */
        TASK buildCatching(@NonNull CheckedRunnable task);
    }

    /**
     * Building stage for immediate task execution with result handling.
     */
    interface BuildStage extends BaseBuildStage<AtomTask, BuildStage> {
        /**
         * Builds a task that returns a result.
         * @param task supplier providing the result
         * @param <T> type of the result
         * @return future holding the task result
         */
        <T> AtomTaskFuture<T> buildSupplying(@NonNull Supplier<T> task);

        /**
         * Builds a task that returns a result with exception handling.
         * @param task function providing the result that may throw checked exceptions
         * @param <T> type of the result
         * @return future holding the task result
         */
        <T> AtomTaskFuture<T> buildCatchingSupplying(@NonNull CheckedFunction0<T> task);
    }

    /**
     * Building stage for scheduled tasks with self-referential capabilities.
     */
    interface ScheduledBuildStage extends BaseBuildStage<AtomTaskScheduled, ScheduledBuildStage> {
        /**
         * Switch the executor for which the actual task will be executed on.
         * @param executor the executor to use for task execution
         * @return ScheduledBuildStage for further scheduling configuration
         */
        ScheduledBuildStage withDelegateExecutor(@NonNull AtomExecutor executor);

        /**
         * Builds a scheduled task with access to its own instance.
         * @param task consumer accepting the task instance
         * @return configured AtomTask
         */
        AtomTaskScheduled build(@NonNull Consumer<AtomTaskScheduled> task);

        /**
         * Builds a scheduled task with exception handling and self-reference.
         * @param task consumer accepting the task instance that may throw checked exceptions
         * @return configured AtomTask
         */
        AtomTaskScheduled buildCatching(@NonNull CheckedConsumer<AtomTaskScheduled> task);

        /**
         * Builds a scheduled task that returns a result with self-reference.
         * @param task consumer accepting the future result holder
         * @param <T> type of the result
         * @return future holding the task result
         */
        <T> AtomTaskScheduledFuture<T> buildSupplying(@NonNull Consumer<AtomTaskScheduledFuture<T>> task);

        /**
         * Builds a scheduled task that returns a result with exception handling and self-reference.
         * @param task consumer accepting the future result holder that may throw checked exceptions
         * @param <T> type of the result
         * @return future holding the task result
         */
        <T> AtomTaskScheduledFuture<T> buildCatchingSupplying(@NonNull CheckedConsumer<AtomTaskScheduledFuture<T>> task);
    }
}