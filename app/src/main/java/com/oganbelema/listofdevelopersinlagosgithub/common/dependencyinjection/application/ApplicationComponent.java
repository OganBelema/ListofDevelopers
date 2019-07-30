package com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.application;

import com.oganbelema.listofdevelopersinlagosgithub.common.NetworkUtil;
import com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.PresentationComponent;
import com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.PresentationModule;
import com.oganbelema.listofdevelopersinlagosgithub.database.DeveloperDatabase;
import com.oganbelema.listofdevelopersinlagosgithub.network.FetchGithubUserListUseCase;
import com.oganbelema.listofdevelopersinlagosgithub.network.FetchUserDataUseCase;
import com.oganbelema.listofdevelopersinlagosgithub.network.GithubApi;
import com.oganbelema.listofdevelopersinlagosgithub.repository.DeveloperDetailRepository;
import com.oganbelema.listofdevelopersinlagosgithub.repository.ListOfDeveloperRepository;

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

    public DeveloperDetailRepository getDeveloperDetailRepository();
}
