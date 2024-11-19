package net.endergrid.atom.reference;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a reference to an object, with various options for how the reference is held and managed.
 * <p>
 * The reference can be held weakly or strongly, and can be an identity reference or a hashable reference.
 * <p>
 * The reference can also be associated with a queue for disposal notifications.
 *
 * @param <T> The type of the object being referenced.
 */
public interface AtomReference<T> extends Comparable<AtomReference<T>> {
    static <T> AtomReference<T> hashableWeak(T value) {
        return weak(value, false, null);
    }

    static <T> AtomReference<T> identityWeak(T value) {
        return weak(value, true, null);
    }

    static <T> AtomReference<T> weak(T value, boolean identity, AtomReferenceQueue<T> queue) {
        return AtomReferenceBuilder.<T>create()
                .withType(AtomReferenceType.WEAK)
                .withIdentity(identity)
                .withQueue(queue)
                .build(value);
    }

    static <T> AtomReference<T> hashableStrong(T value) {
        return strong(value, false, null);
    }

    static <T> AtomReference<T> identityStrong(T value) {
        return strong(value, true, null);
    }

    static <T> AtomReference<T> strong(T value, boolean identity, AtomReferenceQueue<T> queue) {
        return AtomReferenceBuilder.<T>create()
                .withType(AtomReferenceType.STRONG)
                .withIdentity(identity)
                .withQueue(queue)
                .build(value);
    }

    static <T> AtomReferenceBuilder<T> builder() {
        return AtomReferenceBuilder.create();
    }

    @Override
    default int compareTo(@NotNull AtomReference<T> o) {
        // First, compare by hash code
        final int hashComparison = Integer.compare(this.hashCode(), o.hashCode());

        if (hashComparison != 0) {
            return hashComparison; // If hashCodes are different, return the comparison result
        }

        // If hashCodes are the same, compare by referent value (if non-null)
        final T thisValue = this.get();
        final T otherValue = o.get();

        if (thisValue == null && otherValue == null) {
            return 0; // Both values are null, consider them equal
        }

        if (thisValue == null) {
            return -1; // This value is null, but the other is not
        }

        if (otherValue == null) {
            return 1; // The other value is null, but this one is not
        }

        // Compare the underlying referent values if both are non-null and are comparable
        if (thisValue instanceof Comparable) {
            return ((Comparable<T>) thisValue).compareTo(otherValue);
        }

        // If values are not comparable, use identity comparison
        return System.identityHashCode(thisValue) - System.identityHashCode(otherValue);
    }

    /**
     * Retrieves the object referenced by this reference.
     *
     * @return The referenced object, or null if it has disposed.
     */
    @Nullable
    T get();

    /**
     * Disposes of the MeshReference object.
     *
     * @return true if the disposal was successful, false otherwise.
     */
    boolean dispose(@NonNull DisposalReason reason, boolean queue);

    @Nullable
    DisposalReason getDisposalReason();

    /**
     * Checks if the referenced object is an identity reference.
     *
     * @return true if the object is an identity reference, false otherwise.
     */
    boolean isIdentity();

    /**
     * Checks if the referent equality check is enabled.
     * <p>
     * If {@code true}, you can check if object refers to reference via {@link AtomReference#equals(Object)}
     * @return true if the referent equality check is enabled, false otherwise.
     */
    boolean isReferentEqualityCheckEnabled();

    /**
     * Checks if the referenced object refers to the specified object.
     *
     * @param object The object to compare against.
     * @return true if the referenced object refers to the specified object, false otherwise.
     */
    boolean refersTo(@NonNull T object);

    /**
     * Checks if the MeshReference object is marked.
     *
     * @return true if the MeshReference object is marked, false otherwise.
     */
    default boolean isMarkedForDisposal() {
        return false;
    }

    static enum DefaultDisposalReason implements DisposalReason {
        DISPOSED,
        GARBAGE_COLLECTED
    }

    static interface DisposalReason {
        String name();
    }
}
