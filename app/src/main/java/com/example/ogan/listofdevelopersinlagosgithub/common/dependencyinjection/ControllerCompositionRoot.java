package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection;

import android.app.Activity;
import android.view.LayoutInflater;

import com.example.ogan.listofdevelopersinlagosgithub.network.FetchGithubUserListUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchUserDataUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.GithubApi;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.ViewMvcFactory;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class ControllerCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final Activity mActivity;

    public ControllerCompositionRoot(CompositionRoot compositionRoot, Activity activity) {
        mCompositionRoot = compositionRoot;
        mActivity = activity;
    }

    public GithubApi getGithubApi() {
        return mCompositionRoot.getGithubApi();
    }

    public LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(mActivity);
    }

    public ViewMvcFactory getViewMvcFactory(){
        return new ViewMvcFactory(getLayoutInflater());
    }


    public FetchUserDataUseCase getFetchUserDataUseCase() {
        return new FetchUserDataUseCase(getGithubApi());
    }

    public FetchGithubUserListUseCase getFetchGithubUserListUseCase() {
        return new FetchGithubUserListUseCase(getGithubApi());
    }
}
