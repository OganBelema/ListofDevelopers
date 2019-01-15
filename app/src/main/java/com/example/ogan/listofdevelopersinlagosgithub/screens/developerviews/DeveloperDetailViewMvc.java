package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.example.ogan.listofdevelopersinlagosgithub.network.users.UserApi;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.views.ObservableViewMvc;

/**
 * Created by Belema Ogan on 12/22/2018.
 */

public interface DeveloperDetailViewMvc extends ObservableViewMvc<DeveloperDetailViewMvc.Listener> {

    public interface Listener {
        void floatingActionButtonClicked();
    }

    void displayData(UserApi userResult);

    void showCardView();

    void hideProgressBar();

    void setToolbarTitle(String toolbarTitle);

    Toolbar getToolBar();

    void setProfileImage(Bitmap bitmap);

    void setProfileImage(Drawable drawable);

    void customiseToolbar(int toolbarBackgroundColor, int toolbarTitleTextColor);

    void customiseView(Bitmap bitmap, Window window);

    void customiseFloatingActionBar(ColorStateList colorStateList);

}
