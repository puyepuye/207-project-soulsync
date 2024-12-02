package use_case.preferences;

import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.*;

public class PreferenceInteractorTest {

    private PreferenceInteractor preferenceInteractor;
    private PreferenceUserDataAccessInterface mockUserDataAccess;
    private PreferenceOutputBoundary mockOutputBoundary;
    private UserFactory mockUserFactory;

    @BeforeEach
    public void setUp() {
        mockUserDataAccess = Mockito.mock(PreferenceUserDataAccessInterface.class);
        mockOutputBoundary = Mockito.mock(PreferenceOutputBoundary.class);
        mockUserFactory = Mockito.mock(UserFactory.class);

        preferenceInteractor = new PreferenceInteractor(mockUserDataAccess, mockOutputBoundary, mockUserFactory);
    }

    @Test
    public void successUpdatePreferencesTest() {
        PreferenceInputData inputData = new PreferenceInputData(
                "Yolanda Thant", "yoli07", "password111", "image.jpg", "password111",
                "Toronto", "Female", new Date(), Arrays.asList("Male"),
                new HashMap<>(Map.of("min", 25, "max", 35)),
                Arrays.asList("Hiking", "Reading"), "Love nature!",
                Map.of("smoking", false, "drinking", true));

        User mockUser = mock(User.class);

        when(mockUserFactory.create(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(mockUser);

        preferenceInteractor.execute(inputData);

        verify(mockUserDataAccess).saveUser(mockUser);
        verify(mockUserDataAccess).updatePreference(mockUser);
        verify(mockOutputBoundary).prepareSuccessView(any(PreferenceOutputData.class));
    }

    @Test
    public void successEmptyTagsTest() {
        PreferenceInputData inputData = new PreferenceInputData(
                "Yolanda Thant", "yoli07", "password111", "image.jpg", "password111",
                "Toronto", "Female", new Date(), Arrays.asList("Male"),
                new HashMap<>(Map.of("min", 25, "max", 35)),
                Collections.emptyList(), "Love nature!",
                Map.of("smoking", false, "drinking", true));

        // Mock UserFactory.create to return a mocked User
        User mockUser = mock(User.class);
        when(mockUserFactory.create(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(mockUser);

        // Mock the User methods to return appropriate values
        when(mockUser.getTags()).thenReturn(Collections.emptyList());  // Tags are empty
        when(mockUser.getBio()).thenReturn("Love nature!");
        when(mockUser.getPreferences()).thenReturn(Map.of("smoking", false, "drinking", true));
        when(mockUser.getPreferredGender()).thenReturn(Arrays.asList("Male"));
        when(mockUser.getPreferredAge()).thenReturn(new HashMap<>(Map.of("min", 25, "max", 35)));

        // Execute the interactor
        preferenceInteractor.execute(inputData);

        // Verify that the preferences are saved correctly
        verify(mockUserDataAccess).saveUser(mockUser);
        verify(mockUserDataAccess).updatePreference(mockUser);

        // Verify that the output boundary prepares the success view
        verify(mockOutputBoundary).prepareSuccessView(any(PreferenceOutputData.class));
    }

    @Test
    public void successEmptyBioTest() {
        PreferenceInputData inputData = new PreferenceInputData(
                "Yolanda Thant", "yoli07", "password111", "image.jpg", "password111",
                "Toronto", "Female", new Date(), Arrays.asList("Male"),
                new HashMap<>(Map.of("min", 25, "max", 35)),
                Arrays.asList("Hiking", "Reading"), "",  // Bio is empty
                Map.of("smoking", false, "drinking", true));

        // Mock UserFactory.create to return a mocked User
        User mockUser = mock(User.class);
        when(mockUserFactory.create(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(mockUser);

        // Mock the User methods to return appropriate values
        when(mockUser.getTags()).thenReturn(Arrays.asList("Hiking", "Reading"));
        when(mockUser.getBio()).thenReturn("");  // Bio is empty
        when(mockUser.getPreferences()).thenReturn(Map.of("smoking", false, "drinking", true));
        when(mockUser.getPreferredGender()).thenReturn(Arrays.asList("Male"));
        when(mockUser.getPreferredAge()).thenReturn(new HashMap<>(Map.of("min", 25, "max", 35)));

        // Execute the interactor
        preferenceInteractor.execute(inputData);

        // Verify that the preferences are saved correctly
        verify(mockUserDataAccess).saveUser(mockUser);
        verify(mockUserDataAccess).updatePreference(mockUser);

        // Verify that the output boundary prepares the success view
        verify(mockOutputBoundary).prepareSuccessView(any(PreferenceOutputData.class));
    }



    @Test
    public void switchToSwipeViewTest() {
        // Execute the switch to swipe view method
        preferenceInteractor.switchToSwipeView();

        // Verify that the presenter switches to the swipe view
        verify(mockOutputBoundary).switchToSwipeView();
    }

}
