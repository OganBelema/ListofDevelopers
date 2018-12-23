package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.UserGson.UserApi;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Belema Ogan on 12/22/2018.
 */

interface DeveloperDetailViewMvc {

    public interface Listener {
        void floatingActionButtonClicked();
    }

    void registerListener(Listener listener);

    void unregisterListener(Listener listener);

    View getRootView();

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
