package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.application;

import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.NetworkModule;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.PresentationComponent;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.PresentationModule;
import com.example.ogan.listofdevelopersinlagosgithub.network.GithubApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Belema Ogan on 1/14/2019.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    public GithubApi getGithubApi();

    public PresentationComponent newPresentationComponent(PresentationModule presentationModule,
                                                          NetworkModule networkModule);
}
