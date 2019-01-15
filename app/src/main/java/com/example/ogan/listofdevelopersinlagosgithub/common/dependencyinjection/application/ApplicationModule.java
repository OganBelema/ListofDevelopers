package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.application;

import com.example.ogan.listofdevelopersinlagosgithub.common.Constants;
import com.example.ogan.listofdevelopersinlagosgithub.network.GithubApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Belema Ogan on 1/14/2019.
 */

@Module
public class ApplicationModule {

    @Singleton
    @Provides
    Retrofit getRetrofit(){
         return new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    @Singleton
    @Provides
    GithubApi getGithubApi(Retrofit retrofit) {
        return retrofit.create(GithubApi.class);
    }
}
