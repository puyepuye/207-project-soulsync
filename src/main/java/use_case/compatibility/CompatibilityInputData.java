package use_case.compatibility;

public class CompatibilityInputData {
    private final String username;
    private final String password;

    public CompatibilityInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }
}
