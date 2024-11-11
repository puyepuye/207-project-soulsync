package interface_adapter.signup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String username = "";
    private String usernameError;
    private String password = "";
    private String passwordError;
    private String repeatPassword = "";
    private String repeatPasswordError;
    private String fullname = "";
    private String fullnameError;
    private String image = "";
    private String imageError;
    private String location = "";
    private String locationError;
    private String gender = "";
    private String genderError;
    private Date dateOfBirth;
    private String dateOfBirthError;

    public String getUsername() {
        return username;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String getRepeatPasswordError() {
        return repeatPasswordError;
    }

    public String getFullname() {
        return fullname;
    }

    public String getFullnameError() {
        return fullnameError;
    }

    public String getImage() {
        return image;
    }

    public String getImageError() {
        return imageError;
    }

    public String getLocation() {
        return location;
    }

    public String getLocationError() {
        return locationError;
    }

    public String getGender() {
        return gender;
    }

    public String getGenderError() {
        return genderError;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfBirthError() {
        return dateOfBirthError;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public void setRepeatPasswordError(String repeatPasswordError) {
        this.repeatPasswordError = repeatPasswordError;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setFullnameError(String fullnameError) {
        this.fullnameError = fullnameError;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImageError(String imageError) {
        this.imageError = imageError;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLocationError(String locationError) {
        this.locationError = locationError;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGenderError(String genderError) {
        this.genderError = genderError;
    }

    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public void setDateOfBirthError(String dateOfBirthError) { this.dateOfBirthError = dateOfBirthError; }

    @Override
    public String toString() {
        return "SignupState{"
                + "fullname='" + fullname + '\''
                + ", username='" + username + '\''
                + ", password='" + password + '\''
                + ", repeatPassword='" + repeatPassword + '\''
                + ", image='" + image + '\''
                + ", location='" + location + '\''
                + ", gender='" + gender + '\''
                + ", dateOfBirth=" + dateOfBirth
                + '}';
    }
}
