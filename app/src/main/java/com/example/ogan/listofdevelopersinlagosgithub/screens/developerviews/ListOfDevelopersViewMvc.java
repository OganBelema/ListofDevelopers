package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.ObservableViewMvc;

import java.util.ArrayList;

/**
 * Created by Belema Ogan on 12/22/2018.
 */

public interface ListOfDevelopersViewMvc extends ObservableViewMvc<ListOfDevelopersViewMvc.Listener> {

    public interface Listener {

        void loadMoreItems();

        boolean isLoading();

        void onRefresh();

        int getTotalPageCount();

        boolean isLastPage();

    }

    void stopRefreshing();

    void hideProgressBar();

    void bindData(ArrayList<Item> data);

    void showLoadingFooter();

    void removeLoadingFooter();


}
