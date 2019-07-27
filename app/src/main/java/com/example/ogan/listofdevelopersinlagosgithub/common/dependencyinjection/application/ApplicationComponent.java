package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.application;

import com.example.ogan.listofdevelopersinlagosgithub.common.NetworkUtil;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.PresentationComponent;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.PresentationModule;
import com.example.ogan.listofdevelopersinlagosgithub.database.DeveloperDatabase;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchGithubUserListUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchUserDataUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.GithubApi;
import com.example.ogan.listofdevelopersinlagosgithub.repository.ListOfDeveloperRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Belema Ogan on 1/14/2019.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, DatabaseModule.class})
public interface ApplicationComponent {

    public GithubApi getGithubApi();

    public FetchUserDataUseCase getFetchUserDataUseCase();

    public FetchGithubUserListUseCase getFetchGithubUserListUseCase();

    public PresentationComponent newPresentationComponent(PresentationModule presentationModule);

    public DeveloperDatabase getDeveloperDatabase();

    public NetworkUtil getNetworkUtil();

    public ListOfDeveloperRepository getListOfDeveloperRepository();
}
