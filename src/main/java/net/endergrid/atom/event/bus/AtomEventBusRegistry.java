package net.endergrid.atom.event.bus;

import dev.oop778.bindings.type.Bindable;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

public interface AtomEventBusRegistry extends AtomEventRegistrable<Object> {
    void registerBus(@NonNull AtomEventBus<?> bus, @Nullable Bindable registerer);
}
