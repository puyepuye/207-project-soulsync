package use_case.compatibility;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The input data for the Change Password Use Case.
 */
public class CompatibilityInputData {

    private final String userName;
    private final String password;
    private final String image;
    private final String fullName;
    private final String location;
    private final String gender;
    private final List<String> preferredGender;
    private final Date dateOfBirth;
    private final Map<String, Integer> preferredAge;
    private final String bio;
    private final Map<String, Boolean> preferences;
    private final List<String> tags;
    private final List<String> matched;


    public CompatibilityInputData(String userName, String password, String image, String fullName,
                                  String location, String gender, List<String> preferredGender, Date dateOfBirth,
                                  Map<String, Integer> preferredAge, String bio, Map<String, Boolean> preferences,
                                  List<String> tags, List<String> matched) {
        this.userName = userName;
        this.password = password;
        this.image = image;
        this.fullName = fullName;
        this.location = location;
        this.gender = gender;
        this.preferredGender = preferredGender;
        this.dateOfBirth = dateOfBirth;
        this.preferredAge = preferredAge;
        this.bio = bio;
        this.preferences = preferences;
        this.tags = tags;
        this.matched = matched;
    }

    String getPassword() {
        return password;
    }

    String getUsername() {
        return userName;
    }

    String getImage() { return image; }

    String getFullName() {return fullName;}

    String getLocation() {return location;}

    String getGender() {return gender;}

    List<String> getPreferredGender() {return preferredGender;}

    Date getDateOfBirth() {return dateOfBirth;}

    Map<String, Integer> getPreferredAge() {return preferredAge;}

    String getBio() {return bio;}

    Map<String, Boolean> getPreferences() {return preferences;}

    List<String> getTags() {return tags;}

    List<String> getMatched() {return matched;}

}
