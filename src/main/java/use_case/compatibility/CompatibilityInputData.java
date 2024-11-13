package use_case.compatibility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompatibilityInputData {
    private final String username;

    public CompatibilityInputData(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }

}
