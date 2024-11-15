package data_access.repository;

import entity.CommonUser;
import entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.List;

public interface UserRepo extends MongoRepository<CommonUser, String>, SignupUserDataAccessInterface {


    CommonUser findByusername(String username);

    @NotNull
    public List<CommonUser> findAll();
}
