package use_case.edit_profile;

import entity.User;
import entity.UserFactory;
import use_case.chat.ChatDataAccessInterface;

import javax.swing.*;
import java.text.ParseException;

import java.util.*;

/**
 * The Signup Interactor.
 */
public class EditProfileInteractor implements EditProfileInputBoundary {
    private final EditProfileUserDataAccessInterface userDataAccessObject;
    private final EditProfileOutputBoundary userPresenter;
    private final UserFactory userFactory;
    private final ChatDataAccessInterface chatDataAccessObject;

    public EditProfileInteractor(EditProfileUserDataAccessInterface editProfileDataAccessInterface,
                            EditProfileOutputBoundary editProfileOutputBoundary,
                            UserFactory userFactory,
                            ChatDataAccessInterface chatDataAccessInterface) {
        this.userDataAccessObject = editProfileDataAccessInterface;
        this.userPresenter = editProfileOutputBoundary;
        this.userFactory = userFactory;
        this.chatDataAccessObject = chatDataAccessInterface;
    }

    @Override
    public void execute(EditProfileInputData editProfileInputData) throws ParseException {
        if (editProfileInputData.getImage() != null || editProfileInputData.getImage() != "") {
            final User currentUserInfo = userDataAccessObject.get(editProfileInputData.getUsername());
            final User user =  userFactory.create(currentUserInfo.getUsername(),
                    currentUserInfo.getPassword(),
                    editProfileInputData.getImage(),
                    currentUserInfo.getFullname(),
                    currentUserInfo.getLocation(),
                    currentUserInfo.getGender(),
                    currentUserInfo.getPreferredGender(),
                    currentUserInfo.getDateOfBirth(),
                    currentUserInfo.getPreferredAge(),
                    currentUserInfo.getBio(),
                    currentUserInfo.getPreferences(),
                    currentUserInfo.getTags(),
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}}
            );
            userDataAccessObject.changeImage(user);
            chatDataAccessObject.createChatUser(editProfileInputData.getUsername(), currentUserInfo.getFullname(), editProfileInputData.getImage());
            final EditProfileOutputData editProfileOutputData = new EditProfileOutputData(user.getUsername(), user.getFullname(), user.getPassword(), user.getImage(),
                    user.getLocation(), user.getGender(), user.getDateOfBirth(), user.getPreferredGender(), user.getPreferredAge(), false);

