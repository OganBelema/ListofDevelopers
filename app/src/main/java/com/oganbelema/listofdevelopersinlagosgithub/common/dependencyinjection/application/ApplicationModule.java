package com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.application;

import android.content.Context;


import com.oganbelema.listofdevelopersinlagosgithub.common.Constants;
import com.oganbelema.listofdevelopersinlagosgithub.common.NetworkUtil;
import com.oganbelema.listofdevelopersinlagosgithub.database.DeveloperDatabase;
import com.oganbelema.listofdevelopersinlagosgithub.network.FetchGithubUserListUseCase;
import com.oganbelema.listofdevelopersinlagosgithub.network.FetchUserDataUseCase;
import com.oganbelema.listofdevelopersinlagosgithub.network.GithubApi;
import com.oganbelema.listofdevelopersinlagosgithub.repository.DeveloperDetailRepository;
import com.oganbelema.listofdevelopersinlagosgithub.repository.ListOfDeveloperRepository;

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
