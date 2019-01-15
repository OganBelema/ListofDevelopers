package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ogan.listofdevelopersinlagosgithub.network.items.Item;
import com.example.ogan.listofdevelopersinlagosgithub.R;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.views.BaseObservableViewMvc;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.views.ViewMvcFactory;

import java.util.ArrayList;

/**
 * Created by Belema Ogan on 12/21/2018.
 */

public class ListOfDevelopersViewMvcImpl extends BaseObservableViewMvc<ListOfDevelopersViewMvc.Listener>
        implements SwipeRefreshLayout.OnRefreshListener, ListOfDevelopersViewMvc {

    private final RecyclerView mRecyclerView;
    private final ProgressBar mProgressBar;
    private final SwipeRefreshLayout mSwipeRefreshLayout;
    private final RecyclerAdapter mRecyclerAdapter;
    private final PaginationScrollListener mPaginationScrollListener;

    public ListOfDevelopersViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {

        setRootView(inflater.inflate(R.layout.list_of_developers_activity, parent, false));
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        mRecyclerAdapter = new RecyclerAdapter(getContext(), viewMvcFactory);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mPaginationScrollListener = new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                for (Listener listener: getListeners()){
                    listener.loadMoreItems();
                }
            }

            @Override
            public int getTotalPageCount() {
                int totalPageCount = 0;

                for (Listener listener : getListeners()){
                   totalPageCount  = listener.getTotalPageCount();
                }

                return totalPageCount;
            }

            @Override
            public boolean isLastPage() {
                boolean isLastPage = false;

                for (Listener listener : getListeners()){
                    isLastPage = listener.isLastPage();
                }

                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                 boolean isLoading = false;

                 for (Listener listener: getListeners()){
                     isLoading = listener.isLoading();
                 }

                return isLoading;
            }
        };

        mRecyclerView.addOnScrollListener(mPaginationScrollListener);


    }

    @Override
    public void onRefresh() {
        for (Listener listener: getListeners()){
            listener.onRefresh();
        }
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void bindData(ArrayList<Item> data) {
        mRecyclerAdapter.addAll(data);
    }

    @Override
    public void showLoadingFooter() {
        mRecyclerAdapter.addLoadingFooter();
    }

    @Override
    public void removeLoadingFooter() {
        mRecyclerAdapter.removeLoadingFooter();
    }
}
