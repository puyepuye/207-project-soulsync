package use_case.edit_profile;

import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import use_case.chat.ChatDataAccessInterface;

import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EditProfileInteractorTest {

    private EditProfileInteractor editProfileInteractor;
    private EditProfileUserDataAccessInterface mockDataAccessObject;
    private EditProfileOutputBoundary mockOutputBoundary;
    private UserFactory mockUserFactory;
    private ChatDataAccessInterface mockChatDataAccess;

    @BeforeEach
    public void setUp() {
        mockDataAccessObject = mock(EditProfileUserDataAccessInterface.class);
        mockOutputBoundary = mock(EditProfileOutputBoundary.class);
        mockUserFactory = mock(UserFactory.class);
        mockChatDataAccess = mock(ChatDataAccessInterface.class);

        editProfileInteractor = new EditProfileInteractor(
                mockDataAccessObject,
                mockOutputBoundary,
                mockUserFactory,
                mockChatDataAccess
        );
    }


    @Test
    public void testSwitchToSwipeView() {
        editProfileInteractor.switchToSwipeView();
        verify(mockOutputBoundary).switchToSwipeView();
    }
}
