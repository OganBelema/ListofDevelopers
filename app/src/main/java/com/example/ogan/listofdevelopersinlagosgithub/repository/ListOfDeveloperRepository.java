package com.example.ogan.listofdevelopersinlagosgithub.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;

import com.example.ogan.listofdevelopersinlagosgithub.common.NetworkUtil;
import com.example.ogan.listofdevelopersinlagosgithub.database.DeveloperDatabase;
import com.example.ogan.listofdevelopersinlagosgithub.database.InsertResultService;
import com.example.ogan.listofdevelopersinlagosgithub.model.items.ApiResult;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchGithubUserListUseCase;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ListOfDeveloperRepository implements FetchGithubUserListUseCase.Listener{

    private static final int PAGE_START = 1;

    private boolean mFirstCall = true;

    private int totalPages = 1;

    private int currentPage = PAGE_START;

    private boolean isLoading = false;

    private boolean isLastPage = false;

    private final Context mContext;
    private final NetworkUtil mNetworkUtil;
    private final DeveloperDatabase mDeveloperDatabase;
    private final FetchGithubUserListUseCase mFetchGithubUserListUseCase;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    public MutableLiveData<Throwable> mErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<Response<ApiResult>> mResponseLiveData = new MutableLiveData<>();
    public MutableLiveData<ApiResult> mApiResultLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> mDisplayLoadingFooter = new MutableLiveData<>();

    public ListOfDeveloperRepository(Context context, FetchGithubUserListUseCase fetchGithubUserListUseCase,
                                     NetworkUtil networkUtil, DeveloperDatabase developerDatabase) {
        mContext = context.getApplicationContext();
        mNetworkUtil = networkUtil;
        mDeveloperDatabase = developerDatabase;
        mFetchGithubUserListUseCase = fetchGithubUserListUseCase;
        mFetchGithubUserListUseCase.registerListener(this);
    }

    public boolean getIsLoading() {
        return isLoading;
    }

    public int getTotalPages(){
        return totalPages;
    }

    public boolean getIsLastPage(){
        return isLastPage;
    }

    @Override
    public void onGithubUserListFetched(String header, ApiResult apiResult) {

        cacheData(apiResult);

        if (mFirstCall) {
            String[] l = header != null ? header.split(",") : new String[0];
            String[] m = l[1].split(";");
            String[] n = m[0].split("page=");
            String[] o = n[1].split(">");
            totalPages = Integer.parseInt(o[0]);

            if (apiResult != null) {
                mApiResultLiveData.postValue(apiResult);

                //implementing the pagination to load more results
                if (currentPage <= totalPages) {
                    mDisplayLoadingFooter.postValue(true);
                } else {
                    isLastPage = true;
                }
            }
        } else {
            mDisplayLoadingFooter.postValue(false);
            isLoading =false;

            if (apiResult != null) {
                mApiResultLiveData.postValue(apiResult);

                if (currentPage != totalPages) {
                    mDisplayLoadingFooter.postValue(true);
                } else {
                    isLastPage = true;
                }
            }
        }
    }

    public void loadData() {

        if (mNetworkUtil.isConnected()){
            //performing network call with retrofit
            mFetchGithubUserListUseCase.loadDataAndNotify(currentPage);
        } else {
            mCompositeDisposable.add(
                    mDeveloperDatabase.getResultDao().getResult()
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Consumer<ApiResult>() {
                                @Override
                                public void accept(ApiResult result) {
                                    mApiResultLiveData.postValue(result);
                                }
                            })
            );
        }

    }

    //to load the next page of results from the API
    private void loadNextPage() {
        mFirstCall = false;
        mFetchGithubUserListUseCase.loadDataAndNotify(currentPage);
    }

    public void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        loadNextPage();
    }

    @Override
    public void onRequestFailed(Response<ApiResult> response) {
        mResponseLiveData.postValue(response);
    }

    @Override
    public void onNetworkRequestFailed(Throwable t) {
        mErrorLiveData.postValue(t);
    }

    private void cacheData(ApiResult apiResult) {
        Intent startInsertResultService = new Intent(mContext, InsertResultService.class);
        startInsertResultService.putExtra(InsertResultService.RESULT_KEY, apiResult);
        mContext.startService(startInsertResultService);
    }

    public void refresh(){
        totalPages = 1;
        currentPage = PAGE_START;
        mFirstCall = true;
        isLoading = false;
        isLastPage = false;
        loadData();
    }

    public void clear(){
        mFetchGithubUserListUseCase.unregisterListener(this);
        mCompositeDisposable.dispose();
    }

}
