package com.example.ogan.listofdevelopersinlagosgithub;

import android.view.View;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;

import java.util.ArrayList;

/**
 * Created by Belema Ogan on 12/22/2018.
 */

interface ListOfDevelopersViewMvc extends ObservableViewMvc<ListOfDevelopersViewMvc.Listener>{

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
