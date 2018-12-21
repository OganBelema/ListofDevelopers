package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belema Ogan on 12/21/2018.
 */

public class ListOfDevelopersViewMvcImpl implements SwipeRefreshLayout.OnRefreshListener, ListOfDevelopersViewMvc {

    private final List<Listener> mListeners = new ArrayList<>();

    private final View mRootView;
    private final RecyclerView mRecyclerView;
    private final ProgressBar mProgressBar;
    private final SwipeRefreshLayout mSwipeRefreshLayout;
    private final RecyclerAdapter mRecyclerAdapter;
    private final PaginationScrollListener mPaginationScrollListener;

    public ListOfDevelopersViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {

        mRootView = inflater.inflate(R.layout.list_of_developers_activity, parent, false);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.loading_spinner);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipeRefresh);

        mRecyclerAdapter = new RecyclerAdapter(getContext());

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
                for (Listener listener: mListeners){
                    listener.loadMoreItems();
                }
            }

            @Override
            public int getTotalPageCount() {
                int totalPageCount = 0;

                for (Listener listener : mListeners){
                   totalPageCount  = listener.getTotalPageCount();
                }

                return totalPageCount;
            }

            @Override
            public boolean isLastPage() {
                boolean isLastPage = false;

                for (Listener listener : mListeners){
                    isLastPage = listener.isLastPage();
                }

                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                 boolean isLoading = false;

                 for (Listener listener: mListeners){
                     isLoading = listener.isLoading();
                 }

                return isLoading;
            }
        };

        mRecyclerView.addOnScrollListener(mPaginationScrollListener);


    }

    @Override
    public void registerListener(Listener listener){
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(Listener listener){
        mListeners.remove(listener);
    }

    private Context getContext() {
        return mRootView.getContext();
    }

    @Override
    public View getRootView(){
        return mRootView;
    }

    @Override
    public void onRefresh() {
        for (Listener listener: mListeners){
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
