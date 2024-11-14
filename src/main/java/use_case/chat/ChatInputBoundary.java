package use_case.chat;

public interface ChatInputBoundary {
    /**
     * Executes the chat use case.
     * @param chatInputData the input data
     */
    void execute(ChatInputData chatInputData);

}
