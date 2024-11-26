package net.endergrid.atom.executor;

import lombok.NonNull;
import net.endergrid.atom.Atom;
import net.endergrid.atom.executor.task.AtomTaskBuilder;

public interface AtomExecutorFactory {
    static AtomExecutorFactory get() {
        return Atom.get().getSingletonManager().getSingleton(AtomExecutorFactory.class);
    }

    /**
     * Creates a new {@link AtomTaskBuilder.Selector} instance for building Atom tasks.
     *
     * @return a new {@link AtomTaskBuilder.Selector} instance
     */
    AtomTaskBuilder.Selector createTaskBuilder();

    /**
     * Creates a new {@link AtomExecutorBuilder.ExecutorTypeSelector} instance for building Atom executors.
     *
     * @param name the name of the Atom executor to create
     * @return a new {@link AtomExecutorBuilder.ExecutorTypeSelector} instance
     */
    AtomExecutorBuilder.ExecutorTypeSelector createAtomExecutorBuilder(@NonNull String name);
}
