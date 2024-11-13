package data_access;

import data_access.repository.UserRepository;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude={MongoAutoConfiguration.class})
@EnableMongoRepositories

public class SpringMain implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringMain.class, args);


    }

    public void GetUserByName(String name) {
        User user = userRepository.get(name);
        System.out.println(user.getUsername());
    }

    @Override
    public void run(String... args) throws Exception {
        GetUserByName("bob");
    }

}
