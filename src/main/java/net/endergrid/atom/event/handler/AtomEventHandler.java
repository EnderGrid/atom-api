package net.endergrid.atom.event.handler;

import lombok.NonNull;
import net.endergrid.atom.event.AtomEventContext;

public interface AtomEventHandler<EVENT> {
    @NonNull
    Continuation handle(@NonNull EVENT event, AtomEventContext<EVENT> context);

    interface Continuation {}
}
