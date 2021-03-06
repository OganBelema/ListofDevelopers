package com.oganbelema.listofdevelopersinlagosgithub.network;


import com.oganbelema.listofdevelopersinlagosgithub.model.items.ApiResult;
import com.oganbelema.listofdevelopersinlagosgithub.model.users.UserApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ogan on 8/10/17.
 */

public interface GithubApi {

    @GET("search/users?q=language:java+location:lagos")
    Call<ApiResult> getGithubUser(@Query("page") int page);

    @GET("users/{user}")
    Call<UserApi> getUserData(@Path("user") String user);
}
