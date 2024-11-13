package entity;

import org.bson.types.ObjectId;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("CommonMatches")
public class CommonMatches {

    private final ObjectId userAId;
    private final ObjectId userBId;
    private final Date matchDate;
    private final Boolean isActive;

    public CommonMatches(ObjectId userAId, ObjectId userBId, Date matchDate, Boolean isActive) {
        this.userAId = userAId;
        this.userBId = userBId;
        this.matchDate = matchDate;
        this.isActive = isActive;
    }

    public ObjectId getUserAId() { return userAId; }

    public ObjectId getUserBId() { return userBId; }

    public Date getMatchDate() { return matchDate; }

    public Boolean getIsActive() {return isActive;}
}
