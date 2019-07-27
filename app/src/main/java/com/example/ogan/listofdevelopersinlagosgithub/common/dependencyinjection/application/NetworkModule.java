package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.application;

import com.example.ogan.listofdevelopersinlagosgithub.network.FetchGithubUserListUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchUserDataUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.GithubApi;


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
