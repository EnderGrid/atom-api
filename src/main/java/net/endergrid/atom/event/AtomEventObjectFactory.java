package net.endergrid.atom.event;

import lombok.NonNull;
import net.endergrid.atom.Atom;
import net.endergrid.atom.event.group.AtomEventGroup;
import net.endergrid.atom.event.handler.AtomEventRegistration;

public interface AtomEventObjectFactory {
    static AtomEventObjectFactory get() {
        return Atom.get().getSingletonManager().getSingleton(AtomEventObjectFactory.class);
    }

    <T> AtomEventRegistration.Builder<T> createEventHandlerRegistrationBuilder();

    <E> AtomEventGroup<E> createEventGroup(@NonNull Class<E> clazz);
}
