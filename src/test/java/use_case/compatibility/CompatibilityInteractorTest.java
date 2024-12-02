package use_case.compatibility;

import entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CompatibilityInteractorTest {

    private CompatibilityInteractor interactor;
    private CompatibilityUserDataAccessInterface userDataAccess;
    private CompatibilityOutputBoundary presenter;

    @Before
    public void setUp() {
        userDataAccess = mock(CompatibilityUserDataAccessInterface.class);
        presenter = mock(CompatibilityOutputBoundary.class);
        interactor = new CompatibilityInteractor(userDataAccess, presenter);
    }

    // Interactor Tests
    @Test
    public void executeUserExistsTest() {
        User mockUser = mock(User.class);
        String username = "Alice";

        when(userDataAccess.existsByName(username)).thenReturn(true);
        when(userDataAccess.get(username)).thenReturn(mockUser);
        when(mockUser.getUsername()).thenReturn(username);

        CompatibilityInputData inputData = new CompatibilityInputData(username);
        interactor.execute(inputData);

        ArgumentCaptor<CompatibilityOutputData> outputCaptor = ArgumentCaptor.forClass(CompatibilityOutputData.class);
        verify(presenter).prepareSuccessView(outputCaptor.capture());
        CompatibilityOutputData outputData = outputCaptor.getValue();

        assertEquals(username, outputData.getUsername());
        verify(userDataAccess, times(1)).get(username);
    }

    @Test
    public void executeUserDoesNotExistTest() {
        String username = "NonExistentUser";
        when(userDataAccess.existsByName(username)).thenReturn(false);

        CompatibilityInputData inputData = new CompatibilityInputData(username);
        interactor.execute(inputData);

        verify(presenter).prepareFailView("User does not exists.");
        verify(userDataAccess, never()).get(anyString());
    }

    @Test
    public void getMatchedUsersTest() {
        User mockUser = mock(User.class);
        String username = "Alice";
        List<String> matchedUsers = List.of("Bob", "Charlie");

        when(userDataAccess.existsByName(username)).thenReturn(true);
        when(userDataAccess.get(username)).thenReturn(mockUser);
        when(mockUser.getMatched()).thenReturn(matchedUsers);

        CompatibilityInputData inputData = new CompatibilityInputData(username);
        String[] result = interactor.getMatchedUsers(inputData);

        assertArrayEquals(matchedUsers.toArray(new String[0]), result);
        verify(presenter, times(1)).prepareSuccessView(any(CompatibilityOutputData.class));
    }

    @Test
    public void getUserDOBTest() {
        User mockUser = mock(User.class);
        String username = "Alice";
        Date mockDOB = new Date();

        when(userDataAccess.get(username)).thenReturn(mockUser);
        when(mockUser.getDateOfBirth()).thenReturn(mockDOB);

        CompatibilityInputData inputData = new CompatibilityInputData(username);
        Date dob = interactor.getUserDOB(inputData);

        assertEquals(mockDOB, dob);
        verify(userDataAccess, times(1)).get(username);
    }

    @Test
    public void getUserPreferencesTest() {
        User mockUser = mock(User.class);
        String username = "Alice";
        Map<String, Boolean> preferences = new HashMap<>();
        preferences.put("Morning", true);
        preferences.put("Music", true);

        when(userDataAccess.get(username)).thenReturn(mockUser);
        when(mockUser.getPreferences()).thenReturn(preferences);

        CompatibilityInputData inputData = new CompatibilityInputData(username);
        Map<String, Boolean> result = interactor.getUserPreferences(inputData);

        assertEquals(preferences, result);
        verify(userDataAccess, times(1)).get(username);
    }

    // Compatibility Calculator Tests
    @Test
    public void preferenceCompatibilityTestPerfectMatch() {
        Map<String, Boolean> preferencesUser1 = new HashMap<>();
        Map<String, Boolean> preferencesUser2 = new HashMap<>();

        preferencesUser1.put("Morning", true);
        preferencesUser1.put("Spontaneous", false);
        preferencesUser1.put("Food", false);
        preferencesUser1.put("Mountain", true);
        preferencesUser1.put("Music", true);
        preferencesUser1.put("Reading", false);
        preferencesUser1.put("Nature", false);
        preferencesUser1.put("Half-boiled eggs", false);

        preferencesUser2.putAll(preferencesUser1);

        CompatibilityCalculator calculator = new CompatibilityCalculator(preferencesUser1, preferencesUser2);
        int compatibilityScore = calculator.calculate();

        assertEquals(57, compatibilityScore);
    }

    @Test
    public void getMatchedUsersUserDoesNotExistTest() {
        String username = "NonExistentUser";

        // Mock behavior: User does not exist
        when(userDataAccess.existsByName(username)).thenReturn(false);

        CompatibilityInputData inputData = new CompatibilityInputData(username);
        String[] result = interactor.getMatchedUsers(inputData);

        // Verify presenter fails with the correct message
        verify(presenter).prepareFailView("User does not exists.");

        // Verify the result is an empty array
        assertArrayEquals(new String[0], result);

        // Verify no further interaction with userDataAccess
        verify(userDataAccess, never()).get(anyString());
    }

    @Test
    public void preferenceCompatibilityTestZeroMatch() {
        Map<String, Boolean> preferencesUser1 = new HashMap<>();
        Map<String, Boolean> preferencesUser2 = new HashMap<>();

        preferencesUser1.put("Morning", true);
        preferencesUser1.put("Spontaneous", false);
        preferencesUser1.put("Food", false);
        preferencesUser1.put("Mountain", true);
        preferencesUser1.put("Music", true);
        preferencesUser1.put("Reading", false);
        preferencesUser1.put("Nature", false);
        preferencesUser1.put("Half-boiled eggs", false);

        preferencesUser2.put("Morning", false);
        preferencesUser2.put("Spontaneous", true);
        preferencesUser2.put("Food", true);
        preferencesUser2.put("Mountain", false);
        preferencesUser2.put("Music", false);
        preferencesUser2.put("Reading", true);
        preferencesUser2.put("Nature", true);
        preferencesUser2.put("Half-boiled eggs", true);

        CompatibilityCalculator calculator = new CompatibilityCalculator(preferencesUser1, preferencesUser2);
        int compatibilityScore = calculator.calculate();

        assertEquals(0, compatibilityScore);
    }

    @Test
    public void preferenceCompatibilityTestPartialMatch() {
        Map<String, Boolean> preferencesUser1 = new HashMap<>();
        Map<String, Boolean> preferencesUser2 = new HashMap<>();

        preferencesUser1.put("Morning", true);
        preferencesUser1.put("Spontaneous", false);
        preferencesUser1.put("Food", false);
        preferencesUser1.put("Mountain", true);
        preferencesUser1.put("Music", true);
        preferencesUser1.put("Reading", false);
        preferencesUser1.put("Nature", false);
        preferencesUser1.put("Half-boiled eggs", false);

        preferencesUser2.put("Morning", true);
        preferencesUser2.put("Spontaneous", true);
        preferencesUser2.put("Food", false);
        preferencesUser2.put("Mountain", false);
        preferencesUser2.put("Music", true);
        preferencesUser2.put("Reading", true);
        preferencesUser2.put("Nature", true);
        preferencesUser2.put("Half-boiled eggs", false);

        CompatibilityCalculator calculator = new CompatibilityCalculator(preferencesUser1, preferencesUser2);
        int compatibilityScore = calculator.calculate();

        int expectedScore = 14;
        assertEquals(expectedScore, compatibilityScore);
    }

    // Fengshui Calculator Tests
    @Test
    public void fengshuiCompatibilityTestHighCompatibility() {
        Calendar calendar1 = new GregorianCalendar(1981, Calendar.APRIL, 15);
        Date date1 = calendar1.getTime();

        Calendar calendar2 = new GregorianCalendar(1983, Calendar.AUGUST, 25);
        Date date2 = calendar2.getTime();

        FengshuiCalculator calculator = new FengshuiCalculator(date1, date2);
        String result = calculator.calculateScore();

        assertEquals("Low Compatibility: There are some conflicts, more effort needed.", result);
    }

    @Test
    public void fengshuiCompatibilityTestLowCompatibility() {
        Calendar calendar1 = new GregorianCalendar(1991, Calendar.MAY, 10);
        Date date1 = calendar1.getTime();

        Calendar calendar2 = new GregorianCalendar(1992, Calendar.JUNE, 20);
        Date date2 = calendar2.getTime();

        FengshuiCalculator calculator = new FengshuiCalculator(date1, date2);
        String result = calculator.calculateScore();

        assertEquals("Low Compatibility: There are some conflicts, more effort needed.", result);
    }

    @Test
    public void fengshuiCompatibilityTestVeryLowCompatibility() {
        Calendar calendar1 = new GregorianCalendar(1985, Calendar.JULY, 10);
        Date date1 = calendar1.getTime();

        Calendar calendar2 = new GregorianCalendar(1993, Calendar.FEBRUARY, 18);
        Date date2 = calendar2.getTime();

        FengshuiCalculator calculator = new FengshuiCalculator(date1, date2);
        String result = calculator.calculateScore();

        assertEquals("Low Compatibility: There are some conflicts, more effort needed.", result);
    }

    @Test
    public void fengshuiCompatibilityTestModerateCompatibility() {
        Calendar calendar1 = new GregorianCalendar(1986, Calendar.FEBRUARY, 12);
        Date date1 = calendar1.getTime();

        Calendar calendar2 = new GregorianCalendar(1994, Calendar.MAY, 20);
        Date date2 = calendar2.getTime();

        FengshuiCalculator calculator = new FengshuiCalculator(date1, date2);
        String result = calculator.calculateScore();

        assertEquals("Low Compatibility: There are some conflicts, more effort needed.", result);
    }
}
