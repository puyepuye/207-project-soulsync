package data_access;

import entity.User;
import entity.UserFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SendBirdChatObject {
    private final UserFactory userFactory;
    private final String apiKey;
    private final String appID;

    public SendBirdChatObject(UserFactory userFactory, String newID) {
        this.userFactory = userFactory;
        // apiKey =  System.getenv("SendBird_API_KEY");
        appID = "CE20803F-158E-4B7E-A270-2FD7B78C5F4F"; //TODO: un-hardcode this.
    }

    public SendBirdChatObject() {
        apiKey =  System.getenv("SENDBIRD_API_KEY");
        appID = "CE20803F-158E-4B7E-A270-2FD7B78C5F4F"; //TODO: un-hardcode this.
        this.userFactory = null;
    }

    /**
     * Creates a new user in SendBird's database
     */
    public void CreateSendBirdUser(String uniqueID, String username, String profilePicture){
        JSONObject requestBody = new JSONObject();
        requestBody.put("user_id", uniqueID);
        requestBody.put("nickname", username);
        requestBody.put("profile_url", profilePicture);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api-" + appID + ".sendbird.com/v3/users"))
                .header("Api-token", apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject responseJSON = new JSONObject(response.body());
            if (responseJSON.has("error")) {
                if (responseJSON.getJSONObject("error").equals(true)){
                    System.out.println("Something went wrong, server threw an error");
                }
            }

        } catch (InterruptedException | IOException e) {
            System.out.println("Something went wrong");
        }
    }

    public String GetUser(){
        return "";
    }

    /**
     * @param user_id1
     * @param user_id2
     *
     * Creates a SendBird with url being "{user_id1}_{user_id2}_chat"
     */
    public void CreateSendBirdChat(String user_id1, String user_id2){

        // TODO: Add check if the two users are matched in MongoDB
        List<String> chatUsers = new ArrayList<>();
        chatUsers.add(user_id1);
        chatUsers.add(user_id2);
        JSONObject requestBody = new JSONObject();

        requestBody.put("name", user_id1 + "_" + user_id2 + "_chat"); // name of chat
        requestBody.put("channel_url", user_id1 + "_" + user_id2 + "_chat"); // url of chat
        requestBody.put("operator_ids", chatUsers); // the 2 users involved in a chat

        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api-" + appID + ".sendbird.com/v3/open_channels"))
                .header("Api-token", apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject responseJSON = new JSONObject(response.body());
            if (responseJSON.has("error")) {
                if (responseJSON.getJSONObject("error").equals(true)){
                    System.out.println("Something went wrong, likely, the user doesn't exist");
                }
            }

        } catch (InterruptedException | IOException e) {
            System.out.println("Something went wrong, couldn't get a ");
        }

    }

    public void SendMessage(String channelURL, String senderID, String recipientID, String message){
        JSONObject requestBody = new JSONObject();
        requestBody.put("message_type", "MESG");
        requestBody.put("user_id", senderID);
        requestBody.put("message", message);
        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api-" + appID + ".sendbird.com/v3/open_channels/" + channelURL + "/messages"))
                .header("Api-token", apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject responseJSON = new JSONObject(response.body());
            if (responseJSON.has("error")) {
                if (responseJSON.getJSONObject("error").equals(true)){
                    System.out.println("Something went wrong, likely, the user doesn't exist");
                }
            }

        } catch (InterruptedException | IOException e) {
            System.out.println("Something went wrong, couldn't get a ");
        }
    }

    /**
     * @param channelURL
     * @return a JSON object containing a list of messages, its sender, time stamp, read-status
     */
    public List<JSONObject> GetChatMessages(String channelURL){
        // build request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api-" + appID + ".sendbird.com/v3/open_channels/" + channelURL
                        + "/messages?message_ts=" + System.currentTimeMillis()))
                .header("Api-token", apiKey)
                .header("Content-Type", "application/json; charset=utf8")
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject responseJSON = new JSONObject(response.body());

            if (responseJSON.has("error")) {
                if (responseJSON.getJSONObject("error").equals(true)){
                    System.out.println("Couldn't get the chat messages, URL is likely wrong");
                    return new ArrayList<>();
                }
            }

            // for each message
            return ExtractMessagesFromJSON(responseJSON);

        } catch (InterruptedException | IOException e) {
            System.out.println("Something went wrong, the endpoint had an error. ");
            return new ArrayList<>();
        }
    }

    public List<JSONObject> ExtractMessagesFromJSON(JSONObject inputJSON) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);
        JSONArray messages = inputJSON.getJSONArray("messages");
        List<JSONObject> result = new ArrayList<>();

        for (int i = 0; i < messages.length(); i++) {
            JSONObject messageSummary = new JSONObject();
            JSONObject message = messages.getJSONObject(i);
            messageSummary.put("message", message.getString("message"));
            String timestamp = formatter.format(Instant.ofEpochMilli(message.getLong("created_at")));
            messageSummary.put("timestamp", timestamp);
            messageSummary.put("user_id", message.getJSONObject("user").getString("user_id"));
            messageSummary.put("user_name", message.getJSONObject("user").getString("nickname"));
            result.add(messageSummary);

        }
        return result;
}

    public static void main(String[] args) {
        SendBirdChatObject so = new SendBirdChatObject();
        //so.CreateSendBirdUser("1234567", "Yollie", "");
        String sampleObject = "{\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"type\": \"MESG\",\n" +
                "            \"message_id\": 99211369,\n" +
                "            \"message\": \"hi, this is a test message!\",\n" +
                "            \"data\": \"\",\n" +
                "            \"custom_type\": \"\",\n" +
                "            \"file\": {},\n" +
                "            \"created_at\": 1731172973394,\n" +
                "            \"user\": {\n" +
                "                \"user_id\": \"69\",\n" +
                "                \"profile_url\": \"https://i.scdn.co/image/ab67616d0000b273baf89eb11ec7c657805d2da0\",\n" +
                "                \"require_auth_for_profile_image\": false,\n" +
                "                \"nickname\": \"Mac\",\n" +
                "                \"metadata\": {},\n" +
                "                \"role\": \"operator\",\n" +
                "                \"is_active\": true\n" +
                "            },\n" +
                "            \"channel_url\": \"1234\",\n" +
                "            \"updated_at\": 0,\n" +
                "            \"message_survival_seconds\": -1,\n" +
                "            \"mentioned_users\": [],\n" +
                "            \"mention_type\": \"users\",\n" +
                "            \"silent\": false,\n" +
                "            \"message_retention_hour\": -1,\n" +
                "            \"channel_type\": \"open\",\n" +
                "            \"translations\": {},\n" +
                "            \"is_removed\": false,\n" +
                "            \"is_op_msg\": true,\n" +
                "            \"message_events\": {\n" +
                "                \"send_push_notification\": \"receivers\",\n" +
                "                \"update_unread_count\": true,\n" +
                "                \"update_mention_count\": true,\n" +
                "                \"update_last_message\": true\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        JSONObject sample_response = new JSONObject(sampleObject);
        List<JSONObject> jo = so.ExtractMessagesFromJSON(sample_response);
        // System.out.println(jo.toString());
        so.CreateSendBirdUser("42069", "Yollie", "https://cdn.britannica.com/96/1296-050-4A65097D/gelding-bay-coat.jpg");
        so.CreateSendBirdChat("42069", "69");
        so.SendMessage("42069_69_chat", "42069", "69",
                "Hello, this is mac sending a test message via Java");
        so.SendMessage("42069_69_chat", "69", "42069",
                "Hello Yole");
        System.out.println(so.GetChatMessages("42069_69_chat"));
    }
}
