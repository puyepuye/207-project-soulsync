package use_case.change_password;

import entity.User;
import entity.UserFactory;

import java.util.*;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {
    private final ChangePasswordUserDataAccessInterface userDataAccessObject;
    private final ChangePasswordOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public ChangePasswordInteractor(ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface,
                                    ChangePasswordOutputBoundary changePasswordOutputBoundary,
                                    UserFactory userFactory) {
        this.userDataAccessObject = changePasswordDataAccessInterface;
        this.userPresenter = changePasswordOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        Date sampleDate = new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime();
        final User user =  userFactory.create(changePasswordInputData.getUsername(), changePasswordInputData.getPassword(), "imageLink", "Fullname", "Location",
                "gender", new ArrayList<>() {{}}, sampleDate, new HashMap<>() {{put("min", 18); put("max", 99);}}, "", new HashMap<>() {{}}, new ArrayList<>() {{}}, new ArrayList<>() {{}});

        userDataAccessObject.changePassword(user);

        final ChangePasswordOutputData changePasswordOutputData = new ChangePasswordOutputData(user.getUsername(),
        //final ChangePasswordOutputData changePasswordOutputData = new ChangePasswordOutputData(user.getFullName(),
                                                                                  false);
        userPresenter.prepareSuccessView(changePasswordOutputData);
    }
}
