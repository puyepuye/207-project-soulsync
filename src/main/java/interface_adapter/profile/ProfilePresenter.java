//package interface_adapter.profile;
//
//import interface_adapter.ViewManagerModel;
//import use_case.preferences.PreferenceOutputBoundary;
//import use_case.preferences.PreferenceOutputData;
//
///**
// * The Presenter for the Login Use Case.
// */
//public class ProfilePresenter implements PreferenceOutputBoundary {
//
//    private final ProfileViewModel preferencesViewModel;
//    private final ViewManagerModel viewManagerModel;
//
//    public ProfilePresenter(ViewManagerModel viewManagerModel,
//                            ProfileViewModel preferencesViewModel) {
//        this.viewManagerModel = viewManagerModel;
//        this.preferencesViewModel = preferencesViewModel;
//    }
//
//    @Override
//    public void prepareSuccessView(PreferenceOutputData response) {
//        // On success, switch to the preferences view.
//
//        final ProfileState preferencesState = preferencesViewModel.getState();
//        this.preferencesViewModel.setState(preferencesState);
//        this.preferencesViewModel.firePropertyChanged();
//
//        this.viewManagerModel.setState(preferencesViewModel.getViewName());
//        this.viewManagerModel.firePropertyChanged();
//    }
//
//    @Override
//    public void prepareFailView(String error) {
//        final ProfileState preferencesState = preferencesViewModel.getState();
//        preferencesViewModel.firePropertyChanged();
//    }
//
////    @Override
////    public void switchToPreferencesView() {
////        viewManagerModel.setState(preferencesViewModel.getViewName());
////        viewManagerModel.firePropertyChanged();
////    }
//}
