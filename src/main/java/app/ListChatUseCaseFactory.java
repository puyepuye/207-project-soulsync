package app;

import static app.NavbarUseCaseFactory.createNavbarUseCase;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.compatibility.CompatibilityViewModel;
import interface_adapter.edit_profile.EditProfileViewModel;
import interface_adapter.listchat.ListChatController;
import interface_adapter.listchat.ListChatPresenter;
import interface_adapter.listchat.ListChatViewModel;
import interface_adapter.navbar.NavbarController;
import interface_adapter.navbar.NavbarViewModel;
import interface_adapter.swipe.SwipeViewModel;
import use_case.listchat.ListChatDataAccessInterface;
import use_case.listchat.ListChatInputBoundary;
import use_case.listchat.ListChatInteractor;
import use_case.listchat.ListChatOutputBoundary;
import view.ListChatView;

/**
 * This class contains the static factory function for creating the list chat.
 */
public final class ListChatUseCaseFactory {

    // prevent initialization
    private ListChatUseCaseFactory() {

    }

    /**
     * Factory function for creating the ListChatView.
     *
     * @param viewManagerModel the ViewManagerModel responsible for managing transitions and view states
     * @param listChatViewModel the ListChatViewModel used to manage the state and data of the list chat feature
     * @param chatViewModel the ChatViewModel used to manage chat-related data and interactions
     * @param listChatDataAccessObject the ListChatDataAccessInterface providing data access for the list chat feature
     * @param navbarViewModel the NavbarViewModel managing the state and data of the navigation bar
     * @param swipeViewModel the SwipeViewModel responsible for handling swipe-related actions and data
     * @param compatibilityViewModel the CompatibilityViewModel used to manage compatibility-related data
     * @param editProfileViewModel the EditProfileViewModel used to manage profile editing data and state
     * @return a fully constructed ListChatView instance
     */

    public static ListChatView create(ViewManagerModel viewManagerModel,
                                      ListChatViewModel listChatViewModel,
                                      ChatViewModel chatViewModel,
                                      ListChatDataAccessInterface listChatDataAccessObject,
                                      NavbarViewModel navbarViewModel,
                                      SwipeViewModel swipeViewModel,
                                      CompatibilityViewModel compatibilityViewModel,
                                      EditProfileViewModel editProfileViewModel) {
        final ListChatController listChatController = createListChatUseCase(viewManagerModel,
                listChatViewModel, chatViewModel, listChatDataAccessObject);
        final NavbarController navbarController = createNavbarUseCase(viewManagerModel, swipeViewModel, navbarViewModel,
                compatibilityViewModel, editProfileViewModel, listChatViewModel);
        return new ListChatView(listChatViewModel, listChatController, navbarController);
    }

    /**
     * Factory function for creating the ListChatView.
     *
     * @param viewManagerModel the ViewManagerModel responsible for managing transitions and view states
     * @param listChatViewModel the ListChatViewModel used to manage the state and data of the list chat feature
     * @param chatViewModel the ChatViewModel used to manage chat-related data and interactions
     * @param listChatDataAccessObject the ListChatDataAccessInterface providing data access for the list chat feature
     * @return a fully constructed ListChatView instance
     */
    public static ListChatController createListChatUseCase(ViewManagerModel viewManagerModel,
                                                           ListChatViewModel listChatViewModel,
                                                           ChatViewModel chatViewModel,
                                                           ListChatDataAccessInterface listChatDataAccessObject) {

        final ListChatOutputBoundary listChatPresenter = new ListChatPresenter(listChatViewModel,
                chatViewModel, viewManagerModel);
        final ListChatInputBoundary listChatInteractor = new ListChatInteractor(listChatPresenter,
                listChatDataAccessObject);
        return new ListChatController(listChatInteractor, listChatDataAccessObject);

    }
}
