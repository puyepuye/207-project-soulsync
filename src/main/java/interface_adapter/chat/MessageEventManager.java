package interface_adapter.chat;

import entity.ChatMessage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MessageEventManager {
    private static MessageEventManager instance;
    private final PropertyChangeSupport support;
    private ChatMessage latestMessage;

    private MessageEventManager() {
        support = new PropertyChangeSupport(this);
    }

    public static MessageEventManager getInstance() {
        if (instance == null) {
            instance = new MessageEventManager();
        }
        return instance;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void setNewMessage(ChatMessage oldMessage, ChatMessage newMessage) {
        support.firePropertyChange("message", oldMessage, newMessage);
        this.latestMessage = newMessage;
    }
}
