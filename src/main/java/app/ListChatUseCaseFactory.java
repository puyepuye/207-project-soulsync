package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatViewModel;
import interface_adapter.compatibility.CompatibilityViewModel;
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
import static app.NavbarUseCaseFactory.createNavbarUseCase;

public class ListChatUseCaseFactory {

    // prevent initialization
    private ListChatUseCaseFactory() {

    }

    public static ListChatView create(ViewManagerModel viewManagerModel,
                                      ListChatViewModel listChatViewModel,
                                      ChatViewModel chatViewModel,
                                      ListChatDataAccessInterface listChatDataAccessObject,
                                      NavbarViewModel navbarViewModel,
                                      SwipeViewModel swipeViewModel,
                                      CompatibilityViewModel compatibilityViewModel){
        final ListChatController listChatController = createListChatUseCase(viewManagerModel,
                listChatViewModel, chatViewModel, listChatDataAccessObject);
        final NavbarController navbarController = createNavbarUseCase(viewManagerModel, swipeViewModel,navbarViewModel,
                compatibilityViewModel, listChatViewModel);
        return new ListChatView(listChatViewModel, listChatController, navbarController);


    }

    public static ListChatController createListChatUseCase(ViewManagerModel viewManagerModel,
                                                           ListChatViewModel listChatViewModel,
                                                           ChatViewModel chatViewModel,
                                                           ListChatDataAccessInterface listChatDataAccessObject){

        final ListChatOutputBoundary listChatPresenter = new ListChatPresenter(listChatViewModel,
                chatViewModel, viewManagerModel);
        final ListChatInputBoundary listChatInteractor = new ListChatInteractor(listChatPresenter, listChatDataAccessObject);
        return new ListChatController(listChatInteractor, listChatDataAccessObject);

    }
}
