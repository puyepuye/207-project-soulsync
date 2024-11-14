package data_access.repository;

import entity.mackeys;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import javax.crypto.Mac;
import java.util.List;

public interface MacRepo extends MongoRepository<mackeys, String> {
    @Query("{name: '?0'}")
    mackeys findByName(String name);

    public List<mackeys> findAll();
}
