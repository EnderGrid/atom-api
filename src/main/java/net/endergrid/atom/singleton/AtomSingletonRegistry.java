package net.endergrid.atom.singleton;

import dev.oop778.bindings.type.Bindable;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Interface for managing singletons in the Mesh application.
 */
public interface AtomSingletonRegistry {
    /**
     * Returns an Optional containing a singleton instance of the specified class if it is available,
     * or an empty Optional if the class does not have a singleton instance.
     *
     * @param clazz the class of the singleton instance
     * @param <T>   the type of the singleton instance
     * @return an Optional containing the singleton instance if available,
     * or an empty Optional if the class does not have a singleton instance
     */
    <T> Optional<? super T> getNullableSingleton(@NonNull Class<T> clazz);

    /**
     * Returns a singleton instance of the specified class if it is available.
     * If the class does not have a singleton instance, null is returned.
     *
     * @param clazz the class of the singleton instance
     * @param <T>   the type of the singleton instance
     * @return the singleton instance of the specified class if available,
     * or null if the class does not have a singleton instance
     */
    <T> T getSingleton(@NonNull Class<T> clazz);

    /**
     * Registers a singleton object with the registry, optionally with a registration bindable.
     *
     * @param singleton the singleton object to register
     * @param registrationBindable an optional bindable to associate with the registration
     */
    void register(@NonNull Object singleton, @Nullable Bindable registrationBindable);

    /**
     * Registers a singleton object with the registry, optionally with a registration bindable and a hierarchy filter.
     *
     * @param singleton the singleton object to register
     * @param hierarchyFilter a predicate that filters the class hierarchy including self of the singleton object
     * @param registrationBindable an optional bindable to associate with the registration
     */
    void registerWithHierarchy(@NonNull Object singleton, @NonNull Predicate<Class<?>> hierarchyFilter, @Nullable Bindable registrationBindable);

    /**
     * Registers a singleton object with the registry, optionally with a registration bindable.
     * This method calls {@link #registerWithHierarchy(Object, Predicate, Bindable)} with a predicate that always returns true.
     *
     * @param singleton the singleton object to register
     * @param registrationBindable an optional bindable to associate with the registration
     */
    default void registerWithHierarchy(@NonNull Object singleton, @Nullable Bindable registrationBindable) {
        this.registerWithHierarchy(singleton, ($) -> true, registrationBindable);
    }
}
