package use_case.compatibility;

import entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CompatibilityInteractor implements CompatibilityInputBoundary  {
    private final CompatibilityUserDataAccessInterface userDataAccessObject;
    private final CompatibilityOutputBoundary compatibilityPresenter;

    public CompatibilityInteractor(CompatibilityUserDataAccessInterface compatibilityDataAccessInterface,
                                   CompatibilityOutputBoundary compatibilityOutputBoundary) {
        this.userDataAccessObject = compatibilityDataAccessInterface;
        this.compatibilityPresenter = compatibilityOutputBoundary;
    }

    @Override
    public void execute(CompatibilityInputData compatibilityInputData) {
        if (!userDataAccessObject.existsByName(compatibilityInputData.getUsername())) {
            compatibilityPresenter.prepareFailView("User does not exists.");
        }
        else {
            final User user = userDataAccessObject.get(compatibilityInputData.getUsername());
            final CompatibilityOutputData compatibilityOutputData = new CompatibilityOutputData(user.getUsername(), false);
            compatibilityPresenter.prepareSuccessView(compatibilityOutputData);
        }
    }

    @Override
    public String[] getMatchedUsers(CompatibilityInputData compatibilityInputData) {
        if (!userDataAccessObject.existsByName(compatibilityInputData.getUsername())) {
            compatibilityPresenter.prepareFailView("User does not exists.");
            return new String[0];
        }
        else {
            final User user = userDataAccessObject.get(compatibilityInputData.getUsername());
            List<String> matchedUsers = user.getMatched();
            System.out.println(matchedUsers);
            final CompatibilityOutputData compatibilityOutputData = new CompatibilityOutputData(user.getUsername(), false);
            compatibilityPresenter.prepareSuccessView(compatibilityOutputData);
            return matchedUsers.toArray(new String[0]);
        }
    }

    @Override
    public Date getUserDOB(CompatibilityInputData compatibilityInputData) {
        final User user = userDataAccessObject.get(compatibilityInputData.getUsername());
        return user.getDateOfBirth();
    }

    @Override
    public Map<String, Boolean> getUserPreferences(CompatibilityInputData compatibilityInputData) {
        final User user = userDataAccessObject.get(compatibilityInputData.getUsername());
        return user.getPreferences();
    }

}
