package data_access;

import data_access.repository.CustomUserRepository;
import entity.CommonUser;
import entity.User;
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

    //@Override

    public static void main(String[] args) {
        SpringApplication.run(SpringUserDAO.class, args);
        System.out.println("Himom");
    }



    public void run(String... args) {

        // Unit test
        System.out.println("Himom");
        System.out.println(customUserRepository.get("bob"));
        ArrayList<String> preference = new ArrayList<>();
        preference.add("bob");
        Map<String, Integer> map = new HashMap<>();
        Map<String, Boolean> pref = new HashMap<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("blessed");
        CommonUser newUser = new CommonUser("hughjazz","42069","...","Hugh Jass",
                "Saskatchewan", "male", preference , new Date(2021, 12,2),
                map, "I like turtles", pref, tags,null,null,null, null);
        customUserRepository.saveUser(newUser);
        System.out.println(customUserRepository.get("hughjazz"));

    }

}
