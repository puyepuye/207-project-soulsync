package use_case.login;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class LoginInteractorTest2 {
    private LoginInteractor loginInteractor;
    private LoginUserDataAccessInterface mockUserDataAccess;
    private LoginOutputBoundary mockLoginPresenter;

    @Before
    public void setUp() {
        // Initialize mock objects
        mockUserDataAccess = mock(LoginUserDataAccessInterface.class);
        mockLoginPresenter = mock(LoginOutputBoundary.class);

        // Initialize LoginInteractor w/ mocked dependencies
        loginInteractor = new LoginInteractor(mockUserDataAccess, mockLoginPresenter);
    }

    @Test
    public void testLoginSuccess() {
        // Test data
        String username = "usertest";
        String password = "userpassword";
        User user = new User(username, password);

        // Mock behavior of user data access interface
        when(mockUserDataAccess.existsByName(username)).thenReturn(true);
        when(mockUserDataAccess.get(username)).thenReturn(user);

        // Create input data for login attempt
        LoginInputData loginInputData = new LoginInputData(username, password);

        // Call execute method
        loginInteractor.execute(loginInputData);

        // Verify current was set
        verify(mockUserDataAccess).setCurrentUser(username);

        // Verify success view was prepared w/ correct output data
        LoginOutputData expectedOuputData = new LoginOutputData(username, false);
        verify(mockLoginPresenter).prepareSuccessView(expectedOuputData);
    }

    @Test
    public void testLoginFailureUserNotFound() {
        String username = "usertest";
        String password = "userpassword";

        when(mockUserDataAccess.existsByName(username)).thenReturn(false);

        LoginInputData loginInputData = new LoginInputData(username, password);

        loginInteractor.execute(loginInputData);

        verify(mockLoginPresenter).prepareFailView(username + " does not exist");
    }

    @Test
    public void testLoginFailurePasswordIncorrect() {
        String username = "usertest";
        String password = "userpassword";
        String correctPassword = "correctPassword";
        User user = new User(username, correctPassword);

        when(mockUserDataAccess.existsByName(username)).thenReturn(true);
        when(mockUserDataAccess.get(username)).thenReturn(user);

        LoginInputData loginInputData = new LoginInputData(username, password);

        loginInteractor.execute(loginInputData);

        verify(mockLoginPresenter).prepareFailView("Password for " + username + " does not exist");
    }

    @Test

}