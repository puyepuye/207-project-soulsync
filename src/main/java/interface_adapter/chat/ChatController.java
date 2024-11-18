package interface_adapter.chat;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

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


        //notificationService.processNotification(message);
    }
}