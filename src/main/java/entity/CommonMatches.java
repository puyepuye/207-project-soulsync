package entity;

import org.bson.types.ObjectId;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("matchesCollection")
public class CommonMatches implements Matches{

    private final String usernameA;
    private final String usernameB;

    public CommonMatches(String usernameA, String usernameB) {
        this.usernameA = usernameA;
        this.usernameB = usernameB;
    }

    public String getUsernameA() { return usernameA; }

    public String getUsernameB() { return usernameB; }
}
