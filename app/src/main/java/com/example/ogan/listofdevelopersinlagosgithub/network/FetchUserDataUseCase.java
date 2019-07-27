package com.example.ogan.listofdevelopersinlagosgithub.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.example.ogan.listofdevelopersinlagosgithub.model.users.UserApi;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.BaseObservable;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Belema Ogan on 12/24/2018.
 */

public class FetchUserDataUseCase extends BaseObservable<FetchUserDataUseCase.Listener> {

    private final GithubApi mGithubApi;

    public FetchUserDataUseCase(GithubApi githubApi) {
        mGithubApi = githubApi;
    }

    public interface Listener {

        void onUserFetched(UserApi userApi);

        void onUserFetchFailed();

        void onRequestFailed();

        void onImageLoaded(Bitmap bitmap);

        void onImageLoadingFail(Drawable errorDrawable);
    }

    public void fetchUserDataAndNotify(String username) {
        mGithubApi.getUserData(username).enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(@NonNull Call<UserApi> call, @NonNull Response<UserApi> response) {

                if (response.isSuccessful()) {
                    UserApi userResult = response.body();
                    notifySuccess(userResult);
                } else {
                    notifyRequestFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserApi> call, @NonNull Throwable t) {
                notifyFailure();
            }
        });
    }

    public void loadImageAndNotify(Context context, String imageUrl){
        Picasso.with(context).load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                notifyImageDownloadSuccess(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                notifyImageDownloadFailure(errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private void notifyImageDownloadFailure(Drawable errorDrawable) {
        for (Listener listener: getListeners()){
            listener.onImageLoadingFail(errorDrawable);
        }
    }

    private void notifyImageDownloadSuccess(Bitmap bitmap) {
        for (Listener listener: getListeners()){
            listener.onImageLoaded(bitmap);
        }
    }

    private void notifyRequestFailure() {
        for (Listener listener: getListeners()){
            listener.onRequestFailed();
        }
    }

    public void notifyFailure(){
        for (Listener listener : getListeners()){
            listener.onUserFetchFailed();
        }
    }

    public void notifySuccess(UserApi userResult) {
        if (userResult != null) {
            for (Listener listener: getListeners()){
                listener.onUserFetched(userResult);
            }
        }
    }
}
