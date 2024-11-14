package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;

/**
 * A simple implementation of the User interface.
 */
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sampleCollection")
public class sampleCollection implements User {

    private final String userName;
    private final String password;
    private final String image;
    private final String fullname;
    private final String location;
    private final String gender;
    private final List<String> preferredGender;
    private final Date dateOfBirth;
    private final Map<String, Integer> preferredAge;
    private final String bio;
    private final Map<String, Boolean> preferences;
    private final List<String> tags;
    private final List<String> matched;
    private ArrayList<String> swipedRight;
    private ArrayList<String> swipedLeft;
    private ArrayList<String> swipedRightOn;

    public sampleCollection(String userName, String password, String image, String fullname, String location, String gender,
                      List<String> preferredGender, Date dateOfBirth, Map<String, Integer> preferredAge, String bio,
                      Map<String, Boolean> preferences, List<String> tags, List<String> matched,
                      ArrayList<String> swipedRight, ArrayList<String> swipedLeft, ArrayList<String> swipedRightOn) {
        super();
        this.userName = userName;
        this.password = password;
        this.image = image;
        this.fullname = fullname;
        this.location = location;
        this.gender = gender;
        this.preferredGender = preferredGender;
        this.dateOfBirth = dateOfBirth;
        this.preferredAge = preferredAge;
        this.bio = bio;
        this.preferences = preferences;
        this.tags = tags;
        this.matched = matched;
        this.swipedRight = swipedRight;
        this.swipedLeft = swipedLeft;
        this.swipedRightOn = swipedRightOn;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getImage() { return image; }

    @Override
    public String getFullname() {
        return fullname;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public Map<String, Integer> getPreferredAge() { return preferredAge; }

    @Override
    public List<String> getPreferredGender() { return preferredGender; }

    @Override
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String getBio() {
        return bio;
    }

    @Override
    public Map<String, Boolean> getPreferences() {
        return preferences;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public List<String> getMatched() {
        return matched;
    }

    @Override
    public ArrayList<String> getSwipedRight() {
        return swipedRight;
    }

    @Override
    public ArrayList<String> getSwipedLeft() {
        return swipedLeft;
    }

    @Override
    public ArrayList<String> getSwipedRightOn() {
        return swipedRightOn;
    }
}
