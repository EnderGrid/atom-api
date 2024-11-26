package net.endergrid.atom.executor.grouped;

public interface GroupedRunnable<T> extends Runnable {
    T getGroupId();
}
