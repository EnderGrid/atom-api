package net.endergrid.atom.executor;

import dev.oop778.bindings.type.Bindable;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

public interface AtomExecutorRegistry {

    /**
     * Retrieves the {@link AtomExecutor} instance with the specified name.
     *
     * @param name the name of the {@link AtomExecutor} to retrieve
     * @return the {@link AtomExecutor} instance with the specified name, or {@code null} if no such instance exists
     */
    @Nullable
    AtomExecutor getExecutor(@NonNull String name);

    /**
     * Registers an {@link AtomExecutor} instance with the registry, along with an optional {@link Bindable} instance.
     *
     * @param executor the {@link AtomExecutor} instance to register
     * @param bindable the optional {@link Bindable} instance to associate with the executor
     */
    void register(@NonNull AtomExecutor executor, @Nullable Bindable bindable);
}
