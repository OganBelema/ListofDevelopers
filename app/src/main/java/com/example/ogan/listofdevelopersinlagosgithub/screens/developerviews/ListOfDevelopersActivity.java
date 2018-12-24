package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.ApiResult;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.GithubApi;
import com.example.ogan.listofdevelopersinlagosgithub.R;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.BaseActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfDevelopersActivity extends BaseActivity implements ListOfDevelopersViewMvc.Listener {

    private static final int PAGE_START = 1;
    private GithubApi mGithubApi;


    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;

    private ListOfDevelopersViewMvc mListOfDevelopersViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListOfDevelopersViewMvc = getCompositionRoot().getViewMvcFactory().getListOfDevelopersViewMvc(null);
        mListOfDevelopersViewMvc.registerListener(this);
        setContentView(mListOfDevelopersViewMvc.getRootView());

        mGithubApi = getCompositionRoot().getGithubApi();

        loadData();
    }

    private void loadData() {

        //performing network call with retrofit
        mGithubApi.getGithubUser(currentPage).enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(@NonNull Call<ApiResult> call, @NonNull Response<ApiResult> response) {

                mListOfDevelopersViewMvc.hideProgressBar();

                if (response.isSuccessful()) {


                    //Getting last page from the header
                    String k = response.headers().get("Link");
                    String[] l = k != null ? k.split(",") : new String[0];
                    String[] m = l[1].split(";");
                    String[] n = m[0].split("page=");
                    String[] o = n[1].split(">");
                    TOTAL_PAGES = Integer.parseInt(o[0]);

                    //getting result and adding it to adapter
                    ApiResult apiResult = response.body();
                    if (apiResult != null) {
                        ArrayList<Item> data = apiResult.getItems();
                        mListOfDevelopersViewMvc.bindData(data);


                        //implementing the pagination to load more results
                        if (currentPage <= TOTAL_PAGES) mListOfDevelopersViewMvc.showLoadingFooter();
                        else isLastPage = true;
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Response message: " + response.message() + " with code: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(@NonNull Call<ApiResult> call, @NonNull Throwable t) {

                mListOfDevelopersViewMvc.hideProgressBar();
                Toast.makeText(getApplicationContext(), "An error occurred while trying to get data. Please check network connection and try again. ", Toast.LENGTH_SHORT).show();

                System.out.println("t" + t.getMessage());

            }
        });
    }

    //to load the next page of results from the API
    private void loadNextPage() {

        mGithubApi.getGithubUser(currentPage).enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(@NonNull Call<ApiResult> call, @NonNull Response<ApiResult> response) {

                if (response.isSuccessful()) {
                    mListOfDevelopersViewMvc.removeLoadingFooter();
                    isLoading = false;

                    ApiResult apiResult = response.body();

                    if (apiResult != null) {
                        ArrayList<Item> data = apiResult.getItems();

                        mListOfDevelopersViewMvc.bindData(data);

                        if (currentPage != TOTAL_PAGES) mListOfDevelopersViewMvc.showLoadingFooter();
                        else isLastPage = true;
                    }
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Response message: " + response.message() + " with code: " + response.code(),
                            Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<ApiResult> call, @NonNull Throwable t) {

                Toast.makeText(getApplicationContext(), "An error occurred while trying to get data. Please check network connection and try again. ", Toast.LENGTH_SHORT).show();

            }
        });

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
}
