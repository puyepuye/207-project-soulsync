package use_case.change_password;

/**
 * The input data for the Change Password Use Case.
 */
public class ChangePasswordInputData {

    private final String username;
    private final String password;

    public ChangePasswordInputData(String username, String password) {
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
