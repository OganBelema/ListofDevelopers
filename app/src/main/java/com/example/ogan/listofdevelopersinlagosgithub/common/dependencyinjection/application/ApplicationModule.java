package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.application;

import android.content.Context;

import com.example.ogan.listofdevelopersinlagosgithub.common.Constants;
import com.example.ogan.listofdevelopersinlagosgithub.common.NetworkUtil;
import com.example.ogan.listofdevelopersinlagosgithub.database.DeveloperDatabase;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchGithubUserListUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchUserDataUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.GithubApi;
import com.example.ogan.listofdevelopersinlagosgithub.repository.DeveloperDetailRepository;
import com.example.ogan.listofdevelopersinlagosgithub.repository.ListOfDeveloperRepository;

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

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

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

    @Provides
    @Singleton
    NetworkUtil getNetworkUtil(){
        return new NetworkUtil(context);
    }

    @Provides
    ListOfDeveloperRepository getListOfDeveloperRepository(FetchGithubUserListUseCase fetchGithubUserListUseCase,
                                                           NetworkUtil networkUtil,
                                                           DeveloperDatabase developerDatabase){
        return new ListOfDeveloperRepository(context, fetchGithubUserListUseCase, networkUtil, developerDatabase);
    }

    @Provides
    DeveloperDetailRepository getDeveloperDetailRepository(FetchUserDataUseCase fetchUserDataUseCase,
                                                           NetworkUtil networkUtil,
                                                           DeveloperDatabase developerDatabase){
        return new DeveloperDetailRepository(context, fetchUserDataUseCase, networkUtil, developerDatabase);
    }
}
