package use_case.compatibility;

public class CompatibilityOutputData {
    private final String username;
    private final boolean useCaseFailed;

    public CompatibilityOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }
}
