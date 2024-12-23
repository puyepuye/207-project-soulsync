package use_case.signup;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Output Data for the Signup Use Case.
 */
public class SignupOutputData {

    private final String username;
    private final String fullName;
    private final String password;
    private final String image;
    private final String location;
    private final String gender;
    private final Date dateOfBirth;
    private final List<String> preferredGender;
    private final HashMap<String, Integer> preferredAge;
    private final boolean useCaseFailed;

    public SignupOutputData(String username, String fullName, String password, String image, String location,
                            String gender, Date dateOfBirth, List<String> preferredGender,
                            HashMap<String, Integer> preferredAge, boolean useCaseFailed) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.image = image;
        this.location = location;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.preferredGender = preferredGender;
        this.preferredAge = preferredAge;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() { return password;}

    public String getImage() {
        return image;
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

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
