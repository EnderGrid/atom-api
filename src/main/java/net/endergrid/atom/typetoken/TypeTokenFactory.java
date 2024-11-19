package net.endergrid.atom.typetoken;

import lombok.NonNull;
import net.endergrid.atom.Atom;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

/**
 * Provides a factory for creating {@link TypeToken} instances.
 * The {@link TypeTokenFactory} is a singleton that can be accessed using the {@link #get()} method.
 */
public interface TypeTokenFactory {

    static TypeTokenFactory get() {
        return Atom.get().getSingletonManager().getSingleton(TypeTokenFactory.class);
    }

    <T> TypeToken<T> create(@NonNull Type type, @Nullable AnnotatedElement annotatedElement);
}
