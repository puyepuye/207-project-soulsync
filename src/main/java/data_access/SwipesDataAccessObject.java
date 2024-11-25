package data_access;


import com.mongodb.client.MongoCollection;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.tomcat.jni.User;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.List;
import java.util.Map;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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

public class SwipesListDataAccessObject  {
    private final String apiKey;
    private final String appID;
    private final String TOKEN_HEADER = "Api-token";
    private final String API_ENDPOINT;

    private final MongoCollection<Document> userCollection;

    /**
     * Returns the URL of all the chats that this user is a part of.
     * @param username
     * @return a list of URLs of chats that contains that username
     */
    private List<String> CreateSwipingList(){
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

}



