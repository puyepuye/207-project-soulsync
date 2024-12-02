package use_case.login;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LoginInteractorTest {

    private LoginInteractor loginInteractor;
    private LoginUserDataAccessInterface mockLoginUserDAI;
    private LoginOutputBoundary mockLoginOutputBoundary;


    @BeforeEach
    public void mockSetUp() {

        mockLoginUserDAI = Mockito.mock(LoginUserDataAccessInterface.class);
        mockLoginOutputBoundary = Mockito.mock(LoginOutputBoundary.class);
        loginInteractor = new LoginInteractor(mockLoginUserDAI, mockLoginOutputBoundary);

    }

    @Test
    public void successUserLoggedInTest() {

        String username = "userExists";
        String password = "passwordExists";
        LoginInputData inputData = new LoginInputData(username, password);

        User mockUser = Mockito.mock(User.class);

        when(mockLoginUserDAI.existsByName(username)).thenReturn(true);
        when(mockLoginUserDAI.get(username)).thenReturn(mockUser);
        when(mockUser.getUsername()).thenReturn(username);
        when(mockUser.getPassword()).thenReturn(password);

        loginInteractor.execute(inputData);

        verify(mockLoginUserDAI).setCurrentUser(username);
        verify(mockLoginOutputBoundary).prepareSuccessView(any(LoginOutputData.class));
    }

    @Test
    public void failureUserDoesNotExistTest() {

        String username = "userDoesNotExist";
        String password = "passwordDoesNotExist";

        LoginInputData inputData = new LoginInputData(username, password);

        when(mockLoginUserDAI.existsByName(username)).thenReturn(false);

        loginInteractor.execute(inputData);

        verify(mockLoginOutputBoundary).prepareFailView(username + ": Account does not exist.");
    }

    @Test
    public void failurePasswordMismatchTest() {

        String username = "currentUser";
        String password = "passwordMismatch";
        LoginInputData inputData = new LoginInputData(username, password);

        User mockUser = Mockito.mock(User.class);

        when(mockLoginUserDAI.existsByName(username)).thenReturn(true);
        when(mockLoginUserDAI.get(username)).thenReturn(mockUser);
        when(mockUser.getPassword()).thenReturn("correctPassword");

        loginInteractor.execute(inputData);

        verify(mockLoginOutputBoundary).prepareFailView("Incorrect password for \"" + username + "\".");
    }

    @Test
    public void switchToSignupViewTest() {

        loginInteractor.switchToSignupView();

        verify(mockLoginOutputBoundary).switchToSignupView();
    }

    @Test
    public void getUsernameTest() {

        // Setup mock data for successful login
        String username = "The username should match the expected value.";
        boolean useCaseFailed = false;
        LoginOutputData loginOutputData = new LoginOutputData(username, useCaseFailed);

        // Call getUsername() method
        String result = loginOutputData.getUsername();

        // Verify that returned username matches expected username
        assertEquals(username, result, "The username should match the expected value.");
    }
}