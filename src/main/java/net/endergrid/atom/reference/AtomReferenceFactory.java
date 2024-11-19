package net.endergrid.atom.reference;

import lombok.NonNull;

/**
 * Factory for creating and managing atom references
 *
 * @param <T> The base type of values this factory handles
 */
public interface AtomReferenceFactory<T> {
    /**
     * Creates a permanent atomic reference that is managed by the Atom system
     *
     * @param value The initial value to store
     * @param <V>   The specific type of the value, must extend T
     * @return A new managed atomic reference
     */
    <V extends T> AtomReference<V> createPermanent(@NonNull V value);

    /**
     * Creates a temporary atom reference for short-lived operations
     * <p>
     * <bold>NOTE</bold>: The reference can be upgraded to a permanent one by calling {@link #upgradeToPermanent(AtomReference)}
     *
     * @param value The initial value to store
     * @param <V>   The specific type of the value, must extend T
     * @return A new temporary reference
     */
    <V extends T> AtomReference<V> createTemporary(@NonNull V value);

    /**
     * Upgrades a temporary atom reference to a permanent one that is managed by a queue
     *
     * @param value The temporary atom reference to be converted to a permanent one.
     */
    void upgradeToPermanent(@NonNull AtomReference<? super T> value);
}
