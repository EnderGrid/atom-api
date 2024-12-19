package net.endergrid.atom;

import net.endergrid.atom.executor.AtomExecutorRegistry;
import net.endergrid.atom.singleton.AtomSingletonRegistry;
import lombok.NonNull;

public interface Atom {
    static Atom get() {
        if (Instance.INSTANCE == null) {
            throw new IllegalStateException("Atom is not initialized");
        }

        return Instance.INSTANCE;
    }

    @NonNull
    AtomSingletonRegistry getSingletonManager();

    @NonNull
    AtomObjectFactory getObjectFactory();

    @NonNull
    AtomExecutorRegistry getExecutorRegistry();

    class Instance {
        protected static Atom INSTANCE;

        protected static synchronized void setInstance(@NonNull Atom atom) {
            if (INSTANCE != null) {
                throw new IllegalStateException("Atom already initialized");
            }

            INSTANCE = atom;
        }
    }
}
