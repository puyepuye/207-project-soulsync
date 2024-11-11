package use_case.signup;

import java.util.Date;

/**
 * The Input Data for the Signup Use Case.
 */
public class SignupInputData {

    private final String fullname;
    private final String username;
    private final String password;
    private final String image;
    private final String repeatPassword;
    private final String location;
    private final String gender;
    private final String dateOfBirth;

    public SignupInputData(String fullname, String username, String password, String image, String repeatPassword,
                           String location, String gender, String dateOfBirth) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.image = image;
        this.repeatPassword = repeatPassword;
        this.location = location;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getImage() {
        return image;
    }
    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String getLocation() {
        return location;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
