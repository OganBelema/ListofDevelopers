package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.content.res.ColorStateList;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.UserGson.UserApi;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.ObservableViewMvc;

/**
 * Created by Belema Ogan on 12/22/2018.
 */

public interface DeveloperDetailViewMvc extends ObservableViewMvc<DeveloperDetailViewMvc.Listener> {

    public interface Listener {
        void floatingActionButtonClicked();
    }

    void displayData(UserApi userResult, String userUrl);

    void showCardView();

    void hideProgressBar();

    void setToolbarTitle(String toolbarTitle);

    Toolbar getToolBar();

    void setProfileImage(String imageUrl);

    void customiseToolbar(int toolbarBackgroundColor, int toolbarTitleTextColor);

    void customiseView(String imageUrl, Window window);

    void customiseFloatingActionBar(ColorStateList colorStateList);

}