            userPresenter.prepareSuccessView(editProfileOutputData);
        }
        if (editProfileInputData.getPassword().equals(editProfileInputData.getRepeatPassword())) {
            final User currentUserInfo = userDataAccessObject.get(editProfileInputData.getUsername());
            final User user =  userFactory.create(currentUserInfo.getUsername(),
                    editProfileInputData.getPassword(),
                    currentUserInfo.getImage(),
                    currentUserInfo.getFullname(),
                    currentUserInfo.getLocation(),
                    currentUserInfo.getGender(),
                    currentUserInfo.getPreferredGender(),
                    currentUserInfo.getDateOfBirth(),
                    currentUserInfo.getPreferredAge(),
                    currentUserInfo.getBio(),
                    currentUserInfo.getPreferences(),
                    currentUserInfo.getTags(),
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}}
            );
            userDataAccessObject.changePassword(user);
            chatDataAccessObject.createChatUser(editProfileInputData.getUsername(), currentUserInfo.getFullname(), currentUserInfo.getImage());
            final EditProfileOutputData editProfileOutputData = new EditProfileOutputData(user.getUsername(), user.getFullname(), user.getPassword(), user.getImage(),
                    user.getLocation(), user.getGender(), user.getDateOfBirth(), user.getPreferredGender(), user.getPreferredAge(), false);

            userPresenter.prepareSuccessView(editProfileOutputData);
        }
        if (editProfileInputData.getFullname().matches("[\\p{IsAlphabetic} ]+")) {
            final User currentUserInfo = userDataAccessObject.get(editProfileInputData.getUsername());
            System.out.println(currentUserInfo.getUsername());
            System.out.println(currentUserInfo.getPassword());
            final User user =  userFactory.create(currentUserInfo.getUsername(),
                    currentUserInfo.getPassword(),
                    currentUserInfo.getImage(),
                    editProfileInputData.getFullname(),
                    currentUserInfo.getLocation(),
                    currentUserInfo.getGender(),
                    currentUserInfo.getPreferredGender(),
                    currentUserInfo.getDateOfBirth(),
                    currentUserInfo.getPreferredAge(),
                    currentUserInfo.getBio(),
                    currentUserInfo.getPreferences(),
                    currentUserInfo.getTags(),
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}}
            );
            userDataAccessObject.changeFullname(user);
            chatDataAccessObject.createChatUser(editProfileInputData.getUsername(), editProfileInputData.getFullname(), currentUserInfo.getImage());
            final EditProfileOutputData editProfileOutputData = new EditProfileOutputData(user.getUsername(), user.getFullname(), user.getPassword(), user.getImage(),
                    user.getLocation(), user.getGender(), user.getDateOfBirth(), user.getPreferredGender(), user.getPreferredAge(), false);

            userPresenter.prepareSuccessView(editProfileOutputData);
        }
        if (editProfileInputData.getDateOfBirth() != null) {
            final User currentUserInfo = userDataAccessObject.get(editProfileInputData.getUsername());
            final User user =  userFactory.create(currentUserInfo.getUsername(),
                    currentUserInfo.getPassword(),
                    currentUserInfo.getImage(),
                    currentUserInfo.getFullname(),
                    currentUserInfo.getLocation(),
                    currentUserInfo.getGender(),
                    currentUserInfo.getPreferredGender(),
                    editProfileInputData.getDateOfBirth(),
                    currentUserInfo.getPreferredAge(),
                    currentUserInfo.getBio(),
                    currentUserInfo.getPreferences(),
                    currentUserInfo.getTags(),
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}}
            );
            userDataAccessObject.changeDOB(user);
            chatDataAccessObject.createChatUser(editProfileInputData.getUsername(), currentUserInfo.getFullname(), currentUserInfo.getImage());
            final EditProfileOutputData editProfileOutputData = new EditProfileOutputData(user.getUsername(), user.getFullname(), user.getPassword(), user.getImage(),
                    user.getLocation(), user.getGender(), user.getDateOfBirth(), user.getPreferredGender(), user.getPreferredAge(), false);

            userPresenter.prepareSuccessView(editProfileOutputData);
        }
        if (!editProfileInputData.getGender().isEmpty() || !editProfileInputData.getGender().equals("Select Gender")) {
            final User currentUserInfo = userDataAccessObject.get(editProfileInputData.getUsername());
            final User user =  userFactory.create(currentUserInfo.getUsername(),
                    currentUserInfo.getPassword(),
                    currentUserInfo.getImage(),
                    currentUserInfo.getFullname(),
                    currentUserInfo.getLocation(),
                    editProfileInputData.getGender(),
                    currentUserInfo.getPreferredGender(),
                    currentUserInfo.getDateOfBirth(),
                    currentUserInfo.getPreferredAge(),
                    currentUserInfo.getBio(),
                    currentUserInfo.getPreferences(),
                    currentUserInfo.getTags(),
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}}
            );
            userDataAccessObject.changeGender(user);
            chatDataAccessObject.createChatUser(editProfileInputData.getUsername(), currentUserInfo.getFullname(), currentUserInfo.getImage());
            final EditProfileOutputData editProfileOutputData = new EditProfileOutputData(user.getUsername(), user.getFullname(), user.getPassword(), user.getImage(),
                    user.getLocation(), user.getGender(), user.getDateOfBirth(), user.getPreferredGender(), user.getPreferredAge(), false);

            userPresenter.prepareSuccessView(editProfileOutputData);
        }
        if (!editProfileInputData.getLocation().isEmpty() || !editProfileInputData.getLocation().contains("Select")) {
            final User currentUserInfo = userDataAccessObject.get(editProfileInputData.getUsername());
            final User user =  userFactory.create(currentUserInfo.getUsername(),
                    currentUserInfo.getPassword(),
                    currentUserInfo.getImage(),
                    currentUserInfo.getFullname(),
                    editProfileInputData.getLocation(),
                    currentUserInfo.getGender(),
                    currentUserInfo.getPreferredGender(),
                    currentUserInfo.getDateOfBirth(),
                    currentUserInfo.getPreferredAge(),
                    currentUserInfo.getBio(),
                    currentUserInfo.getPreferences(),
                    currentUserInfo.getTags(),
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}}
            );
            userDataAccessObject.changeLocation(user);
            chatDataAccessObject.createChatUser(editProfileInputData.getUsername(), currentUserInfo.getFullname(), currentUserInfo.getImage());
            final EditProfileOutputData editProfileOutputData = new EditProfileOutputData(user.getUsername(), user.getFullname(), user.getPassword(), user.getImage(),
                    user.getLocation(), user.getGender(), user.getDateOfBirth(), user.getPreferredGender(), user.getPreferredAge(), false);

            userPresenter.prepareSuccessView(editProfileOutputData);
        }
        if (editProfileInputData.getPreferredAge() != null) {
            final User currentUserInfo = userDataAccessObject.get(editProfileInputData.getUsername());
            final User user =  userFactory.create(currentUserInfo.getUsername(),
                    currentUserInfo.getPassword(),
                    currentUserInfo.getImage(),
                    currentUserInfo.getFullname(),
                    currentUserInfo.getLocation(),
                    currentUserInfo.getGender(),
                    currentUserInfo.getPreferredGender(),
                    currentUserInfo.getDateOfBirth(),
                    editProfileInputData.getPreferredAge(),
                    currentUserInfo.getBio(),
                    currentUserInfo.getPreferences(),
                    currentUserInfo.getTags(),
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}}
            );
            userDataAccessObject.changePreferredAge(user);
            chatDataAccessObject.createChatUser(editProfileInputData.getUsername(), currentUserInfo.getFullname(), currentUserInfo.getImage());
            final EditProfileOutputData editProfileOutputData = new EditProfileOutputData(user.getUsername(), user.getFullname(), user.getPassword(), user.getImage(),
                    user.getLocation(), user.getGender(), user.getDateOfBirth(), user.getPreferredGender(), user.getPreferredAge(), false);

            userPresenter.prepareSuccessView(editProfileOutputData);
        }
        if (editProfileInputData.getPreferredGender() != null) {
            final User currentUserInfo = userDataAccessObject.get(editProfileInputData.getUsername());
            final User user =  userFactory.create(currentUserInfo.getUsername(),
                    currentUserInfo.getPassword(),
                    currentUserInfo.getImage(),
                    currentUserInfo.getFullname(),
                    currentUserInfo.getLocation(),
                    currentUserInfo.getGender(),
                    editProfileInputData.getPreferredGender(),
                    currentUserInfo.getDateOfBirth(),
                    currentUserInfo.getPreferredAge(),
                    currentUserInfo.getBio(),
                    currentUserInfo.getPreferences(),
                    currentUserInfo.getTags(),
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}},
                    new ArrayList<>() {{}}
            );
            userDataAccessObject.changePreferredGender(user);
            chatDataAccessObject.createChatUser(editProfileInputData.getUsername(), currentUserInfo.getFullname(), currentUserInfo.getImage());
            final EditProfileOutputData editProfileOutputData = new EditProfileOutputData(user.getUsername(), user.getFullname(), user.getPassword(), user.getImage(),
                    user.getLocation(), user.getGender(), user.getDateOfBirth(), user.getPreferredGender(), user.getPreferredAge(), false);

            userPresenter.prepareSuccessView(editProfileOutputData);
        }
    }

}
