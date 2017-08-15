package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.ApiResult;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.GetData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://api.github.com/";

    RecyclerView recycler_view;
    ProgressBar progressBar;
    GetData getData;

    LinearLayoutManager linearLayoutManager;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    private int resultSize;
    RecyclerAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Refresh items
                refreshItems();

            }

            void refreshItems() {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                // Load complete
                onItemsLoadComplete();
            }

            void onItemsLoadComplete() {

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recycler_view.setHasFixedSize(true);
        adapter = new RecyclerAdapter(getApplicationContext());

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recycler_view.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).
                addConverterFactory(GsonConverterFactory.create()).build();

        getData = retrofit.create(GetData.class);

        Call<ApiResult> call = getData.getGithubUser(currentPage);
        call.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                System.out.println("error" + response.errorBody());
                System.out.println("code" + response.code());

                progressBar.setVisibility(View.INVISIBLE);

                ApiResult apiResult = response.body();
                if (apiResult != null) {
                    resultSize = apiResult.getTotalCount();
                    TOTAL_PAGES = getTOTAL_PAGES();
                    ArrayList<Item> data = apiResult.getItems();
                    adapter.addAll(data);

                    if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                } else {

                }

            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "An error occurred while trying to get data. Please check network connection and try again. ", Toast.LENGTH_SHORT).show();

                System.out.println("t" + t.getMessage());

            }
        });

        recycler_view.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
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
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


    }

    private void loadNextPage() {

        Call<ApiResult> call = getData.getGithubUser(currentPage);
        call.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                ApiResult apiResult = response.body();

                if (apiResult != null) {
                    ArrayList<Item> data = apiResult.getItems();

                    adapter.addAll(data);

                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                }

                System.out.println("error" + response.errorBody());
                System.out.println("code" + response.code());

            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "An error occurred while trying to get data. Please check network connection and try again. ", Toast.LENGTH_SHORT).show();

                System.out.println("t" + t.getMessage());

            }
        });

    }

    public int getTOTAL_PAGES(){

      int page = Math.round(resultSize / 30);
        return page;

    }


}
