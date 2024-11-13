package data_access.repository;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TemplateRepo implements TemplateInt{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List getUser(String username) {
        Query query = new Query(Criteria.where("username").is(username));

        return mongoTemplate.find(query, User.class);
    }
}
