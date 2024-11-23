package data_access;

import entity.ChatMessage;
import org.springframework.stereotype.Service;
import use_case.chat.MessageListener;

@Service
public class NotificationService {
    private MessageListener messageListener;

    public void registerMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    public void processNotification(ChatMessage message) {
        if (messageListener != null) {
            messageListener.onMessageReceived(message);
        }
    }
}
