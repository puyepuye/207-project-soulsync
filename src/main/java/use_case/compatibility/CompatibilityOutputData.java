package use_case.compatibility;

/**
 * Output Data for the Change Password Use Case.
 */
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

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
