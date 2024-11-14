package data_access.repository;

import entity.mackeys;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MacRepo extends MongoRepository<mackeys, String> {
    @Query("{name: '?0'}")
    mackeys findByName(String name);

    @NotNull
    public List<mackeys> findAll();
}
