package com.example.ogan.listofdevelopersinlagosgithub.APIgson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ogan on 8/10/17.
 */

public interface GetData {

    @GET("search/users?q=language:java+location:lagos")
    Call<ApiResult> getGithubUser(@Query("page") int page);
}