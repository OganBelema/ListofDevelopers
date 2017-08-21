package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.ApiResult;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.GetData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://api.github.com/";
    private static final int PAGE_START = 1;
    RecyclerView recycler_view;
    GetData getData;

    LinearLayoutManager linearLayoutManager;
    RecyclerAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        //setting on refresh listener to reload data when view is pull down
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshItems();

            }

            void refreshItems() {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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

        //performing network call with retrofit
        Call<ApiResult> call = getData.getGithubUser(currentPage);
        call.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {

                progressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {

                    //Getting last page from the header
                    String h = response.headers().get("Link");
                    String[] l = h.split(",");
                    String[] m = l[1].split(";");
                    String[] n = m[0].split("page=");
                    String[] o = n[1].split(">");
                    TOTAL_PAGES = Integer.parseInt(o[0].toString());

                    //getting result and adding it to adapter
                    ApiResult apiResult = response.body();
                    if (apiResult != null) {
                        ArrayList<Item> data = apiResult.getItems();
                        adapter.addAll(data);

                        //implementing the pagination to load more results
                        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                        else isLastPage = true;
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Response message: " + response.message() + "with code: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "An error occurred while trying to get data. Please check network connection and try again. ", Toast.LENGTH_SHORT).show();

                System.out.println("t" + t.getMessage());

            }
        });

        //applying modified onScrollListener to the recyclerView
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

    //to load the next page of results from the API
    private void loadNextPage() {

        Call<ApiResult> call = getData.getGithubUser(currentPage);
        call.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {

                if (response.isSuccessful()) {
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    ApiResult apiResult = response.body();

                    if (apiResult != null) {
                        ArrayList<Item> data = apiResult.getItems();

                        adapter.addAll(data);

                        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                        else isLastPage = true;
                    }
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Response message: " + response.message() + "with code: " + response.code(),
                            Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "An error occurred while trying to get data. Please check network connection and try again. ", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
