package net.endergrid.atom.user;

/**
 * Defines the basic interface for an Atom user, including functionality for
 * identifying the user, sending messages, and interacting with the Audience API.
 */
public interface AtomUser {

    /**
     * Defines an extension of the {@link AtomUser} interface that includes a generic
     * identifier of type {@code T}. This interface can be used to provide a more
     * specific identifier for an Atom user, beyond the basic {@link AtomUser}
     * functionality.
     */
    static interface Identifiable<T> extends AtomUser {
        T getIdentifier();
    }

    /**
     * Defines an interface for a message receiver that can receive messages and
     * arguments.
     */
    interface MessageReceiver {
        /**
         * Sends a message with the given arguments.
         *
         * @param message the message to send
         * @param args the arguments for the message
         */
        void sendMessage(String message, Object args);
    }
}
