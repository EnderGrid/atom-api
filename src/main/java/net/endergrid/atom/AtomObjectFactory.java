package net.endergrid.atom;

import net.endergrid.atom.reference.AtomReferenceBuilder;

public interface AtomObjectFactory {
    static AtomObjectFactory get() {
        return Atom.get().getObjectFactory();
    }

    <T> AtomReferenceBuilder<T> createReferenceBuilder();
}
