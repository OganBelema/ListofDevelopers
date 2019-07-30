package com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.application;



import com.oganbelema.listofdevelopersinlagosgithub.network.FetchGithubUserListUseCase;
import com.oganbelema.listofdevelopersinlagosgithub.network.FetchUserDataUseCase;
import com.oganbelema.listofdevelopersinlagosgithub.network.GithubApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Belema Ogan on 1/14/2019.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    FetchUserDataUseCase getFetchUserDataUseCase(GithubApi githubApi) {
        return new FetchUserDataUseCase(githubApi);
    }

    @Provides
    @Singleton
    FetchGithubUserListUseCase getFetchGithubUserListUseCase(GithubApi githubApi) {
        return new FetchGithubUserListUseCase(githubApi);
    }
}
