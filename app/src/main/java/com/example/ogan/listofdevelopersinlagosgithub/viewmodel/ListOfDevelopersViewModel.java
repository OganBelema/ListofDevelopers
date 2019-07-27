package com.example.ogan.listofdevelopersinlagosgithub.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.ogan.listofdevelopersinlagosgithub.common.CustomApplication;
import com.example.ogan.listofdevelopersinlagosgithub.model.items.ApiResult;
import com.example.ogan.listofdevelopersinlagosgithub.repository.ListOfDeveloperRepository;
import com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.RecyclerAdapter;

import retrofit2.Response;

public class ListOfDevelopersViewModel extends ViewModel {

    private final RecyclerAdapter mRecyclerAdapter;

    private final ListOfDeveloperRepository mListOfDeveloperRepository;

    private final  MutableLiveData<Throwable> mErrorLiveData;

    private final MutableLiveData<Response<ApiResult>> mResponseLiveData;

    private final MutableLiveData<ApiResult> mApiResultLiveData;

    private final MutableLiveData<Boolean> mDisplayLoadingFooter;

    public ListOfDevelopersViewModel(Context context, RecyclerAdapter recyclerAdapter) {
        mRecyclerAdapter = recyclerAdapter;
        mListOfDeveloperRepository = ((CustomApplication) context.getApplicationContext()).getApplicationComponent().getListOfDeveloperRepository();
        mResponseLiveData = mListOfDeveloperRepository.mResponseLiveData;
        mErrorLiveData = mListOfDeveloperRepository.mErrorLiveData;
        mApiResultLiveData = mListOfDeveloperRepository.mApiResultLiveData;
        mDisplayLoadingFooter = mListOfDeveloperRepository.mDisplayLoadingFooter;
        mListOfDeveloperRepository.loadData();
    }

    public RecyclerAdapter getRecyclerAdapter() {
        return mRecyclerAdapter;
    }

    public boolean getIsLoading() {
        return mListOfDeveloperRepository.getIsLoading();
    }

    public int getTotalPages(){
        return mListOfDeveloperRepository.getTotalPages();
    }

    public boolean getIsLastPages(){
        return mListOfDeveloperRepository.getIsLastPage();
    }

    public LiveData<Throwable> getErrorLiveData() {
        return mErrorLiveData;
    }

    public LiveData<Response<ApiResult>> getResponseLiveData() {
        return mResponseLiveData;
    }

    public LiveData<ApiResult> getApiResultLiveData() {
        return mApiResultLiveData;
    }

    public LiveData<Boolean> getDisplayLoadingFooter() {
        return mDisplayLoadingFooter;
    }

    @Override
    protected void onCleared() {
        mListOfDeveloperRepository.clear();
        super.onCleared();
    }

    public void loadMoreItems() {
        mListOfDeveloperRepository.loadMoreItems();
    }

    public void refresh(){
        mListOfDeveloperRepository.refresh();
    }
}
