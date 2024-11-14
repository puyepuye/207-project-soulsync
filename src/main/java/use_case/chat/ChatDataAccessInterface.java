package use_case.chat;

public interface ChatDataAccessInterface {

    void sendMessage(String message);

    String receiveMessage();
}
