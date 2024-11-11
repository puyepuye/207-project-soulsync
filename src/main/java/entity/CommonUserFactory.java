package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory {

    @Override
    public User create(String userName,
                       String password,
                       String image,
                       String fullName,
                       String location,
                       String gender,
                       List<String> preferredGender,
                       Date dateOfBirth,
                       Map<String, Integer> preferredAge,
                       String bio,
                       Map<String, Boolean> preferences,
                       List<String> tags,
                       List<String> matched,
                       ArrayList<String> swipedRight,
                       ArrayList<String> swipedLeft,
                       ArrayList<String> swipedRightOn) {
        return new CommonUser(userName, password, image, fullName, location, gender, preferredGender,
                dateOfBirth, preferredAge, bio, preferences, tags, matched, swipedRight, swipedLeft, swipedRightOn);
    }
}
