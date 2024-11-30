package entity;

import java.util.*;

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
                       HashMap<String, Integer> preferredAge,
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
