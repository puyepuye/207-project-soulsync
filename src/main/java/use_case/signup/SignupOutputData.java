package use_case.signup;

import java.util.Date;

/**
 * Output Data for the Signup Use Case.
 */
public class SignupOutputData {

    private final String username;
    private final String fullName;
    private final String image;
    private final String location;
    private final String gender;
    private final Date dateOfBirth;
    private final boolean useCaseFailed;

    public SignupOutputData(String username, String fullName, String image, String location,
                            String gender, Date dateOfBirth, boolean useCaseFailed) {
        this.username = username;
        this.fullName = fullName;
        this.image = image;
        this.location = location;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

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

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
