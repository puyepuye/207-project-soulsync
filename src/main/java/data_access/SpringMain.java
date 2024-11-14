package data_access;

import data_access.repository.MacRepo;
import entity.mackeys;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableMongoRepositories

public class SpringMain implements CommandLineRunner {

    @Autowired
    MacRepo macRepo;
    @Autowired
    NotificationService notificationService;


    public static void main(String[] args) {
        SpringApplication.run(SpringMain.class, args);


    }



    @Override
    public void run(String... args) throws Exception {
        System.out.println(macRepo.findAll());
        for(mackeys macs: macRepo.findAll()){
            System.out.println(macs.getName());
        }
    }

}

@RestController
class Controller {
    public String convertMillisToDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = new Date(millis);
        return sdf.format(date);
    }

    @PostMapping("/")
    public void receiveMessages(@RequestBody String body) throws JSONException {
        JSONObject json = new JSONObject(body);

        // Access the "payload" object
        JSONObject payload = json.getJSONObject("payload");

        // Retrieve "message" and "created_at" values
        String message = payload.getString("message");
        long createdAtMilli = payload.getLong("created_at");

        // Print the values
        System.out.println("Message: " + message);
        System.out.println("Created At: " + convertMillisToDate(createdAtMilli));
        notificationService.processNotification(message);
    }
}
