package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ogan.listofdevelopersinlagosgithub.network.items.ApiResult;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchGithubUserListUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.network.items.Item;
import com.example.ogan.listofdevelopersinlagosgithub.R;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.controllers.BaseActivity;

import java.util.ArrayList;

import retrofit2.Response;

public class ListOfDevelopersActivity extends BaseActivity implements ListOfDevelopersViewMvc.Listener,
        FetchGithubUserListUseCase.Listener {

    private static final int PAGE_START = 1;
    private FetchGithubUserListUseCase mFetchGithubUserListUseCase;

    private boolean mFirstCall = true;


    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;

    private ListOfDevelopersViewMvc mListOfDevelopersViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListOfDevelopersViewMvc = getPresentationComponent().getViewMvcFactory().getListOfDevelopersViewMvc(null);
        mListOfDevelopersViewMvc.registerListener(this);
        mFetchGithubUserListUseCase = getPresentationComponent().getFetchGithubUserListUseCase();
        mFetchGithubUserListUseCase.registerListener(this);

        loadData();
        setContentView(mListOfDevelopersViewMvc.getRootView());
    }

    private void loadData() {

        //performing network call with retrofit
        mFetchGithubUserListUseCase.loadDataAndNotify(currentPage);

    }

    //to load the next page of results from the API
    private void loadNextPage() {
        mFirstCall = false;
        mFetchGithubUserListUseCase.loadDataAndNotify(currentPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.refresh_menu) {
            Intent intent = new Intent(getApplicationContext(), ListOfDevelopersActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshItems() {
        Intent intent = new Intent(getApplicationContext(), ListOfDevelopersActivity.class);
        startActivity(intent);
    }


    @Override
    public void loadMoreItems() {
        isLoading = true;
        currentPage += 1;

        // mocking network delay for API call
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNextPage();
            }
        }, 1000);
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public void onRefresh() {
        refreshItems();
        mListOfDevelopersViewMvc.stopRefreshing();
    }

    @Override
    public int getTotalPageCount() {
        return TOTAL_PAGES;
    }

    @Override
    public boolean isLastPage() {
        return isLastPage;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListOfDevelopersViewMvc.unregisterListener(this);
    }

    @Override
    public void onGithubUserListFetched(String header, ApiResult apiResult) {
        mListOfDevelopersViewMvc.hideProgressBar();

        if (mFirstCall){
            String[] l = header != null ? header.split(",") : new String[0];
            String[] m = l[1].split(";");
            String[] n = m[0].split("page=");
            String[] o = n[1].split(">");
            TOTAL_PAGES = Integer.parseInt(o[0]);

            if (apiResult != null) {
                ArrayList<Item> data = apiResult.getItems();
                mListOfDevelopersViewMvc.bindData(data);

                //implementing the pagination to load more results
                if (currentPage <= TOTAL_PAGES) mListOfDevelopersViewMvc.showLoadingFooter();
                else isLastPage = true;
            }
        } else {
            mListOfDevelopersViewMvc.removeLoadingFooter();
            isLoading = false;

            if (apiResult != null) {
                ArrayList<Item> data = apiResult.getItems();

                mListOfDevelopersViewMvc.bindData(data);

                if (currentPage != TOTAL_PAGES) mListOfDevelopersViewMvc.showLoadingFooter();
                else isLastPage = true;
            }
        }
    }

    @Override
    public void onRequestFailed(Response<ApiResult> response) {
        Toast.makeText(getApplicationContext(),
                "Response message: " + response.message() + " with code: " + response.code(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkRequestFailed(Throwable t) {
        mListOfDevelopersViewMvc.hideProgressBar();
        Toast.makeText(getApplicationContext(),
                "An error occurred while trying to get data. Please check network connection and try again. ",
                Toast.LENGTH_SHORT).show();

        System.out.println("t" + t.getMessage());
    }
}
