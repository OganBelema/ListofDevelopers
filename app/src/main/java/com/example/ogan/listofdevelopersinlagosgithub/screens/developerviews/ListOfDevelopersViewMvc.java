package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import com.example.ogan.listofdevelopersinlagosgithub.model.items.Item;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.views.ObservableViewMvc;

import java.util.ArrayList;

/**
 * Created by Belema Ogan on 12/22/2018.
 */

public interface ListOfDevelopersViewMvc extends ObservableViewMvc<ListOfDevelopersViewMvc.Listener> {

    interface Listener {

        void loadMoreItems();

        boolean isLoading();

        void onRefresh();

        int getTotalPageCount();

        boolean isLastPage();

    }

    void showMessage(String message);

    void stopRefreshing();

    void hideProgressBar();

    void bindData(ArrayList<Item> data);

    void setAdapter(RecyclerAdapter adapter);

    void clearData();

    void showLoadingFooter();

    void removeLoadingFooter();


}
