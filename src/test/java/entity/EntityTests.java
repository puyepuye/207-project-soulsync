package entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserFactory, CommonUser, CommonUserFactory, CommonMatchesFactory, ChatMessage, and ChatChannel.
 */
public class EntityTests {

    @Test
    public void testCreateCommonUser() {
        // Sample data for CommonUser
        String userName = "user1";
        String password = "password123";
        String image = "image_url";
        String fullName = "John Doe";
        String location = "New York";
        String gender = "Male";
        List<String> preferredGender = List.of("Female", "Non-binary");
        Date dateOfBirth = new Date();
        HashMap<String, Integer> preferredAge = new HashMap<>();
        preferredAge.put("min", 20);
        preferredAge.put("max", 30);
        String bio = "This is my bio";
        Map<String, Boolean> preferences = Map.of("preference1", true, "preference2", false);
        List<String> tags = List.of("tag1", "tag2");
        List<String> matched = List.of("user2");
        ArrayList<String> swipedRight = new ArrayList<>(List.of("user3"));
        ArrayList<String> swipedLeft = new ArrayList<>(List.of("user4"));
        ArrayList<String> swipedRightOn = new ArrayList<>(List.of("user5"));

        // Create CommonUser using the factory
        CommonUserFactory userFactory = new CommonUserFactory();
        User commonUser = userFactory.create(userName, password, image, fullName, location, gender, preferredGender,
                dateOfBirth, preferredAge, bio, preferences, tags, matched, swipedRight, swipedLeft, swipedRightOn);

        // Validate the CommonUser fields
        assertNotNull(commonUser);
        assertEquals(userName, commonUser.getUsername());
        assertEquals(password, commonUser.getPassword());
        assertEquals(image, commonUser.getImage());
        assertEquals(fullName, commonUser.getFullname());
        assertEquals(location, commonUser.getLocation());
        assertEquals(gender, commonUser.getGender());
        assertEquals(preferredGender, commonUser.getPreferredGender());
        assertEquals(dateOfBirth, commonUser.getDateOfBirth());
        assertEquals(preferredAge, commonUser.getPreferredAge());
        assertEquals(bio, commonUser.getBio());
        assertEquals(preferences, commonUser.getPreferences());
        assertEquals(tags, commonUser.getTags());
        assertEquals(matched, commonUser.getMatched());
        assertEquals(swipedRight, commonUser.getSwipedRight());
        assertEquals(swipedLeft, commonUser.getSwipedLeft());
        assertEquals(swipedRightOn, commonUser.getSwipedRightOn());
    }

    @Test
    public void testCreateCommonMatches() {
        // Sample data for CommonMatches
        String usernameA = "userA";
        String usernameB = "userB";

        // Create CommonMatchesFactory
        CommonMatchesFactory matchFactory = new CommonMatchesFactory();

        // Use the factory to create a CommonMatches instance
        CommonMatches match = matchFactory.create(usernameA, usernameB);

        // Validate the CommonMatches fields
        assertNotNull(match);
        assertEquals(usernameA, match.getUsernameA());
        assertEquals(usernameB, match.getUsernameB());
    }

    @Test
    public void testCreateUserWithUsernameAndPassword() {
        // Sample data for UserFactory
        String userName = "user1";
        String password = "password123";

        // Create UserFactory
        UserFactory userFactory = new CommonUserFactory();

        // Use the factory to create a User with only username and password
        User user = userFactory.create(userName, password);

        // Validate the User creation
        assertNotNull(user);
        assertEquals(userName, user.getUsername());
        assertEquals(password, user.getPassword());
        assertNull(user.getImage());
        assertNull(user.getFullname());
        assertNull(user.getLocation());
        assertNull(user.getGender());
        assertNull(user.getPreferredGender());
        assertNull(user.getDateOfBirth());
        assertNull(user.getPreferredAge());
        assertNull(user.getBio());
        assertNull(user.getPreferences());
        assertNull(user.getTags());
        assertNull(user.getMatched());
        assertNull(user.getSwipedRight());
        assertNull(user.getSwipedLeft());
        assertNull(user.getSwipedRightOn());
    }

    @Test
    public void testChatMessageConstructor() {
        // Sample data for ChatMessage
        String sender = "user1";
        String message = "Hello, world!";
        String dateTime = "2024-12-01T10:00:00Z";

        // Create ChatMessage instance using the constructor
        ChatMessage chatMessage = new ChatMessage(sender, message, dateTime);

        // Validate the ChatMessage fields
        assertNotNull(chatMessage);
        assertEquals(sender, chatMessage.getSender());
        assertEquals(message, chatMessage.getMessage());
        assertEquals(dateTime, chatMessage.getDateTime());
        assertNull(chatMessage.getChatURL());
    }

    @Test
    public void testChatMessageWithChatURL() {
        // Sample data for ChatMessage with URL
        String sender = "user2";
        String message = "Check out this link!";
        String dateTime = "2024-12-01T12:00:00Z";
        String chatURL = "http://example.com/chat/123";

        // Create ChatMessage instance using the constructor with chatURL
        ChatMessage chatMessage = new ChatMessage(sender, message, dateTime, chatURL);

        // Validate the ChatMessage fields
        assertNotNull(chatMessage);
        assertEquals(sender, chatMessage.getSender());
        assertEquals(message, chatMessage.getMessage());
        assertEquals(dateTime, chatMessage.getDateTime());
        assertEquals(chatURL, chatMessage.getChatURL());
    }

    @Test
    public void testSetChatURL() {
        // Sample data for ChatMessage
        String sender = "user3";
        String message = "Message without URL";
        String dateTime = "2024-12-01T14:00:00Z";

        // Create ChatMessage instance
        ChatMessage chatMessage = new ChatMessage(sender, message, dateTime);

        // Set chatURL
        String newChatURL = "http://example.com/chat/456";
        chatMessage.setChatURL(newChatURL);

        // Validate the updated chatURL
        assertEquals(newChatURL, chatMessage.getChatURL());
    }

    @Test
    public void testCreateChatChannel() {
        // Sample data for ChatChannel
        String channelURL = "http://example.com/chat/1";
        String lastMessage = "Hello!";
        String user1Id = "user1";
        String user2Id = "user2";

        // Create ChatChannel instance
        ChatChannel chatChannel = new ChatChannel(channelURL, lastMessage, user1Id, user2Id);

        // Validate the ChatChannel fields
        assertNotNull(chatChannel);
        assertEquals(channelURL, chatChannel.getChannelURL());
        assertEquals(lastMessage, chatChannel.getLastMessage());
        assertEquals(user1Id, chatChannel.getUser1Id());
        assertEquals(user2Id, chatChannel.getUser2Id());
    }
}
