package com.example.ogan.listofdevelopersinlagosgithub.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.example.ogan.listofdevelopersinlagosgithub.screens.common.BaseObservable;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Belema Ogan on 12/24/2018.
 */

public class FetchGithubUserListUseCase extends BaseObservable<FetchGithubUserListUseCase.Listener> {

    private final GithubApi mGithubApi;

    public FetchGithubUserListUseCase(GithubApi githubApi) {
        mGithubApi = githubApi;
    }

    public interface Listener {

        void onGithubUserListFetched(String header, ApiResult apiResult);

        void onRequestFailed(Response<ApiResult> response);

        void onNetworkRequestFailed(Throwable t);
    }

    public void loadDataAndNotify(int currentPage){
        mGithubApi.getGithubUser(currentPage).enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(@NonNull Call<ApiResult> call, @NonNull Response<ApiResult> response) {

                if (response.isSuccessful()) {


                    //Getting last page from the header
                    String k = response.headers().get("Link");

                    //getting result and adding it to adapter
                    ApiResult apiResult = response.body();

                    notifySuccess(k, apiResult);

                } else {
                    notifyFailure(response);
                }

            }

            @Override
            public void onFailure(@NonNull Call<ApiResult> call, @NonNull Throwable t) {

                notifyNetworkCallFailed(t);

            }
        });
    }

    private void notifyNetworkCallFailed(Throwable t) {
        for (Listener listener: getListeners()){
            listener.onNetworkRequestFailed(t);
        }
    }

    private void notifyFailure(Response<ApiResult> response) {
        for (Listener listener: getListeners()){
            listener.onRequestFailed(response);
        }
    }

    private void notifySuccess(String header, ApiResult apiResult) {
        for (Listener listener: getListeners()){
            listener.onGithubUserListFetched(header, apiResult);
        }
    }
}
