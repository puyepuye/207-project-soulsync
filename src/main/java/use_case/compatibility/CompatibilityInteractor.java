//package use_case.compatibility;
//
//import entity.User;
//import entity.UserFactory;
//
///**
// * The Change Password Interactor.
// */
//public class CompatibilityInteractor implements CompatibilityInputBoundary {
//    private final CompatibilityUserDataAccessInterface userDataAccessObject;
//    private final CompatibilityOutputBoundary userPresenter;
//    private final UserFactory userFactory;
//
//    public CompatibilityInteractor(CompatibilityUserDataAccessInterface compatibilityUserDataAccessInterface,
//                                   CompatibilityOutputBoundary compatibilityOutputBoundary,
//                                   UserFactory userFactory) {
//        this.userDataAccessObject = compatibilityUserDataAccessInterface;
//        this.userPresenter = compatibilityOutputBoundary;
//        this.userFactory = userFactory;
//    }
//
//    @Override
//    public void execute(CompatibilityInputData compatibilityInputData) {
//        final User user = userFactory.create(compatibilityInputData.getUsername(),
//                                             compatibilityInputData.getPassword(),
//                                             compatibilityInputData.getImage(), compatibilityInputData.getFullName(),
//                                             compatibilityInputData.getLocation(), compatibilityInputData.getGender(),
//                                             compatibilityInputData.getPreferredGender(),
//                                             compatibilityInputData.getDateOfBirth(),
//                                             compatibilityInputData.getPreferredAge(), compatibilityInputData.getBio(),
//                                             compatibilityInputData.getPreferences(), compatibilityInputData.getTags(),
//                                             compatibilityInputData.getMatched());
//
//        userDataAccessObject.compatibilityMatch(user);
//
//        // final CompatibilityOutputData changePasswordOutputData = new CompatibilityOutputData(user.getName(), false);
//        // userPresenter.prepareSuccessView(changePasswordOutputData);
//    }
//}
