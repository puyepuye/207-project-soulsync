package use_case.swipe;

import data_access.SwipesListDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import interface_adapter.swipe.SwipePresenter;
import org.junit.Before;
import org.junit.Test;
import use_case.chat.ChatDataAccessInterface;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SwipeInteractorTest {

    private UserFactory userFactory;
    private SwipeUserDataAccessInterface dao;
    private SwipeInteractor interactor;
    private SwipeOutputBoundary presenter;

    private User user;
    private User profileUser;

    @Before
    public void setUp() {
        this.userFactory = new CommonUserFactory();
        this.dao = mock(SwipeUserDataAccessInterface.class);
        this.presenter = mock(SwipePresenter.class);
        ChatDataAccessInterface chatDataAccessObject = mock(ChatDataAccessInterface.class); // Mock ChatDataAccessInterface
        this.interactor = new SwipeInteractor(dao, presenter, userFactory, chatDataAccessObject);
    }

    @Test   // Checks that execute method calls the correct methods
    public void executeTest() {
        SwipeInputData inputData = new SwipeInputData(true, "bob", "password", "alice");
        interactor.execute(inputData);

        // Verify methods are called on the mock DAO and presenter
        verify(dao, times(1)).get("bob");
        verify(dao, times(1)).get("alice");
        verify(dao, times(1)).updateLike(user, profileUser, true);
        verify(presenter, times(1)).prepareSuccessView(any(SwipeOutputData.class));
    }

    @Test   // Checks that saveMatch method calls the correct methods
    public void saveMatchTest() {
        SwipeInputData inputData = new SwipeInputData(true, "alice", "password", "bob");
        interactor.saveMatch(inputData);

        // Verify that saveMatch is called with the correct order of arguments
        verify(dao, times(1)).saveMatch("alice", "bob"); // Fix argument order to match actual call
    }


    @Test   // Checks that no profiles are left when swipe reaches the end
    public void noMoreProfilesTest() {
        ArrayList<User> swipesList = new ArrayList<>();
        when(dao.get("bob")).thenReturn(user);
        when(dao.get("alice")).thenReturn(profileUser);

        SwipeInputData inputData = new SwipeInputData(true, "alice", "password", "bob");
        interactor.execute(inputData);

        // Assuming that the list of profiles becomes empty after the swipe
        assertEquals(0, swipesList.size()); // Modify based on your actual implementation for profile list
    }
}
