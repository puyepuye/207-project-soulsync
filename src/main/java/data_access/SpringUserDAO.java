package data_access;

import data_access.repository.CustomMatchesRepository;
import data_access.repository.CustomUserRepository;
import entity.CommonMatches;
import entity.CommonUser;
import entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableMongoRepositories
public class SpringUserDAO implements CommandLineRunner {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    CustomMatchesRepository customMatchesRepository;

    //@Override

    public static void main(String[] args) {
        SpringApplication.run(SpringUserDAO.class, args);
        System.out.println("Himom");
    }



    public void run(String... args) {

        // Unit test
        System.out.println(customUserRepository.get("bob"));    // Tests getting

//        ArrayList<String> preference = new ArrayList<>();
//        preference.add("bob");
//        Map<String, Integer> map = new HashMap<>();
//        Map<String, Boolean> pref = new HashMap<>();
//        ArrayList<String> tags = new ArrayList<>();
//        tags.add("blessed");
//        CommonUser newUser = new CommonUser("hughjazz","42069","...","Hugh Jass",
//                "Saskatchewan", "male", preference , new Date(2021, 12,2),
//                map, "I like turtles", pref, tags,null,null,null, null);
        // customUserRepository.saveUser(newUser);
        System.out.println(customUserRepository.get("hughjazz").getUsername());

        ObjectId userA = new ObjectId("60b8d6c2f123456789abcdef");
        ObjectId userB = new ObjectId("60b8d6c2f123456789abcdee");
        System.out.println(customMatchesRepository.getMatch(userA, userB));

        ObjectId userC = new ObjectId("696969696blanlanla");
        ObjectId userD = new ObjectId("420420240lololol");
        CommonMatches cm = new CommonMatches(userC, userD, new Date(2021,12,11), true);
        customMatchesRepository.saveMatch(cm);





    }

}
