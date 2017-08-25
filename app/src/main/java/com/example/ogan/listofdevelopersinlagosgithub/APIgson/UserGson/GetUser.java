package com.example.ogan.listofdevelopersinlagosgithub.APIgson.UserGson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ogan on 8/16/17.
 */

public interface GetUser {
    @GET("users/{user}?access_token=387fe9e662044fbd38cfda0bac545e3867e729a1")
    Call<UserApi> getUserData(@Path("user") String user);
}
