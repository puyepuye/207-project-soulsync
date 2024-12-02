package use_case.signup;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private final Date dateOfBirth;
    private final List<String> preferredGender;
    private final HashMap<String, Integer> preferredAge;

    public SignupInputData(String fullname, String username, String password, String image, String repeatPassword,
                           String location, String gender, Date dateOfBirth, List<String> preferredGender,
                           HashMap<String, Integer> preferredAge) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.image = image;
        this.repeatPassword = repeatPassword;
        this.location = location;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.preferredGender = preferredGender;
        this.preferredAge = preferredAge;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public List<String> getPreferredGender() { return preferredGender; }

    public HashMap<String, Integer> getPreferredAge() { return preferredAge; }
}
