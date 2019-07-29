package com.example.ogan.listofdevelopersinlagosgithub.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ogan.listofdevelopersinlagosgithub.model.items.ApiResult;
import com.example.ogan.listofdevelopersinlagosgithub.repository.ListOfDeveloperRepository;
import com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.RecyclerAdapter;

import retrofit2.Response;

public class ListOfDevelopersViewModel extends ViewModel {

    private final RecyclerAdapter mRecyclerAdapter;

    private final ListOfDeveloperRepository mListOfDeveloperRepository;

    public ListOfDevelopersViewModel(ListOfDeveloperRepository listOfDeveloperRepository, RecyclerAdapter recyclerAdapter) {
        mRecyclerAdapter = recyclerAdapter;
        mListOfDeveloperRepository = listOfDeveloperRepository;
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
        return mListOfDeveloperRepository.getErrorLiveData();
    }

    public LiveData<Response<ApiResult>> getResponseLiveData() {
        return mListOfDeveloperRepository.getResponseLiveData();
    }

    public LiveData<ApiResult> getApiResultLiveData() {
        return mListOfDeveloperRepository.getApiResultLiveData();
    }

    public LiveData<Boolean> getDisplayLoadingFooter() {
        return mListOfDeveloperRepository.getDisplayLoadingFooter();
    }

    public LiveData<Boolean> getNoDat(){
        return mListOfDeveloperRepository.getNoData();
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
