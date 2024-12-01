package use_case.signup;

import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import use_case.chat.ChatDataAccessInterface;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class SignupInteractorTest {
    private SignupInteractor signupInteractor;
    private SignupUserDataAccessInterface mockSignupUseDAI;
    private SignupOutputBoundary mockSigninOutputBoundary;
    private UserFactory mockUserFactory;
    private ChatDataAccessInterface mockChatDAI;

    @BeforeEach
    public void mockSetUp() {

        mockSignupUseDAI = Mockito.mock(SignupUserDataAccessInterface.class);
        mockSigninOutputBoundary = Mockito.mock(SignupOutputBoundary.class);
        mockUserFactory = Mockito.mock(UserFactory.class);
        mockChatDAI = Mockito.mock(ChatDataAccessInterface.class);
        signupInteractor = new SignupInteractor(mockSignupUseDAI, mockSigninOutputBoundary, mockUserFactory, mockChatDAI);

    }

    @Test
    public void successUserSignupTest() throws ParseException {

        SignupInputData inputData = new SignupInputData("YolandaThant", "yoli07",
                "password111", "image", "password111", "Toronto",
                "Female", new Date(), Arrays.asList("Male"), new HashMap<>());

        // user DNE
        when(mockSignupUseDAI.existsByName(inputData.getUsername())).thenReturn(false);

        User mockUser = mock(User.class);
        when(mockUserFactory.create(inputData.getUsername(), inputData.getPassword(),
                inputData.getImage(), inputData.getFullname(), inputData.getLocation(), inputData.getGender(),
                inputData.getPreferredGender(), inputData.getDateOfBirth(), inputData.getPreferredAge(),
                "", new HashMap<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>())).thenReturn(mockUser);

        signupInteractor.execute(inputData);

        verify(mockSigninOutputBoundary).prepareSuccessView(any(SignupOutputData.class));
    }

    @Test
    public void failureUserAlreadyExistsTest() throws ParseException {

        SignupInputData inputData = new SignupInputData(null, "Yoli07", "password", null, null, null, null, null, null, null);

        // username already exists
        when(mockSignupUseDAI.existsByName(inputData.getUsername())).thenReturn(true);

        signupInteractor.execute(inputData);

        verify(mockSigninOutputBoundary).prepareFailView("User already exists.");

        // Note: Don't call saveUser() if user alr exists. Basically don't save that username again.
        verify(mockSignupUseDAI, never()).saveUser(any());
    }

    @Test
    public void failurePasswordMismatchTest() throws ParseException {
        SignupInputData inputData = new SignupInputData("YolandaThant", "yoli07",
                "password111", "image", "password21", "Toronto",
                "Female", new Date(), Arrays.asList("Male"), new HashMap<>());

        when(mockSignupUseDAI.existsByName(inputData.getUsername())).thenReturn(false);
        signupInteractor.execute(inputData);
        verify(mockSigninOutputBoundary).prepareFailView("Passwords don't match.");
    }

    @Test
    public void failureInvalidFullName() throws ParseException {

        SignupInputData inputData = new SignupInputData("", "yoli07",
                "password111", "image", "password111", "Toronto",
                "Female", new Date(), Arrays.asList("Male"), new HashMap<>());

        when(mockSignupUseDAI.existsByName(inputData.getUsername())).thenReturn(false);
        signupInteractor.execute(inputData);
        verify(mockSigninOutputBoundary).prepareFailView("Please Enter your full name.");
    }

    @Test
    public void failureEmptyLocationTest() throws ParseException {
        SignupInputData inputData = new SignupInputData("YolandaThant", "yoli07",
                "password111", "image", "password111", "",
                "Female", new Date(), Arrays.asList("Male"), new HashMap<>());

        when(mockSignupUseDAI.existsByName(inputData.getUsername())).thenReturn(false);
        signupInteractor.execute(inputData);
        verify(mockSigninOutputBoundary).prepareFailView("Please choose your country and city.");
    }

    @Test
    public void failureEmptyGenderTest() throws ParseException {
        SignupInputData inputData = new SignupInputData("YolandaThant", "yoli07",
                "password111", "image", "password111", "",
                "", new Date(), Arrays.asList("Male"), new HashMap<>());

        when(mockSignupUseDAI.existsByName(inputData.getUsername())).thenReturn(false);
        signupInteractor.execute(inputData);
        verify(mockSigninOutputBoundary).prepareFailView("Please choose your gender.");
    }

    @Test
    public void failureEmptyPreferredGenderTest() throws ParseException {
        SignupInputData inputData = new SignupInputData("YolandaThant", "yoli07",
                "password111", "image", "password111", "Taiwan",
                "Female", new Date(), null, new HashMap<>());

        when(mockSignupUseDAI.existsByName(inputData.getUsername())).thenReturn(false);
        signupInteractor.execute(inputData);
        verify(mockSigninOutputBoundary).prepareFailView("Please choose your preferred match's gender.");
    }

    @Test
    public void failureEmptyDateOfBirthTest() throws ParseException {
        SignupInputData inputData = new SignupInputData("YolandaThant", "yoli07",
                "password111", "image", "password111", "Toronto",
                "Female", null, Arrays.asList("Male"), new HashMap<>());

        when(mockSignupUseDAI.existsByName(inputData.getUsername())).thenReturn(false);
        signupInteractor.execute(inputData);
        verify(mockSigninOutputBoundary).prepareFailView("Date of birth is required.");
    }

    @Test
    public void failureEmptyPreferredAgeTest() throws ParseException {
        SignupInputData inputData = new SignupInputData("YolandaThant", "yoli07",
                "password111", "image", "password111", "Toronto",
                "Female", new Date(), Arrays.asList("Male"), null);

        when(mockSignupUseDAI.existsByName(inputData.getUsername())).thenReturn(false);
        signupInteractor.execute(inputData);
        verify(mockSigninOutputBoundary).prepareFailView("Please choose your preferred match's age range.");
    }
}
