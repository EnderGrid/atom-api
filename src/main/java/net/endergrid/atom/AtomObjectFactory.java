
package net.endergrid.atom;

import java.util.function.Supplier;

public interface AtomObjectFactory {
    static AtomObjectFactory get() {
        return Atom.get().getObjectFactory();
    }

    <T> T create(Class<T> type);

    <T> void register(Class<T> type, Supplier<? super T> factory);
}
