package interface_adapter.compatibility;

public class CompatibilityState {
    private String username = "";
    private String fengshuiError;
    private String compatibilityError;
    private String password = "";

    public String getUsername() {
        return username;
    }

    public String getFengshuiError() {
        return fengshuiError;
    }

    public String getCompatibilityError() { return compatibilityError; }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFengshuiError(String usernameError) {
        this.fengshuiError = usernameError;
    }

    public void setCompatibilityError(String compatibilityError) { this.compatibilityError = compatibilityError; }

    public void setPassword(String password) {
        this.password = password;
    }

}
