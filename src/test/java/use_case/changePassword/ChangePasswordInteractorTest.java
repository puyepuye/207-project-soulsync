package use_case.changePassword;

import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.change_password.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ChangePasswordInteractorTest {

    private ChangePasswordInteractor changePasswordInteractor;
    private ChangePasswordUserDataAccessInterface mockChangePasswordDAI;
    private ChangePasswordOutputBoundary mockChangePasswordOutputBoundary;
    private UserFactory mockUserFactory;

    @BeforeEach
    public void mockSetUp() {

        mockChangePasswordDAI = mock(ChangePasswordUserDataAccessInterface.class);
        mockChangePasswordOutputBoundary = mock(ChangePasswordOutputBoundary.class);
        mockUserFactory = mock(UserFactory.class);
        changePasswordInteractor = new ChangePasswordInteractor(mockChangePasswordDAI, mockChangePasswordOutputBoundary, mockUserFactory);
    }

    @Test
    public void successUserChangePasswordTest() {
        // Test data
        String username = "user";
        String changePassword = "Yoli07";

        ChangePasswordInputData inputData = new ChangePasswordInputData(username, changePassword);

        // Mocking User object
        User mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn(username);
        when(mockUser.getPassword()).thenReturn(changePassword);

        // Expected return values for lists & maps
        HashMap<String, Integer> preferredAge = new HashMap<>();
        preferredAge.put("min", 18);
        preferredAge.put("max", 99);
        ArrayList<String> swipedRight = new ArrayList<>();
        ArrayList<String> swipedLeft = new ArrayList<>();
        ArrayList<String> swipedRightOn = new ArrayList<>();

        // Mock the factory to return the mock user
        when(mockUserFactory.create(
                eq(username), eq(changePassword), anyString(),
                anyString(), anyString(), anyString(),
                anyList(), any(Date.class),
                eq(preferredAge), anyString(),
                anyMap(), anyList(), anyList(), eq(swipedRight), eq(swipedLeft), eq(swipedRightOn)))
                .thenReturn(mockUser);

        // Execute the interactor method
        changePasswordInteractor.execute(inputData);

        // Verifies password change was attempted
        // ArgumentCaptor -> to capture User object passed to changePassword method of mockChangePasswordDAI &
        // to prepareSuccessView method of mockChangePasswordOutputBoundary.

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(mockChangePasswordDAI).changePassword(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(username, capturedUser.getUsername());
        assertEquals(changePassword, capturedUser.getPassword());

        // Verifies success view was prepared
        ArgumentCaptor<ChangePasswordOutputData> changePasswordOutputCaptor = ArgumentCaptor.forClass(ChangePasswordOutputData.class);
        verify(mockChangePasswordOutputBoundary).prepareSuccessView(changePasswordOutputCaptor.capture());
        ChangePasswordOutputData outputData = changePasswordOutputCaptor.getValue();
        assertEquals(username, outputData.getUsername());
    }

}
