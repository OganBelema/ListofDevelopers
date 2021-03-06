package com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews;


import com.oganbelema.listofdevelopersinlagosgithub.model.items.Item;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.ObservableViewMvc;

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
