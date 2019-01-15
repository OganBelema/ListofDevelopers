package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.presentation;

import android.view.LayoutInflater;

import com.example.ogan.listofdevelopersinlagosgithub.network.FetchGithubUserListUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchUserDataUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.GithubApi;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.views.ViewMvcFactory;

import dagger.Subcomponent;

/**
 * Created by Belema Ogan on 1/14/2019.
 */

@Subcomponent(modules = {PresentationModule.class, NetworkModule.class})
public interface PresentationComponent {

    public GithubApi getGithubApi();

    public LayoutInflater getLayoutInflater();

    public ViewMvcFactory getViewMvcFactory();

    public FetchUserDataUseCase getFetchUserDataUseCase();

    public FetchGithubUserListUseCase getFetchGithubUserListUseCase();
}
