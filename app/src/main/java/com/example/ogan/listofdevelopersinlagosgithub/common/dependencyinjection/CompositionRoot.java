package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.GithubApi;
import com.example.ogan.listofdevelopersinlagosgithub.common.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class CompositionRoot {

    private Retrofit mRetrofit;

    public GithubApi getGithubApi() {
        return getRetrofit().create(GithubApi.class);
    }

    private Retrofit getRetrofit(){
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit;
    }
}
