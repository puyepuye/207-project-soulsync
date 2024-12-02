package data_access;

import entity.ChatChannel;
import entity.ChatMessage;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.chat.ChatDataAccessInterface;
import use_case.listchat.ListChatDataAccessInterface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatDataAccessObject implements ChatDataAccessInterface,
        ListChatDataAccessInterface {
    private final String apiKey;
    private final String appID;
    private final String TOKEN_HEADER = "Api-token";
    private final String API_ENDPOINT;

    public ChatDataAccessObject() {
        final Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("SENDBIRD_API_KEY");
        appID = dotenv.get("SENDBIRD_APP_ID");
        API_ENDPOINT = "https://api-" + appID + ".sendbird.com/v3/";
    }

    /**
     * Creates a new user in SendBird's database.
     *
     * @param uniqueId       the unique id assigned when a user signs up.
     * @param username       the username the user inputs during sign up.
     * @param profilePicture the URL to their profile uploaded to Cloudinarys' end
     */
    public void createChatUser(String uniqueId, String username, String profilePicture) {
        final JSONObject requestBody = new JSONObject();
        requestBody.put("user_id", uniqueId);
        requestBody.put("nickname", username);
        requestBody.put("profile_url", profilePicture);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT + "users"))
                .header(TOKEN_HEADER, apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject responseJSON = new JSONObject(response.body());
            if (responseJSON.has("error")) {
                System.out.println("Something went wrong, server threw an error");
            }

        } catch (InterruptedException | IOException e) {
            System.out.println("Something went wrong");
        }
    }

    public void updateProfilePicture(String uniqueID, String newProfilePicture) {
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("profile_url", newProfilePicture);
//
//        HttpRequest patchRequest = HttpRequest.newBuilder()
//                .uri(URI.create(API_ENDPOINT + "users/" + uniqueID))
//                .header(TOKEN_HEADER, apiKey)
//                .header("Content-Type", "application/json; charset=utf8")
//                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody.toString()))
//                .build();
//
//        HttpClient client = HttpClient.newHttpClient();
//        try {
//            HttpResponse<String> response = client.send(patchRequest, HttpResponse.BodyHandlers.ofString());
//            System.out.println(response.body());
//            JSONObject responseJSON = new JSONObject(response.body());
//            if (responseJSON.has("error")) {
//                System.out.println("Something went wrong, server threw an error");
//            } else {
//                System.out.println("Profile picture updated successfully.");
//            }
//        } catch (InterruptedException | IOException e) {
//            System.out.println("Something went wrong");
//        }
    }



    public void updateFullName(String uniqueID, String newFullName) {
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("nickname", newFullName);
//
//        HttpRequest patchRequest = HttpRequest.newBuilder()
//                .uri(URI.create(API_ENDPOINT + "users/" + uniqueID))
//                .header(TOKEN_HEADER, apiKey)
//                .header("Content-Type", "application/json; charset=utf8")
//                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody.toString()))
//                .build();
//
//        HttpClient client = HttpClient.newHttpClient();
//        try {
//            HttpResponse<String> response = client.send(patchRequest, HttpResponse.BodyHandlers.ofString());
//            System.out.println("Response: " + response.body());
//
//            // Check if the response is valid JSON
//            try {
//                JSONObject responseJSON = new JSONObject(response.body());
//                if (responseJSON.has("error")) {
//                    System.out.println("Something went wrong, server threw an error: " + responseJSON.getString("message"));
//                } else {
//                    System.out.println("Full name updated successfully.");
//                }
//            } catch (JSONException e) {
//                // Response is not JSON
//                System.out.println("Response is not JSON. Raw response: " + response.body());
//            }
//
//        } catch (InterruptedException | IOException e) {
//            System.out.println("Something went wrong");
//            e.printStackTrace();
//        }
    }

    /**
     * @param userId1 1st user
     * @param userId2 2nd user
     *                <p>
     *                Creates a SendBird with url being "{user_id1}_{user_id2}_chat"
     */
    @Override
    public void createChat(String userId1, String userId2) {
        List<String> chatUsers = new ArrayList<>();
        chatUsers.add(userId1);
        chatUsers.add(userId2);
        JSONObject requestBody = new JSONObject();
        System.out.println("creating new chat");
        requestBody.put("name", userId1 + "_" + userId2 + "_chat"); // name of chat
        requestBody.put("channel_url", userId1 + "_" + userId2 + "_chat"); // url of chat
        requestBody.put("operator_ids", chatUsers); // the 2 users involved in a chat

        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT + "open_channels"))
                .header(TOKEN_HEADER, apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
        sendPostReq(postRequest);
    }


    /**
     * Returns the URL of all the chats that this user is a part of.
     *
     * @param username the user_id stored in sendbird of the user we want to get the chat logs of
     * @return a list of URLs of chats that contains that username
     */
    @Override
    public ArrayList<ChatChannel> getAllChats(String username) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT + "open_channels?url_contains=" + username))
                .header(TOKEN_HEADER, apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .GET()
                .build();
        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        JSONObject responseJSON = new JSONObject(response.body());
        return extractChatInfo(responseJSON);
    }


    @Override
    public void sendMessage(String chatURL, ChatMessage chatMessage) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("message_type", "MESG");
        requestBody.put("user_id", chatMessage.getSender());
        requestBody.put("message", chatMessage.getMessage());
        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT + "open_channels/" + chatURL + "/messages"))
                .header(TOKEN_HEADER, apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
        sendPostReq(postRequest);
    }

    @Override
    /**
     * @param chatURL
     * @return a JSON object containing a list of messages, its sender, time stamp, read-status
     */
    public List<ChatMessage> getAllMessages(String chatURL) {
        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT + "open_channels/" + chatURL
                        + "/messages?message_ts=" + System.currentTimeMillis()))
                .header(TOKEN_HEADER, apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject responseJSON = new JSONObject(response.body());

            if (responseJSON.has("error")) {
                System.out.println("Couldn't get the chat messages, URL is likely wrong");
                return new ArrayList<>();
            }

            // for each message
            return extractMessagesFromJSON(responseJSON);

        } catch (InterruptedException | IOException e) {
            System.out.println("Something went wrong, the endpoint had an error. ");
            return new ArrayList<>();
        }
    }

    // helper method to parse all messages in a chat
    private List<ChatMessage> extractMessagesFromJSON(JSONObject inputJSON) {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);
        final JSONArray messages = inputJSON.getJSONArray("messages");
        List<ChatMessage> result = new ArrayList<>();

        // loop through all messages
        for (int i = 0; i < messages.length(); i++) {
            final JSONObject messageObj = messages.getJSONObject(i);
            final String message = messageObj.getString("message");
            final String timestamp = formatter.format(Instant.ofEpochMilli(messageObj.getLong("created_at")));
            final String sender = messageObj.getJSONObject("user").getString("user_id");
            result.add(new ChatMessage(sender, message, timestamp));

        }
        return result;
    }

    private ArrayList<ChatChannel> extractChatInfo(JSONObject inputJSON) {
        ArrayList<ChatChannel> result = new ArrayList<>();
        // Check if the "channels" key exists
        if (inputJSON.has("channels")) {
            final JSONArray channels = inputJSON.getJSONArray("channels");

            // Iterate over the array of channels
            for (int i = 0; i < channels.length(); i++) {
                final JSONObject channel = channels.getJSONObject(i);
                // Add the channel_url to the list
                final String url = channel.getString("channel_url");
                final String user1 = url.split("_")[0];
                final String user2 = url.split("_")[1];
                final List<ChatMessage> allMessages = getAllMessages(url);
                String lastMessage;
                if (allMessages.isEmpty()) {
                    lastMessage = "Say 'hi!' or something, just don't be weird";
                }
                else {
                    lastMessage = allMessages.get(allMessages.size() - 1).getMessage();
                }
                result.add(new ChatChannel(url, lastMessage, user1, user2));
            }
        }
        return result;
    }

    // helper method to send post requests
    private void sendPostReq(HttpRequest postRequest) {
        final HttpClient client = HttpClient.newHttpClient();
        try {
            final HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            final JSONObject responseJSON = new JSONObject(response.body());
            if (responseJSON.has("error")) {
                System.out.println("Something went wrong, likely, the user doesn't exist");
            }

        }
        catch (InterruptedException | IOException e) {
            System.out.println("Something went wrong, couldn't get a ");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        ChatDataAccessObject cDAO = new ChatDataAccessObject();
//        cDAO.createChat("tete", "poppy12");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://70a4-138-51-79-15.ngrok-free.app/"))    //TODO: add endpoint
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString("hi mom"))
                .build();
        System.out.println("sent messge");
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);

    }
}
