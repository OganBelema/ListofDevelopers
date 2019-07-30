package com.oganbelema.listofdevelopersinlagosgithub.viewmodel;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.oganbelema.listofdevelopersinlagosgithub.model.users.UserApi;
import com.oganbelema.listofdevelopersinlagosgithub.repository.DeveloperDetailRepository;


public class DeveloperDetailViewModel extends ViewModel {

    private final DeveloperDetailRepository mDeveloperDetailRepository;

    DeveloperDetailViewModel(DeveloperDetailRepository mDeveloperDetailRepository) {
        this.mDeveloperDetailRepository = mDeveloperDetailRepository;
    }

    public LiveData<Boolean> loading(){
        return mDeveloperDetailRepository.getLoading();
    }

    public LiveData<UserApi> getUserApi(){
        return mDeveloperDetailRepository.getUserApi();
    }

    public LiveData<Boolean> getUserFetchingError(){
        return mDeveloperDetailRepository.getUserFetchingError();
    }

    public LiveData<Boolean> getRequestFailed(){
        return mDeveloperDetailRepository.getRequestFailed();
    }

    public LiveData<Bitmap> getBitmap(){
        return mDeveloperDetailRepository.getBitmap();
    }

    public LiveData<Drawable> getErrorDrawable(){
        return mDeveloperDetailRepository.getErrorDrawable();
    }

    public LiveData<Boolean> getNoData(){
        return mDeveloperDetailRepository.getNoData();
    }

    public void loadData(String username, String avatar){
        mDeveloperDetailRepository.loadData(username, avatar);
    }

    @Override
    protected void onCleared() {
        mDeveloperDetailRepository.clear();
        super.onCleared();
    }
}
