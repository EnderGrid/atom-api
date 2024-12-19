package net.endergrid.atom.reference;

import lombok.NonNull;

import javax.annotation.Nullable;

public interface AtomReferenceQueue<T> {

    AtomReferenceQueue<Object> NOOP = new AtomReferenceQueue<Object>() {
        @Override
        public void safeOffer(@NonNull AtomReference<? super Object> reference, AtomReference.@NonNull DisposalReason reason) {}

        @Nullable
        @Override
        public AtomReference<Object> safePoll() {
            return null;
        }
    };

    @Nullable
    AtomReference<? super T> safePoll();

    void safeOffer(@NonNull AtomReference<? super T> reference, @NonNull AtomReference.DisposalReason reason);
}
