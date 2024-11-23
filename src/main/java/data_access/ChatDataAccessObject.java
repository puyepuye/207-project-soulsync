package data_access;

import entity.ChatMessage;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.chat.ChatDataAccessInterface;

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

public class ChatDataAccessObject  implements ChatDataAccessInterface {
    private final String apiKey;
    private final String appID;
    private final String TOKEN_HEADER = "Api-token";
    private final String API_ENDPOINT;

    public ChatDataAccessObject() {
        Dotenv dotenv = Dotenv.load();
        apiKey = dotenv.get("SEND_BIRD_API_KEY");
        appID = dotenv.get("SENDBIRD_APP_ID");
        API_ENDPOINT = "https://api-" + appID + ".sendbird.com/v3/";
    }


    /**
     * Creates a new user in SendBird's database
     */
    public void createChatUser(String uniqueID, String username, String profilePicture){
        JSONObject requestBody = new JSONObject();
        requestBody.put("user_id", uniqueID);
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

    /**
     * @param user_id1
     * @param user_id2
     *
     * Creates a SendBird with url being "{user_id1}_{user_id2}_chat"
     */
    public void CreateSendBirdChat(String user_id1, String user_id2){
        List<String> chatUsers = new ArrayList<>();
        chatUsers.add(user_id1);
        chatUsers.add(user_id2);
        JSONObject requestBody = new JSONObject();

        requestBody.put("name", user_id1 + "_" + user_id2 + "_chat"); // name of chat
        requestBody.put("channel_url", user_id1 + "_" + user_id2 + "_chat"); // url of chat
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
     * @param username
     * @return a list of URLs of chats that contains that username
     */
    private List<String> getAllChats(String username) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT+ "open_channels?url_contains=" + username))
                .header(TOKEN_HEADER, apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .GET()
                .build();
        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        JSONObject responseJSON = new JSONObject(response.body());
        return  extractChatURLFromJSON(responseJSON);
    }



    @Override
    public void sendMessage(String chatURL, ChatMessage chatMessage){
        JSONObject requestBody = new JSONObject();
        requestBody.put("message_type", "MESG");
        requestBody.put("user_id", chatMessage.getSender());
        requestBody.put("message", chatMessage.getMessage());
        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create( API_ENDPOINT + "open_channels/" + chatURL + "/messages"))
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
    public List<ChatMessage> getAllMessages(String chatURL){
        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_ENDPOINT+ "open_channels/" + chatURL
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
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);
        JSONArray messages = inputJSON.getJSONArray("messages");
        List<ChatMessage> result = new ArrayList<>();

        // loop through all messages
        for (int i = 0; i < messages.length(); i++) {
            JSONObject messageObj = messages.getJSONObject(i);
            String message = messageObj.getString("message");
            String timestamp = formatter.format(Instant.ofEpochMilli(messageObj.getLong("created_at")));
            String sender = messageObj.getJSONObject("user").getString("nickname");
            result.add(new ChatMessage(sender, message, timestamp));

        }
        return result;
    }

    private List<String> extractChatURLFromJSON(JSONObject inputJSON) {
        List<String> result = new ArrayList<>();
        // Check if the "channels" key exists
        if (inputJSON.has("channels")) {
            JSONArray channels = inputJSON.getJSONArray("channels");

            // Iterate over the array of channels
            for (int i = 0; i < channels.length(); i++) {
                JSONObject channel = channels.getJSONObject(i);

                // Add the channel_url to the list
                if (channel.has("channel_url")) {
                    result.add(channel.getString("channel_url"));
                }
            }
        }
        return result;
    }

    // helper method to send post requests
    private void sendPostReq(HttpRequest postRequest) {
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject responseJSON = new JSONObject(response.body());
            if (responseJSON.has("error")) {
                System.out.println("Something went wrong, likely, the user doesn't exist");
            }

        } catch (InterruptedException | IOException e) {
            System.out.println("Something went wrong, couldn't get a ");
        }
    }
}