package net.endergrid.atom.executor;

import dev.oop778.bindings.type.Bindable;
import lombok.NonNull;
import net.endergrid.atom.executor.grouped.AtomGroupedExecutor;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * An interface for building instances of {@link AtomExecutor}.
 * Provides a fluent API for configuring the executor, including
 * options for thread pool size, thread naming, and other settings.
 * <p>
 * This interface uses a staged builder pattern to guide the user
 * through the configuration process, ensuring that all necessary
 * parameters are set before building the executor.
 */
public interface AtomExecutorBuilder {
    /**
     * Initiates the building process by specifying a name for the executor.
     *
     * @param name the name of the executor
     * @return the next stage in the builder process, allowing selection of the pool type
     */
    static ExecutorTypeSelector named(@NonNull String name) {
        return AtomExecutorFactory.get().createAtomExecutorBuilder(name);
    }

    /**
     * The initial stage of the builder process, where the type of executor pool is selected.
     */
    interface ExecutorTypeSelector {
        /**
         * Wraps an existing {@link Executor} instance.
         *
         * @param executor the executor to wrap
         * @return a {@link WrappedExecutorConfiguration} stage for further configuration
         */
        WrappedExecutorConfiguration wrap(@NonNull Executor executor);

        /**
         * Selects a work-stealing executor.
         *
         * @return a {@link WorkStealingExecutorBuilder} stage for further configuration
         */
        WorkStealingExecutorBuilder workStealing();

        /**
         * Selects a dynamic thread pool executor.
         *
         * @return a {@link DynamicThreadPoolBuilder} stage for further configuration
         */
        DynamicThreadPoolBuilder dynamic();

        /**
         * Selects a grouped executor for handling grouped tasks.
         *
         * @param <T> the type of tasks handled by the grouped executor
         * @return a {@link GroupedExecutorBuilder} stage for further configuration
         */
        <T> GroupedExecutorBuilder<T> grouped();

        /**
         * Selects a cached thread pool executor.
         *
         * @return a {@link CachedThreadPoolBuilder} stage for further configuration
         */
        CachedThreadPoolBuilder cached();
    }

    /**
     * A generic builder interface for constructing an {@link AtomExecutor}.
     *
     * @param <EXECUTOR> the type of executor being built
     */
    interface Builder<EXECUTOR extends AtomExecutor> {
        /**
         * Builds and returns the configured executor instance.
         *
         * @return the configured executor
         */
        EXECUTOR build();

        /**
         * Builds and registers the executor into {@link AtomExecutorRegistry}
         * @return the configured executor
         */
        EXECUTOR buildAndRegister(@Nullable Bindable bindable);
    }

    /**
     * A stage in the builder process for wrapping an existing executor.
     */
    interface WrappedExecutorConfiguration {
        /**
         * Configures a detector function to determine if the current thread is part of the executor.
         *
         * @param detector a function that detects if a thread is part of the executor
         * @return the current {@link WrappedExecutorConfiguration} stage for further configuration
         */
        WrappedExecutorConfiguration withCurrentThreadDetector(Function<Thread, Boolean> detector);
    }

    /**
     * A configurable builder interface for setting common executor properties.
     *
     * @param <EXECUTOR> the type of executor being built
     * @param <B>        the type of the builder for method chaining
     */
    interface ExecutorConfigurationBuilder<EXECUTOR extends AtomExecutor, B extends ExecutorConfigurationBuilder<EXECUTOR, B>> extends Builder<EXECUTOR> {
        /**
         * Sets the thread priority for the executor.
         *
         * @param priority the thread priority
         * @return the current builder stage for further configuration
         */
        B withThreadPriority(int priority);

        /**
         * Sets whether the executor's threads should be daemon threads.
         *
         * @param daemon true if threads should be daemon threads, false otherwise
         * @return the current builder stage for further configuration
         */
        B withDaemon(boolean daemon);
    }

    /**
     * A builder interface for configuring dynamic thread pool properties.
     *
     * @param <EXECUTOR> the type of executor being built
     * @param <B>        the type of the builder for method chaining
     */
    interface DynamicThreadPoolConfigurationBuilder<EXECUTOR extends AtomExecutor, B extends DynamicThreadPoolConfigurationBuilder<EXECUTOR, B>> extends ExecutorConfigurationBuilder<EXECUTOR, B> {
        /**
         * Sets the core number of threads for the executor.
         *
         * @param coreThreadCount the core thread count
         * @return the current builder stage for further configuration
         */
        B withCoreThreadCount(int coreThreadCount);

        /**
         * Sets the maximum number of threads for the executor.
         *
         * @param maxThreadCount the maximum thread count
         * @return the current builder stage for further configuration
         */
        B withMaxThreadCount(int maxThreadCount);

        /**
         * Sets the keep-alive time for idle threads in the executor.
         *
         * @param keepAliveTime the keep-alive time
         * @param unit          the time unit for the keep-alive time
         * @return the current builder stage for further configuration
         */
        B withKeepAliveTime(long keepAliveTime, TimeUnit unit);

        /**
         * Sets whether core threads are allowed to time out.
         *
         * @param allowCoreThreadTimeOut true if core threads can time out, false otherwise
         * @return the current builder stage for further configuration
         */
        B withAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut);
    }

    /**
     * A builder interface for creating a dynamic thread pool executor.
     */
    interface DynamicThreadPoolBuilder extends DynamicThreadPoolConfigurationBuilder<AtomExecutor, DynamicThreadPoolBuilder> {
    }

    /**
     * A builder interface for creating a grouped executor.
     *
     * @param <T> the type of tasks handled by the grouped executor
     */
    interface GroupedExecutorBuilder<T> extends DynamicThreadPoolConfigurationBuilder<AtomGroupedExecutor<T>, GroupedExecutorBuilder<T>> {
    }

    /**
     * A builder interface for creating a work-stealing executor.
     */
    interface WorkStealingExecutorBuilder extends ExecutorConfigurationBuilder<AtomExecutor, WorkStealingExecutorBuilder> {
        /**
         * Sets the parallelism level for the work-stealing executor.
         *
         * @param parallelism the level of parallelism
         * @return the current builder stage for further configuration
         */
        WorkStealingExecutorBuilder withParallelism(int parallelism);

        /**
         * Configures the executor to operate in asynchronous mode.
         *
         * @return the current builder stage for further configuration
         */
        WorkStealingExecutorBuilder withAsyncMode();
    }

    /**
     * A builder interface for creating a cached thread pool executor.
     */
    interface CachedThreadPoolBuilder extends ExecutorConfigurationBuilder<AtomExecutor, CachedThreadPoolBuilder> {
    }
}