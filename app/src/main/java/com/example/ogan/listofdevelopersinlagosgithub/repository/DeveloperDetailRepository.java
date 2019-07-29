package com.example.ogan.listofdevelopersinlagosgithub.repository;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ogan.listofdevelopersinlagosgithub.common.NetworkUtil;
import com.example.ogan.listofdevelopersinlagosgithub.database.DeveloperDatabase;
import com.example.ogan.listofdevelopersinlagosgithub.database.InsertUserService;
import com.example.ogan.listofdevelopersinlagosgithub.model.users.UserApi;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchUserDataUseCase;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeveloperDetailRepository implements FetchUserDataUseCase.Listener {

    private final Context mContext;

    private final FetchUserDataUseCase mFetchUserDataUseCase;

    private final NetworkUtil mNetworkUtil;

    private final DeveloperDatabase mDeveloperDatabase;

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private final MutableLiveData<Boolean> mLoading = new MutableLiveData<>();

    private final MutableLiveData<Boolean> mUserFetchingError = new MutableLiveData<>();

    private MutableLiveData<UserApi> mUserApi = new MutableLiveData<>();

    private final MutableLiveData<Boolean> mRequestFailed = new MutableLiveData<>();

    private final MutableLiveData<Bitmap> mBitmap = new MutableLiveData<>();

    private final MutableLiveData<Drawable> mErrorDrawable = new MutableLiveData<>();

    private final MutableLiveData<Boolean> mNoData = new MutableLiveData<>();

    public DeveloperDetailRepository(Context context, FetchUserDataUseCase fetchUserDataUseCase, NetworkUtil networkUtil, DeveloperDatabase developerDatabase) {
        mContext = context.getApplicationContext();
        mFetchUserDataUseCase = fetchUserDataUseCase;
        mNetworkUtil = networkUtil;
        mDeveloperDatabase = developerDatabase;
        mFetchUserDataUseCase.registerListener(this);
    }

    public LiveData<Boolean> getLoading() {
        return mLoading;
    }

    public LiveData<UserApi> getUserApi() {
        return mUserApi;
    }

    public LiveData<Boolean> getUserFetchingError() {
        return mUserFetchingError;
    }

    public LiveData<Boolean> getRequestFailed() {
        return mRequestFailed;
    }

    public LiveData<Bitmap> getBitmap(){
        return mBitmap;
    }

    public LiveData<Drawable> getErrorDrawable(){
        return mErrorDrawable;
    }

    public LiveData<Boolean> getNoData(){
        return mNoData;
    }

    public void loadData(String username, String avatar) {

        if (mNetworkUtil.isConnected()){
            //making network call with retrofit
            mFetchUserDataUseCase.fetchUserDataAndNotify(username);

            //to load image into imageView
            mFetchUserDataUseCase.loadImageAndNotify(avatar);
        } else {
            mDeveloperDatabase.getUserDao().getUser(username)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new SingleObserver<UserApi>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mCompositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(UserApi userApi) {
                            mLoading.postValue(false);
                            if (userApi != null){
                                mUserApi.postValue(userApi);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mLoading.postValue(false);
                            mNoData.postValue(true);
                        }
                    });
        }
    }

    @Override
    public void onUserFetched(UserApi userApi) {
        mLoading.postValue(false);
        mUserApi.postValue(userApi);
        cacheData(userApi);
    }

    @Override
    public void onUserFetchFailed() {
        mLoading.postValue(false);
        mUserFetchingError.postValue(true);
    }

    @Override
    public void onRequestFailed() {
        mLoading.postValue(false);
        mRequestFailed.postValue(true);
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        mBitmap.postValue(bitmap);
    }

    @Override
    public void onImageLoadingFail(Drawable errorDrawable) {
        mErrorDrawable.postValue(errorDrawable);
    }

    private void cacheData(final UserApi userApi) {
        Intent startInsertResultService = new Intent(mContext, InsertUserService.class);
        startInsertResultService.putExtra(InsertUserService.USER_KEY, userApi);
        mContext.startService(startInsertResultService);
    }

    public void clear(){
        mFetchUserDataUseCase.unregisterListener(this);
        mCompositeDisposable.dispose();
    }
}
