package interface_adapter.chat;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import entity.ChatMessage;

/**
 * Separate listener used to update the chat view with new messages.
 */
public final class MessageEventManager {
    private static MessageEventManager instance;
    private final PropertyChangeSupport support;

    private MessageEventManager() {
        support = new PropertyChangeSupport(this);
    }

    /**
     * Factory method.
     * @return an instance of the MessageEventManager class.
     */
    public static MessageEventManager getInstance() {
        if (instance == null) {
            instance = new MessageEventManager();
        }
        return instance;
    }

    /**
     * A method that adds an object to the list of observers.
     * @param listener the object you want to listen to changes.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * A method to update the UI once a new message has been received.
     * @param oldMessage the old message
     * @param newMessage the new message
     */
    public void setNewMessage(ChatMessage oldMessage, ChatMessage newMessage) {
        support.firePropertyChange("message", oldMessage, newMessage);
    }
}
