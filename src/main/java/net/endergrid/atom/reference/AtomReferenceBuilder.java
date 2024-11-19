package net.endergrid.atom.reference;

import net.endergrid.atom.Atom;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A builder interface for creating {@link AtomReference} instances.
 * <p>
 * The {@link AtomReferenceBuilder} provides a fluent API for configuring the properties of an {@link AtomReference},
 * such as the reference type, queue, and identity. The builder can be used to create both factory and individual
 * {@link AtomReference} instances.
 * <p>
 * By default, the reference type is set to {@link AtomReferenceType#STRONG}, the identity is set to {@code false},
 * @param <T> the type of the value stored in the {@link AtomReference}
 */
public interface AtomReferenceBuilder<T> {

    static <T> AtomReferenceBuilder<T> create() {
        return Atom.get().getObjectFactory().create(AtomReferenceBuilder.class);
    }

    /**
     * Sets the reference type for the {@link AtomReference} being built.
     *
     * @param type the reference type to use
     * @return this builder instance for method chaining
     */
    AtomReferenceBuilder<T> withType(@NotNull AtomReferenceType type);

    /**
     * Sets the queue for the {@link AtomReference} being built.
     *
     * @param queue the queue to use for the {@link AtomReference}, or {@code null} to use the default queue
     * @return this builder instance for method chaining
     */
    AtomReferenceBuilder<T> withQueue(@Nullable AtomReferenceQueue<T> queue);

    /**
     * Sets the identity for the {@link AtomReference} being built.
     *
     * @param identity {@code true} to enable identity comparison, {@code false} to use hash comparison
     * @return this builder instance for method chaining
     */
    AtomReferenceBuilder<T> withIdentity(boolean identity);

    /**
     * Sets whether object comparison should be allowed for the {@link AtomReference} being built.
     * <p>
     * Aka meaning that if this is true {@code reference.equals(referrant)} will return true if the reference is the same object as the referent.
     *
     * @param enableCheck {@code true} to allow referent comparison, {@code false} to only use reference comparison
     * @return this builder instance for method chaining
     */
    AtomReferenceBuilder<T> withReferentEqualityCheck(boolean enableCheck);

    /**
     * Builds a factory for creating {@link AtomReference} instances.
     *
     * @return a factory for creating {@link AtomReference} instances
     */
    AtomReferenceFactory<T> buildFactory();

    /**
     * Builds an {@link AtomReference} instance with the specified value.
     *
     * @param value the value to store in the {@link AtomReference}
     * @return an {@link AtomReference} instance with the specified value
     */
    <VALUE extends T> AtomReference<VALUE> build(@NotNull VALUE value);
}
