package use_case.listchat;

public interface ListChatOutputBoundary {
    void prepareSuccessView();
    void enterChat(String chatURL);
}
